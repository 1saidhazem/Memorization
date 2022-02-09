package com.example.memorization.RoomDatabase;

import androidx.room.Embedded;

public class WalletGroup {
    @Embedded
    private Wallet wallet;
    @Embedded
    private User user;
    private int CenterId;
    private String centerName;
    private String groupName;

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getCenterId() {
        return CenterId;
    }

    public void setCenterId(int centerId) {
        CenterId = centerId;
    }

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public WalletGroup(Wallet wallet, User user, int centerId, String centerName, String groupName) {
        this.wallet = wallet;
        this.user = user;
        CenterId = centerId;
        this.centerName = centerName;
        this.groupName = groupName;
    }

    public WalletGroup() {
    }
}
