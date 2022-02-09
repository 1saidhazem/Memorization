package com.example.memorization.RoomDatabase;

public class StudentUser {
    private int centerId;
    private int groupId;
    private int idStudent;

    public int getCenterId() {
        return centerId;
    }

    public void setCenterId(int centerId) {
        this.centerId = centerId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    public StudentUser(int centerId, int groupId, int idStudent) {
        this.centerId = centerId;
        this.groupId = groupId;
        this.idStudent = idStudent;
    }

    public StudentUser() {
    }
}
