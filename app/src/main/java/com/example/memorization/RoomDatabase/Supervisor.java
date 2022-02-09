package com.example.memorization.RoomDatabase;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = User.class, parentColumns = {"identificationNumber"},
                childColumns = {"identificationNumberSupervisor"}, onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE),
        @ForeignKey(entity = Group.class, parentColumns = {"id"},
                childColumns = {"idGroup"}, onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)
})
public class Supervisor {

    @PrimaryKey
    private long identificationNumberSupervisor;
    private int idGroup;

    public long getIdentificationNumberSupervisor() {
        return identificationNumberSupervisor;
    }

    public void setIdentificationNumberSupervisor(long identificationNumberSupervisor) {
        this.identificationNumberSupervisor = identificationNumberSupervisor;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }


    public Supervisor(long identificationNumberSupervisor , int idGroup) {  // , int idCenter
        this.identificationNumberSupervisor = identificationNumberSupervisor;
        this.idGroup = idGroup;
    }

    public Supervisor() {
    }
}
