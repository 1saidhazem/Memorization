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
import com.example.memorization.Adapters.SupervisorAdapter;
import com.example.memorization.Adapters.WalletAdapter;
import com.example.memorization.R;
import com.example.memorization.RoomDatabase.MyViewModel;
import com.example.memorization.RoomDatabase.User;
import com.example.memorization.databinding.FragmentWalletsBinding;

import java.util.ArrayList;
import java.util.List;

public class WalletsFragment extends Fragment {

    private MyViewModel mvm;
    private SharedPreferences sp;
    private long idWallet;
    private AllWalletAdapter adapter;

    public WalletsFragment() {
        // Required empty public constructor
    }

    public static WalletsFragment newInstance() {
        return new WalletsFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentWalletsBinding binding = FragmentWalletsBinding.inflate(getLayoutInflater(),container,false);

        mvm = new ViewModelProvider(this).get(MyViewModel.class);
        sp = getActivity().getSharedPreferences("dataUserLogin", MODE_PRIVATE);
        idWallet = sp.getLong("loginId",-1);
        adapter = new AllWalletAdapter(new ArrayList<>(),"");

        mvm.getWalletById(idWallet).observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                adapter.setUsersWallet(users);
            }
        });

        binding.fragmentWalletsRv.setAdapter(adapter);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.fragmentWalletsRv.setLayoutManager(lm);
        binding.fragmentWalletsRv.setHasFixedSize(true);

        return binding.getRoot();
    }
}