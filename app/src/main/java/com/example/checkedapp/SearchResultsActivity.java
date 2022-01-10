/*
 * Activity that opens whenever a query is entered in the search page. Displays items related to the query
 * in a RecyclerView. The user selects items to add to a listing, then creates it, opening the favourites page.
 * This is also the activity that makes the product API request to retrieve the results to be displayed.
 */

package com.example.checkedapp;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.checkedapp.fragments.favourites.FavouritesFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchResultsActivity extends Activity {

    private String JSON_URL = "https://amazon-data-product-scraper.p.rapidapi.com/search/";

    ArrayList<Item> items;
    ArrayList<Item> selectedItems;
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

        if (getIntent().getIntExtra("fragmentNumber", 0) == 2) {
            Log.d("message", "it worked");
        }

        else {
            setuprecyclerview(items);
            jsonParse(getKeyword(getIntent()));
        }
    }
    private void jsonParse(String query){

        int lastspace = 0;
        for (int i = 0; i<query.length(); i++){
            if (query.charAt(i)==' '){
                JSON_URL = JSON_URL + query.substring(lastspace, i) + "%20";
                lastspace = i+1;
            }
        }
        JSON_URL = JSON_URL+ query.substring(lastspace)+"?api_key=32df47294e867894f92dcd6d71f0f58f";



        request = new JsonObjectRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray jsonArray = response.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject Jobj = jsonArray.getJSONObject(i);

                        Item item = new Item();
                        item.setItemName(Jobj.getString("name").substring(0, Math.min(Jobj.getString("name").length(), 35))+"...");
                        try{
                            item.setItemPrice(Jobj.getDouble("price"));
                        }
                        catch (JSONException tmm){
                            item.setItemPrice(0.0);
                        }
                        try {
                            item.setItemStars(Jobj.getDouble("stars"));
                        }
                        catch (JSONException tmm){
                            item.setItemStars(0.0);
                        }
                        item.setItemLink(Jobj.getString("url"));
                        item.setImageUrl(Jobj.getString("image"));
                        item.setItemId(i);
                        items.add(item);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                setuprecyclerview(items);
            }

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
                params.put("x-rapidapi-host", "amazon-web-scrapper1.p.rapidapi.com");
                params.put("x-rapidapi-key", "9762313f3fmsh261831e1ac2a541p11b3d8jsna6690dad2326");
                return params;
            }
        };

            request.setRetryPolicy(new DefaultRetryPolicy(
    0,
    -1,
    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(request);


    }
    private void setuprecyclerview(List<Item> items) {

        adapter = new ItemsAdapter(this, items);
        rvItems.setHasFixedSize(true);
        rvItems.setAdapter(adapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        Button createListButton = (Button) findViewById(R.id.createListButton);
        createListButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int numSelected = 0;
                selectedItems = new ArrayList<Item>();
                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        selectedItems.add(items.get(i));
                        Log.d("Message",items.get(i).toString());

                        items.get(i).setSelected(false);
                        numSelected++;
                    }
                }
                if (numSelected !=0) {
                    setuprecyclerview(selectedItems);
                    saveData();
                    getData();
                    goToFavourites(v);
                }
            }
        });
    }

    public void goToFavourites(View v) {
        String query = getKeyword(getIntent());
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.putExtra("fragmentNumber",2);
        i.putExtra("query", query);
        startActivity(i);

    }
    public void saveData() {

        //Get keyword the user searched for and use it as the header for SharedPreferences
        String query = getKeyword(getIntent());

        SharedPreferences sharedPreferences = getSharedPreferences(query, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
            String data = "";

            for (int i = 0; i< selectedItems.size(); i++){
                    data = data + selectedItems.get(i).toString()+"\n";
            }
            editor.putString("keyName",data);
            Log.d("Saving:",data);
            editor.apply();

            /*Update the keys database with this new key from the search
            SharedPreferences keys = getSharedPreferences("keys", MODE_PRIVATE);
            SharedPreferences.Editor keyEditor = keys.edit();
            String currentKeys = keys.getString("keyName", "defaultValue");
            if (!(currentKeys.contains(query))) {
                String newKeys = currentKeys + "\n" + query;
                keyEditor.putString("keyName", newKeys);
                keyEditor.apply();
            }

             */
    }
        public void getData(){

        String query = getKeyword(getIntent());
        SharedPreferences sharedPreferences = getSharedPreferences(query, MODE_PRIVATE);
        String data = sharedPreferences.getString("keyName","defaultValue");
        Log.d("Data:",data);
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

    private String getKeyword(Intent intent) {
        return intent.getStringExtra(SearchManager.QUERY);
    }


}
