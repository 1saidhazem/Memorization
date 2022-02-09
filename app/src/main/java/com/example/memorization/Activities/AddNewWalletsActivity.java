package com.example.memorization.Activities;

import static com.example.memorization.Constant.COLLECTION_USER;
import static com.example.memorization.Constant.COLLECTION_WALLET;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.memorization.Adapters.GroupsSpinnerAdapter;
import com.example.memorization.Adapters.MemoCentersSpinnerAdapter;
import com.example.memorization.Adapters.WalletAdapter;
import com.example.memorization.Adapters.onClickAddNewWallet;
import com.example.memorization.R;
import com.example.memorization.RoomDatabase.Center;
import com.example.memorization.RoomDatabase.CenterGroup;
import com.example.memorization.RoomDatabase.Group;
import com.example.memorization.RoomDatabase.MyViewModel;
import com.example.memorization.RoomDatabase.User;
import com.example.memorization.RoomDatabase.Wallet;
import com.example.memorization.databinding.ActivityAddNewWallestsBinding;
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

public class AddNewWalletsActivity extends AppCompatActivity {

    ActivityAddNewWallestsBinding binding;
    private Bitmap bitmap;
    private byte[] imageByte = null;
    private String branchWallet;
    private MyViewModel mvm;
    int groupId, centerId, walletPosition;
    private Uri imgURI;
    private FirebaseFirestore fireStore;
    long phoneNumberWallet, identificationWallet;
    private MemoCentersSpinnerAdapter centersSpinnerAdapter;
    private GroupsSpinnerAdapter groupsSpinnerAdapter;
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNewWallestsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mvm = new ViewModelProvider(this).get(MyViewModel.class);
        fireStore = FirebaseFirestore.getInstance();

        sp = getSharedPreferences("dataUserLogin", MODE_PRIVATE);
        edit = sp.edit();

        walletPosition = getIntent().getIntExtra("positionWallet", -1);
        String walletName = getIntent().getStringExtra("walletName");
        String walletAddress = getIntent().getStringExtra("walletAddress");
        long walletId = getIntent().getLongExtra("walletId", 0);
        long walletPhoneNumber = getIntent().getLongExtra("walletPhoneNumber", 0);

        try {
            if (walletPosition == -1) {
                // عملية اضافة
                enableFields();
                clearFields();
            } else {
                //عملية عرض
                disableFields();
                binding.addNewWalletEtName.setText(walletName);
                binding.addNewWalletEtAddress.setText(walletAddress);
                binding.addNewWalletEtIdentity.setText(String.valueOf(walletId));
                binding.addNewWalletEtPhoneNumber.setText(String.valueOf(walletPhoneNumber));
            }
        } catch (Exception e) {
            Log.e("ttt", e.getMessage());
        }

        centersSpinnerAdapter = new MemoCentersSpinnerAdapter(new ArrayList<>());
        groupsSpinnerAdapter = new GroupsSpinnerAdapter(new ArrayList<>());

        mvm.getAllCenter().observe(this, new Observer<List<Center>>() {
            @Override
            public void onChanged(List<Center> centers) {
                centersSpinnerAdapter.setCenters(centers);
            }
        });

        mvm.getAllGroup().observe(this, new Observer<List<Group>>() {
            @Override
            public void onChanged(List<Group> groups) {
                groupsSpinnerAdapter.setGroups(groups);
            }
        });

        binding.addNewWalletSpinnerMemorizationCenter.setAdapter(centersSpinnerAdapter);
        binding.addNewWalletSpinnerMemorizationGroup.setAdapter(groupsSpinnerAdapter);


        binding.addNewWalletSpinnerMemorizationCenter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                centerId = (int) binding.addNewWalletSpinnerMemorizationCenter.getSelectedItemId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

       centerId= (int) binding.addNewWalletSpinnerMemorizationCenter.getSelectedItemId();
        Log.d("ggg", "centerId : "+centerId);



        binding.addNewWalletBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName = binding.addNewWalletEtName.getText().toString().trim();
                String address = binding.addNewWalletEtAddress.getText().toString().trim();
                String identification = binding.addNewWalletEtIdentity.getText().toString().trim();
                String phoneNumber = binding.addNewWalletEtPhoneNumber.getText().toString().trim();
                String password = binding.addNewWalletEtPassword.getText().toString().trim();
                groupId = (int) binding.addNewWalletSpinnerMemorizationGroup.getSelectedItemId();
                centerId = (int) binding.addNewWalletSpinnerMemorizationCenter.getSelectedItemId();

                if (!fullName.isEmpty() && !address.isEmpty() && !identification.isEmpty() &&
                        !phoneNumber.isEmpty() && !password.isEmpty()) {
                    validateDataUserWallet(identification, fullName, phoneNumber, address, password, 2, imageByte);
                }

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

