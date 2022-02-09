package com.example.memorization.RoomDatabase;

public class StudentCenterGroup {
    private String groupName;
    private String CenterName;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCenterName() {
        return CenterName;
    }

    public void setCenterName(String centerName) {
        CenterName = centerName;
    }

    public StudentCenterGroup(String groupName, String centerName) {
        this.groupName = groupName;
        CenterName = centerName;
    }

    public StudentCenterGroup() {
    }
}
