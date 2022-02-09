package com.example.memorization.Fragments;

import static android.content.Context.MODE_PRIVATE;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.example.memorization.Activities.AddNewCenterActivity;
import com.example.memorization.Activities.ProfileCenterActivity;
import com.example.memorization.Activities.GroupMemorizationActivity;
import com.example.memorization.Adapters.CenterAdapter;
import com.example.memorization.Adapters.onClickAddNewCenter;
import com.example.memorization.Adapters.onClickListGroup;
import com.example.memorization.Maps.ShowMapsActivity;
import com.example.memorization.R;
import com.example.memorization.RoomDatabase.Center;
import com.example.memorization.RoomDatabase.MyViewModel;
import com.example.memorization.databinding.FragmentMemoCentersBinding;
import com.example.memorization.onClick;
import java.util.ArrayList;
import java.util.List;

public class memoCentersFragment extends Fragment {

    private CenterAdapter adapter;
    private MyViewModel mvm;
    double Longitude, Latitude;
    int id_center;
    long idManager;

    public memoCentersFragment() {
        // Required empty public constructor
    }

    public static memoCentersFragment newInstance() {
        return new memoCentersFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentMemoCentersBinding binding = FragmentMemoCentersBinding.inflate(getLayoutInflater(), container, false);
        mvm = new ViewModelProvider(this).get(MyViewModel.class);

        SharedPreferences sp = getActivity().getSharedPreferences("dataUserLogin", MODE_PRIVATE);
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.memoCenterToolbar);

        int validity = sp.getInt("validity", -1);
        idManager = sp.getLong("loginId", -1);
        if (validity != 0) {
            binding.fragmentMemoCenterFabAddCenter.setVisibility(View.GONE);
        }
        if (validity == 0){
            // مدير
            binding.fragmentMemoCenterFabAddCenter.setVisibility(View.VISIBLE);
            binding.fragmentMemoCenterFabAddCenter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getContext(), AddNewCenterActivity.class));
                }
            });

            adapter = new CenterAdapter(new ArrayList<>(), new onClickAddNewCenter() {
                @Override
                public void onClickedAddNewCenter(Center center, int id) {
                    Intent intent = new Intent(getContext(), ProfileCenterActivity.class);
                    intent.putExtra("center_id", id);
                    intent.putExtra("centerName", center.getName());
                    intent.putExtra("NumberOfGroupsAllowed", center.getNumberOfGroupsAllowed());
//                intent.putExtra("imgCenter", center.getLogo());  // Permission Denial
                    startActivity(intent);
                }
            },
                    // click List Group
                    new onClickListGroup() {
                        @Override
                        public void onClicked(int idCenter, Center center) {
                            Intent intent = new Intent(getActivity(), GroupMemorizationActivity.class);
                            intent.putExtra("idCenter", idCenter);
                            intent.putExtra("centerName", center.getName());
                            id_center = idCenter;
                            Log.d("ttt", "id Center" + idCenter);
                            startActivity(intent);
                        }
                    },
                    // click Map
                    new onClick<Integer>() {
                        @Override
                        public void onClickItem(Integer integer) {
                            Intent intent = new Intent(getContext(), ShowMapsActivity.class);
                            intent.putExtra("idCenter", integer);
                            intent.putExtra("Longitude", Longitude);
                            intent.putExtra("Latitude", Latitude);

                            startActivity(intent);
                        }
                    });

            mvm.getAllCenterManager(idManager).observe(getViewLifecycleOwner(), new Observer<List<Center>>() {
                @Override
                public void onChanged(List<Center> centers) {
                    adapter.setCenters(centers);
                }
            });

            binding.fragmentMemoCenterRv.setAdapter(adapter);
            RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            binding.fragmentMemoCenterRv.setLayoutManager(lm);
            binding.fragmentMemoCenterRv.setHasFixedSize(true);
        }
        else if (validity == 1){
            // مشرف
            adapter = new CenterAdapter(new ArrayList<>(), new onClickAddNewCenter() {
                @Override
                public void onClickedAddNewCenter(Center center, int id) {
                }
            },
                    // click List Group
                    new onClickListGroup() {
                        @Override
                        public void onClicked(int idCenter, Center center) {
                        }
                    },
                    // click Map
                    new onClick<Integer>() {
                        @Override
                        public void onClickItem(Integer integer) {
                        }
                    });

            mvm.getCenterSupervisor(idManager).observe(getViewLifecycleOwner(), new Observer<List<Center>>() {
                @Override
                public void onChanged(List<Center> centers) {
                    adapter.setCenters(centers);
                }
            });

            binding.fragmentMemoCenterRv.setAdapter(adapter);
            RecyclerView.LayoutManager lm = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
            binding.fragmentMemoCenterRv.setLayoutManager(lm);
            binding.fragmentMemoCenterRv.setHasFixedSize(true);
        }
        return binding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.memo_center_menu, menu);
        MenuItem mSearchMenuItem = menu.findItem(R.id.memo_center_search);
        SearchView searchView = (SearchView) mSearchMenuItem.getActionView();
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.isEmpty() || !query.equals("")) {
                    mvm.SearchCenter(query).observe(getViewLifecycleOwner(), new Observer<List<Center>>() {
                        @Override
                        public void onChanged(List<Center> centers) {
                            adapter.setCenters(centers);
                        }
                    });
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty() || !newText.equals("")) {
                    mvm.SearchCenter(newText).observe(getViewLifecycleOwner(), new Observer<List<Center>>() {
                        @Override
                        public void onChanged(List<Center> centers) {
                            adapter.setCenters(centers);
                        }
                    });
                }
                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mvm.getAllCenterManager(idManager).observe(getViewLifecycleOwner(), new Observer<List<Center>>() {
                    @Override
                    public void onChanged(List<Center> centers) {
                        adapter.setCenters(centers);
                    }
                });
                return false;
            }
        });
    }
}
