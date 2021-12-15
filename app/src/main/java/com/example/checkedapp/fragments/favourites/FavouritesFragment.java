package com.example.checkedapp.fragments.favourites;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.checkedapp.Item;
import com.example.checkedapp.ItemListing;
import com.example.checkedapp.ItemListingAdapter;
import com.example.checkedapp.R;
import com.example.checkedapp.databinding.FragmentFavouritesBinding;

import java.util.ArrayList;
import java.util.List;

public class FavouritesFragment extends Fragment {

    private List<ItemListing> list;
    private RecyclerView recyclerView;
    private FavouritesViewModel FavouritesViewModel;
    private FragmentFavouritesBinding binding;
    private String keyword;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FavouritesViewModel =
                new ViewModelProvider(this).get(FavouritesViewModel.class);

        binding = FragmentFavouritesBinding.inflate(inflater, container, false);
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);

        //Temporary only for testing
        list = new ArrayList<ItemListing>();
        ArrayList<Item> test2 = new ArrayList<>();

        SharedPreferences sharedprefs = this.getActivity().getSharedPreferences("9762313f3fmsh261831e1ac2a541p11b3d8jsna6690dad2326", Context.MODE_PRIVATE);
        String allKeys = sharedprefs.getString("keyName","defaultValue");
        Log.d("All keys", allKeys);

            int lastLine = 0;
            String thisKey;
            for (int i = 0; i < allKeys.length(); i++) {
                if (allKeys.charAt(i) == '\n') {
                    thisKey = allKeys.substring(lastLine, i);
                    Log.d("This key",thisKey);
                    ArrayList<Item> newArray = new ArrayList<>();
                    createNewObjects(thisKey, newArray);
                    lastLine = i + 1;
                }
                if (i == allKeys.length()-1){
                    thisKey = allKeys.substring(lastLine, i+1);
                    Log.d("This key", thisKey);
                    ArrayList<Item> newArray = new ArrayList<>();
                    createNewObjects(thisKey, newArray);
                }
            }

        test2.add(new Item("Test", 10.00, 4.5, true, "notalink", "notaurl",3));
        test2.add(new Item("test2", 15.00, 5.0, true, "", "",4));
        list.add(new ItemListing(test2, "TestListing2"));

        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new ItemListingAdapter(view.getContext(), list));
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }


    public void createNewObjects(String keyword, ArrayList<Item> test) {
        if (!(keyword.equals("defaultValue"))) {
            SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(keyword, Context.MODE_PRIVATE);
            String data = sharedPreferences.getString("keyName", "defaultValue");
            Log.d("Data", data);

            int lastElement = 0;
            int numElement = 0;
            String iName = "";
            double iPrice = 0.00;
            double iStars = 0;
            boolean iInStock = false;
            String iLink = "";
            String iImUrl = "";
            int iId = 0;

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
                        test.add(new Item(iName, iPrice, iStars, iInStock, iLink, iImUrl, iId));
                        Log.d("Id", data.substring(lastElement, i));
                        numElement = 0;
                    }
                    lastElement = i + 1;
                    numElement++;
                }
            }
            list.add(new ItemListing(test,keyword.toUpperCase()));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}