package com.example.project2_gmk.userDatabase.typeConverter;

import androidx.room.TypeConverter;

import com.example.project2_gmk.itemDatabase.Item;
import com.example.project2_gmk.userDatabase.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListTypeConverter {

//    Gson gson = new Gson();

    @TypeConverter
    public static ArrayList<Item> stringToItemList(String data){
//        if(data == null){
//            return Collections.emptyList();
//        }
        Type arrayListType = new TypeToken<ArrayList<Item>>(){}.getType();
        return new Gson().fromJson(data, arrayListType);

    }

    @TypeConverter
    public static String itemListToString(ArrayList<Item> items){
        Gson gson = new Gson();
        return gson.toJson(items);
    }

}
