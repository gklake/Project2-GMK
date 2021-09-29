package com.example.project2_gmk.adminActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.project2_gmk.R;


public class AdminActivity extends AppCompatActivity {

    Button buttonAddItem;
    Button buttonDeleteItem;
    Button buttonModifyItem;
    Button buttonViewExistingItems;
    Button buttonViewAllUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        wireUpDisplay();

        buttonAddItem.setOnClickListener(v -> startActivity(new Intent(AdminActivity.this, AdminAddItemActivity.class)));

        buttonViewExistingItems.setOnClickListener(v -> startActivity(new Intent(AdminActivity.this, AdminViewExistingItemsActivity.class)));

        buttonViewAllUsers.setOnClickListener(v -> startActivity(new Intent(AdminActivity.this, AdminViewExistingUsersActivity.class)));

        buttonDeleteItem.setOnClickListener(v -> startActivity(new Intent(AdminActivity.this, AdminDeleteItemActivity.class)));

        buttonModifyItem.setOnClickListener(v -> startActivity(new Intent(AdminActivity.this, AdminModifyItemActivity.class)));
    }

    private void wireUpDisplay() {
        buttonAddItem = findViewById(R.id.buttonAddItem);
        buttonDeleteItem = findViewById(R.id.buttonDeleteItem);
        buttonModifyItem = findViewById(R.id.buttonModifyItem);
        buttonViewExistingItems = findViewById(R.id.buttonViewExistingItems);
        buttonViewAllUsers = findViewById(R.id.buttonViewAllUsers);
    }

}