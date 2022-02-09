package com.example.memorization.RoomDatabase;

public class IdCenterAndGroup {
    private int idCenter;
    private int idGroup;

    public int getIdCenter() {
        return idCenter;
    }

    public void setIdCenter(int idCenter) {
        this.idCenter = idCenter;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    public IdCenterAndGroup(int idCenter, int idGroup) {
        this.idCenter = idCenter;
        this.idGroup = idGroup;
    }

    public IdCenterAndGroup() {}

}
