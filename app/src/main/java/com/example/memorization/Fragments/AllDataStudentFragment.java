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
import com.example.memorization.Adapters.AllStudentAdapter;
import com.example.memorization.RoomDatabase.MyViewModel;
import com.example.memorization.RoomDatabase.User;
import com.example.memorization.databinding.FragmentAllDataStudentBinding;
import java.util.ArrayList;
import java.util.List;

public class AllDataStudentFragment extends Fragment {

    private MyViewModel mvm;
    private AllStudentAdapter adapter;

    public AllDataStudentFragment() {
        // Required empty public constructor
    }

    public static AllDataStudentFragment newInstance() {
        return new AllDataStudentFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentAllDataStudentBinding binding = FragmentAllDataStudentBinding.inflate(getLayoutInflater(), container, false);
        mvm = new ViewModelProvider(this).get(MyViewModel.class);

        adapter = new AllStudentAdapter(new ArrayList<>());

        mvm.getAllStudent().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                adapter.setUsersStudent(users);
            }
        });

        binding.fragmentAllDataStudentRv.setAdapter(adapter);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.fragmentAllDataStudentRv.setLayoutManager(lm);
        binding.fragmentAllDataStudentRv.setHasFixedSize(true);

        return binding.getRoot();
    }
}