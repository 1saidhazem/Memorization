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

import com.example.memorization.Adapters.GroupMemorizationAdapter;
import com.example.memorization.Adapters.onClickAddNewGroup;
import com.example.memorization.Adapters.onClickListWallet;
import com.example.memorization.R;
import com.example.memorization.RoomDatabase.Group;
import com.example.memorization.RoomDatabase.MyViewModel;
import com.example.memorization.databinding.FragmentGroupsBinding;
import com.example.memorization.onClick;

import java.util.ArrayList;
import java.util.List;

public class GroupsFragment extends Fragment {

    private MyViewModel mvm;
    private GroupMemorizationAdapter adapter;
    private SharedPreferences sp;
    private long idSupervisor;
    private int validity;


    public GroupsFragment() {
        // Required empty public constructor
    }

    public static GroupsFragment newInstance() {
        return new GroupsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentGroupsBinding binding = FragmentGroupsBinding.inflate(getLayoutInflater(),container,false);
        mvm = new ViewModelProvider(this).get(MyViewModel.class);
        sp = getActivity().getSharedPreferences("dataUserLogin", MODE_PRIVATE);
        validity = sp.getInt("validity", -1);
        idSupervisor = sp.getLong("loginId",-1);

        adapter = new GroupMemorizationAdapter(new ArrayList<>(),
                new onClickAddNewGroup() {
                    @Override
                    public void onClickedAddNewGroup(Group group, int countItem) {
                    }
                },
                new onClickListWallet() {
                    @Override
                    public void onClicked(int position) {

                    }
                },
                new onClick<Integer>() {
                    @Override
                    public void onClickItem(Integer integer) {

                    }
                });

        if (validity==1){

            mvm.getGroupSupervisor(idSupervisor).observe(getViewLifecycleOwner(),
                    new Observer<List<Group>>() {
                @Override
                public void onChanged(List<Group> groups) {
                    adapter.setGroups(groups);
                }
            });

        }
        else if (validity==3){

            mvm.getGroupStudent(idSupervisor).observe(getViewLifecycleOwner(),
                    new Observer<List<Group>>() {
                @Override
                public void onChanged(List<Group> groups) {
                    adapter.setGroups(groups);
                }
            });

        }

        binding.fragmentGroupMemorizationRv.setAdapter(adapter);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.fragmentGroupMemorizationRv.setLayoutManager(lm);
        binding.fragmentGroupMemorizationRv.setHasFixedSize(true);

        return binding.getRoot();
    }
}