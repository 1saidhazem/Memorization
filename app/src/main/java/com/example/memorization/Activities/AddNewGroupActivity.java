package com.example.memorization.Activities;

import static com.example.memorization.Constant.COLLECTION_GROUP;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.memorization.Adapters.ManagerCenterSpinnerAdapter;
import com.example.memorization.R;
import com.example.memorization.RoomDatabase.Group;
import com.example.memorization.RoomDatabase.MyViewModel;
import com.example.memorization.RoomDatabase.User;
import com.example.memorization.databinding.ActivityAddNewGroupBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddNewGroupActivity extends AppCompatActivity {

    ActivityAddNewGroupBinding binding;
    private MyViewModel mvm;
    private String OverallRatingGroup;
    private String MemorizationGroupName;
    private String description;
    private String StrImg;
    private String CenterName;
    private String branch;
    private String groupName;
    private int NumberStudentsAllowedGroup , groupId ;
    private int idCenter;
    private Bitmap bitmap;
    private byte[] imageByte = null;
    private ManagerCenterSpinnerAdapter spinnerAdapter;
    private SharedPreferences sp ;
    private SharedPreferences.Editor edit;
    private FirebaseFirestore fireStore;
    long idSupervisor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNewGroupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.detailsToolbar);
        sp = getSharedPreferences("dataUserLogin", MODE_PRIVATE);
        edit = sp.edit();
        mvm = new ViewModelProvider(this).get(MyViewModel.class);
        fireStore = FirebaseFirestore.getInstance();

        branch = sp.getString("branch","");
        StrImg = sp.getString("imgByte","");

        groupId =getIntent().getIntExtra("group_id",-1);
        CenterName =getIntent().getStringExtra("CenterName");
        idCenter =getIntent().getIntExtra("idCenter",-1);
        edit.putInt("GroupId",groupId);
        edit.commit();
        groupName = getIntent().getStringExtra("groupName");
        int numberOfStudentsAllowed = getIntent().getIntExtra("numberOfStudentsAllowed",0);
        int countItem = sp.getInt("countItem",-1);
        String groupDescription = getIntent().getStringExtra("groupDescription");
        try {
            if (groupId == -1) {
                // عملية اضافة
                enableFields();
                clearFields();
            }
            else {
                // عملية عرض
                disableFields();
                binding.addNewGroupEtName.setText(groupName);
                binding.addNewGroupEtNumberOfStudentAllowed.setText(String.valueOf(numberOfStudentsAllowed));
                binding.addNewGroupEtDescription.setText(groupDescription);
            }
        } catch (Exception e) {
            Log.e("ttt", e.getMessage());
        }

        binding.addNewGroupFabListWallets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), WalletsActivity.class));
            }
        });

        binding.addNewGroupFabListStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(),StudentActivity.class));
            }
        });

        //Save
        binding.addNewCenterBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MemorizationGroupName = binding.addNewGroupEtName.getText().toString().trim();
                NumberStudentsAllowedGroup = Integer.parseInt(binding.addNewGroupEtNumberOfStudentAllowed.getText().toString().trim());
                description = binding.addNewGroupEtDescription.getText().toString().trim();

                idSupervisor = sp.getLong("idSupervisor",-1);

                if (idSupervisor != -1){
                    imageByte = StrImg.getBytes();
                    Group group = new Group(MemorizationGroupName,imageByte,branch,numberOfStudentsAllowed,idCenter,CenterName
                    ,idSupervisor,description);
                    if (countItem <= numberOfStudentsAllowed){
                        mvm.insertGroup(group);
                        backupGroup(idSupervisor,MemorizationGroupName,NumberStudentsAllowedGroup,description,CenterName);
                        finish();
                    }else {
                        Toast.makeText(getApplicationContext(), "عدد الحلقات ممتلئ..", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "الرجاء اضافة مشرف للحلقة", Toast.LENGTH_SHORT).show();
                }

            }
        });

        spinnerAdapter = new ManagerCenterSpinnerAdapter(new ArrayList<>());
        mvm.getAllManager().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                spinnerAdapter.setUsersManager(users);
            }
        });

        binding.addNewGroupSpinnerCenterName.setAdapter(spinnerAdapter);

        binding.addNewGroupBtnAddNewSupervisor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),AddNewSupervisorActivity.class);
                startActivity(intent);
            }
        });

    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    private void enableFields() {
        binding.addNewGroupEtName.setEnabled(true);
        binding.addNewGroupEtNumberOfStudentAllowed.setEnabled(true);
        binding.addNewCenterBtnSave.setVisibility(View.VISIBLE);
        binding.addNewGroupBtnAddNewSupervisor.setVisibility(View.VISIBLE);
        binding.addNewGroupEtDescription.setEnabled(true);
        binding.addNewGroupSpinnerCenterName.setEnabled(true);
        binding.addNewGroupFabListStudent.setVisibility(View.GONE);
        binding.addNewGroupFabListWallets.setVisibility(View.GONE);
    }

    private void clearFields() {
        binding.addNewGroupEtName.setText("");
        binding.addNewGroupEtNumberOfStudentAllowed.setText("");
        binding.addNewGroupEtDescription.setText("");
    }

    private void disableFields() {
        binding.addNewGroupEtName.setEnabled(false);
        binding.addNewCenterBtnSave.setVisibility(View.GONE);
        binding.addNewGroupBtnAddNewSupervisor.setVisibility(View.GONE);
        binding.addNewGroupEtNumberOfStudentAllowed.setEnabled(false);
        binding.addNewGroupEtDescription.setEnabled(false);
        binding.addNewGroupSpinnerCenterName.setEnabled(false);
        binding.addNewGroupFabListStudent.setVisibility(View.VISIBLE);
        binding.addNewGroupFabListWallets.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);
        MenuItem save = menu.findItem(R.id.details_menu_save);
        MenuItem edit = menu.findItem(R.id.details_menu_edit);
        MenuItem delete = menu.findItem(R.id.details_menu_delete);

        if (groupId == -1) {
            // عملية اضافة
            save.setVisible(true);
            edit.setVisible(false);
            delete.setVisible(false);
        } else {
            // عملية عرض
            save.setVisible(false);
            edit.setVisible(true);
            delete.setVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.details_menu_save:
                String MemorizationGroupName = binding.addNewGroupEtName.getText().toString().trim();
                int NumberStudentsAllowedGroup = Integer.parseInt(binding.addNewGroupEtNumberOfStudentAllowed.getText().toString().trim());
                String description = binding.addNewGroupEtDescription.getText().toString().trim();

                Group group = new Group(MemorizationGroupName,imageByte,branch,NumberStudentsAllowedGroup,idCenter,
                        CenterName,idSupervisor,description);

                Toast.makeText(this, "تم تعديل الحلقة بنجاح", Toast.LENGTH_SHORT).show();
                mvm.updateGroup(group);
                finish();
                return true;
            case R.id.details_menu_edit:
                enableFields();
                MenuItem save = binding.detailsToolbar.getMenu().findItem(R.id.details_menu_save);
                MenuItem edit = binding.detailsToolbar.getMenu().findItem(R.id.details_menu_edit);
                MenuItem delete = binding.detailsToolbar.getMenu().findItem(R.id.details_menu_delete);
                delete.setVisible(false);
                edit.setVisible(false);
                save.setVisible(true);
                return true;
            case R.id.details_menu_delete:
                mvm.deleteGroup(groupId);
                Toast.makeText(this, "تم حذف الحلقة بنجاح", Toast.LENGTH_SHORT).show();
                finish();
                return true;
        }
        return false;
    }

    private void backupGroup(long idSupervisor, String MemorizationGroupName,int NumberStudentsAllowedGroup,String description,String CenterName) {

        Group group = new Group();
        group.setIdentificationNumberGroup(idSupervisor);
        group.setName(MemorizationGroupName);
        group.setNumberOfStudentsAllowedInGroup(NumberStudentsAllowedGroup);
        group.setDescription(description);
        group.setCenterName(CenterName);

        fireStore.collection(COLLECTION_GROUP).add(group).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("ttt", "onSuccess done...");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("ttt", "onFailure done..." +e.getMessage());
            }
        });
    }
}