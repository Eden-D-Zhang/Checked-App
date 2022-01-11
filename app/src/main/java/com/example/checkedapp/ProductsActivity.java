package com.example.checkedapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ProductsActivity extends Activity {

    ArrayList<Item> items;
    private RecyclerView rvProducts;
    private ItemsAdapter adapter;
    private String keyword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        items = new ArrayList<>();
        rvProducts = (RecyclerView) findViewById(R.id.rvProducts);

      setuprecyclerview(items);

        keyword = getIntent().getStringExtra("name");

        SharedPreferences sharedPreferences = getSharedPreferences(keyword, MODE_PRIVATE);
        String allProducts = sharedPreferences.getString("keyName", "defaultValue");
        Log.d("Products:",allProducts);

        createObjects(allProducts);


    }

    public void setuprecyclerview(List<Item> items){
        adapter = new ItemsAdapter(this, items);
        rvProducts.setHasFixedSize(true);
        rvProducts.setAdapter(adapter);
        rvProducts.setLayoutManager(new LinearLayoutManager(this));

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
