/*
 * Activity similar to SearchResultsActivity that displays products within an Item Listing. Allows the user to open web page of all
 * Items within the Listing, and has a button that redirects to SearchResultsActivity so the user can get updated information on Items,
 * as well as the ability to add or remove Items from the Listing. Items that were already in the Listing will be highlighted green.
 * Unfortunately, since the API displays slightly different results when searching the same query, Items that were in the Listing may not
 * be displayed and updated when opening SearchResultsActivity.
 */

package com.example.checkedapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductsActivity extends Activity {

    ArrayList<Item> items;
    private RecyclerView rvProducts;
    private ItemsAdapter adapter;
    private String keyword;

    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;
    String[] sorting = {"Price","Quantity","Rating"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        items = new ArrayList<>();
        rvProducts = (RecyclerView) findViewById(R.id.rvProducts);

      setuprecyclerview(items);

        keyword = getIntent().getStringExtra("name");

        //Retrieves Item information from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(keyword, MODE_PRIVATE);
        String allProducts = sharedPreferences.getString("keyName", "defaultValue");
        Log.d("Products:",allProducts);

        createObjects(allProducts);

        //Sorting function that allows the user to sort Items based on price, rating, or availability.
        autoCompleteTxt = findViewById(R.id.autoCompleteTextView);

        adapterItems = new ArrayAdapter<String>(this, R.layout.dropdown_item,sorting);

        autoCompleteTxt.setAdapter(adapterItems);

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //OnItemClick: method called whenever one of the sorting options is selected from the drop down menu.
            //Rearranges the items array and runs setuprecyclerview to refresh the new order of the products each time.

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String item = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(),"Sorting items by "+item.toLowerCase(Locale.ROOT), Toast.LENGTH_SHORT).show();

                if (item.equals("Price")){
                    Item sItem = new Item();
                    int i=1;
                    while (i<items.size()){

                        //Check first if the item has an unlisted price(0)
                        if (items.get(i).getItemPrice()==0.0){
                            sItem = items.get(i);
                            items.remove(i);
                            items.add(sItem);
                            i++;
                        }
                                //If the current item is cheaper than the item above it in the list
                                else if (items.get(i).getItemPrice() < items.get(i - 1).getItemPrice()) {
                                    //Save the item as a variable, delete its original instance and re-insert a space above
                                    sItem = items.get(i);
                                    items.remove(i);
                                    items.add(i - 1, sItem);
                                    if (i != 1) {
                                        i--;
                                    }
                                } else if (items.get(i).getItemPrice() >= items.get(i - 1).getItemPrice()) {
                                    i++;
                                }
                    }
                    setuprecyclerview(items);
                }
                else if (item.equals("Quantity")){
                    Item sItem = new Item();
                    int i=1;
                    while (i<items.size()){
                        //Check first if the item has an unlisted quantity(-1)
                        if (items.get(i).getItemQuantity()==-1){
                            sItem = items.get(i);
                            items.remove(i);
                            items.add(sItem);
                            i++;
                        }
                        //If the current item has more available in stock than the item above it in the list
                        else if (items.get(i).getItemQuantity() > items.get(i - 1).getItemQuantity()) {
                            //Save the item as a variable, delete its original instance and re-insert a space above
                            sItem = items.get(i);
                            items.remove(i);
                            items.add(i - 1, sItem);
                            if (i != 1) {
                                i--;
                            }
                        } else if (items.get(i).getItemQuantity() <= items.get(i - 1).getItemQuantity()) {
                            i++;
                        }
                    }
                    setuprecyclerview(items);
                }
                else if (item.equals("Rating")){
                    Item sItem = new Item();
                    int i=1;
                    while (i<items.size()){

                        //If the current item has a higher rating than the item above it in the list
                        if (items.get(i).getItemStars() > items.get(i - 1).getItemStars()) {
                            //Save the item as a variable, delete its original instance and re-insert a space above
                            sItem = items.get(i);
                            items.remove(i);
                            items.add(i - 1, sItem);
                            if (i != 1) {
                                i--;
                            }
                        } else if (items.get(i).getItemStars() <= items.get(i - 1).getItemStars()) {
                            i++;
                        }
                    }
                    setuprecyclerview(items);
                }
                }
            });
    }

    public void setuprecyclerview(List<Item> items){
        adapter = new ItemsAdapter(getIntent(),this, items);
        rvProducts.setHasFixedSize(true);
        rvProducts.setAdapter(adapter);
        rvProducts.setLayoutManager(new LinearLayoutManager(this));

        //Button that redirects to SearchResultsActivity to update, add, and/or remove Items.
        Button updateListButton = (Button) findViewById(R.id.updateListButton);
        updateListButton.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v){
                Intent i = new Intent(getApplicationContext(), SearchResultsActivity.class);
                i.putExtra("fragmentNumber", 2);
                i.putExtra("name", keyword);
                startActivity(i);
            }
    });
}
    //Populates Item information
    public void createObjects(String data){

        String iName = "";
        double iPrice = 0;
        double iStars = 0;
        boolean iInStock = false;
        String iLink = "";
        String iImUrl = "";
        int iId = 0;
        int iQuant = 0;

        int numElement = 0;
        int lastElement = 0;

        try {
            for (int i = 0; i < data.length(); i++) {
                if (i == 0) {
                    Log.d("Name", data.substring(0, data.indexOf('\n')));
                    iName = data.substring(0, data.indexOf('\n'));
                    numElement = 2;
                    i = data.indexOf('\n') + 1;
                    lastElement = i;
                }
                if (data.charAt(i) == '\n') {
                    if (numElement == 1) {
                        iName = (data.substring(lastElement, i));
                        Log.d("Name", data.substring(lastElement, i));
                    }
                    if (numElement == 2) {
                        iPrice = (Double.parseDouble(data.substring(lastElement, i)));
                        Log.d("Price", data.substring(lastElement, i));
                    }
                    if (numElement == 3) {
                        iStars = (Double.parseDouble(data.substring(lastElement, i)));
                        Log.d("Stars", data.substring(lastElement, i));
                    }
                    if (numElement == 4) {
                        iInStock = (Boolean.parseBoolean(data.substring(lastElement, i)));
                        Log.d("Stock", data.substring(lastElement, i));
                    }
                    if (numElement == 5) {
                        iLink = (data.substring(lastElement, i));
                        Log.d("Link", data.substring(lastElement, i));
                    }
                    if (numElement == 6) {
                        iImUrl = (data.substring(lastElement, i));
                        Log.d("Imageurl", data.substring(lastElement, i));
                    }
                    if (numElement == 7) {
                        iId = (Integer.parseInt(data.substring(lastElement, i)));
                        Log.d("Id", data.substring(lastElement, i));
                    }
                    if (numElement == 8) {
                        iQuant = (Integer.parseInt(data.substring(lastElement, i)));
                        Log.d("Quantity",data.substring(lastElement,i));
                        items.add(new Item(iName, iPrice, iStars, iInStock, iLink, iImUrl, iId, iQuant));
                        numElement = 0;
                    }
                    lastElement = i + 1;
                    numElement++;
                }
            }
        } catch (IndexOutOfBoundsException e) {
            Log.d("Error", "Ind.O.O.B.E");
        }
    }
}