        binding.addNewWalletSpinnerBranches.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                branchWallet = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void enableFields() {
        binding.addNewWalletEtName.setEnabled(true);
        binding.addNewWalletEtAddress.setEnabled(true);
        binding.addNewWalletEtIdentity.setEnabled(true);
        binding.addNewWalletEtPhoneNumber.setEnabled(true);
        binding.addNewWalletEtPassword.setVisibility(View.VISIBLE);
        binding.addNewWalletEtPassword.setEnabled(true);
        binding.addNewWalletSpinnerBranches.setEnabled(true);
        binding.addNewWalletSpinnerMemorizationCenter.setEnabled(true);
        binding.addNewWalletSpinnerMemorizationGroup.setEnabled(true);
    }

    private void clearFields() {
        binding.addNewWalletEtName.setText("");
        binding.addNewWalletEtAddress.setText("");
        binding.addNewWalletEtIdentity.setText("");
        binding.addNewWalletEtPhoneNumber.setText("");
        binding.addNewWalletEtPassword.setText("");
    }

    private void disableFields() {
        binding.addNewWalletEtName.setEnabled(false);
        binding.addNewWalletEtAddress.setEnabled(false);
        binding.addNewWalletEtIdentity.setEnabled(false);
        binding.addNewWalletEtPhoneNumber.setEnabled(false);
        binding.addNewWalletEtPassword.setVisibility(View.GONE);
        binding.addNewWalletSpinnerBranches.setEnabled(false);
        binding.addNewWalletSpinnerMemorizationCenter.setEnabled(false);
        binding.addNewWalletSpinnerMemorizationGroup.setEnabled(false);
    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    private void validateDataUserWallet(String identificationStr, String fullNameWallet, String phoneNumberStr, String addressWallet, String passwordWallet, int validityWallet, byte[] imageWallet) {

        if (!phoneNumberStr.isEmpty() && !phoneNumberStr.equals("")) {
            phoneNumberWallet = Long.parseLong(phoneNumberStr);

            if (!identificationStr.isEmpty() && !identificationStr.equals("")) {
                identificationWallet = Long.parseLong(identificationStr);
            }
            if (fullNameWallet.isEmpty() && fullNameWallet.equals("")) {
                binding.addNewWalletEtName.setError("Name is empty");
            } else {
                binding.addNewWalletEtName.setError(null);
            }
            if (phoneNumberWallet == 0) {
                binding.addNewWalletEtPhoneNumber.setError("Phone Number is empty");
            } else {
                binding.addNewWalletEtPhoneNumber.setError(null);
            }
            if (identificationWallet == 0) {  // &&identification != id
                binding.addNewWalletEtIdentity.setError("Identification is empty");
            } else {
                binding.addNewWalletEtIdentity.setError(null);
            }
            if (addressWallet.isEmpty() && addressWallet.equals("")) {
                binding.addNewWalletEtAddress.setError("Address is empty");
            } else {
                binding.addNewWalletEtAddress.setError(null);
            }
            if (passwordWallet.isEmpty() && passwordWallet.equals("")) {
                binding.addNewWalletEtPassword.setError("Password is empty");
            } else {
                binding.addNewWalletEtPassword.setError(null);
            }
        }

        User user = new User(identificationWallet, fullNameWallet, null, phoneNumberWallet, null, addressWallet, passwordWallet, null, validityWallet, imageWallet);

        mvm.insertUser(user, new onClick<User>() {
            @Override
            public void onClickItem(User user) {
                Wallet wallet = new Wallet(user.getIdentificationNumber(), groupId, centerId, branchWallet);
                mvm.insertWallet(wallet);
                backupWallets(identificationWallet, groupId, groupId, branchWallet);
            }
        });
        edit.putString("branchWallet", branchWallet);
        edit.commit();
        backupUsers(identificationWallet, fullNameWallet, phoneNumberWallet, addressWallet, passwordWallet, validityWallet);
        finish();
    }

    private void backupUsers(long identificationWallet, String fullNameWallet, long phoneNumberWallet, String addressWallet, String passwordWallet, int validityWallet) {
        User user = new User();
        user.setIdentificationNumber(identificationWallet);
        user.setFullName(fullNameWallet);
        user.setMobileNumber(phoneNumberWallet);
        user.setAddress(addressWallet);
        user.setPassword(passwordWallet);
        user.setValidity(validityWallet);

        fireStore.collection(COLLECTION_USER).add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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

    private void backupWallets(long identificationWallet, int idGroup, int idCenter, String branchWallet) {
        Wallet wallet = new Wallet();
        wallet.setIdentificationNumberWallet(identificationWallet);
        wallet.setIdGroup(idGroup);
        wallet.setIdCenter(idCenter);
        wallet.setBranch(branchWallet);

        fireStore.collection(COLLECTION_WALLET).add(wallet).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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