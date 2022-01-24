/*
 * Object class for items. Stores basic information on products. Information is retrieved from Amazon using an API.
 * Displayed in the SearchResultsActivity class. Multiple items can be in an item listing, which compares the items
 * to return relevant information.
 */

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
    private int itemQuantity;
    private boolean isExpanded;

    public Item(){
    }

    //Primary constructor. Used when populating RecyclerViews in SearchResultsActivity and ProductsActivity
    public Item (String name, double price, double stars, boolean stock, String link, String url, int id, int quantity) {
        this.itemName = name;
        this.itemPrice = price;
        this.itemStars = stars;
        this.isInStock = stock;
        this.itemLink = link;
        this.imageUrl = url;
        this.itemId = id;
        this.itemQuantity = quantity;
    }
    //Returns information on the Item in String format
    public String toString(){ return itemName+"\n"+itemPrice+"\n"+itemStars+"\n"+isInStock+"\n"+itemLink+"\n"+imageUrl+"\n"+itemId+"\n"+itemQuantity;
}

    //Getter methods
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

    public int getItemQuantity() {return itemQuantity;}

    public boolean getIsExpanded() {
        return isExpanded;
    }

    //Setter methods
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

    public void setItemQuantity(int quantity) {itemQuantity = quantity;}

    public void setExpanded() {
        isExpanded = !isExpanded;
    }

}
