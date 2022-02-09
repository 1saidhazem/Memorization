package com.example.memorization.RoomDatabase;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(foreignKeys = {
        @ForeignKey(entity = User.class, parentColumns = {"identificationNumber"},
                childColumns = {"senderId"},
                onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)})
public class Message {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String messageBody;
    private long senderId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public Message(int id, String messageBody, long senderId) {
        this.id = id;
        this.messageBody = messageBody;
        this.senderId = senderId;
    }

    public Message() {
    }

}
