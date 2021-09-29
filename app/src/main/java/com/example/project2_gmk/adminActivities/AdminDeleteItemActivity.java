package com.example.project2_gmk.adminActivities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.DialogInterface;
import android.os.Bundle;

import com.example.project2_gmk.deleteItemRecyclerView.DeleteItemRecyclerView;
import com.example.project2_gmk.R;
import com.example.project2_gmk.deleteItemRecyclerView.DeleteRecyclerViewAdapter;
import com.example.project2_gmk.itemDatabase.Item;
import com.example.project2_gmk.itemDatabase.ItemDAO;
import com.example.project2_gmk.itemDatabase.ItemDatabase;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.ArrayList;
import java.util.List;

public class AdminDeleteItemActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DeleteRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<DeleteItemRecyclerView> deleteItemRecyclerViewArrayList;
    ItemDAO itemDAO;
    List<Item> items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delete_item);
        
        createItemList();

        buildRecyclerView();

    }

    public void removeItem(int position){
        //Retrieving item from database using itemName and deleting it from the database
        Item removedItem = itemDAO.getItemFromName(deleteItemRecyclerViewArrayList.get(position).getItemName());
        itemDAO.delete(removedItem);
        //Updating the recyclerView
        deleteItemRecyclerViewArrayList.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyDataSetChanged();
        StyleableToast.makeText(AdminDeleteItemActivity.this, removedItem.getName() + " has been removed from the shop.", R.style.specialToast).show();
    }

    private void buildRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new DeleteRecyclerViewAdapter(deleteItemRecyclerViewArrayList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(position -> {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setTitle("Confirming Item Deletion")
                        .setMessage("Are you sure you want to remove " + deleteItemRecyclerViewArrayList.get(position).getItemName() + " from the shop?");
            alertBuilder.setPositiveButton("Yes", (dialog, which) -> removeItem(position));
            alertBuilder.setNegativeButton("Cancel", (dialog, which) -> StyleableToast.makeText(AdminDeleteItemActivity.this, deleteItemRecyclerViewArrayList.get(position).getItemName() + " will not be removed from the shop.", R.style.specialToast).show());
            alertBuilder.setCancelable(true);
            alertBuilder.create().show();
        });
    }

    private void createItemList() {
        implementItemDAO();
        deleteItemRecyclerViewArrayList = new ArrayList<>();
        //Adding each item from the database to the ArrayList
        for(Item item: items){
            deleteItemRecyclerViewArrayList.add(new DeleteItemRecyclerView(item.getName(), item.getDescription()));
        }
    }

    private void implementItemDAO() {
        itemDAO = Room.databaseBuilder(this, ItemDatabase.class, ItemDatabase.DB_NAME)
            .allowMainThreadQueries()
            .build()
            .getItemDAO();
        items = itemDAO.getItems();
    }

}