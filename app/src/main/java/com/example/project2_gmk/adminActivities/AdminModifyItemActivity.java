package com.example.project2_gmk.adminActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;

import com.example.project2_gmk.R;
import com.example.project2_gmk.itemDatabase.Item;
import com.example.project2_gmk.itemDatabase.ItemDAO;
import com.example.project2_gmk.itemDatabase.ItemDatabase;
import com.example.project2_gmk.modifyItemRecyclerView.ModifyItemRecyclerView;
import com.example.project2_gmk.modifyItemRecyclerView.ModifyRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class AdminModifyItemActivity extends AppCompatActivity implements ModifyItemDialog.ModifyItemDialogListener {
    private RecyclerView recyclerView;
    private ModifyRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ModifyItemRecyclerView> modifyItemRecyclerViewArrayList;
    ItemDAO itemDAO;
    List<Item> items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_modify_item);

        createItemList();

        buildRecyclerView();

    }

    public void changeItem(int position){
        //Changing the display of the item so the admin knows what just changed
        adapter.notifyItemChanged(position);
        modifyItemRecyclerViewArrayList.get(position).changeDescription("Item Clicked");
    }


    private void buildRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewModifyItem);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new ModifyRecyclerViewAdapter(modifyItemRecyclerViewArrayList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setModifyItemClickListener(position -> {
            openDialog();
            changeItem(position);

        });
    }

    private void openDialog() {
        ModifyItemDialog modifyItemDialog = new ModifyItemDialog();
        modifyItemDialog.show(getSupportFragmentManager(), "Modify Item Dialog");
    }

    private void createItemList() {
        implementItemDAO();
        modifyItemRecyclerViewArrayList = new ArrayList<>();
        for(Item item: items){
            if(item.getName().isEmpty()){
                continue;
            }
            modifyItemRecyclerViewArrayList.add(new ModifyItemRecyclerView(item.getName(), item.getDescription()));
        }
    }

    private void implementItemDAO() {
        itemDAO = Room.databaseBuilder(this, ItemDatabase.class, ItemDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getItemDAO();
        items = itemDAO.getItems();
    }

    @Override
    public void applyInformation(String name, String description, double price, int stock) {
        Item updatedItem = new Item(name, price, description, stock);
        itemDAO.update(updatedItem);
    }
}