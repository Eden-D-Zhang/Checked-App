package com.example.checkedapp;

import java.util.ArrayList;

public class Item {

    private String itemName;
    private double itemPrice;
    private double itemStars;
    private boolean isInStock;
    private String itemLink;
    private boolean isSelected = false;

    public Item (String name, double price, double stars, boolean stock, String link) {
        itemName = name;
        itemPrice = price;
        itemStars = stars;
        isInStock = stock;
        itemLink = link;
    }

    public String getItemName() {
        return itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public double getItemStars() {
        return itemStars;
    }

    public boolean getItemStock() {
        return isInStock;
    }

    public String getItemLink() {
        return itemLink;
    }

    public void setSelected(boolean select) {
        isSelected = select;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public static ArrayList<Item> createItemList(int numItems) {
        ArrayList<Item> items = new ArrayList<Item>();

        for (int i = 1; i <= numItems; i++) {
            items.add(new Item("PS5", 623.00, 4.5, true, "https://empty_link"));
        }

        return items;
    }

}
