package com.example.memorization.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;

import java.util.List;

@Dao
@TypeConverters({DateConverter.class})
public interface MessageDao {

    @Insert
    void insertMessage(Message... messages);

    @Query("select * from Message")
    LiveData<List<Message>> getAllMessage();

}
