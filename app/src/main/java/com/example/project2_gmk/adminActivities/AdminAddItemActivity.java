package com.example.project2_gmk.adminActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.project2_gmk.R;
import com.example.project2_gmk.itemDatabase.Item;
import com.example.project2_gmk.itemDatabase.ItemDAO;
import com.example.project2_gmk.itemDatabase.ItemDatabase;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.List;

public class AdminAddItemActivity extends AppCompatActivity {

    TextView textViewItemsDisplay;
    EditText editTextItemName;
    EditText editTextItemPrice;
    EditText editTextItemDescription;
    EditText editTextNumberOfItems;
    Button buttonSubmitAddition;
    ItemDAO itemDAO;
    List<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_item);

        wireUpDisplay();

        implementItemDAO();

        refreshTextView();

        buttonSubmitAddition.setOnClickListener(v -> {
            getItemParameters();
            refreshTextView();
        });


    }

    private void implementItemDAO() {
        itemDAO = Room.databaseBuilder(this, ItemDatabase.class, ItemDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getItemDAO();
    }

    private void wireUpDisplay() {
        textViewItemsDisplay = findViewById(R.id.textViewItemsDisplay);
        textViewItemsDisplay.setMovementMethod(new ScrollingMovementMethod());

        editTextItemName = findViewById(R.id.editTextItemName);
        editTextItemPrice = findViewById(R.id.editTextItemPrice);
        editTextItemDescription = findViewById(R.id.editTextItemDescription);
        editTextNumberOfItems = findViewById(R.id.editTextNumberOfItems);

        buttonSubmitAddition = findViewById(R.id.buttonSubmitAddition);
    }

    private void getItemParameters(){
        String name = "No Name Specified";
        double price = 0.0;
        String description = "No Description Specified";
        int stock = 0;

        name = editTextItemName.getText().toString();

        try {
            price = Double.parseDouble(editTextItemPrice.getText().toString());
        } catch (Exception e) {
            StyleableToast.makeText(AdminAddItemActivity.this, "Invalid/Empty price input, default added: 0.0", R.style.specialToast).show();
            e.printStackTrace();
        }

        description = editTextItemDescription.getText().toString();

        try {
            stock = Integer.parseInt(editTextNumberOfItems.getText().toString());
        } catch (NumberFormatException e) {
            StyleableToast.makeText(AdminAddItemActivity.this, "Invalid/Empty stock input, default added: 0", R.style.specialToast).show();
            e.printStackTrace();
        }

        //Creating new Item based on entered parameters
        Item newItem = new Item(name, price, description, stock);

        //Checking if the Item is already in the database
        Item testingIfItemExists = itemDAO.getItemFromName(name);

        //If the Item is already in the database, then it will not be added again. If it is not then it will be added
        if(testingIfItemExists == null){
            StyleableToast.makeText(AdminAddItemActivity.this, newItem.getName() + " added to the shop.", R.style.specialToast).show();
            itemDAO.insert(newItem);
        } else {
            StyleableToast.makeText(AdminAddItemActivity.this, testingIfItemExists.getName() + " already exists and will not be added again.", R.style.specialToast).show();
        }
    }

    private void refreshTextView(){
        items = itemDAO.getItems();
        if(items.size() <= 0){
            textViewItemsDisplay.setText(R.string.no_items_in_store);
        }
        StringBuilder sb = new StringBuilder();
        for(Item item : items){
            sb.append(item);
            sb.append("\n");
            sb.append("*+*-*+*-*+*-*+*-*+*-*+*-*");
            sb.append("\n");
        }
        textViewItemsDisplay.setText(sb.toString());
    }

}