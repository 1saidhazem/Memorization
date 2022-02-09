package com.example.memorization.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.TypeConverters;

import java.util.List;

@Dao
@TypeConverters({DateConverter.class})
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertUser(User user);

    @Query("select * from User where identificationNumber=:id")
    User getUserById(long id);

    @Query("select * from User order by fullName asc")
    LiveData<List<User>> getAllUser();

    @Query("select * from User Where validity = 0")
    LiveData<List<User>> getAllManager();

    @Query("select * from User Where validity = 1")
    LiveData<List<User>> getAllSupervisor();

    @Query("select * from User Where validity = 1 and User.identificationNumber=:idSupervisor")
    LiveData<List<User>> getSupervisorById(long idSupervisor);

    @Query("select * from User Where validity = 2")
    LiveData<List<User>> getAllWallet();

    @Query("select * from User Where validity = 2 and User.identificationNumber=:idWallet")
    LiveData<List<User>> getWalletById(long idWallet);

    @Query("select * from User Where validity = 3")
    LiveData<List<User>> getAllStudent();

    @Query("select fullName from User where identificationNumber=:idSender")
    String  getSenderName(long idSender);

    @Query("select * " +
            " from Student WHERE Student.identificationNumberStudent=:idStudent ")
    Student getInfoStudent(long idStudent);

    @Query("select mobileNumber from User where validity=3")
    LiveData<List<Long>> getAllPhoneNumberStudent();

    @Query("select * from User INNER JOIN Student" +
            " ON User.identificationNumber=Student.identificationNumberStudent" +
            " where validity=3 and User.identificationNumber=:idStudent")
    LiveData<List<User>> getStudentById(long idStudent);

    @Query("select count(identificationNumber) from User where validity=3")
    int getCountStudent();

    @Query("select password from User where identificationNumber=:idUser")
    String getPasswordCurrentUser(long idUser);

    @Query("UPDATE User SET password=:newPass WHERE identificationNumber=:idUser")
    void updatePassword(String newPass,long idUser);
}
