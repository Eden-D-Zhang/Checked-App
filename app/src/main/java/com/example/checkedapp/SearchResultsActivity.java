package com.example.checkedapp;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class SearchResultsActivity extends Activity {

    private final String JSON_URL = "https://amazon-data-product-scraper.p.rapidapi.com/search/gaming%20mouse?api_key=548851825ac43f460f8ec20f2c8ab823";

    ArrayList<Item> items;
    private RequestQueue mQueue;
    private JsonObjectRequest request;
    private RecyclerView rvItems;
    private ItemsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleIntent(getIntent());
        setContentView(R.layout.activity_searchresults);
        items = new ArrayList<>();
        rvItems = (RecyclerView) findViewById(R.id.rvItems);
        mQueue = Volley.newRequestQueue(this);
        setuprecyclerview(items);
       // items = Item.createItemList(20);
        jsonParse();
    }
    private void jsonParse() {
        request = new JsonObjectRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray jsonArray = response.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject Jobj = jsonArray.getJSONObject(i);

                        Item item = new Item();
                        item.setItemName(Jobj.getString("name"));
                        item.setItemPrice(Jobj.getDouble("price"));
                        item.setItemStars(Jobj.getDouble("stars"));
                        item.setItemLink(Jobj.getString("url"));
                        item.setImageUrl(Jobj.getString("image"));

                        items.add(item);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            setuprecyclerview(items);
            }
/*
       request = new JsonArrayRequest(Request.Method.GET, JSON_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {

                            try {
                                JSONObject item = response.getJSONObject(i);

                                Item product = new Item();
                                product.setItemName(item.getString("name"));
                                product.setItemPrice(item.getDouble("price"));
                                product.setItemStars(item.getDouble("stars"));
                                product.setItemLink(item.getString("url"));
                                product.setImageUrl(item.getString("image"));
                                items.add(product);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        setuprecyclerview(items);
                    }*/
                }, new com.android.volley.Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("x-rapidapi-host", "amazon-data-product-scraper.p.rapidapi.com");
                params.put("x-rapidapi-key", "9762313f3fmsh261831e1ac2a541p11b3d8jsna6690dad2326");
                return params;
            }
        };

        //request.setRetryPolicy(new RetryPolicy() {

            request.setRetryPolicy(new DefaultRetryPolicy(
    0,
    -1,
    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            /*@Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }

        });
*/

        mQueue.add(request);


    }
    private void setuprecyclerview(List<Item> items){


        adapter = new ItemsAdapter(this, items);
        rvItems.setHasFixedSize(true);
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


}
