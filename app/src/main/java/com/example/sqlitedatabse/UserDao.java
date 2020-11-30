package com.example.sqlitedatabse;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDao {
    @Insert(onConflict = REPLACE)
    void insert(User user);

    @Delete
    void delete(User user);

    @Delete
    void reset(List<User> userList);

    @Query("UPDATE users SET first_name = :sFirstname,last_name = :sLastname WHERE uid = :suid ")
    void update(int suid,String sFirstname,String sLastname );


    @Query("SELECT * FROM users")
    List<User> getAll();

}
