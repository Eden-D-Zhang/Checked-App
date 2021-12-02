package com.example.checkedapp;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ItemListing {

    private String listingName;
    private final ArrayList<Item> itemList;
    private double lowestPrice;
    private double highestPrice;
    private boolean hasStock;

    public ItemListing(ArrayList<Item> items, String name) {
        listingName = name;
        itemList = items;
        this.setLowestPrice();
        this.setHighestPrice();
        this.setHasStock();
    }

    public String getListingName() {
        return listingName;
    }

    public double getLowestPrice() {
        return lowestPrice;
    }

    public double getHighestPrice() {
        return highestPrice;
    }

    public boolean getHasStock() {
        return hasStock;
    }

    public void setLowestPrice() {
        double min = itemList.get(0).getItemPrice();
        for (Item item : itemList) {
            if (item.getItemPrice() < min) {
                min = item.getItemPrice();
            }
        }

        lowestPrice = min;
    }

    public void setHighestPrice() {
        double max = itemList.get(0).getItemPrice();
        for (Item item : itemList) {
            if (item.getItemPrice() > max) {
                max = item.getItemPrice();
            }
        }

        highestPrice = max;
    }

    public void setHasStock() {
        for (Item item : itemList) {
            if (item.getItemStock()) {
                hasStock = true;
                return;
            }
        }

        hasStock = false;
    }


}
