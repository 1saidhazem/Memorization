package com.example.memorization;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsMessage;

import java.util.ArrayList;
import java.util.List;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                Object[] pdus = (Object[]) bundle.get("pdus");
                SmsMessage[] messages = new SmsMessage[pdus.length];
                List<SmsMessage[]> list = new ArrayList<>();
                for (int i =0 ;i < pdus.length ; i++){
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    list.add(messages);
                }

                if (messages.length > -1){
                    Intent in = new Intent(context,SMSService.class);
                    in.putExtra("message",messages);
                    context.startService(in);
                }else {
                    abortBroadcast();
                }
            }
        }
    }
}