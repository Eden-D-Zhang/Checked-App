package com.example.checkedapp;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ItemListing {

    private final ArrayList<Item> itemList;
    private double lowestPrice;
    private boolean hasStock;

    public ItemListing(ArrayList<Item> items) {
        itemList = items;
        this.setLowestPrice();
        this.setHasStock();
    }

    public double getLowestPrice() {
        return lowestPrice;
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
