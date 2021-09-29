package com.example.project2_gmk.adminActivities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.room.Room;

import com.example.project2_gmk.R;
import com.example.project2_gmk.itemDatabase.ItemDAO;
import com.example.project2_gmk.itemDatabase.ItemDatabase;
import com.muddzdev.styleabletoast.StyleableToast;

public class ModifyItemDialog extends AppCompatDialogFragment {

    private EditText editTextItemName;
    private EditText editTextItemDescription;
    private EditText editTextItemPrice;
    private EditText editTextItemStock;
    private ModifyItemDialogListener listener;

    ItemDAO itemDAO;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.admin_modify_item_dialog, null);

        itemDAO = Room.databaseBuilder(getContext(), ItemDatabase.class, ItemDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getItemDAO();


        editTextItemName = view.findViewById(R.id.dialogItemName);
        editTextItemDescription = view.findViewById(R.id.dialogItemDescription);
        editTextItemPrice = view.findViewById(R.id.dialogItemPrice);
        editTextItemStock = view.findViewById(R.id.dialogItemStock);


        builder.setView(view)
                .setTitle("Enter New Item Specifications")
                .setMessage(" - If item name is empty, the item will not be modified. \n " +
                        " - If the item name is changed to another item name in the shop, then that item will be modified instead. \n" +
                        "     * For example, if you are editing 'Black Keycaps' but you enter 'Baby Blue Keycaps' then the Baby Blue Keycaps will be modified ")
                .setNegativeButton("Cancel", (dialog, which) -> StyleableToast.makeText(getContext(), "Item will not be modified.", R.style.specialToast).show())
                .setPositiveButton("Confirm", (dialog, which) -> {
                    String itemName = editTextItemName.getText().toString();
                    String itemDescription = editTextItemDescription.getText().toString();

                    double itemPrice = 0;
                    try {
                        itemPrice = Double.parseDouble(editTextItemPrice.getText().toString());
                    } catch (NumberFormatException e) {
                        StyleableToast.makeText(getContext(), "Invalid/Empty price input, item will not be modified. Returning to previous page.", R.style.specialToast).show();
                        startActivity(new Intent(getActivity(), AdminActivity.class));
                        e.printStackTrace();
                    }

                    int itemStock = 0;
                    try {
                        itemStock = Integer.parseInt(editTextItemStock.getText().toString());
                    } catch (NumberFormatException e) {
                        StyleableToast.makeText(getContext(), "Invalid/Empty stock input, item will not be modified. Returning to previous page.", R.style.specialToast).show();
                        startActivity(new Intent(getActivity(), AdminActivity.class));
                        e.printStackTrace();
                    }

                    if(itemName.isEmpty() || itemDescription.isEmpty() || editTextItemPrice.getText().toString().isEmpty() || editTextItemStock.getText().toString().isEmpty()){
                        StyleableToast.makeText(getContext(), "Invalid/Empty input(s), item will not be modified. Returning to previous page.", R.style.specialToast).show();
                        startActivity(new Intent(getActivity(), AdminActivity.class));
                    } else if (itemDAO.getItemFromName(itemName) == null){
                        StyleableToast.makeText(getContext(), "Empty name detected, item will not be modified. Returning to previous page", R.style.specialToast).show();
                        startActivity(new Intent(getActivity(), AdminActivity.class));
                    }else {
                        listener.applyInformation(itemName, itemDescription, itemPrice, itemStock);
                    }
                });

        return builder.create();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (ModifyItemDialogListener) context;
        } catch(ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement ModifyItemListener");
        }
    }

    public interface ModifyItemDialogListener{
        void applyInformation(String name, String description, double price, int stock);
    }

}
