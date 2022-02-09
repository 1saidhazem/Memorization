package com.example.memorization.RoomDatabase;

import androidx.room.Embedded;

public class CenterSupervisor {
    @Embedded
    private Student student;
    @Embedded
    private User user;
    @Embedded
    private Center center;

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

    public Center getCenter() {
        return center;
    }

    public void setCenter(Center center) {
        this.center = center;
    }

    public CenterSupervisor(Student student, User user, Center center) {
        this.student = student;
        this.user = user;
        this.center = center;
    }

    public CenterSupervisor() {
    }
}
