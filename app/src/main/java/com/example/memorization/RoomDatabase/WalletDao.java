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
public interface WalletDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWallet(Wallet... wallets);

    @Query("select * from wallet Where idGroup=:idGroup ")
    LiveData<List<Wallet>> getWalletById(int idGroup);


    @Query("select User.* , Wallet.* ,Center.id as CenterId, Center.name as centerName , `Group`.name as groupName from User " +
            " INNER JOIN Wallet ON User.identificationNumber=Wallet.identificationNumberWallet" +
            " INNER JOIN `Group` ON `Group`.id=Wallet.idGroup" +
            " INNER JOIN Center ON Center.id=Wallet.idCenter" +
            " WHERE Wallet.idGroup=:groupId")
    LiveData<List<WalletGroup>> getAllWalletInGroup(int groupId);

//    @Query("select * from Wallet" +
//            " INNER JOIN User ON Wallet.identificationNumberWallet=User.identificationNumber" +
//            " INNER JOIN Task ON Wallet.identificationNumberWallet=Task.studentReceiver" +
//            " WHERE MAX(Task.rating)")
//    Wallet getBestWallet();

}
