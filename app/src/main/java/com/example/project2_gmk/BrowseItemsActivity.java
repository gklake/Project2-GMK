package com.example.project2_gmk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.example.project2_gmk.itemDatabase.Item;
import com.example.project2_gmk.itemDatabase.ItemDAO;
import com.example.project2_gmk.itemDatabase.ItemDatabase;

import java.util.List;

public class BrowseItemsActivity extends AppCompatActivity {

    TextView textViewBrowseItems;
    ItemDAO itemDAO;
    List<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_items);

        wireUpDisplay();

        implementItemDAO();

        printAllItems();

    }

    private void printAllItems() {
        if(items.size() <= 0){
            textViewBrowseItems.setText(R.string.no_items_in_store);
        }
        StringBuilder sb = new StringBuilder();
        for(Item item : items){
            if(item.getName().isEmpty()){
                continue;
            }
            sb.append(item);
            sb.append("\n");
            sb.append("*+*-*+*-*+*-*+*-*+*-*+*-*");
            sb.append("\n");
        }
        textViewBrowseItems.setText(sb.toString());
    }

    private void implementItemDAO() {
        itemDAO = Room.databaseBuilder(this, ItemDatabase.class, ItemDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getItemDAO();
        items = itemDAO.getItems();
    }

    private void wireUpDisplay() {
        textViewBrowseItems = findViewById(R.id.textViewBrowseItems);
        textViewBrowseItems.setMovementMethod(new ScrollingMovementMethod());
    }
}