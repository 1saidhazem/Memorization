package com.example.memorization.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CenterDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCenter(Center... centers);

    @Query("select * from Center order by name asc")
    LiveData<List<Center>> getAllCenter();

    @Query("select id from Center")
    LiveData<List<Integer>> getCenterId();

    @Update
    void updateCenter(Center center);

    @Query("delete from Center where id=:idCenter")
    void deleteCenter(int idCenter);

    @Query("select * from Center where name like '%'|| :query ||'%' order by name asc")
    LiveData<List<Center>> SearchCenter(String query);

    @Query("select * from Center where id=:idCenter")
    Center getLatLong(int idCenter);

    @Query("select id from Center")
    int getIdCenter();

    @Query("select * from Center Where identificationNumberManager=:idManager")
    LiveData<List<Center>> getAllCenterManager(long idManager);

    @Query("select * from Center Where identificationNumberManager=:idSupervisor")
    LiveData<List<Center>> getCenterSupervisor(long idSupervisor);
}
