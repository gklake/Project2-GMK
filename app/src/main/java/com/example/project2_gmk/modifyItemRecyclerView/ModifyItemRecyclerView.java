package com.example.project2_gmk.modifyItemRecyclerView;

public class ModifyItemRecyclerView {
    private String itemName;
    private String itemDescription;

    public ModifyItemRecyclerView(String textViewItemName, String textViewItemDescription) {
        this.itemName = textViewItemName;
        this.itemDescription = textViewItemDescription;
    }

    public void changeName(String text) {
        itemName = text;
    }

    public void changeDescription(String text) {
        itemDescription = text;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
}
