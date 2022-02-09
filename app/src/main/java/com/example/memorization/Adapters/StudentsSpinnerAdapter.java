package com.example.memorization.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.memorization.R;
import com.example.memorization.RoomDatabase.StudentGroup;
import com.example.memorization.RoomDatabase.User;

import java.util.List;

public class StudentsSpinnerAdapter extends BaseAdapter {
    private List<User> students;

    public StudentsSpinnerAdapter(List<User> students) {
        this.students = students;
    }

    public void setStudents(List<User> students) {
        this.students = students;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public User getItem(int i) {
        return students.get(i);
    }

    @Override
    public long getItemId(int i) {
        return students.get(i).getIdentificationNumber();
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
        User student = students.get(i);
        tv_name.setText(student.getFullName());

        return v;
    }
}
