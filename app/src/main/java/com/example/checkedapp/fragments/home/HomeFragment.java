/*
 * Fragment that houses the home page.
 */

package com.example.checkedapp.fragments.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.checkedapp.Item;
import com.example.checkedapp.R;
import com.example.checkedapp.SearchResultsActivity;
import com.example.checkedapp.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("9762313f3fmsh261831e1ac2a541p11b3d8jsna6690dad2326", Context.MODE_PRIVATE);
        Log.d("Keyprefs",sharedPreferences.getString("keyName","defaultValue"));
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        Button recentSearchButton = (Button) root.findViewById(R.id.recentSearchButton);
        recentSearchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String recentQuery = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("recent", "");

                Intent i = new Intent(getContext(), SearchResultsActivity.class);
                i.putExtra("query", recentQuery);
                i.putExtra("fragmentNumber", 1);

                startActivity(i);


            }
        });



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}