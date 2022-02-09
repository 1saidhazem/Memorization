package com.example.memorization.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.memorization.R;
import com.example.memorization.RoomDatabase.Center;
import com.example.memorization.RoomDatabase.Group;

import java.util.List;

public class GroupsSpinnerAdapter extends BaseAdapter {
    private List<Group> groups;

    public GroupsSpinnerAdapter(List<Group> groups) {
        this.groups = groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return groups.size();
    }

    @Override
    public Group getItem(int i) {
        return groups.get(i);
    }

    @Override
    public long getItemId(int i) {
        return groups.get(i).getId();
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
        Group group = groups.get(i);
        tv_name.setText(group.getName());

        return v;
    }
}
