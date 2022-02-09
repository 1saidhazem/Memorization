package com.example.memorization.RoomDatabase;

import androidx.room.Embedded;

public class StudentGroup {
   @Embedded
   private Student student;
   @Embedded
   private User user;
   private int CenterId;
   private String centerName;
   private String groupName;


    public int getCenterId() {
        return CenterId;
    }

    public void setCenterId(int centerId) {
        CenterId = centerId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public StudentGroup(Student student, User user, String centerName, String groupName) {
        this.student = student;
        this.user = user;
        this.centerName = centerName;
        this.groupName = groupName;
    }

    public StudentGroup() {
    }
}
