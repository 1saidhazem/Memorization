package com.example.memorization.Activities;

import static com.example.memorization.Constant.COLLECTION_ADVERTISING;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.memorization.RoomDatabase.Advertising;
import com.example.memorization.RoomDatabase.MyViewModel;
import com.example.memorization.databinding.ActivityAddNewAdvertisingBinding;
import com.example.memorization.onClick;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AddNewAdvertisingActivity extends AppCompatActivity {

    ActivityAddNewAdvertisingBinding binding;
    private FirebaseFirestore fireStore;
    private SharedPreferences sp1,sp2;
    private int CenterId,idAdvertising;
    private ArrayList<String > body;
    private MyViewModel mvm;
    private long idUser;
    String senderName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNewAdvertisingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fireStore = FirebaseFirestore.getInstance();
        mvm = new ViewModelProvider(this).get(MyViewModel.class);
        sp1 = getSharedPreferences("centerId",MODE_PRIVATE);
        sp2 = getSharedPreferences("dataUserLogin", MODE_PRIVATE);
        idUser = sp2.getLong("loginId", -1);
        mvm.getSenderName(idUser, new onClick<String>() {
            @Override
            public void onClickItem(String s) {
                senderName = s;
            }
        });

        CenterId = sp1.getInt("CenterId",-1);
        idAdvertising = getIntent().getIntExtra("idAdvertising",-1);

        if (idAdvertising == -1){
            // عملية اضافة
            binding.addNewAdvertisingBtnPublishing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendAdvertising();
                    finish();
                }
            });
        }else {
            // عملية عرض
//            binding.addNewAdvertisingEtMessageAdvertising.setText(body.);
        }



    }

    private void sendAdvertising() {

        if (!isValid()) return;
        String messageAdvertising = binding.addNewAdvertisingEtMessageAdvertising.getText().toString();

        Advertising advertising = new Advertising();
        advertising.setAdvertisingBody(messageAdvertising);
        advertising.setDate(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
        advertising.setSender(senderName);
        advertising.setIdCenter(CenterId);

        fireStore.collection(COLLECTION_ADVERTISING).add(advertising).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("ttt", "onSuccess done...");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("ttt", "onFailure done...");
            }
        });
    }

    private boolean isValid() {
        if (binding.addNewAdvertisingEtMessageAdvertising.getText().toString().isEmpty()) {
            return false;
        }
        return true;
    }
}