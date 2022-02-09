package com.example.memorization.RoomDatabase;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

@Entity(
        foreignKeys = {
                @ForeignKey(entity = User.class, parentColumns = {"identificationNumber"},
                        childColumns = {"studentReceiver"}, onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = User.class, parentColumns = {"identificationNumber"},
                        childColumns = {"taskSender"}, onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = Group.class, parentColumns = {"id"},
                        childColumns = {"groupId"}, onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE),
                @ForeignKey(entity = Center.class, parentColumns = {"id"},
                        childColumns = {"centerId"}, onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE)
        }
)
@TypeConverters({DateConverter.class})
public class Task {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private long studentReceiver;
    private int groupId;
    private int centerId;
    private long taskSender;
    private Date fromDate;
    private Date toDate;
    private int from;
    private int to;
    private String quantityType;
    private float rating;
    private String feedbackOnRating;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getStudentReceiver() {
        return studentReceiver;
    }

    public void setStudentReceiver(long studentReceiver) {
        this.studentReceiver = studentReceiver;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getCenterId() {
        return centerId;
    }

    public void setCenterId(int centerId) {
        this.centerId = centerId;
    }

    public long getTaskSender() {
        return taskSender;
    }

    public void setTaskSender(long taskSender) {
        this.taskSender = taskSender;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public String getQuantityType() {
        return quantityType;
    }

    public void setQuantityType(String quantityType) {
        this.quantityType = quantityType;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getFeedbackOnRating() {
        return feedbackOnRating;
    }

    public void setFeedbackOnRating(String feedbackOnRating) {
        this.feedbackOnRating = feedbackOnRating;
    }

    public Task(long studentReceiver, int idGroup, int idCenter, long taskSender,int from, int to, Date fromDate, Date toDate, String quantityType, float rating, String feedbackOnRating) {
        this.studentReceiver = studentReceiver;
        this.groupId = idGroup;
        this.centerId = idCenter;
        this.taskSender = taskSender;
        this.from = from;
        this.to = to;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.quantityType = quantityType;
        this.rating = rating;
        this.feedbackOnRating = feedbackOnRating;
    }

    public Task() {
    }
}
