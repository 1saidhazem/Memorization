package com.example.memorization.Activities;

import static com.example.memorization.Constant.COLLECTION_CENTER;
import static com.example.memorization.Constant.COLLECTION_GROUP;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.memorization.Adapters.GroupMemorizationAdapter;
import com.example.memorization.Adapters.onClickAddNewGroup;
import com.example.memorization.Adapters.onClickListWallet;
import com.example.memorization.Adapters.onClickListenerCenterNameGroup;
import com.example.memorization.RoomDatabase.Center;
import com.example.memorization.RoomDatabase.CenterGroup;
import com.example.memorization.RoomDatabase.Group;
import com.example.memorization.RoomDatabase.MyViewModel;
import com.example.memorization.RoomDatabase.countGroupListener;
import com.example.memorization.databinding.ActivityGroupMemorizationBinding;
import com.example.memorization.onClick;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class GroupMemorizationActivity extends AppCompatActivity {

    ActivityGroupMemorizationBinding binding;
    private int idCenter;
    private String CenterName;
    private GroupMemorizationAdapter adapter;
    private MyViewModel mvm;
    public static final String GROUP_KEY = "group_key";
    SharedPreferences sp1, sp2;
    SharedPreferences.Editor edit1,edit2;
    FirebaseFirestore fireStore;
    private CenterGroup info;
    int idGroup, validity;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGroupMemorizationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sp1 = getSharedPreferences("centerId", MODE_PRIVATE);
        edit1 = sp1.edit();

        fireStore = FirebaseFirestore.getInstance();

        mvm = new ViewModelProvider(this).get(MyViewModel.class);
        sp2 = getSharedPreferences("dataUserLogin", MODE_PRIVATE);
        edit2 = sp2.edit();

        id = sp2.getLong("loginId", -1);
        validity = sp2.getInt("validity", -1);

        idCenter = getIntent().getIntExtra("idCenter", 0);
        CenterName = getIntent().getStringExtra("centerName");

        adapter = new GroupMemorizationAdapter(new ArrayList<>(), new onClickAddNewGroup() {
            @Override
            public void onClickedAddNewGroup(Group group, int countItem) {
                if (validity == 0) {
                    Intent intent = new Intent(getBaseContext(), AddNewGroupActivity.class);
                    intent.putExtra("group_id", group.getId());
                    edit1.putInt("idGroup", group.getId());
                    idGroup = group.getId();
                    edit1.commit();
                    intent.putExtra("groupName", group.getName());
                    intent.putExtra("numberOfStudentsAllowed", group.getNumberOfStudentsAllowedInGroup());
                    intent.putExtra("countItem", adapter.getItemCount());
                    intent.putExtra("groupDescription", group.getDescription());
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "ليس لديه الصلاحية للوصول الى بيانات الحلقة", Toast.LENGTH_SHORT).show();
                }
            }
        },
                // click List Wallet
                new onClickListWallet() {
                    @Override
                    public void onClicked(int position) {
                        if (validity == 0 || validity == 1 || validity == 2) {
                            Intent intent = new Intent(getBaseContext(), WalletsActivity.class);
                            intent.putExtra("idGroup", position);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "ليس لديك الصلاحية لمشاهدة قائمة المحفظين", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                // click List Student
                new onClick<Integer>() {
                    @Override
                    public void onClickItem(Integer integer) {
                        if (validity == 0 || validity == 1 || validity == 2) {
                            Intent intent = new Intent(getBaseContext(), StudentActivity.class);
                            intent.putExtra("idGroup", integer);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "ليس لديك الصلاحية لمشاهدة قائمة الطلاب", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        mvm.getGroupById(idCenter).observe(GroupMemorizationActivity.this, new Observer<List<Group>>() {
            @Override
            public void onChanged(List<Group> groups) {
                adapter.setGroups(groups);
            }
        });

        edit2.putInt("countItem", adapter.getItemCount());
        binding.groupMemorizationRv.setAdapter(adapter);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(GroupMemorizationActivity.this, RecyclerView.VERTICAL, false);
        binding.groupMemorizationRv.setLayoutManager(lm);
        binding.groupMemorizationRv.setHasFixedSize(true);


        if (validity != 0) {
            binding.groupMemorizationFab.setVisibility(View.GONE);
        } else {
            binding.groupMemorizationFab.setVisibility(View.VISIBLE);
            binding.groupMemorizationFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getBaseContext(), AddNewGroupActivity.class);
                    intent.putExtra("idCenter", idCenter);
                    intent.putExtra("CenterName", CenterName);
                    startActivity(intent);
                }
            });
        }

    }
}