package com.example.memorization.RoomDatabase;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(foreignKeys = {@ForeignKey(entity = User.class, parentColumns = {"identificationNumber"},
        childColumns = {"identificationNumberManager"}, onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)})
public class Center {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private byte[] logo;
    private double longitude;
    private double latitude;
    private int numberOfGroupsAllowed;
    private long identificationNumberManager;
    private String branch;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getNumberOfGroupsAllowed() {
        return numberOfGroupsAllowed;
    }

    public void setNumberOfGroupsAllowed(int numberOfGroupsAllowed) {
        this.numberOfGroupsAllowed = numberOfGroupsAllowed;
    }

    public long getIdentificationNumberManager() {
        return identificationNumberManager;
    }

    public void setIdentificationNumberManager(long identificationNumberManager) {
        this.identificationNumberManager = identificationNumberManager;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Center(String name, byte[] logo, double longitude, double latitude, int numberOfGroupsAllowed, long identificationNumberManager, String branch) {
        this.name = name;
        this.logo = logo;
        this.longitude = longitude;
        this.latitude = latitude;
        this.numberOfGroupsAllowed = numberOfGroupsAllowed;
        this.identificationNumberManager = identificationNumberManager;
        this.branch = branch;
    }

    public Center() {}
}
