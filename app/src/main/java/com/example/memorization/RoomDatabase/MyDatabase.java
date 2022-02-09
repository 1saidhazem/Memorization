package com.example.memorization.RoomDatabase;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class,Student.class,Supervisor.class,Wallet.class,Center.class,Group.class,Task.class,Message.class}, version = 8, exportSchema = false)
public abstract class MyDatabase extends RoomDatabase {

    public abstract UserDao UserDao();
    public abstract StudentDao studentDao();
    public abstract WalletDao walletDao();
    public abstract CenterDao centerDao();
    public abstract GroupDao groupDao();
    public abstract TaskDao taskDao();
    public abstract MessageDao messageDao();

    private static volatile MyDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 6;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static MyDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyDatabase.class, "memorization_database")
                            .addCallback(RoomDatabase)
//                            .addMigrations(migration)
                            .fallbackToDestructiveMigration()
//                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static Callback RoomDatabase = new Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() ->{

            });

        }
    };

}
