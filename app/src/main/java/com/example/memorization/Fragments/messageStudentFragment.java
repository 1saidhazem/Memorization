package com.example.memorization.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.memorization.Adapters.MessageAdapter;
import com.example.memorization.R;
import com.example.memorization.RoomDatabase.Message;
import com.example.memorization.RoomDatabase.MyViewModel;
import com.example.memorization.RoomDatabase.User;
import com.example.memorization.databinding.FragmentMessageStudentBinding;
import com.example.memorization.onClick;

import java.util.ArrayList;
import java.util.List;

public class messageStudentFragment extends Fragment {

    private MyViewModel mvm;
    private MessageAdapter adapter;

    public messageStudentFragment() {
        // Required empty public constructor
    }

    public static messageStudentFragment newInstance() {
        return new messageStudentFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentMessageStudentBinding binding = FragmentMessageStudentBinding.inflate(getLayoutInflater(),container,false);
        mvm = new ViewModelProvider(this).get(MyViewModel.class);

        adapter = new MessageAdapter(new ArrayList<>(), new onClick<Integer>() {
            @Override
            public void onClickItem(Integer integer) {

            }
        });
        mvm.getAllMessage().observe(getViewLifecycleOwner(), new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                adapter.setMessages(messages);
            }
        });

        binding.fragmentMessageStudentRv.setAdapter(adapter);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.fragmentMessageStudentRv.setLayoutManager(lm);
        binding.fragmentMessageStudentRv.setHasFixedSize(true);

        return binding.getRoot();
    }
}