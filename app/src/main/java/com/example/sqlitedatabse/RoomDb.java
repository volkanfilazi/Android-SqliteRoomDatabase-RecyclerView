package com.example.sqlitedatabse;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class},version = 1,exportSchema = false)
public abstract class RoomDb extends RoomDatabase {

    private static RoomDb database;
    private static String DATABASE_NAME = "database";

    public synchronized static RoomDb getInstance(Context context){
        if (database == null){
            database = Room.databaseBuilder(context.getApplicationContext(),RoomDb.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    public abstract UserDao userDao();
}
