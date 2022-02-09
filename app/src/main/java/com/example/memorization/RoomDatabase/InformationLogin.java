package com.example.memorization.RoomDatabase;

public class InformationLogin {
    private long id;
    private String password;
    private int validity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getValidity() {
        return validity;
    }

    public void setValidity(int validity) {
        this.validity = validity;
    }

    public InformationLogin() {
    }

    public InformationLogin(long id, String password, int validity) {
        this.id = id;
        this.password = password;
        this.validity = validity;
    }
}
