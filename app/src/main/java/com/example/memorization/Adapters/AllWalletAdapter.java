package com.example.memorization.Adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memorization.R;
import com.example.memorization.RoomDatabase.User;
import com.example.memorization.databinding.CustomStudentBinding;
import com.example.memorization.databinding.CustomWalletsBinding;

import java.util.List;

public class AllWalletAdapter extends RecyclerView.Adapter<AllWalletAdapter.WalletViewHolder> {

    private List<User> usersWallet;
    private String branch;

    public AllWalletAdapter(List<User> usersStudent,String branch) {
        this.usersWallet = usersStudent;
        this.branch = branch;
    }

    public void setUsersWallet(List<User> usersWallet) {
        this.usersWallet = usersWallet;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WalletViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_wallets , null , false);
        WalletViewHolder ViewHolder = new WalletViewHolder(v);
        return ViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WalletViewHolder holder, int position) {
        User wallet = usersWallet.get(position);
        holder.bind(wallet);
    }

    @Override
    public int getItemCount() {
        return usersWallet.size();
    }

    class WalletViewHolder extends RecyclerView.ViewHolder{

        CustomWalletsBinding binding;
        User user;

        public WalletViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = CustomWalletsBinding.bind(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
        public void bind(User wallet) {
            this.user=wallet;

            if (user.getImg() != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(user.getImg() ,0 , user.getImg().length);
                binding.customWalletsImg.setImageBitmap(bitmap);
            }else {
                binding.customWalletsImg.setImageResource(R.drawable.ic_default_logo_supervisor);
            }

            binding.customWalletsName.setText(user.getFullName());
            binding.customWalletsPhoneNumber.setText(String.valueOf(user.getMobileNumber()));
            binding.customWalletsBranch.setText(branch);

        }
    }
}

