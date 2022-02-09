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
import com.example.memorization.Adapters.CenterAdapter;
import com.example.memorization.Adapters.onClickAddNewCenter;
import com.example.memorization.Adapters.onClickListGroup;
import com.example.memorization.RoomDatabase.Center;
import com.example.memorization.RoomDatabase.MyViewModel;
import com.example.memorization.databinding.FragmentAllDataCenterBinding;
import com.example.memorization.onClick;
import java.util.ArrayList;
import java.util.List;

public class AllDataCenterFragment extends Fragment {

    private CenterAdapter adapter;

    public AllDataCenterFragment() {
        // Required empty public constructor
    }

    public static AllDataCenterFragment newInstance() {
        return new AllDataCenterFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentAllDataCenterBinding binding = FragmentAllDataCenterBinding.inflate(getLayoutInflater(),container,false);
        MyViewModel mvm = new ViewModelProvider(this).get(MyViewModel.class);

        adapter = new CenterAdapter(new ArrayList<>(), new onClickAddNewCenter() {
            @Override
            public void onClickedAddNewCenter(Center center, int id) {

            }
        }, new onClickListGroup() {
            @Override
            public void onClicked(int idCenter, Center center) {

            }
        }, new onClick() {
            @Override
            public void onClickItem(Object o) {

            }
        });

        mvm.getAllCenter().observe(getViewLifecycleOwner(), new Observer<List<Center>>() {
            @Override
            public void onChanged(List<Center> centers) {
                adapter.setCenters(centers);
            }
        });

        binding.fragmentAllDataCenterRv.setAdapter(adapter);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.fragmentAllDataCenterRv.setLayoutManager(lm);
        binding.fragmentAllDataCenterRv.setHasFixedSize(true);

        return binding.getRoot();
    }
}