package com.example.project2_gmk;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.example.project2_gmk.itemDatabase.Item;
import com.example.project2_gmk.itemDatabase.ItemDAO;
import com.example.project2_gmk.itemDatabase.ItemDatabase;
import com.example.project2_gmk.userDatabase.User;
import com.example.project2_gmk.userDatabase.UserDAO;
import com.example.project2_gmk.userDatabase.UserDatabase;
import com.example.project2_gmk.viewCartRecyclerView.ViewCartRecyclerView;
import com.example.project2_gmk.viewCartRecyclerView.ViewCartRecyclerViewAdapter;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.ArrayList;
import java.util.List;


public class ViewCartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ViewCartRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ViewCartRecyclerView> viewCartRecyclerViewArrayList;

    TextView textViewCurrentCart;
    SharedPreferences sharedPreferences;
    ItemDAO itemDAO;
    List<Item> items;
    User user;
    ArrayList<Item> userCart;
    UserDAO userDAO;

    private static final String SHARED_PREF_NAME = "pref";
    private static final String KEY_NAME = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);

        textViewCurrentCart = findViewById(R.id.textViewCurrentCart);

        implementUserDAO();

        setUpSharedPreferences();

        createUserCartList();

        buildRecyclerView();

    }

    private void removeFromCart(int position) {
        //Removing item from user's cart && updating userDAO(updating the List<Item> that belongs to the user)
        userCart.remove(position);
        textViewCurrentCart.setText(user.getUsername() + "'s Cart ("  + user.getUsersCart().size() + "): ");
        userDAO.update(user);
        //Retrieving the item using getItemFromName and storing in temp variable(removedItem)
        Item removedItem = itemDAO.getItemFromName(viewCartRecyclerViewArrayList.get(position).getName());
        int currentStock = removedItem.getStock();
        //Increasing the stock && updating the itemDAO
        removedItem.setStock(currentStock + 1);
        itemDAO.update(removedItem);
        //Removing Item from Recycler View ArrayList and notifying the adapter
        viewCartRecyclerViewArrayList.remove(position);
        adapter.notifyDataSetChanged();
        adapter.notifyItemChanged(position);
        StyleableToast.makeText(ViewCartActivity.this,  removedItem.getName() + " Removed From Cart", R.style.specialToast).show();
    }


    private void buildRecyclerView() {
        recyclerView = findViewById(R.id.recyclerViewViewCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new ViewCartRecyclerViewAdapter(viewCartRecyclerViewArrayList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setViewCartClickListener(position -> {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setTitle("Confirming Item Removal")
                        .setMessage("Are you sure you would like to remove " + viewCartRecyclerViewArrayList.get(position).getName() + " from your cart?");
            alertBuilder.setPositiveButton("Yes", (dialog, which) -> removeFromCart(position));
            alertBuilder.setNegativeButton("Cancel", (dialog, which) -> StyleableToast.makeText(ViewCartActivity.this, viewCartRecyclerViewArrayList.get(position).getName() + " will not be removed from your cart.", R.style.specialToast).show());
            alertBuilder.setCancelable(true);
            alertBuilder.create().show();
        });
    }

    private void createUserCartList() {
        implementItemDAO();
        viewCartRecyclerViewArrayList = new ArrayList<>();
        for(Item item: userCart){
            viewCartRecyclerViewArrayList.add(new ViewCartRecyclerView(item.getName(), item.getDescription(), item.getPrice()));
        }
    }

    private void setUpSharedPreferences() {
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String name = sharedPreferences.getString(KEY_NAME, null);
        if(name != null){
            user = userDAO.getUserFromUsername(name);
            userCart = user.getUsersCart();
            textViewCurrentCart.setText(name + "'s Cart ("  + user.getUsersCart().size() + "): ");
        }
    }

    private void implementUserDAO() {
        userDAO = Room.databaseBuilder(this, UserDatabase.class, UserDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserDAO();
    }

    private void implementItemDAO() {
        itemDAO = Room.databaseBuilder(this, ItemDatabase.class, ItemDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getItemDAO();
        items = itemDAO.getItems();
    }

}