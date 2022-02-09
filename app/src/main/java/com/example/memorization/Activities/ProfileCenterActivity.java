package com.example.memorization.Activities;

import static com.example.memorization.Constant.ADVERTISING_BODY;
import static com.example.memorization.Constant.CENTER_ID;
import static com.example.memorization.Constant.COLLECTION_ADVERTISING;
import static com.example.memorization.Constant.DATE;
import static com.example.memorization.Constant.SENDER_ADVERTISING;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.memorization.Adapters.AdvertisingAdapter;
import com.example.memorization.RoomDatabase.Advertising;
import com.example.memorization.databinding.ActivityProfileCenterBinding;
import com.example.memorization.onClick;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ProfileCenterActivity extends AppCompatActivity {

    ActivityProfileCenterBinding binding;
    private int CenterId = -1;
    private String centerName = "";
    private byte[] imgCenter;
    private int NumberOfGroupsAllowed,validity;
    private SharedPreferences sp1,sp2;
    private SharedPreferences.Editor edit;
    private FirebaseFirestore fireStore;
    private AdvertisingAdapter adapter;
    private String body ="",sender="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileCenterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fireStore = FirebaseFirestore.getInstance();
        sp1 = getSharedPreferences("centerId",MODE_PRIVATE);
        sp2 = getSharedPreferences("dataUserLogin", MODE_PRIVATE);
        edit = sp1.edit();

        binding.progressBar.setVisibility(View.VISIBLE);
        CenterId = getIntent().getIntExtra("center_id", -1);
        edit.putInt("CenterId",CenterId);
        edit.commit();
//        imgCenter = getIntent().getByteArrayExtra("imgCenter");
        centerName = getIntent().getStringExtra("centerName");
        NumberOfGroupsAllowed = getIntent().getIntExtra("NumberOfGroupsAllowed", 00);
        binding.profileCenterTvName.setText(centerName);

        validity = sp2.getInt("validity", -1);
        if (validity != 0 && validity != 1){
            binding.profileCenterFabAddNewAdvertising.setVisibility(View.GONE);
        }else {
            binding.profileCenterFabAddNewAdvertising.setVisibility(View.VISIBLE);
            binding.profileCenterFabAddNewAdvertising.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getBaseContext(),AddNewAdvertisingActivity.class));
                }
            });
        }

        getAdvertising();
        binding.profileCenterImgSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), AddNewCenterActivity.class);
                intent.putExtra("CenterId", CenterId);
                intent.putExtra("centerName", centerName);
                intent.putExtra("NumberOfGroupsAllowed", NumberOfGroupsAllowed);
                startActivity(intent);
            }
        });
    }

    private void getAdvertising() {
        fireStore.collection(COLLECTION_ADVERTISING).orderBy(DATE, Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null) {
                    Log.d("ttt", "getMessages ::" + error.getMessage());
                    return;
                }
                List<Advertising> AdvertisingList = new ArrayList<>();

                for (QueryDocumentSnapshot doc : value) {
                    if (doc.get(ADVERTISING_BODY) != null && doc.getLong(CENTER_ID).intValue() == CenterId ) {
                        Advertising advertising = new Advertising();
                        body = doc.getString(ADVERTISING_BODY);
                        sender =doc.getString(SENDER_ADVERTISING);
                        advertising.setAdvertisingBody(body);
                        advertising.setDate(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
                        advertising.setSender(sender);
                        advertising.setIdCenter(doc.getLong(CENTER_ID).intValue());
                        AdvertisingList.add(advertising);
                    }
                }

                adapter = new AdvertisingAdapter(AdvertisingList, new onClick() {
                    @Override
                    public void onClickItem(Object o) {
                        Intent intent = new Intent(getBaseContext(),AddNewAdvertisingActivity.class);
                        intent.putExtra("idAdvertising",Integer.parseInt(o.toString()));
                        startActivity(intent);
                    }
                });

                binding.profileAdvertisingRv.smoothScrollToPosition(AdvertisingList.size()-1);
                binding.progressBar.setVisibility(View.GONE);
                binding.profileAdvertisingRv.setAdapter(adapter);
                RecyclerView.LayoutManager lm = new LinearLayoutManager(ProfileCenterActivity.this, RecyclerView.VERTICAL, false);
                binding.profileAdvertisingRv.setLayoutManager(lm);
                binding.profileAdvertisingRv.setHasFixedSize(true);

            }
        });
    }
}