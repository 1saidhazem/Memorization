package com.example.memorization.Adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.memorization.R;
import com.example.memorization.RoomDatabase.Center;
import com.example.memorization.RoomDatabase.MyViewModel;
import com.example.memorization.RoomDatabase.User;
import com.example.memorization.databinding.CustomSupervisorBinding;

import java.util.List;

public class SupervisorAdapter extends RecyclerView.Adapter<SupervisorAdapter.SupervisorViewHolder> {

    private List<User> users;

    public SupervisorAdapter(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SupervisorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_supervisor , null , false);
        SupervisorAdapter.SupervisorViewHolder ViewHolder = new SupervisorAdapter.SupervisorViewHolder(v);
        return ViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SupervisorViewHolder holder, int position) {
        User user = users.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class SupervisorViewHolder extends RecyclerView.ViewHolder{

        CustomSupervisorBinding binding;
        User user;

        public SupervisorViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = CustomSupervisorBinding.bind(itemView);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    listener.onClickedAddNewCenter(center,id);
//                    notifyDataSetChanged();
//                }
//            });

        }
        public void bind(User user) {
            this.user = user;

            if (user.getImg() != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(user.getImg() ,0 , user.getImg().length);
                binding.customWalletsImg.setImageBitmap(bitmap);
            } else {
                // صورة افتراضية
                binding.customWalletsImg.setImageResource(R.drawable.ic_default_logo_supervisor);
            }
            if (!user.getFullName().isEmpty() && !user.getFullName().equals("")) {
                binding.customSupervisorName.setText(user.getFullName());
            }
            if (user.getMobileNumber() != 0){
                binding.customSupervisorPhoneNumber.setText(String.valueOf(user.getMobileNumber()));
            }
            if (!user.getAddress().isEmpty() && !user.getAddress().equals("")){
                binding.customSupervisorBranch.setText(user.getAddress());
            }

        }
    }
}
