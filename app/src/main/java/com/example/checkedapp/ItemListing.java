/*
 * Object file for ItemListings to be shown in the favourites page. Methods to set and retrieve information on the
 * items within the listing.
 */

package com.example.checkedapp;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ItemListing {

    private String listingName;
    private final ArrayList<Item> itemList;
    private double lowestPrice;
    private double highestPrice;
    private boolean hasStock;
    private Item cheapestItem;
    private boolean isExpanded;

    public ItemListing(ArrayList<Item> items, String name) {
        listingName = name;
        itemList = items;
        this.setHighestPrice();
        this.setLowestPrice();
        this.setHasStock();
        this.setCheapestItem();
    }

    public String getListingName() {
        return listingName;
    }

    public boolean getIsExpanded() {
        return isExpanded;
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

    public String getFirstImage(){
        return itemList.get(0).getImage_url();
    }

    public Item getCheapestItem() {
        return cheapestItem;
    }

    public ArrayList<Item> getItemList() {return itemList;}

    public void setIsExpanded() {
        isExpanded = !isExpanded;
    }

    public void setCheapestItem() {
        Item cheapest = itemList.get(0);

        while (cheapest.getItemPrice() == 0.0) {
            cheapest = itemList.get(itemList.indexOf(cheapest) + 1);
        }

        for (Item item : itemList) {
            if (item.getItemPrice() < cheapest.getItemPrice() && item.getItemPrice() > 0.0) {
                cheapest = item;
            }
        }

        cheapestItem = cheapest;
    }

    public void setLowestPrice() {
        double min = getHighestPrice();
        for (Item item : itemList) {
            if (item.getItemPrice() < min && item.getItemPrice() > 0.0) {
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
