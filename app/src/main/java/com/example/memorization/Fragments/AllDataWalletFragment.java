package com.example.memorization.Fragments;

import static android.content.Context.MODE_PRIVATE;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.memorization.Adapters.AllWalletAdapter;
import com.example.memorization.RoomDatabase.MyViewModel;
import com.example.memorization.RoomDatabase.User;
import com.example.memorization.databinding.FragmentAllDataWalletBinding;
import java.util.ArrayList;
import java.util.List;

public class AllDataWalletFragment extends Fragment {

    private AllWalletAdapter adapter;
    private SharedPreferences sp;

    public AllDataWalletFragment() {
        // Required empty public constructor
    }

    public static AllDataWalletFragment newInstance() {
        return new AllDataWalletFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentAllDataWalletBinding binding = FragmentAllDataWalletBinding.inflate(getLayoutInflater(),container,false);
        MyViewModel mvm = new ViewModelProvider(this).get(MyViewModel.class);
        sp = getActivity().getSharedPreferences("dataUserLogin", MODE_PRIVATE);
        String branch = sp.getString("branchWallet","");
        adapter = new AllWalletAdapter(new ArrayList<>(),branch);

        mvm.getAllWallet().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                adapter.setUsersWallet(users);
            }
        });

        binding.fragmentAllDataWalletRv.setAdapter(adapter);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.fragmentAllDataWalletRv.setLayoutManager(lm);
        binding.fragmentAllDataWalletRv.setHasFixedSize(true);

        return binding.getRoot();
    }
}