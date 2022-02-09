package com.example.memorization.Activities;

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

import com.example.memorization.Adapters.TaskAdapter;
import com.example.memorization.RoomDatabase.MyViewModel;
import com.example.memorization.RoomDatabase.Task;
import com.example.memorization.databinding.ActivityOverallAssessmentStudentBinding;
import com.example.memorization.onClick;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OverallAssessmentStudentActivity extends AppCompatActivity {

    private ActivityOverallAssessmentStudentBinding binding;
    private Calendar selectedDate;
    TaskAdapter taskAdapter;
    private MyViewModel mvm;
    private SharedPreferences sp;
    private int validity;
    private long studentId;
    private String studentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOverallAssessmentStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mvm = new ViewModelProvider(this).get(MyViewModel.class);
        sp = getSharedPreferences("dataUserLogin", MODE_PRIVATE);
        selectedDate = Calendar.getInstance();
        validity = sp.getInt("validity", -1);
        int idGroup = getIntent().getIntExtra("idGroup", -1);
        studentId = getIntent().getLongExtra("studentId",-1);
        Log.d("hhh","Student id :"+studentId);

        binding.overallFabAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), AddNewTaskActivity.class);
                intent.putExtra("idGroup", idGroup);
                startActivity(intent);
            }
        });

        binding.overallBtnFrom.setOnClickListener(new View.OnClickListener() {
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
                                binding.overallBtnFrom
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

        binding.overallBtnTo.setOnClickListener(new View.OnClickListener() {
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
                                binding.overallBtnTo
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


        mvm.getNameStudent(studentId, new onClick<String>() {
            @Override
            public void onClickItem(String s) {
                studentName = s;
            }
        });

        taskAdapter = new TaskAdapter(new ArrayList<>(), new onClick<Task>() {
            @Override
            public void onClickItem(Task task) {
                Intent intent = new Intent(getBaseContext(), AddNewTaskActivity.class);
                intent.putExtra("id", 1);
                intent.putExtra("from",task.getFrom());
                intent.putExtra("to",task.getTo());
                intent.putExtra("fromDate",String.valueOf(task.getFromDate()));
                intent.putExtra("toDate",String.valueOf(task.getToDate()));
                intent.putExtra("feedback",task.getFeedbackOnRating());
                startActivity(intent);
            }
        },studentName);

        mvm.getTasksByIdStudent(55).observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                taskAdapter.setTasks(tasks);
            }
        });

        binding.overallRv.setAdapter(taskAdapter);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        binding.overallRv.setLayoutManager(lm);
        binding.overallRv.setHasFixedSize(true);

    }
}