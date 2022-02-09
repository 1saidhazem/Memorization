package com.example.memorization.Activities;

import static com.example.memorization.Constant.COLLECTION_STUDENT;
import static com.example.memorization.Constant.COLLECTION_TASK;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RatingBar;

import com.example.memorization.Adapters.ListenerIdCenterAndGroup;
import com.example.memorization.Adapters.StudentsSpinnerAdapter;
import com.example.memorization.R;
import com.example.memorization.RoomDatabase.MyViewModel;
import com.example.memorization.RoomDatabase.Student;
import com.example.memorization.RoomDatabase.StudentGroup;
import com.example.memorization.RoomDatabase.StudentUser;
import com.example.memorization.RoomDatabase.Task;
import com.example.memorization.RoomDatabase.User;
import com.example.memorization.databinding.ActivityAddNewTaskBinding;
import com.example.memorization.onClick;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddNewTaskActivity extends AppCompatActivity {

    private ActivityAddNewTaskBinding binding;
    private Calendar selectedDate;
    private String type;
    int from, to, idGroup2, idCenter2;
    float rating;
    private StudentsSpinnerAdapter studentsSpinnerAdapter;
    private MyViewModel mvm;
    private long idStudent, idSender;
    private FirebaseFirestore fireStore;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNewTaskBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fireStore = FirebaseFirestore.getInstance();
        mvm = new ViewModelProvider(this).get(MyViewModel.class);
        sp = getSharedPreferences("dataUserLogin", MODE_PRIVATE);
        selectedDate = Calendar.getInstance();

        idSender = sp.getLong("loginId", -1);

        int id = getIntent().getIntExtra("id", -1);
        int From = getIntent().getIntExtra("from", 0);
        int To = getIntent().getIntExtra("to", 0);
        String toDate = getIntent().getStringExtra("toDate");
        String feedback = getIntent().getStringExtra("feedback");

        if (id == -1) {
            // عملية اضافة
            enableFields();
            clearFields();
        } else {
            //عملية عرض
            disableFields();
            binding.addNewTaskEtFrom.setText(String.valueOf(From));
            binding.addNewTaskEtTo.setText(String.valueOf(To));
            binding.addNewTaskEtFeedbackOnTheEvaluation.setText(feedback);
            binding.addNewTaskBtnTaskEndDate.setText(toDate);
        }


        binding.addNewTaskBtnTaskEndDate.setOnClickListener(new View.OnClickListener() {
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
                                binding.addNewTaskBtnTaskEndDate
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

        binding.addNewTaskSpinnerRequiredQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = (String) adapterView.getSelectedItem();
                switch (item) {
                    case "جزء":
                        type = "a";
                        break;
                    case "سورة":
                        type = "b";
                        break;
                    case "صفحة":
                        type = "c";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        studentsSpinnerAdapter = new StudentsSpinnerAdapter(new ArrayList<>());

        mvm.getAllStudent().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                studentsSpinnerAdapter.setStudents(users);
            }
        });

        binding.addNewTaskSpinnerStudents.setAdapter(studentsSpinnerAdapter);

        binding.addNewTaskRbEvaluation.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rating = ratingBar.getRating();
            }
        });

        binding.addNewTaskSpinnerStudents.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idStudent = binding.addNewTaskSpinnerStudents.getSelectedItemId();
                Log.d("ggg", "id Student : " + idStudent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mvm.getInfoStudent(55, new ListenerIdCenterAndGroup() {
            @Override
            public void onReturned(int idCenter, int idGroup) {
                idGroup2 = idGroup;
                idCenter2 = idCenter;
            }
        });


        binding.addNewCenterBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fromStr = binding.addNewTaskEtFrom.getText().toString().trim();
                String toStr = binding.addNewTaskEtTo.getText().toString().trim();
                String feedBack = binding.addNewTaskEtFeedbackOnTheEvaluation.getText().toString().trim();
                if (!fromStr.isEmpty()) {
                    from = Integer.parseInt(fromStr);
                } else if (!toStr.isEmpty()) {
                    to = Integer.parseInt(toStr);
                }

                Task task = new Task(idStudent, idGroup2, idCenter2, idSender, from, to,
                        Calendar.getInstance().getTime(), selectedDate.getTime(), type,
                        rating, feedBack);

                mvm.insertTask(task);
                backupTasks(idStudent, idGroup2, idCenter2, idSender, from, to,
                        Calendar.getInstance().getTime(), selectedDate.getTime(), type,
                        rating, feedBack);
                finish();
            }
        });
    }

    private void enableFields() {
        binding.addNewTaskSpinnerRequiredQuantity.setEnabled(true);
        binding.addNewTaskEtFrom.setEnabled(true);
        binding.addNewTaskEtTo.setEnabled(true);
        binding.addNewTaskSpinnerStudents.setEnabled(true);
        binding.addNewTaskRbEvaluation.setEnabled(false);
        binding.addNewTaskEtFeedbackOnTheEvaluation.setEnabled(true);
        binding.addNewTaskBtnTaskEndDate.setEnabled(true);
    }

    private void clearFields() {
        binding.addNewTaskEtFrom.setText("");
        binding.addNewTaskEtTo.setText("");
        binding.addNewTaskEtFeedbackOnTheEvaluation.setText("");
    }

    private void disableFields() {
        binding.addNewTaskSpinnerRequiredQuantity.setEnabled(false);
        binding.addNewTaskEtFrom.setEnabled(false);
        binding.addNewTaskEtTo.setEnabled(false);
        binding.addNewTaskSpinnerStudents.setEnabled(false);
        binding.addNewTaskRbEvaluation.setEnabled(true);
        binding.addNewTaskEtFeedbackOnTheEvaluation.setEnabled(false);
        binding.addNewTaskBtnTaskEndDate.setEnabled(false);
    }

    private void backupTasks(long studentReceiver, int idGroup, int idCenter, long taskSender, int from, int to, Date fromDate, Date toDate, String quantityType, float rating, String feedbackOnRating) {
        Task task = new Task();
        task.setStudentReceiver(studentReceiver);
        task.setGroupId(idGroup);
        task.setCenterId(idCenter);
        task.setTaskSender(taskSender);
        task.setFrom(from);
        task.setTo(to);
        task.setFromDate(fromDate);
        task.setToDate(toDate);
        task.setQuantityType(quantityType);
        task.setRating(rating);
        task.setFeedbackOnRating(feedbackOnRating);

        fireStore.collection(COLLECTION_TASK).add(task).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d("ttt", "on Success done...");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("ttt", "on Failure done..." + e.getMessage());
            }
        });
    }
}