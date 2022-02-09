package com.example.memorization;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.memorization.RoomDatabase.Message;
import com.example.memorization.RoomDatabase.MyViewModel;
import com.example.memorization.RoomDatabase.Repository;

public class SMSService extends Service {

    Repository repository = new Repository(getApplication());

    public SMSService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String msg =intent.getStringExtra("message");
        Message message = new Message();
        message.setMessageBody(msg);
        repository.insertMessage(message);
        return START_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}