package com.example.memorization.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.memorization.Adapters.PagerAllDataAdapter;
import com.example.memorization.R;
import com.example.memorization.databinding.FragmentAllDataBinding;
import com.example.memorization.databinding.FragmentMemoCentersBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class allDataFragment extends Fragment {

    public allDataFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentAllDataBinding binding = FragmentAllDataBinding.inflate(getLayoutInflater(), container, false);
        String [] tabs = {"المراكز","الحلقات","المحفظين","المشرفين","الطلبة"};
        ArrayList<Fragment> list = new ArrayList<>();
        list.add(AllDataCenterFragment.newInstance());
        list.add(AllDataGroupFragment.newInstance());
        list.add(AllDataWalletFragment.newInstance());
        list.add(AllDataSupervisorFragment.newInstance());
        list.add(AllDataStudentFragment.newInstance());

        PagerAllDataAdapter adapter_pager = new PagerAllDataAdapter(getActivity(), list);
        binding.viewPager.setAdapter(adapter_pager);

        new TabLayoutMediator(binding.tabLayout,binding.viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabs[position]);
            }
        }).attach();

        return binding.getRoot();
    }
}