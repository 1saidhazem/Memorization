package com.example.memorization.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.memorization.Activities.ScannerBarCodeActivity;
import com.example.memorization.R;
import com.example.memorization.RoomDatabase.MyViewModel;
import com.example.memorization.RoomDatabase.countGroupListener;
import com.example.memorization.databinding.FragmentHomeBinding;
import com.example.memorization.onClick;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentHomeBinding binding = FragmentHomeBinding.inflate(getLayoutInflater(),container,false);

        MyViewModel mvm = new ViewModelProvider(this).get(MyViewModel.class);


        binding.homeImgScanBarCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ScannerBarCodeActivity.class));
            }
        });

        mvm.getCountGroup(new countGroupListener() {
            @Override
            public void valueCountGroup(int countGroup) {
                binding.homeTvCountGroupMemo.post(new Runnable() {
                    @Override
                    public void run() {
                        binding.homeTvCountGroupMemo.setText(String.valueOf(countGroup));
                    }
                });
            }
        });

        mvm.getCountStudent(new onClick<Integer>() {
            @Override
            public void onClickItem(Integer integer) {
                binding.homeTvCountStudent.post(new Runnable() {
                    @Override
                    public void run() {
                        binding.homeTvCountStudent.setText(String.valueOf(integer));
                    }
                });
            }
        });

        return binding.getRoot();
    }
}