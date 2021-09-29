package com.example.project2_gmk.userDatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert
    void insert(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + UserDatabase.USERLOG_TABLE + " ORDER BY username")
    List<User> getUsers();

    @Query("SELECT * FROM "+ UserDatabase.USERLOG_TABLE + " WHERE username = :username AND password = :password")
    User getUser(String username, String password);

    @Query("SELECT * FROM "+ UserDatabase.USERLOG_TABLE + " WHERE username = :username")
    User getUserFromUsername(String username);


}
