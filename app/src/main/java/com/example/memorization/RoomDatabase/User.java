package com.example.memorization.RoomDatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import java.util.Date;

@Entity
@TypeConverters({DateConverter.class})
public class User {

    @PrimaryKey
    private long identificationNumber;
    private String fullName;
    private String email;
    private long mobileNumber;
    private Date birthDay;
    private String address;
    private String password;
    private String rePassword;
    private int validity;
    private byte[] img;
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(long identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public int getValidity() {
        return validity;
    }

    public void setValidity(int validity) {
        this.validity = validity;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    // Manager
    public User(long identificationNumber,String fullName,String email,long mobileNumber,Date birthDay,String address,String password,String rePassword, int validity,byte[] img) {
        this.identificationNumber = identificationNumber;
        this.fullName = fullName;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.birthDay = birthDay;
        this.address = address;
        this.password = password;
        this.rePassword = rePassword;
        this.validity = validity;
        this.img = img;
    }

    public User() {}

}
