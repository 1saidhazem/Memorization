package com.example.memorization.RoomDatabase;

public class Advertising {
    private String advertisingBody;
    private String sender;
    private long date;
    private int idCenter;
    private int idAdvertising;

    public Advertising() {}

    public String getAdvertisingBody() {
        return advertisingBody;
    }

    public void setAdvertisingBody(String advertisingBody) {
        this.advertisingBody = advertisingBody;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getIdCenter() {
        return idCenter;
    }

    public void setIdCenter(int idCenter) {
        this.idCenter = idCenter;
    }

    public int getIdAdvertising() {
        return idAdvertising;
    }

    public void setIdAdvertising(int idAdvertising) {
        this.idAdvertising = idAdvertising;
    }
}
