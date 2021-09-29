package com.example.project2_gmk.userDatabase;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.project2_gmk.userDatabase.typeConverter.ListTypeConverter;

@Database(entities = {User.class}, version = 2)
@TypeConverters(ListTypeConverter.class)
public abstract class UserDatabase extends RoomDatabase {

    public static final String DB_NAME = "USERLOG_DATABASE";
    public static final String USERLOG_TABLE = "USERLOG_TABLE";

    public abstract UserDAO getUserDAO();
}
