package com.example.memorization.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.memorization.RoomDatabase.InfoLogin;
import com.example.memorization.RoomDatabase.MyViewModel;
import com.example.memorization.RoomDatabase.User;
import com.example.memorization.databinding.ActivityLoginBinding;
import com.example.memorization.onClick;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    private SharedPreferences sp;
    private SharedPreferences.Editor edit;
    private MyViewModel mvm;
    private List<User> userList;
    private String IdentificationNumber = "", passwordUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mvm = new ViewModelProvider(this).get(MyViewModel.class);
        sp = getSharedPreferences("dataUserLogin", MODE_PRIVATE);
        edit = sp.edit();

        String identification = sp.getString("identification", "").trim();
        String password = sp.getString("password", "").trim();

        binding.etIdentification.setText(identification);
        binding.etPassword.setText(password);

        binding.tvRegisterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), SignUpActivity.class));
            }
        });

        binding.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String loginId = binding.etIdentification.getText().toString().trim();
                String loginPass = binding.etPassword.getText().toString().trim();
                if (loginId.isEmpty() && loginPass.isEmpty()) {
                    Toast.makeText(getBaseContext(), "الرجاء تعبئة بيانات تسجيل الدخول", Toast.LENGTH_SHORT).show();
                } else {
                    edit.putLong("loginId", Long.parseLong(loginId));
                    edit.putString("loginPass", loginPass);
                    edit.commit();
                    mvm.getUserById(Long.parseLong(loginId), new onClick<User>() {
                        @Override
                        public void onClickItem(User user) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (user != null) {
                                        int validity = user.getValidity();
                                        edit.putInt("validity", validity);
                                        edit.commit();

                                        if (user.getPassword().equals(loginPass)) {
                                            if (validity == 0) {
                                                Toast.makeText(getBaseContext(), "تم تسجيل الدخول ك مدير", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getBaseContext(), MainActivity.class));
                                                finish();
                                            } else if (validity == 1) {
                                                Toast.makeText(getBaseContext(), "تم تسجيل الدخول ك مشرف", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getBaseContext(), MainActivity.class));
                                                finish();
                                            } else if (validity == 2) {
                                                Toast.makeText(getBaseContext(), "تم تسجيل الدخول ك محفظ", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getBaseContext(), MainActivity.class));
                                                finish();
                                            } else if (validity == 3) {
                                                Toast.makeText(getBaseContext(), "تم تسجيل الدخول ك طالب", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(getBaseContext(), MainActivity.class));
                                                finish();
                                            }
                                        } else {
                                            Toast.makeText(getBaseContext(), "كلمة المرور غير صحيحة", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(getBaseContext(), "الحساب غير موجود", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });

    }
}