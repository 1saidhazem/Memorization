package com.example.memorization.Adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memorization.R;
import com.example.memorization.RoomDatabase.Student;
import com.example.memorization.RoomDatabase.StudentGroup;
import com.example.memorization.RoomDatabase.User;
import com.example.memorization.RoomDatabase.UserGroup;
import com.example.memorization.databinding.CustomStudentBinding;
import com.example.memorization.onClick;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private List<StudentGroup> usersStudent;
    private onClickAddNewWallet listener;
    private onClickStudent listenerStudent;
    private onClick<Integer> clickTasks;

    public StudentAdapter(List<StudentGroup> usersStudent,onClick<Integer> clickTasks, onClickStudent listenerStudent) {  // , int studentId
        this.usersStudent = usersStudent;
        this.clickTasks = clickTasks;
        this.listenerStudent =listenerStudent;
    }

    public void setUsersStudent(List<StudentGroup> usersStudent) {
        this.usersStudent = usersStudent;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_student , null , false);
        StudentViewHolder ViewHolder = new StudentViewHolder(v);
        return ViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        StudentGroup Student = usersStudent.get(position);
        holder.bind(Student);
    }

    @Override
    public int getItemCount() {
        return usersStudent.size();
    }

    class StudentViewHolder extends RecyclerView.ViewHolder{

        CustomStudentBinding binding;
        StudentGroup Student;
        User user;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = CustomStudentBinding.bind(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listenerStudent.onClick(Student,getAdapterPosition());
                }
            });
        }
        public void bind(StudentGroup Student) {
            this.Student=Student;

            if (Student.getUser().getImg() != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(Student.getUser().getImg() ,0 , Student.getUser().getImg().length);
                binding.customStudentImg.setImageBitmap(bitmap);
            }else {
                binding.customStudentImg.setImageResource(R.drawable.ic_default_logo_supervisor);
            }

            binding.customStudentTvName.setText(Student.getUser().getFullName());
            binding.customStudentTvPhoneNumber.setText(String.valueOf(Student.getUser().getMobileNumber()));
            binding.customStudentTvBranch.setText(Student.getStudent().getBranch());
            binding.customStudentTvGroup.setText(Student.getGroupName());
            binding.customStudentTvCenter.setText(Student.getCenterName());
            binding.customStudentLinearListTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickTasks.onClickItem(getAdapterPosition());
                }
            });

        }
    }
}
