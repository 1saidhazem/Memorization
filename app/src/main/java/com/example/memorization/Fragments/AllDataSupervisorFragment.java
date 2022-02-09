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
import com.example.memorization.Adapters.SupervisorAdapter;
import com.example.memorization.RoomDatabase.MyViewModel;
import com.example.memorization.RoomDatabase.User;
import com.example.memorization.databinding.FragmentAllDataSupervisorBinding;
import java.util.ArrayList;
import java.util.List;

public class AllDataSupervisorFragment extends Fragment {

    private SupervisorAdapter adapter;

    public AllDataSupervisorFragment() {
        // Required empty public constructor
    }

    public static AllDataSupervisorFragment newInstance() {
        return new AllDataSupervisorFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentAllDataSupervisorBinding binding = FragmentAllDataSupervisorBinding.inflate(getLayoutInflater(),container,false);

        MyViewModel mvm = new ViewModelProvider(this).get(MyViewModel.class);

        adapter = new SupervisorAdapter(new ArrayList<>());
        mvm.getAllSupervisor().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                adapter.setUsers(users);
            }
        });

        binding.fragmentAllDataSupervisorRv.setAdapter(adapter);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.fragmentAllDataSupervisorRv.setLayoutManager(lm);
        binding.fragmentAllDataSupervisorRv.setHasFixedSize(true);

        return binding.getRoot();
    }
}