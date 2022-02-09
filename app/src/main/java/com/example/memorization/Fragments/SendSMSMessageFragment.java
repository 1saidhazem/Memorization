package com.example.memorization.Fragments;

import android.Manifest;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.memorization.Maps.MapsActivity;
import com.example.memorization.R;
import com.example.memorization.RoomDatabase.MyViewModel;
import com.example.memorization.databinding.FragmentSendSMSMessageBinding;
import com.google.android.gms.maps.SupportMapFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;


public class SendSMSMessageFragment extends Fragment {

    FragmentSendSMSMessageBinding binding;
    private MyViewModel mvm;
    String msgBody;
    List<Long> mobileNumber;

    public SendSMSMessageFragment() {
        // Required empty public constructor
    }

    public static SendSMSMessageFragment newInstance() {
        return new SendSMSMessageFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSendSMSMessageBinding.inflate(getLayoutInflater(), container, false);
        mvm = new ViewModelProvider(this).get(MyViewModel.class);

        SimpleDateFormat format = new SimpleDateFormat("HH:mm a");
        binding.fragmentSendMsgTvDate.setText(format.format(Calendar.getInstance().getTime()));

        ActivityResultLauncher<String[]> arl = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
            @Override
            public void onActivityResult(Map<String, Boolean> result) {
                if (result.get(Manifest.permission.SEND_SMS) &&
                        result.get(Manifest.permission.READ_SMS)) {
                    sendSMSMessage();
                }
            }
        });

        arl.launch(new String[]{Manifest.permission.SEND_SMS, Manifest.permission.READ_SMS});

        return binding.getRoot();
    }

    void sendSMSMessage() {
        mobileNumber = new ArrayList<>();
        mvm.getAllPhoneNumberStudent().observe(getViewLifecycleOwner(), new Observer<List<Long>>() {
            @Override
            public void onChanged(List<Long> longs) {
                mobileNumber = longs;
            }
        });
        SmsManager sms = SmsManager.getDefault();

        binding.fragmentSendMsgBtnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msgBody = binding.fragmentSendMsgEtBody.getText().toString().trim();
                if (!mobileNumber.isEmpty()) {
                    for (Long phones : mobileNumber) {
                        sms.sendTextMessage(String.valueOf(phones), null, msgBody, null, null);
                    }
                    binding.fragmentSendMsgEtBody.setText("");
                    Toast.makeText(getContext(), "تم ارسال الرسالة لجميع الطلبة", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}