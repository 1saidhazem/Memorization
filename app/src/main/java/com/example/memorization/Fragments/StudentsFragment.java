package com.example.memorization.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.memorization.Adapters.AllStudentAdapter;
import com.example.memorization.Adapters.AllWalletAdapter;
import com.example.memorization.R;
import com.example.memorization.RoomDatabase.MyViewModel;
import com.example.memorization.RoomDatabase.User;
import com.example.memorization.databinding.FragmentStudentsBinding;

import java.util.ArrayList;
import java.util.List;

public class StudentsFragment extends Fragment {

    private MyViewModel mvm;
    private SharedPreferences sp;
    private long idStudent;
    private AllStudentAdapter adapter;

    public StudentsFragment() {
        // Required empty public constructor
    }

    public static StudentsFragment newInstance(String param1, String param2) {
        return new StudentsFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentStudentsBinding binding = FragmentStudentsBinding.inflate(getLayoutInflater(),container,false);

        mvm = new ViewModelProvider(this).get(MyViewModel.class);
        sp = getActivity().getSharedPreferences("dataUserLogin", MODE_PRIVATE);
        idStudent = sp.getLong("loginId",-1);

        adapter = new AllStudentAdapter(new ArrayList<>());

        mvm.getStudentById(idStudent).observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                adapter.setUsersStudent(users);
            }
        });

        binding.fragmentStudentRv.setAdapter(adapter);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.fragmentStudentRv.setLayoutManager(lm);
        binding.fragmentStudentRv.setHasFixedSize(true);

        return binding.getRoot();
    }
}