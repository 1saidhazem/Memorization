package com.example.memorization.Activities;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.memorization.Fragments.GroupsFragment;
import com.example.memorization.Fragments.HomeFragment;
import com.example.memorization.Fragments.SendSMSMessageFragment;
import com.example.memorization.Fragments.StudentsFragment;
import com.example.memorization.Fragments.SupervisorFragment;
import com.example.memorization.Fragments.WalletsFragment;
import com.example.memorization.Fragments.allDataFragment;
import com.example.memorization.Fragments.memoCentersFragment;
import com.example.memorization.Fragments.messageStudentFragment;
import com.example.memorization.Fragments.settingFragment;
import com.example.memorization.R;

import com.example.memorization.databinding.ActivityMainBinding;
import com.example.memorization.databinding.FragmentSendSMSMessageBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ActionBarDrawerToggle toggle;
    private SharedPreferences sp;
    private Fragment fragment = null;
    private long idUser;
    private int validity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.mainToolbar);
        toggle = new ActionBarDrawerToggle(this, binding.drawerLayout, R.string.Open, R.string.Close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sp = getSharedPreferences("dataUserLogin", MODE_PRIVATE);
        idUser = sp.getLong("loginId", -1);
        validity = sp.getInt("validity", -1);

        /*
        if (validity != 0){
            NavigationView navigationView = findViewById(R.id.nv);
            Menu navMenu = navigationView.getMenu();
            navMenu.findItem(R.id.nav_all_data).setVisible(false);
//            navMenu.findItem(R.id.nav_memorization_center).setVisible(false);
            navMenu.findItem(R.id.nav_wallets).setVisible(false);
            navMenu.findItem(R.id.nav_groups).setVisible(false);
            navMenu.findItem(R.id.nav_students).setVisible(false);
        }
        if (validity != 1){
            NavigationView navigationView = findViewById(R.id.nv);
            Menu navMenu = navigationView.getMenu();
            navMenu.findItem(R.id.nav_send_message).setVisible(false);
            navMenu.findItem(R.id.nav_wallets).setVisible(false);
            navMenu.findItem(R.id.nav_groups).setVisible(false);
            navMenu.findItem(R.id.nav_students).setVisible(false);
        }
        if (validity != 2){
            NavigationView navigationView = findViewById(R.id.nv);
            Menu navMenu = navigationView.getMenu();
            navMenu.findItem(R.id.nav_send_message).setVisible(false);
            navMenu.findItem(R.id.nav_wallets).setVisible(false);
            navMenu.findItem(R.id.nav_students).setVisible(false);
        }
        if (validity != 3){
            NavigationView navigationView = findViewById(R.id.nv);
            Menu navMenu = navigationView.getMenu();
            navMenu.findItem(R.id.nav_students).setVisible(false);
            navMenu.findItem(R.id.nav_groups).setVisible(false);
        }
        */


        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();

        binding.nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.nav_home:
                        fragment = new HomeFragment();
                        getSupportActionBar().setTitle("Memorization Centers");
                        break;
                    case R.id.nav_memorization_center:
                        if (validity==0 || validity==1) {
//                            NavigationView navigationView = findViewById(R.id.nv);
//                            Menu navMenu = navigationView.getMenu();
//                            navMenu.findItem(R.id.nav_memorization_center).setVisible(true);
                            fragment = new memoCentersFragment();
                        } else {
                            Toast.makeText(getBaseContext(), "ليس لديك الصلاحية للوصول الى جميع المراكز", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.nav_supervisors:
                        if (validity == 1) {
                            fragment = new SupervisorFragment();
                        }else {
                            Toast.makeText(getBaseContext(), "ليس لديك الصلاحية للوصول الى للمشرفين", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.nav_wallets:
                        if (validity == 2) {
                            fragment = new WalletsFragment();
                        }else {
                            Toast.makeText(getBaseContext(), "ليس لديك الصلاحية للوصول الى للمحفظين", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.nav_groups:
                        if (validity == 3 || validity == 1) {
                            fragment = new GroupsFragment();
                        }else {
                            Toast.makeText(getBaseContext(), "ليس لديك الصلاحية للوصول الى للحلقات", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.nav_students:
                        if (validity == 3) {
                            fragment = new StudentsFragment();
                        }else {
                            Toast.makeText(getBaseContext(), "ليس لديك الصلاحية للوصول الى للطلاب", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.nav_send_message:
                        if (validity == 1 || validity == 2) {
                            fragment = new SendSMSMessageFragment();

                        } else {
                            Toast.makeText(getBaseContext(), "ليس لديك الصلاحية لارسال رسائل للطلبة", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.nav_all_data:
                        if (validity == 0) {
                            fragment = new allDataFragment();
                        } else {
                            Toast.makeText(getBaseContext(), "ليس لديك الصلاحية للوصول الى جميع البيانات", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.nav_setting:
                        fragment = new settingFragment();
                        break;
                    case R.id.nav_message_student:
                        if (validity == 3) {
                            fragment = new messageStudentFragment();
                        } else {
                            Toast.makeText(getBaseContext(), "ليس لديك الصلاحية لعرض الرسائل", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case R.id.nav_sign_out:
                        startActivity(new Intent(getBaseContext(), LoginActivity.class));
                        finish();
                        break;
                }
                if (fragment == null) {
                    fragment = new HomeFragment();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

                binding.drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return false;
    }

}