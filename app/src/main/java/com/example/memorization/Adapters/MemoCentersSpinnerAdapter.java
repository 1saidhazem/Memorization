package com.example.memorization.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.memorization.R;
import com.example.memorization.RoomDatabase.Center;
import com.example.memorization.onClick;

import java.util.List;

public class MemoCentersSpinnerAdapter extends BaseAdapter {
    private List<Center> centers;

    public MemoCentersSpinnerAdapter(List<Center> centers) {
        this.centers = centers;
    }

    public void setCenters(List<Center> centers) {
        this.centers = centers;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return centers.size();
    }

    @Override
    public Center getItem(int i) {
        return centers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return centers.get(i).getId();
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
        Center center = centers.get(i);
        tv_name.setText(center.getName());

        return v;
    }
}
