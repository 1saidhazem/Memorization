package com.example.memorization.RoomDatabase;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(entity = User.class, parentColumns = {"identificationNumber"},
                childColumns = {"identificationNumberWallet"}, onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE),
        @ForeignKey(entity = Group.class, parentColumns = {"id"},
                childColumns = {"idGroup"}, onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE),
        @ForeignKey(entity = Center.class, parentColumns = {"id"},
                childColumns = {"idCenter"}, onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)
})
public class Wallet {

    @PrimaryKey
    private Long identificationNumberWallet;
    private int idGroup;
    private int idCenter;
    private String branch;

    public Long getIdentificationNumberWallet() {
        return identificationNumberWallet;
    }

    public void setIdentificationNumberWallet(Long identificationNumberWallet) {
        this.identificationNumberWallet = identificationNumberWallet;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    public int getIdCenter() {
        return idCenter;
    }

    public void setIdCenter(int idCenter) {
        this.idCenter = idCenter;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Wallet(Long identificationNumberWallet, int idGroup, int idCenter, String branch) {
        this.identificationNumberWallet = identificationNumberWallet;
        this.idGroup = idGroup;
        this.idCenter = idCenter;
        this.branch = branch;
    }

    public Wallet() {}
}
