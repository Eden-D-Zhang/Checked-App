package com.example.checkedapp;

import java.util.ArrayList;

public class Item {

    private String itemName;
    private double itemPrice;
    private double itemStars;
    private boolean isInStock;
    private String itemLink;
    private String imageUrl;
    private boolean isSelected = false;
    private int itemId;

    public Item(){
    }

    public Item (String name, double price, double stars, boolean stock, String link, String url, int id) {
        this.itemName = name;
        this.itemPrice = price;
        this.itemStars = stars;
        this.isInStock = stock;
        this.itemLink = link;
        this.imageUrl = url;
        this.itemId = id;
    }

    public String toString(){ return itemName+" "+itemPrice+" "+itemStars+" "+isInStock+" "+itemLink+" "+imageUrl+" "+itemId;}

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

    public String getImage_url() {
        return imageUrl;
    }

    public int getItemId() { return itemId; }

    public void setItemId(int id) {this.itemId = id; }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemStars(double itemStars) {
        this.itemStars = itemStars;
    }

    public void setItemLink(String itemLink) {
        this.itemLink = itemLink;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setSelected(boolean select) {
        isSelected = select;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setItemPrice(double newPrice) {
        itemPrice = newPrice;
    }

    public void setInStock(boolean stock) {
        isInStock = stock;
    }



    /*public static ArrayList<Item> createItemList(int numItems) {
        ArrayList<Item> items = new ArrayList<Item>();

        for (int i = 1; i <= numItems; i++) {
            items.add(new Item("PS5", 623.00, 4.5, true, "https://empty_link", "https://m.media-amazon.com/images/I/61t7877TYES.jpg"));
        }

        return items;
    }
*/
}
