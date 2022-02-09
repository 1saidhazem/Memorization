package com.example.memorization.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import java.util.List;

@Dao
@TypeConverters({DateConverter.class})
public interface GroupDao {

    @Insert
    void insertGroup(Group... groups);

    @Query("select * from `Group` order by name asc")
    LiveData<List<Group>> getAllGroup();

    @Query("select * from `Group` INNER JOIN Center" +
            " ON `Group`.id=Center.id" +
            " WHERE `Group`.idCenter=:idCenter")
    LiveData<List<Group>> getGroupInCenter(int idCenter);

    @Query("select * from `Group` where idCenter=:id")
    LiveData<List<Group>> getAllGroupById(int id);

    @Query("select * from `Group` where idCenter=:idCenter")
    Group getCenterNameGroup(int idCenter);

    @Query("select count(id) from `Group`")
    int getCountGroup();

    @Query("delete from `Group` where id=:idGroup")
    void deleteGroup(int idGroup);

    @Update
    void updateGroup(Group group);

    @Query("select Center.name as nameCenter ,Center.logo as logoCenter," +
            "`Group`.identificationNumberGroup as identificationNumberGroupSupervisor ,Center.branch as branchCenter," +
            "Center.id as idCenter , `Group`.id as idGroup" +
            " from `Group` INNER JOIN Center ON `Group`.id=Center.id")
    CenterGroup getInfoGroupInCenter();

    @Query("select Center.id as idCenter , `Group`.id as idGroup from `Group` INNER JOIN Center ON `Group`.id=Center.id")
    IdCenterAndGroup getIdCenterAndGroup();

    @Query("select * from `Group` Where identificationNumberGroup=:idSupervisor")
    LiveData<List<Group>> getGroupSupervisor(long idSupervisor);

    @Query("select * from `Group` INNER JOIN Student" +
            " ON `Group`.identificationNumberGroup=Student.identificationNumberStudent" +
            " Where identificationNumberGroup=:idStudent")
    LiveData<List<Group>> getGroupStudent(long idStudent);

//    @Query("select * from `Group`" +
//            " INNER JOIN User ON `Group`.identificationNumberGroup=User.identificationNumber" +
//            " INNER JOIN Student ON `Group`.identificationNumberGroup=Student.identificationNumberStudent" +
//            " INNER JOIN Task ON `Group`.identificationNumberGroup=Task.studentReceiver" +
//            " WHERE sum(Task.rating) / count(Task.id)")
//    Group getBestGroup();
}
