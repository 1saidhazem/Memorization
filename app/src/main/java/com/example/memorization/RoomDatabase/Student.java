package com.example.memorization.RoomDatabase;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(entity = User.class, parentColumns = {"identificationNumber"},
                childColumns = {"identificationNumberStudent"}, onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE),
        @ForeignKey(entity = Group.class, parentColumns = {"id"},
                childColumns = {"idGroup"}, onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE),
        @ForeignKey(entity = Center.class, parentColumns = {"id"},
                childColumns = {"idCenter"}, onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE),
        @ForeignKey(entity = Wallet.class, parentColumns = {"identificationNumberWallet"},
                childColumns = {"idWallet"}, onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)
})
public class Student {

    @PrimaryKey
    private long identificationNumberStudent;
    private int idGroup;
    private int idCenter;
    private long idWallet;
    private String branch;

    public long getIdentificationNumberStudent() {
        return identificationNumberStudent;
    }

    public void setIdentificationNumberStudent(long identificationNumberStudent) {
        this.identificationNumberStudent = identificationNumberStudent;
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

    public long getIdWallet() {
        return idWallet;
    }

    public void setIdWallet(long idWallet) {
        this.idWallet = idWallet;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Student(long identificationNumberStudent, int idGroup, int idCenter, long idWallet, String branch) {
        this.identificationNumberStudent = identificationNumberStudent;
        this.idGroup = idGroup;
        this.idCenter = idCenter;
        this.idWallet = idWallet;
        this.branch = branch;
    }

    public Student() {}
}
