package com.example.memorization.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.memorization.R;
import com.example.memorization.RoomDatabase.User;

import java.util.List;

public class WalletsSpinnerAdapter extends BaseAdapter {
    private List<User> wallets;

    public WalletsSpinnerAdapter(List<User> wallets) {
        this.wallets = wallets;
    }

    public void setWallets(List<User> wallets) {
        this.wallets = wallets;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return wallets.size();
    }

    @Override
    public User getItem(int i) {
        return wallets.get(i);
    }

    @Override
    public long getItemId(int i) {
        return wallets.get(i).getIdentificationNumber();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if(v==null){
            v = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.custom_spinner_item,
                            null,false);
        }
        TextView tv_name = v.findViewById(R.id.tv_custom_sp);
        User wallet = wallets.get(i);
        tv_name.setText(wallet.getFullName());

        return v;
    }
}
