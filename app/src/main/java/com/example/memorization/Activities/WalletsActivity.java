package com.example.memorization.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.memorization.Adapters.ListenerIdCenterAndGroup;
import com.example.memorization.Adapters.WalletAdapter;
import com.example.memorization.Adapters.onClickAddNewWallet;
import com.example.memorization.RoomDatabase.MyViewModel;
import com.example.memorization.RoomDatabase.User;
import com.example.memorization.RoomDatabase.WalletGroup;
import com.example.memorization.databinding.ActivityWalletsBinding;
import com.example.memorization.onClick;

import java.util.ArrayList;
import java.util.List;

public class WalletsActivity extends AppCompatActivity {

    ActivityWalletsBinding binding;
    private int walletId;
    private WalletAdapter adapter;
    private MyViewModel mvm;
    private int IdCenter, IdGroup, validity;
    private SharedPreferences sp1, sp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWalletsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sp1 = getSharedPreferences("centerId", MODE_PRIVATE);
        sp2 = getSharedPreferences("dataUserLogin", MODE_PRIVATE);

        mvm = new ViewModelProvider(this).get(MyViewModel.class);
        int idGroup = getIntent().getIntExtra("idGroup", -1);

        validity = sp2.getInt("validity", -1);


        adapter = new WalletAdapter(new ArrayList<>(), mvm, new onClickAddNewWallet() {
            @Override
            public void onClickedAddNewWallet(WalletGroup wallet, int position) {
                if (validity == 0 || validity == 1) {
                    Intent intent = new Intent(getBaseContext(), AddNewWalletsActivity.class);
                    intent.putExtra("positionWallet", 1);
                    intent.putExtra("walletName", wallet.getUser().getFullName());
                    intent.putExtra("walletAddress", wallet.getUser().getAddress());
                    intent.putExtra("walletId", wallet.getUser().getIdentificationNumber());
                    intent.putExtra("walletPhoneNumber", wallet.getUser().getMobileNumber());
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "ليس لديك الصلاحية لعرض بيانات المحفظ", Toast.LENGTH_SHORT).show();
                }
            }
        }, new ListenerIdCenterAndGroup() {
            @Override
            public void onReturned(int idCenter, int idGroup) {
                IdCenter = idCenter;
                IdGroup = idGroup;
            }
        },
                // click List Groups
                new onClick<Integer>() {
                    @Override
                    public void onClickItem(Integer integer) {
                        Intent intent = new Intent(getBaseContext(), GroupMemorizationActivity.class);
                        intent.putExtra("positionWallet", integer);
                        startActivity(intent);
                    }
                });


        mvm.getAllWalletInGroup(idGroup).observe(this, new Observer<List<WalletGroup>>() {
            @Override
            public void onChanged(List<WalletGroup> walletGroups) {
                adapter.setUsersWallet(walletGroups);
            }
        });


        if (validity != 0 && validity != 1) {
            binding.walletsFab.setVisibility(View.GONE);
        } else {
            binding.walletsFab.setVisibility(View.VISIBLE);
            binding.walletsFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getBaseContext(), AddNewWalletsActivity.class));
                }
            });
        }

        binding.walletsRv.setAdapter(adapter);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        binding.walletsRv.setLayoutManager(lm);
        binding.walletsRv.setHasFixedSize(true);
    }
}