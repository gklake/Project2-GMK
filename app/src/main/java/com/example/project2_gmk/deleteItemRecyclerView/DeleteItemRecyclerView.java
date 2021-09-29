package com.example.project2_gmk.deleteItemRecyclerView;

public class DeleteItemRecyclerView {
    private String itemName;
    private String itemDescription;

    public DeleteItemRecyclerView(String textViewItemName, String textViewItemDescription) {
        this.itemName = textViewItemName;
        this.itemDescription = textViewItemDescription;
    }

    public void changeText1(String text) {
        itemName = text;
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
