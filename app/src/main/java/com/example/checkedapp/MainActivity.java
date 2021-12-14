package com.example.checkedapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.example.checkedapp.fragments.favourites.FavouritesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import com.example.checkedapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_favourites, R.id.navigation_search)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        //When launching MainActivity after a new item listing is created
        if (getIntent().getIntExtra("fragmentNumber", 0) == 2){

            //Get the keyword that was just searched
            String lastSearch = getIntent().getStringExtra("query");

            //Check the key database for if this key has been searched before
            SharedPreferences keyprefs = getSharedPreferences("9762313f3fmsh261831e1ac2a541p11b3d8jsna6690dad2326", MODE_PRIVATE);
            SharedPreferences.Editor keyEditor = keyprefs.edit();
            String currentKeys = keyprefs.getString("keyName", "defaultValue");

            if ((currentKeys.contains("defaultValue"))){
                keyEditor.putString("keyName", lastSearch);
                keyEditor.apply();
            }
            //If the database doesn't already have this key
            if (!(currentKeys.contains(lastSearch))){
                String newKeys = currentKeys+"\n"+lastSearch;
                Log.d("New keys:",newKeys);
                keyEditor.putString("keyName", newKeys);
                keyEditor.apply();

            }

            navController.navigate(R.id.navigation_favourites);

        }
    }

}