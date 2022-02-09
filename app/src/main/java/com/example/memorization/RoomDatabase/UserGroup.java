package com.example.memorization.RoomDatabase;

import java.util.Date;

public class UserGroup {
    private long idStudent;
    private int idGroup;
    private String fullNameUser;
    private String addressUser;
    private long mobileNumberUser;

    public long getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(long idStudent) {
        this.idStudent = idStudent;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public String getFullNameUser() {
        return fullNameUser;
    }

    public void setFullNameUser(String fullNameUser) {
        this.fullNameUser = fullNameUser;
    }

    public String getAddressUser() {
        return addressUser;
    }

    public void setAddressUser(String addressUser) {
        this.addressUser = addressUser;
    }

    public long getMobileNumberUser() {
        return mobileNumberUser;
    }

    public void setMobileNumberUser(long mobileNumberUser) {
        this.mobileNumberUser = mobileNumberUser;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    public UserGroup(long idStudent, int idGroup, String fullNameUser, String addressUser, long mobileNumberUser) {
        this.idStudent = idStudent;
        this.idGroup = idGroup;
        this.fullNameUser = fullNameUser;
        this.addressUser = addressUser;
        this.mobileNumberUser = mobileNumberUser;
    }

    public UserGroup() {
    }
}
