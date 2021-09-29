package com.example.project2_gmk.userDatabase;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.project2_gmk.itemDatabase.Item;
import com.example.project2_gmk.userDatabase.typeConverter.ListTypeConverter;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName = UserDatabase.USERLOG_TABLE)
public class User implements Serializable {

    @PrimaryKey
    @NonNull
    private String username;

    private String password;
    private boolean isAdmin;
    private ArrayList<Item> usersCart;

    public User(@NonNull String username, String password, boolean isAdmin){
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        usersCart = new ArrayList<>();
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return this.getUsername().equals("admin2") && this.getPassword().equals("admin2");
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public ArrayList<Item> getUsersCart() {
        return usersCart;
    }

    public void setUsersCart(ArrayList<Item> usersCart) {
        this.usersCart = usersCart;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Username: ").append(username);
        sb.append("\n");
        sb.append("Password: ").append("******");
        sb.append("\n");
        if(isAdmin()){
            sb.append("Admin?: Yes");
        }else {
            sb.append("Admin?: No");
        }
        sb.append("\n");

        return sb.toString();
    }

}
