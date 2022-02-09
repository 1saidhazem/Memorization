package com.example.memorization.Activities;

import static com.example.memorization.Constant.COLLECTION_USER;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.memorization.R;
import com.example.memorization.RoomDatabase.MyViewModel;
import com.example.memorization.RoomDatabase.User;
import com.example.memorization.databinding.ActivitySignUpBinding;
import com.example.memorization.onClick;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class SignUpActivity extends AppCompatActivity {

    private ActivitySignUpBinding binding;
    private Calendar selectedDate;
    private String branch,imagePath;
    private Date date;
    private MyViewModel mvm;
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;
    private Bitmap bitmap;
    private Uri imgURI;
    private byte[] imageByte = null;
    private FirebaseFirestore fireStore;
    private FirebaseStorage storage;
    private StorageReference storageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mvm = new ViewModelProvider(this).get(MyViewModel.class);
        fireStore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        selectedDate = Calendar.getInstance();

        sp = getSharedPreferences("dataUserLogin", MODE_PRIVATE);
        edit = sp.edit();

        ActivityResultLauncher<String> arlImg = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null) {
                    imgURI = result;
                    binding.profileImage.setImageURI(result);
                    imagePath = "images/" + UUID.randomUUID() + ".png";

                    StorageReference imgReF = FirebaseStorage.getInstance().getReference(imagePath);
                    imgReF.putFile(imgURI).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("ttt", e.getMessage());
                        }
                    });

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

        binding.btnBirthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog datePicker = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view,
                                                  int year,
                                                  int monthOfYear,
                                                  int dayOfMonth) {
                                binding.btnBirthDay
                                        .setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                                selectedDate.set(Calendar.YEAR, year);
                                selectedDate.set(Calendar.MONTH, monthOfYear);
                                selectedDate.clear(Calendar.HOUR_OF_DAY);
                                selectedDate.clear(Calendar.MINUTE);
                                selectedDate.clear(Calendar.SECOND);
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)

                );
                datePicker.show(getSupportFragmentManager(), "DatePickerDialog");
            }
        });

        binding.spinnerBranches.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                branch = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String identification = binding.etIdentification.getText().toString().trim();
                String fullName = binding.etFullName.getText().toString().trim();
                String email = binding.etEmail.getText().toString().trim();
                String phoneNumber = binding.etPhoneNumber.getText().toString().trim();
                String address = binding.etAddress.getText().toString().trim();
                String password = binding.etPassword.getText().toString().trim();
                String rePassword = binding.etRePassword.getText().toString().trim();

                validateDataUserManager(identification, fullName, email, phoneNumber, selectedDate.getTime(), address, password, rePassword, 0, imageByte);

            }
        });

    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

    private void validateDataUserManager(String identificationStr, String fullName, String email, String phoneNumberStr, Date birthdate, String address, String password, String rePassword, int validity, byte[] image) {

        if (email.isEmpty()) {
            binding.etEmail.setError("Field can't be Empty");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.setError("Please Enter a Valid Email Address");
        } else {
            binding.etEmail.setError(null);
        }

        long phoneNumber = 0, identification = 0;
        if (!phoneNumberStr.isEmpty() && !phoneNumberStr.equals("")) {
            phoneNumber = Long.parseLong(phoneNumberStr);
        }
        if (!identificationStr.isEmpty() && !identificationStr.equals("")) {
            identification = Long.parseLong(identificationStr);
        }

        if (fullName.isEmpty() && fullName.equals("")) {
            binding.etFullName.setError("Name is empty");
        } else {
            binding.etFullName.setError(null);
        }
        if (phoneNumber == 0) {
            binding.etPhoneNumber.setError("Phone Number is empty");
        } else {
            binding.etPhoneNumber.setError(null);
        }
        if (identification == 0) {
            binding.etIdentification.setError("Identification is empty");
        } else {
            binding.etPhoneNumber.setError(null);
        }
        if (address.isEmpty() && address.equals("")) {
            binding.etAddress.setError("Address is empty");
        } else {
            binding.etAddress.setError(null);
        }
        if (password.isEmpty() && password.equals("")) {
            binding.etPassword.setError("Password is empty");
        } else {
            binding.etPassword.setError(null);
        }
        if (rePassword.isEmpty() && rePassword.equals("")) {
            binding.etRePassword.setError("rePassword is empty");
        } else {
            binding.etRePassword.setError(null);
        }

        User user = new User(identification, fullName, email, phoneNumber, birthdate, address, password, rePassword, validity, image);

        if (password.equals(rePassword) && !password.equals("") &&
                !password.isEmpty() && !rePassword.equals("") &&
                !rePassword.isEmpty()) {
            long idManager = mvm.insertUser(user, new onClick<User>() {
                @Override
                public void onClickItem(User user) {

                }
            });
            backupUsers(identification,fullName,email,phoneNumber,birthdate,address,password,rePassword,validity);
            edit.putString("identification", String.valueOf(identification));
            edit.putString("password", password);
            edit.commit();
            Intent intent = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getBaseContext(), "The password is not the Same", Toast.LENGTH_SHORT).show();
        }
    }

    private void backupUsers(long identification,String fullName,String email,long phoneNumber,Date birthdate,String address,String password,String rePassword,int validity) {

        User user = new User();
        user.setIdentificationNumber(identification);
        user.setFullName(fullName);
        user.setEmail(email);
        user.setImage(imagePath);
        user.setMobileNumber(phoneNumber);
        user.setBirthDay(birthdate);
        user.setAddress(address);
        user.setPassword(password);
        user.setRePassword(rePassword);
        user.setValidity(validity);

        fireStore.collection(COLLECTION_USER).add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
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