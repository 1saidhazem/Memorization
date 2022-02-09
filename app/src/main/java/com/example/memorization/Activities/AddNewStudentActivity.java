package com.example.memorization.Activities;

import static com.example.memorization.Constant.COLLECTION_STUDENT;
import static com.example.memorization.Constant.COLLECTION_USER;
import static com.example.memorization.Constant.COLLECTION_WALLET;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.memorization.Adapters.GroupsSpinnerAdapter;
import com.example.memorization.Adapters.MemoCentersSpinnerAdapter;
import com.example.memorization.Adapters.WalletsSpinnerAdapter;
import com.example.memorization.RoomDatabase.Center;
import com.example.memorization.RoomDatabase.CenterGroup;
import com.example.memorization.RoomDatabase.Group;
import com.example.memorization.RoomDatabase.IdCenterAndGroup;
import com.example.memorization.RoomDatabase.MyViewModel;
import com.example.memorization.RoomDatabase.ReturnedIdCenterAndGroup;
import com.example.memorization.RoomDatabase.Student;
import com.example.memorization.RoomDatabase.User;
import com.example.memorization.RoomDatabase.Wallet;
import com.example.memorization.databinding.ActivityAddNewStudentBinding;
import com.example.memorization.onClick;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class AddNewStudentActivity extends AppCompatActivity {

    private ActivityAddNewStudentBinding binding;
    private FirebaseFirestore fireStore;
    private Calendar selectedDate;
    private MyViewModel mvm;
    private String branchStudent;
    private Uri imageUri;
    private Bitmap bitmap,barcode;
    private byte[] imageByte = null;
    long idWallet=0,identificationStudent=0,phoneNumberStudent=0;
    int idCenter=0;
    int idGroup=0;
    private MemoCentersSpinnerAdapter centersSpinnerAdapter;
    private GroupsSpinnerAdapter groupsSpinnerAdapter;
    private WalletsSpinnerAdapter walletsSpinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNewStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mvm = new ViewModelProvider(this).get(MyViewModel.class);
        fireStore = FirebaseFirestore.getInstance();
        selectedDate = Calendar.getInstance();

        String fullName = getIntent().getStringExtra("fullName");
        String address = getIntent().getStringExtra("address");
        long Identification = getIntent().getLongExtra("Identification",0);
        long MobileNumber = getIntent().getLongExtra("MobileNumber",0);
        String BirthDay =getIntent().getStringExtra("BirthDay");

        int itemStudentPosition = getIntent().getIntExtra("itemStudentPosition",-1);
        if (itemStudentPosition == -1){
            // عملية اضافة
            enableFields();
            clearFields();
            binding.addNewStudentImgBarCode.setVisibility(View.GONE);
        }
        else {
            // عملية عرض
            disableFields();
            binding.addNewStudentEtName.setText(fullName);
            binding.addNewStudentEtAddress.setText(address);
            binding.addNewStudentEtIdentity.setText(String.valueOf(Identification));
            binding.addNewStudentEtPhoneNumber.setText(String.valueOf(MobileNumber));
            binding.btnBirthDay.setText(BirthDay);
            binding.addNewStudentImgBarCode.setVisibility(View.VISIBLE);
//            if (barcode != null)
                binding.addNewStudentImgBarCode.setImageBitmap(barcode);
        }

        centersSpinnerAdapter = new MemoCentersSpinnerAdapter(new ArrayList<>());
        groupsSpinnerAdapter = new GroupsSpinnerAdapter(new ArrayList<>());
        walletsSpinnerAdapter = new WalletsSpinnerAdapter(new ArrayList<>());

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
        mvm.getAllWallet().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                walletsSpinnerAdapter.setWallets(users);
            }
        });

        binding.addNewStudentSpinnerMemorizationCenter.setAdapter(centersSpinnerAdapter);
        binding.addNewStudentSpinnerMemorizationGroup.setAdapter(groupsSpinnerAdapter);
        binding.addNewStudentSpinnerWalletName.setAdapter(walletsSpinnerAdapter);



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

        binding.addNewStudentSpinnerBranches.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                branchStudent = (String) adapterView.getSelectedItem();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        binding.addNewStudentBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 String idStr = binding.addNewStudentEtIdentity.getText().toString().trim();
                 String phoneStr = binding.addNewStudentEtPhoneNumber.getText().toString().trim();
                 if (!idStr.isEmpty() && !idStr.equals("")){
                     identificationStudent = Long.parseLong(idStr);
                 }
                String fullNameStudent = binding.addNewStudentEtName.getText().toString().trim();
                if (!phoneStr.isEmpty() || !phoneStr.equals("")){
                    phoneNumberStudent = Long.parseLong(phoneStr);
                }
                String addressStudent = binding.addNewStudentEtAddress.getText().toString().trim();
                String passwordStudent = binding.addNewStudentEtPassword.getText().toString().trim();

                idWallet = binding.addNewStudentSpinnerWalletName.getSelectedItemId();
                idCenter = (int) binding.addNewStudentSpinnerMemorizationCenter.getSelectedItemId();
                idGroup = (int) binding.addNewStudentSpinnerMemorizationGroup.getSelectedItemId();

                if (identificationStudent!=0 && !fullNameStudent.isEmpty() && phoneNumberStudent!=0 && !addressStudent.isEmpty() && !passwordStudent.isEmpty()){
                    MultiFormatWriter writer = new MultiFormatWriter();
                    try {
                        BitMatrix matrix = writer.encode(idStr, BarcodeFormat.QR_CODE,350,350);
                        BarcodeEncoder encoder = new BarcodeEncoder();
                        barcode =encoder.createBitmap(matrix);
                        InputMethodManager manager = (InputMethodManager)
                                getSystemService(Context.INPUT_METHOD_SERVICE);
                        manager.hideSoftInputFromWindow(binding.addNewStudentEtIdentity.getWindowToken()
                        ,0);

                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                    User user = new User(identificationStudent,fullNameStudent,null,phoneNumberStudent,selectedDate.getTime(),addressStudent,passwordStudent,null,3,imageByte);
                    mvm.insertUser(user, new onClick<User>() {
                        @Override
                        public void onClickItem(User user) {
                            Student student = new Student(identificationStudent,idGroup,idCenter,idWallet,branchStudent);
                            mvm.insertStudent(student);
                            Log.d("hhh","id Student ::: "+identificationStudent);
                            backupStudents(identificationStudent,idGroup,idCenter,idWallet,branchStudent);
                            Intent intent = new Intent();
                            intent.putExtra("studentId",identificationStudent);
                            startActivity(intent);
                        }
                    });
                    backupUsers(identificationStudent,fullNameStudent,phoneNumberStudent,addressStudent,selectedDate.getTime(),passwordStudent,3);
                    finish();
                }
                else {
                    Toast.makeText(getBaseContext(), "الرجاء تعبئة البيانات", Toast.LENGTH_SHORT).show();
                }

            }
        });



        binding.addNewStudentFabRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), OverallAssessmentStudentActivity.class);
                startActivity(intent);
            }
        });

        ActivityResultLauncher<String> arlImg = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null) {
                    imageUri = result;
                    binding.profileImage.setImageURI(result);

                    try {
                        InputStream inputStream = getContentResolver().openInputStream(imageUri);
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
        binding.addNewStudentEtName.setEnabled(true);
        binding.addNewStudentEtAddress.setEnabled(true);
        binding.addNewStudentEtIdentity.setEnabled(true);
        binding.addNewStudentEtPhoneNumber.setEnabled(true);
        binding.btnBirthDay.setEnabled(true);
        binding.addNewStudentFabRating.setVisibility(View.GONE);
        binding.addNewStudentBtnSave.setEnabled(true);
        binding.addNewStudentEtPassword.setEnabled(true);
        binding.addNewStudentSpinnerBranches.setEnabled(true);
        binding.addNewStudentSpinnerMemorizationCenter.setEnabled(true);
        binding.addNewStudentSpinnerMemorizationGroup.setEnabled(true);
        binding.addNewStudentSpinnerWalletName.setEnabled(true);
    }

    private void clearFields() {
        binding.profileImage.setImageURI(null);
        binding.addNewStudentEtName.setText("");
        binding.addNewStudentEtAddress.setText("");
        binding.addNewStudentEtIdentity.setText("");
        binding.addNewStudentEtPhoneNumber.setText("");
        binding.addNewStudentEtPassword.setText("");
    }

    private void disableFields() {
        binding.profileImage.setEnabled(false);
        binding.addNewStudentEtName.setEnabled(false);
        binding.addNewStudentEtAddress.setEnabled(false);
        binding.addNewStudentEtIdentity.setEnabled(false);
        binding.addNewStudentEtPhoneNumber.setEnabled(false);
        binding.btnBirthDay.setEnabled(false);
        binding.addNewStudentBtnSave.setEnabled(false);
        binding.addNewStudentEtPassword.setVisibility(View.GONE);
        binding.addNewStudentFabRating.setVisibility(View.VISIBLE);
        binding.addNewStudentSpinnerBranches.setEnabled(false);
        binding.addNewStudentSpinnerMemorizationCenter.setEnabled(false);
        binding.addNewStudentSpinnerMemorizationGroup.setEnabled(false);
        binding.addNewStudentSpinnerWalletName.setEnabled(false);
    }


    private void backupUsers(long identificationStudent, String fullNameStudent, long phoneNumberStudent, String addressStudent, Date birthdate,String passwordStudent, int validityStudent) {
        User user = new User();
        user.setIdentificationNumber(identificationStudent);
        user.setFullName(fullNameStudent);
        user.setMobileNumber(phoneNumberStudent);
        user.setAddress(addressStudent);
        user.setBirthDay(birthdate);
        user.setPassword(passwordStudent);
        user.setValidity(validityStudent);

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
    private void backupStudents(long identificationStudent,int idGroup,int idCenter,long idWallet,String branchStudent) {
        Student student = new Student();
        student.setIdentificationNumberStudent(identificationStudent);
        student.setIdGroup(idGroup);
        student.setIdCenter(idCenter);
        student.setIdWallet(idWallet);
        student.setBranch(branchStudent);

        fireStore.collection(COLLECTION_STUDENT).add(student).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("ttt", "on Success done...");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("ttt", "on Failure done..." +e.getMessage());
            }
        });
    }
}