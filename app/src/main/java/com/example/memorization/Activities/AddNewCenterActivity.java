package com.example.memorization.Activities;

import static com.example.memorization.Constant.COLLECTION_CENTER;

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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.memorization.Adapters.ManagerCenterSpinnerAdapter;
import com.example.memorization.Maps.MapsActivity;
import com.example.memorization.R;
import com.example.memorization.RoomDatabase.Center;
import com.example.memorization.RoomDatabase.MyViewModel;
import com.example.memorization.RoomDatabase.User;
import com.example.memorization.databinding.ActivityAddNewCenterBinding;
import com.example.memorization.onClick;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddNewCenterActivity extends AppCompatActivity {

    ActivityAddNewCenterBinding binding;
    private MyViewModel mvm;
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;
    String centerName, branchesName, StrImg;
    private int numberOfGroupAllowed;
    private int center_id;
    private int idCenter;
    private double Longitude, Latitude;
    private Bitmap bitmap;
    private byte[] imageByte = null;
    private ManagerCenterSpinnerAdapter spinnerAdapter;
    private Uri imgURI;
    private FirebaseFirestore fireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNewCenterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sp = getSharedPreferences("dataUserLogin", MODE_PRIVATE);
        edit = sp.edit();
        setSupportActionBar(binding.detailsToolbar);



        mvm = new ViewModelProvider(this).get(MyViewModel.class);
        fireStore = FirebaseFirestore.getInstance();

        center_id = getIntent().getIntExtra("CenterId", -1);
        Log.d("sss", "Center Id : " + center_id);
        centerName = getIntent().getStringExtra("centerName");
        numberOfGroupAllowed = getIntent().getIntExtra("NumberOfGroupsAllowed", 00);

        try {
            if (center_id == -1) {
                // عملية اضافة
                enableFields();
                clearFields();
            } else {
                // عملية عرض
                disableFields();
                binding.addNewCenterEtName.setText(centerName);
                binding.addNewCenterEtNumberOfGroupsAllowed.setText(String.valueOf(numberOfGroupAllowed));
            }
        } catch (Exception e) {
            Log.e("ttt", e.getMessage());
        }

        spinnerAdapter = new ManagerCenterSpinnerAdapter(new ArrayList<>());
        mvm.getAllManager().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                spinnerAdapter.setUsersManager(users);
            }
        });

        binding.addNewCenterSpinnerCenterManager.setAdapter(spinnerAdapter);

        ActivityResultLauncher<Intent> launcherImgMap = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getData() != null) {
                    Longitude = result.getData().getDoubleExtra("Longitude", 0);
                    Latitude = result.getData().getDoubleExtra("Latitude", 0);
                }
            }
        });

        binding.addNewCenterBtnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), MapsActivity.class);
                launcherImgMap.launch(intent);
            }
        });
        binding.addNewCenterFabGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddNewCenterActivity.this, GroupMemorizationActivity.class);
                startActivity(intent);
            }
        });
        binding.addNewCenterBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String centerName = binding.addNewCenterEtName.getText().toString().trim();
                int numberOfGroupAllowed = Integer.parseInt(binding.addNewCenterEtNumberOfGroupsAllowed.getText().toString().trim());
                branchesName = (String) binding.addNewCenterSpinnerBranches.getSelectedItem();
                edit.putString("branch",branchesName);
                edit.putString("imgByte",String.valueOf(imageByte));
                edit.commit();
                long IdentificationNumberManager = Long.parseLong(sp.getString("identification", ""));

                mvm.getIdCenter(new onClick() {
                    @Override
                    public void onClickItem(Object o) {
                        idCenter = Integer.parseInt(o.toString());
                    }
                });
                Center center = new Center(centerName, imageByte, Longitude, Latitude,
                        numberOfGroupAllowed, IdentificationNumberManager, branchesName);

                mvm.insertCenter(center);
                backupCenter(centerName, numberOfGroupAllowed, Longitude, Latitude, branchesName, IdentificationNumberManager, idCenter);
                finish();
            }
        });


        ActivityResultLauncher<String> arlImg = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null) {
                    imgURI = result;
                    binding.profileImage.setImageURI(result);

                    try {
                        InputStream inputStream = getContentResolver().openInputStream(imgURI);
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        binding.profileImage.setImageBitmap(bitmap);

                        imageByte = getBytes(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        binding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arlImg.launch("image/*");
            }
        });

    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    private void enableFields() {
        binding.profileImage.setEnabled(true);
        binding.addNewCenterEtName.setEnabled(true);
        binding.addNewCenterEtNumberOfGroupsAllowed.setEnabled(true);
        binding.addNewCenterBtnAddress.setEnabled(true);
        binding.addNewCenterBtnSave.setVisibility(View.VISIBLE);
        binding.addNewCenterSpinnerCenterManager.setEnabled(true);
        binding.addNewCenterSpinnerBranches.setEnabled(true);
    }

    private void clearFields() {
        binding.profileImage.setImageURI(null);
        binding.addNewCenterEtName.setText("");
        binding.addNewCenterEtNumberOfGroupsAllowed.setText("");
    }

    private void disableFields() {
        binding.profileImage.setEnabled(false);
        binding.addNewCenterEtName.setEnabled(false);
        binding.addNewCenterBtnAddress.setEnabled(false);
        binding.addNewCenterBtnSave.setVisibility(View.GONE);
        binding.addNewCenterEtNumberOfGroupsAllowed.setEnabled(false);
        binding.addNewCenterSpinnerCenterManager.setEnabled(false);
        binding.addNewCenterSpinnerBranches.setEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);
        MenuItem save = menu.findItem(R.id.details_menu_save);
        MenuItem edit = menu.findItem(R.id.details_menu_edit);
        MenuItem delete = menu.findItem(R.id.details_menu_delete);

        if (center_id == -1) {
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
                long IdentificationNumberManager = sp.getLong("IdentificationNumberManager", 0);
                String centerName = binding.addNewCenterEtName.getText().toString();
                String branchesName = binding.addNewCenterSpinnerBranches.getSelectedItem().toString();
                int numberOfGroupAllowed = Integer.parseInt(binding.addNewCenterEtNumberOfGroupsAllowed.getText().toString());
                if (!centerName.isEmpty() && !branchesName.isEmpty() && numberOfGroupAllowed != 0) {  // &&IdentificationNumberManager!=0
                    Center center = new Center(centerName, imageByte, Longitude, Latitude,
                            numberOfGroupAllowed, IdentificationNumberManager, branchesName);
                    Toast.makeText(this, "تم تعديل المركز بنجاح", Toast.LENGTH_SHORT).show();

                    mvm.updateCenter(center);
                    finish();
                }
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
                mvm.deleteCenter(center_id);
                Toast.makeText(this, "تم حذف المركز بنجاح", Toast.LENGTH_SHORT).show();
                finish();
                return true;
        }


        return false;
    }

    private void backupCenter(String centerName, int numberOfGroupAllowed, double Longitude, double Latitude, String branchesName, long IdentificationNumberManager, int idCenter) {

        Center center = new Center();
        Log.d("ddd", "id Center :: " + idCenter);
        center.setId(idCenter);
        center.setName(centerName);
        center.setNumberOfGroupsAllowed(numberOfGroupAllowed);
        center.setLongitude(Longitude);
        center.setLatitude(Latitude);
        center.setBranch(branchesName);
        center.setIdentificationNumberManager(IdentificationNumberManager);

        fireStore.collection(COLLECTION_CENTER).add(center).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("ttt", "onSuccess done...");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("ttt", "onFailure done..." + e.getMessage());
            }
        });
    }
}