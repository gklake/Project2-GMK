package com.example.project2_gmk.itemDatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ItemDAO {

    @Insert
    void insert(Item... items);

    @Update
    void update(Item... items);

    @Delete
    void delete(Item item);

    @Query("SELECT * FROM " + ItemDatabase.ITEMLOG_TABLE + " ORDER BY name")
    List<Item> getItems();


    @Query("SELECT * FROM "+ ItemDatabase.ITEMLOG_TABLE + " WHERE name = :name")
    Item getItemFromName(String name);


}
