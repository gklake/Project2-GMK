package com.example.project2_gmk.itemDatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Item.class}, version = 1)
public abstract class ItemDatabase extends RoomDatabase {

    public static final String DB_NAME = "ITEMLOG_DATABASE";
    public static final String ITEMLOG_TABLE = "ITEMLOG_TABLE";

    public abstract ItemDAO getItemDAO();
}
