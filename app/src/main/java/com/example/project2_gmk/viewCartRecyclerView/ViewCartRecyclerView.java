package com.example.project2_gmk.viewCartRecyclerView;

public class ViewCartRecyclerView {
    private String name;
    private String description;
    private double price;

    public ViewCartRecyclerView(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public void changeName(String text){
        name = text;
    }

    public void changeDescription(String text){
        description = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
