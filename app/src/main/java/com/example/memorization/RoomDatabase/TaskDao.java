package com.example.memorization.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;

import java.util.Date;
import java.util.List;

@Dao
@TypeConverters({DateConverter.class})
public interface TaskDao {

    @Insert
    void insertTask(Task... tasks);

    @Query("select * from Task order by rating desc")
    LiveData<List<Task>> getAllTask();

    @Query("select * from Task where studentReceiver=:idStudent")
    LiveData<List<Task>> getTasksByIdStudent(long idStudent);

    @Query("select Task.* from Task" +
            " INNER JOIN Student ON Student.identificationNumberStudent=Task.studentReceiver" +
            " where identificationNumberStudent=:idStudent and `from` >=:from and `to`<=:to ")
    LiveData<List<Task>> SearchTask(long idStudent ,Date from ,Date to);
}
