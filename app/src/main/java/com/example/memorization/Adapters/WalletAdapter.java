package com.example.memorization.Adapters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memorization.Activities.WalletsActivity;
import com.example.memorization.R;
import com.example.memorization.RoomDatabase.CenterGroup;
import com.example.memorization.RoomDatabase.MyViewModel;
import com.example.memorization.RoomDatabase.User;
import com.example.memorization.RoomDatabase.Wallet;
import com.example.memorization.RoomDatabase.WalletGroup;
import com.example.memorization.databinding.CustomWalletsBinding;
import com.example.memorization.onClick;

import java.util.ArrayList;
import java.util.List;

public class WalletAdapter extends RecyclerView.Adapter<WalletAdapter.WalletViewHolder> {

    List<WalletGroup> usersWallet;
    private onClickAddNewWallet listener;
    private ListenerIdCenterAndGroup listenerIdCenterAndGroup;
    private onClick<Integer> clickListGroup;
    private MyViewModel mvm;

    public WalletAdapter(List<WalletGroup> usersWallet, MyViewModel mvm, onClickAddNewWallet listener, ListenerIdCenterAndGroup listenerIdCenterAndGroup, onClick<Integer> clickListGroup) {
        this.usersWallet = usersWallet;
        this.mvm = mvm;
        this.listener = listener;
        this.listenerIdCenterAndGroup = listenerIdCenterAndGroup;
        this.clickListGroup = clickListGroup;
    }

    public void setUsersWallet(List<WalletGroup> usersWallet) {
        this.usersWallet = usersWallet;
        notifyDataSetChanged();
    }

    public MyViewModel getMvm() {
        return mvm;
    }

    public void setMvm(MyViewModel mvm) {
        this.mvm = mvm;
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
        WalletGroup wallet = usersWallet.get(position);
        holder.bind(wallet,mvm,position);
    }

    @Override
    public int getItemCount() {
        return usersWallet.size();
    }

    class WalletViewHolder extends RecyclerView.ViewHolder{

        CustomWalletsBinding binding;
        WalletGroup wallet;
        MyViewModel mvm;

        public WalletViewHolder(@NonNull View itemView) {
            super(itemView);

            binding = CustomWalletsBinding.bind(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClickedAddNewWallet(wallet,getAdapterPosition());
                }
            });
        }


        public void bind(WalletGroup wallet,MyViewModel mvm,int position) {
            this.wallet = wallet;
            this.mvm = mvm;

            if (wallet.getUser().getImg() != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(wallet.getUser().getImg() ,0 , wallet.getUser().getImg().length);
                binding.customWalletsImg.setImageBitmap(bitmap);
            }else {
                binding.customWalletsImg.setImageResource(R.drawable.ic_default_logo_supervisor);
            }
            binding.customWalletsName.setText(wallet.getUser().getFullName());
            binding.customWalletsPhoneNumber.setText(String.valueOf(wallet.getUser().getMobileNumber()));
            binding.customWalletsBranch.setText(wallet.getWallet().getBranch());
            binding.customWalletLinearListGroups.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListGroup.onClickItem(getAdapterPosition());
                }
            });
        }
    }
}
