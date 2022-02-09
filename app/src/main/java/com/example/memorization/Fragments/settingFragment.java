package com.example.memorization.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.memorization.Activities.MainActivity;
import com.example.memorization.MyReceiver;
import com.example.memorization.R;
import com.example.memorization.RoomDatabase.MyViewModel;
import com.example.memorization.databinding.FragmentSettingBinding;
import com.example.memorization.onClick;

import java.util.Locale;

public class settingFragment extends Fragment {

    private MyViewModel mvm;
    private MyReceiver receiver;
    private SharedPreferences sp;
    private long idUser;

    public settingFragment() {
        // Required empty public constructor
    }

    public static settingFragment newInstance() {
        return new settingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentSettingBinding binding = FragmentSettingBinding.inflate(getLayoutInflater(), container, false);
        mvm = new ViewModelProvider(this).get(MyViewModel.class);
        sp = getActivity().getSharedPreferences("dataUserLogin", MODE_PRIVATE);
        idUser = sp.getLong("loginId", -1);

        binding.fragmentSettingTvSyncData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ComponentName componentName = new ComponentName(getContext(), ScheduleDataSync.class);
//                JobInfo info = new JobInfo.Builder(ID_JOB_INFO,componentName)
//                        .setPeriodic(3000)
//                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
//                        .build();
//                JobScheduler scheduler = (JobScheduler) getContext().getSystemService(Context.JOB_SCHEDULER_SERVICE);
//                scheduler.schedule(info);
            }
        });

        binding.fragmentSettingTvChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });


        binding.fragmentSettingSpinnerAppLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String language = (String) adapterView.getItemAtPosition(i);
                switch (language) {
                    case "Arabic":
                        setLocale(getActivity(), "ar");
                        break;
                    case "English":
                        setLocale(getActivity(), "en");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        receiver = new MyReceiver();

        binding.fragmentSettingTvActivateMessageMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentFilter filter = new IntentFilter();
                filter.addAction("android.provider.telephony.SMS_RECIEVED");
                getActivity().registerReceiver(receiver,filter);
                Toast.makeText(getContext(), "تم تشغيل وضع الرسائل", Toast.LENGTH_SHORT).show();
            }
        });

            binding.fragmentSettingTvUnActivateMessageMode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().unregisterReceiver(receiver);
                    Toast.makeText(getContext(), "تم فصل وضع الرسائل", Toast.LENGTH_SHORT).show();
                }
            });


        return binding.getRoot();
    }

    public void setLocale(Activity activity, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
        Intent refresh = new Intent(getContext(), MainActivity.class);
        startActivity(refresh);
        getActivity().finish();
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.change_password);

        EditText etOldPass = dialog.findViewById(R.id.change_password_et_old_pass);
        EditText etNewPass = dialog.findViewById(R.id.change_password_et_new_pass);
        EditText etConfirmNewPass = dialog.findViewById(R.id.change_password_et_confirm_new_pass);
        Button changePass = dialog.findViewById(R.id.change_password_btn_change);

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPass = etOldPass.getText().toString().trim();
                String newPas = etNewPass.getText().toString().trim();
                String confirmNewPass = etConfirmNewPass.getText().toString().trim();
                mvm.getPasswordCurrentUser(idUser, new onClick<String>() {
                    @Override
                    public void onClickItem(String s) {
                        if (!oldPass.isEmpty() && oldPass.equals(s)) {
                            if (newPas.equals(confirmNewPass) && !newPas.isEmpty() && !confirmNewPass.isEmpty()) {
                                mvm.updatePassword(newPas, idUser);
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getContext(), "تم تغيير كلمة المرور بنجاح", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                });
                            } else {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getContext(), "كلمة المرور الجديدة غير متطابقة", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } else {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getContext(), "كلمة المرور القديمة غير صحيحة", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

}

