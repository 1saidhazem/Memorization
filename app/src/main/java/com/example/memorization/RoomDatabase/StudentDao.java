package com.example.memorization.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.TypeConverters;

import java.util.List;

@Dao
@TypeConverters({DateConverter.class})
public interface StudentDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertStudent(Student... students);

    @Transaction
    @Query("SELECT * FROM User WHERE validity=3")
    LiveData<List<User>> getAllStudent();

    @Query("select User.* , Student.* ,Center.id as CenterId, Center.name as centerName , `Group`.name as groupName from User " +
            " INNER JOIN Student ON User.identificationNumber=Student.identificationNumberStudent" +
            " INNER JOIN `Group` ON `Group`.id=Student.idGroup" +
            " INNER JOIN Center ON Center.id=Student.idCenter" +
            " WHERE Student.idGroup=:groupId")
    LiveData<List<StudentGroup>> getAllStudentInGroup(int groupId);

    @Query("select User.fullName as nameStudent from Student INNER JOIN User" +
            " ON Student.identificationNumberStudent=User.identificationNumber" +
            " where identificationNumberStudent=:idStudent")
    String getNameStudent(long idStudent);

    @Query("select Center.name as CenterName ,`Group`.name as groupName from Student " +
            "INNER JOIN Center ON Student.idCenter=Center.id " +
            "INNER JOIN `Group` ON Student.idGroup=`Group`.id")
    LiveData<List<StudentCenterGroup>> getNames();
}
