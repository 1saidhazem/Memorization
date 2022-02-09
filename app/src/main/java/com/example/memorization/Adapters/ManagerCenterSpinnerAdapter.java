package com.example.memorization.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.memorization.R;
import com.example.memorization.RoomDatabase.User;

import java.util.List;

public class ManagerCenterSpinnerAdapter extends BaseAdapter {
    private List<User> Managers;

    public ManagerCenterSpinnerAdapter(List<User> Managers) {
        this.Managers = Managers;
    }

    public List<User> getUsersManager() {
        return Managers;
    }

    public void setUsersManager(List<User> Managers) {
        this.Managers = Managers;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return Managers.size();
    }

    @Override
    public User getItem(int i) {
        return Managers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return Managers.get(i).getIdentificationNumber();
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
        User Manager = Managers.get(i);

        tv_name.setText(Manager.getFullName());

        return v;
    }
}
