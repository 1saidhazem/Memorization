package com.example.memorization.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.memorization.Adapters.StudentAdapter;
import com.example.memorization.Adapters.onClickAddNewWallet;
import com.example.memorization.Adapters.onClickStudent;
import com.example.memorization.RoomDatabase.MyViewModel;
import com.example.memorization.RoomDatabase.StudentGroup;
import com.example.memorization.RoomDatabase.User;
import com.example.memorization.RoomDatabase.UserGroup;
import com.example.memorization.databinding.ActivityStudentBinding;
import com.example.memorization.onClick;

import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends AppCompatActivity {

    ActivityStudentBinding binding;
    private MyViewModel mvm;
    private long studentId;
    private int idGroup, validity;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mvm = new ViewModelProvider(this).get(MyViewModel.class);
        sp = getSharedPreferences("dataUserLogin", MODE_PRIVATE);

        idGroup = getIntent().getIntExtra("idGroup", -1);

        validity = sp.getInt("validity", -1);
        if (validity != 0 && validity != 1) {
            binding.studentFab.setVisibility(View.GONE);
        } else {
            binding.studentFab.setVisibility(View.VISIBLE);
            binding.studentFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getBaseContext(), AddNewStudentActivity.class);
                    intent.putExtra("idGroup", idGroup);
                    startActivity(intent);
                }
            });
        }
        validity = sp.getInt("validity", -1);

        StudentAdapter adapter =
                new StudentAdapter(new ArrayList<>(), new onClick<Integer>() {
                    @Override
                    public void onClickItem(Integer integer) {
                        Intent intent = new Intent(getBaseContext(), OverallAssessmentStudentActivity.class);
                        intent.putExtra("idGroup", idGroup);

                        startActivity(intent);
                    }
                }, new onClickStudent() {
                    @Override
                    public void onClick(StudentGroup studentGroup, int position) {
                        if (validity == 0 || validity == 1) {
                            Intent intent = new Intent(getBaseContext(), AddNewStudentActivity.class);
                            intent.putExtra("itemStudentPosition", position);
                            intent.putExtra("fullName", studentGroup.getUser().getFullName());
                            intent.putExtra("address", studentGroup.getUser().getAddress());
                            intent.putExtra("Identification", studentGroup.getUser().getIdentificationNumber());
                            intent.putExtra("MobileNumber", studentGroup.getUser().getMobileNumber());
                            intent.putExtra("BirthDay", String.valueOf(studentGroup.getUser().getBirthDay()));
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "ليس لديك الصلاحية لقراءة بيانات الطالب", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        mvm.getAllStudentInGroup(idGroup).observe(this, new Observer<List<StudentGroup>>() {
            @Override
            public void onChanged(List<StudentGroup> studentGroups) {
                adapter.setUsersStudent(studentGroups);
            }
        });

        binding.studentRv.setAdapter(adapter);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        binding.studentRv.setLayoutManager(lm);
        binding.studentRv.setHasFixedSize(true);

    }
}