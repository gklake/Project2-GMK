package com.example.project2_gmk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.example.project2_gmk.itemDatabase.Item;
import com.example.project2_gmk.itemDatabase.ItemDAO;
import com.example.project2_gmk.itemDatabase.ItemDatabase;
import com.example.project2_gmk.userDatabase.User;
import com.example.project2_gmk.userDatabase.UserDAO;
import com.example.project2_gmk.userDatabase.UserDatabase;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.ArrayList;
import java.util.List;

public class SearchItemActivity extends AppCompatActivity {

    AutoCompleteTextView autoCompleteTextView;
    TextView textViewItemInfo;
    Button buttonAddItemToCart;
    UserDAO userDAO;
    User user;
    ItemDAO itemDAO;
    List<Item> items;
    ArrayList<String> itemNames;

    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "pref";
    private static final String KEY_NAME = "username";


    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_item);

        implementItemDAO();

        implementUserDAO();

        wireUpDisplay();

        setUpSharedPreferences();

        itemNames = new ArrayList<>();
        for(Item item : items){
            if(item.getName().isEmpty() || item.getDescription().isEmpty() || item.getPrice() <= 0){
                continue;
            }
            itemNames.add(item.getName());
        }
        itemNames.toArray();


        //Initialize Adapter
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, itemNames);

        //Get suggestion after the number of letters typed(can also be set in xml file)
        autoCompleteTextView.setThreshold(1);

        //Set adapter
        autoCompleteTextView.setAdapter(arrayAdapter);

        //Set Listener
        autoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            textViewItemInfo.setText(itemDAO.getItemFromName(arrayAdapter.getItem(position)).toString());
            buttonAddItemToCart.setVisibility(View.VISIBLE);
            closeKeyboard();

            Item cartItem = itemDAO.getItemFromName(arrayAdapter.getItem(position));
            if(cartItem.getStock() <= 0){
                buttonAddItemToCart.setEnabled(false);
                buttonAddItemToCart.setText(R.string.sold_out);
                StyleableToast.makeText(SearchItemActivity.this, cartItem.getName() + " is no longer in stock.", R.style.specialToast).show();
            } else {
                buttonAddItemToCart.setEnabled(true);
                buttonAddItemToCart.setText(R.string.add_item_to_cart);
                buttonAddItemToCart.setOnClickListener(v -> {
                    int currentStock = cartItem.getStock();
                    cartItem.setStock(currentStock - 1);
                    itemDAO.update(cartItem);
                    textViewItemInfo.setText(itemDAO.getItemFromName(arrayAdapter.getItem(position)).toString());
                    user.getUsersCart().add(cartItem);
                    userDAO.update(user);
                    StyleableToast.makeText(getApplicationContext(), cartItem.getName() + " Added to Cart", R.style.specialToast).show();
                    if(cartItem.getStock() <= 0){
                        buttonAddItemToCart.setEnabled(false);
                        buttonAddItemToCart.setText(R.string.sold_out);
                        StyleableToast.makeText(SearchItemActivity.this, cartItem.getName() + " is no longer in stock.", R.style.specialToast).show();
                    }
                });
            }

        });
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void implementUserDAO() {
        userDAO = Room.databaseBuilder(this, UserDatabase.class, UserDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getUserDAO();
    }

    private void wireUpDisplay() {
        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        textViewItemInfo = findViewById(R.id.textViewItemInfo);
        buttonAddItemToCart = findViewById(R.id.buttonAddItemToCart);
    }

    private void implementItemDAO() {
        itemDAO = Room.databaseBuilder(this, ItemDatabase.class, ItemDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getItemDAO();
        items = itemDAO.getItems();
    }

    private void setUpSharedPreferences(){
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String name = sharedPreferences.getString(KEY_NAME, null);
        if(name != null){
            user = userDAO.getUserFromUsername(name);
        }
    }

}