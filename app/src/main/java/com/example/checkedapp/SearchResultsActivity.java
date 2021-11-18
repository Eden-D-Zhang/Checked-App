package com.example.checkedapp;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchResultsActivity extends Activity {

    ArrayList<Item> items;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());
        setContentView(R.layout.activity_searchresults);

        RecyclerView rvItems = (RecyclerView) findViewById(R.id.rvItems);

        items = Item.createItemList(10);

        ItemsAdapter adapter = new ItemsAdapter(items);

        rvItems.setAdapter(adapter);

        rvItems.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
        }
    }

    boolean isClicked = false;
    public void itemSelect(View view) {

        if (!isClicked) {
            view.setBackgroundResource(R.color.purple_500);
            isClicked = true;
        }
        else {
            view.setBackgroundResource(R.color.white);
            isClicked = false;
        }
    }


}
