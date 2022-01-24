/*
 * Fragment that houses the search page. Features a search function at the top where the user inputs a query to open the
 * SearchResultsActivity.
 */

package com.example.checkedapp.fragments.search;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.app.appsearch.SearchResults;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.checkedapp.R;
import com.example.checkedapp.SearchResultsActivity;
import com.example.checkedapp.databinding.FragmentSearchBinding;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;

    }

    //Initializes search bar at the top of the Search Page
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_search, menu);

        //Initializes SearchView, starts SearchResultActivity when query is submitted.
        SearchManager searchManager = (SearchManager) requireActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        SearchableInfo searchableInfo = searchManager.getSearchableInfo(new ComponentName(getContext(), SearchResultsActivity.class));
        searchView.setSearchableInfo(searchableInfo);
        searchView.setOnQueryTextListener (new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {

                //Stores query as the most recent query to be used by the "Last Checked" button in Home Page.
                PreferenceManager.getDefaultSharedPreferences(getContext()).edit().putString("recent", query).apply();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                return true;
            }

        });

        
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}