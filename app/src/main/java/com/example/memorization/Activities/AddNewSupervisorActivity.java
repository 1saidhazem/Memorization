package com.example.memorization.Activities;

import static com.example.memorization.Constant.COLLECTION_USER;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.Manifest;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import com.example.memorization.RoomDatabase.MyViewModel;
import com.example.memorization.RoomDatabase.User;
import com.example.memorization.databinding.ActivityAddNewSupervisorBinding;
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
import java.util.Map;

public class AddNewSupervisorActivity extends AppCompatActivity {

    ActivityAddNewSupervisorBinding binding;
    private Bitmap bitmap;
    private byte[] imageByte = null;
    private String branch;
    private MyViewModel mvm;
    private Uri imgURI;
    private FirebaseFirestore fireStore;
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNewSupervisorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mvm = new ViewModelProvider(this).get(MyViewModel.class);
        fireStore = FirebaseFirestore.getInstance();
        sp = getSharedPreferences("dataUserLogin", MODE_PRIVATE);
        edit = sp.edit();

        ActivityResultLauncher<String[]> arl = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
            @Override
            public void onActivityResult(Map<String, Boolean> result) {
                if (result.get(Manifest.permission.SEND_SMS) &&
                        result.get(Manifest.permission.READ_SMS)) {

                }
            }
        });

        arl.launch(new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS});
        SmsManager sms = SmsManager.getDefault();

        binding.addNewSupervisorBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String identification = binding.addNewSupervisorEtIdentity.getText().toString().trim();
                String fullName = binding.addNewSupervisorEtName.getText().toString().trim();
                String phoneNumber = binding.addNewSupervisorEtPhoneNumber.getText().toString().trim();
                String address = binding.addNewSupervisorEtAddress.getText().toString().trim();
                String password = binding.addNewSupervisorEtPassword.getText().toString().trim();

                validateDataUserSupervisor(identification, fullName, phoneNumber, address, password, 1, imageByte);
                if (!identification.isEmpty()) {
                    sms.sendTextMessage(identification, null,
                            "تم اضافاتك ك مشرف ... كلمة مرورك هي : " + password,
                            null, null);
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

        binding.addNewSupervisorSpinnerBranches.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                branch = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    private void validateDataUserSupervisor(String identificationStr, String fullName, String phoneNumberStr, String address, String password, int validity, byte[] image) {
        long phoneNumber = 0, identificationSupervisor = 0;

        if (!phoneNumberStr.isEmpty() && !phoneNumberStr.equals("")) {
            phoneNumber = Long.parseLong(phoneNumberStr);

            if (!identificationStr.isEmpty() && !identificationStr.equals("")) {
                identificationSupervisor = Long.parseLong(identificationStr);
            }
            if (fullName.isEmpty() && fullName.equals("")) {
                binding.addNewSupervisorEtName.setError("Name is empty");
            } else {
                binding.addNewSupervisorEtName.setError(null);
            }
            if (phoneNumber == 0) {
                binding.addNewSupervisorEtPhoneNumber.setError("Phone Number is empty");
            } else {
                binding.addNewSupervisorEtPhoneNumber.setError(null);
            }

            if (identificationSupervisor == 0) {  // &&identification != id
                binding.addNewSupervisorEtIdentity.setError("Identification is empty");
            } else {
                binding.addNewSupervisorEtIdentity.setError(null);
            }
            if (address.isEmpty() && address.equals("")) {
                binding.addNewSupervisorEtAddress.setError("Address is empty");
            } else {
                binding.addNewSupervisorEtAddress.setError(null);
            }
            if (password.isEmpty() && password.equals("")) {
                binding.addNewSupervisorEtPassword.setError("Password is empty");
            } else {
                binding.addNewSupervisorEtPassword.setError(null);
            }
        }
        edit.putLong("idSupervisor", identificationSupervisor);
        edit.commit();
        Log.d("ccc", "id Supervisor :: " + identificationSupervisor);
        User user = new User(identificationSupervisor, fullName, null, phoneNumber, null, address, password, null, validity, image);
        mvm.insertUser(user, new onClick<User>() {
            @Override
            public void onClickItem(User user) {

            }
        });

        backupUsers(identificationSupervisor, fullName, phoneNumber, address, password, validity);
        finish();
    }

    private void backupUsers(long identification, String fullName, long phoneNumber, String address, String password, int validity) {

        User user = new User();
        user.setIdentificationNumber(identification);
        user.setFullName(fullName);
        user.setMobileNumber(phoneNumber);
        user.setAddress(address);
        user.setPassword(password);
        user.setValidity(validity);

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
}