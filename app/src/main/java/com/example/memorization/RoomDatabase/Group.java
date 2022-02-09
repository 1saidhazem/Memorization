package com.example.memorization.RoomDatabase;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(foreignKeys = {
        // مشرف
        @ForeignKey(entity = User.class, parentColumns = {"identificationNumber"},
                childColumns = {"identificationNumberGroup"}, onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE),
        @ForeignKey(entity = Center.class, parentColumns = {"id"},
                childColumns = {"idCenter"}, onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE),
})
@TypeConverters({DateConverter.class})
public class Group {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private byte[] logo;
    private String branch;
    private int numberOfStudentsAllowedInGroup;
    private int idCenter;
    private String centerName;
    private long identificationNumberGroup;  // مشرف
    private String description;


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

    public String getCenterName() {
        return centerName;
    }

    public void setCenterName(String centerName) {
        this.centerName = centerName;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public int getNumberOfStudentsAllowedInGroup() {
        return numberOfStudentsAllowedInGroup;
    }

    public void setNumberOfStudentsAllowedInGroup(int numberOfStudentsAllowedInGroup) {
        this.numberOfStudentsAllowedInGroup = numberOfStudentsAllowedInGroup;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdCenter() {
        return idCenter;
    }

    public void setIdCenter(int idCenter) {
        this.idCenter = idCenter;
    }

    public long getIdentificationNumberGroup() {
        return identificationNumberGroup;
    }

    public void setIdentificationNumberGroup(long identificationNumberGroup) {
        this.identificationNumberGroup = identificationNumberGroup;
    }

    public Group(String name, byte[] logo, String branch, int numberOfStudentsAllowedInGroup, int idCenter, String centerName, long identificationNumberGroup, String description) {
        this.name = name;
        this.logo = logo;
        this.branch = branch;
        this.numberOfStudentsAllowedInGroup = numberOfStudentsAllowedInGroup;
        this.idCenter = idCenter;
        this.centerName = centerName;
        this.identificationNumberGroup = identificationNumberGroup;
        this.description = description;
    }

    public Group() {}
}
