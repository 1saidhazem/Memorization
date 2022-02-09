package com.example.memorization.Adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.memorization.R;
import com.example.memorization.RoomDatabase.StudentGroup;
import com.example.memorization.RoomDatabase.User;
import com.example.memorization.databinding.CustomStudentBinding;
import com.example.memorization.onClick;
import java.util.List;

public class AllStudentAdapter extends RecyclerView.Adapter<AllStudentAdapter.StudentViewHolder> {

    private List<User> usersStudent;

    public AllStudentAdapter(List<User> usersStudent) {
        this.usersStudent = usersStudent;
    }

    public void setUsersStudent(List<User> usersStudent) {
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
        User Student = usersStudent.get(position);
        holder.bind(Student);
    }

    @Override
    public int getItemCount() {
        return usersStudent.size();
    }

    class StudentViewHolder extends RecyclerView.ViewHolder{

        CustomStudentBinding binding;
        User user;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = CustomStudentBinding.bind(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });
        }
        public void bind(User Student) {
            this.user=Student;


            if (user.getImg() != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(user.getImg() ,0 , user.getImg().length);
                binding.customStudentImg.setImageBitmap(bitmap);
            }else {
                binding.customStudentImg.setImageResource(R.drawable.ic_default_logo_supervisor);
            }

            binding.customStudentTvName.setText(user.getFullName());
            binding.customStudentTvPhoneNumber.setText(String.valueOf(user.getMobileNumber()));
//            binding.customStudentTvBranch.setText(user.getBranchName());

        }
    }
}

