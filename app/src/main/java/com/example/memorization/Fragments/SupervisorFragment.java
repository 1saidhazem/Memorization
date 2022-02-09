package com.example.memorization.Fragments;

import static android.content.Context.MODE_PRIVATE;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.memorization.Adapters.SupervisorAdapter;
import com.example.memorization.RoomDatabase.MyViewModel;
import com.example.memorization.RoomDatabase.User;
import com.example.memorization.databinding.FragmentSupervisorBinding;
import java.util.ArrayList;
import java.util.List;

public class SupervisorFragment extends Fragment {

    private MyViewModel mvm;
    private SharedPreferences sp;
    private long idSupervisor;
    private SupervisorAdapter adapter;

    public SupervisorFragment() {
        // Required empty public constructor
    }

    public static SupervisorFragment newInstance() {
        return new SupervisorFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentSupervisorBinding binding = FragmentSupervisorBinding.inflate(getLayoutInflater(),container,false);
        mvm = new ViewModelProvider(this).get(MyViewModel.class);
        sp = getActivity().getSharedPreferences("dataUserLogin", MODE_PRIVATE);
        idSupervisor = sp.getLong("loginId",-1);

        adapter = new SupervisorAdapter(new ArrayList<>());

        mvm.getSupervisorById(idSupervisor).observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                adapter.setUsers(users);
            }
        });

        binding.fragmentSupervisorRv.setAdapter(adapter);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.fragmentSupervisorRv.setLayoutManager(lm);
        binding.fragmentSupervisorRv.setHasFixedSize(true);

        return binding.getRoot();
    }
}