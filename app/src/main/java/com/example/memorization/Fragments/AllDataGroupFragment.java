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
import com.example.memorization.Adapters.GroupMemorizationAdapter;
import com.example.memorization.Adapters.onClickAddNewGroup;
import com.example.memorization.Adapters.onClickListWallet;
import com.example.memorization.RoomDatabase.Group;
import com.example.memorization.RoomDatabase.MyViewModel;
import com.example.memorization.databinding.FragmentAllDataGroupBinding;
import com.example.memorization.onClick;
import java.util.ArrayList;
import java.util.List;

public class AllDataGroupFragment extends Fragment {

    private GroupMemorizationAdapter adapter;

    public AllDataGroupFragment() {
        // Required empty public constructor
    }

    public static AllDataGroupFragment newInstance() {
        return new AllDataGroupFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentAllDataGroupBinding binding = FragmentAllDataGroupBinding.inflate(getLayoutInflater(),container,false);

        MyViewModel mvm = new ViewModelProvider(this).get(MyViewModel.class);

        adapter = new GroupMemorizationAdapter( new ArrayList<>(),
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
                new onClick() {
                    @Override
                    public void onClickItem(Object o) {
                    }
                });

        mvm.getAllGroup().observe(getViewLifecycleOwner(), new Observer<List<Group>>() {
            @Override
            public void onChanged(List<Group> groups) {
                adapter.setGroups(groups);
            }
        });

        binding.fragmentAllDataGroupRv.setAdapter(adapter);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.fragmentAllDataGroupRv.setLayoutManager(lm);
        binding.fragmentAllDataGroupRv.setHasFixedSize(true);

        return binding.getRoot();
    }
}