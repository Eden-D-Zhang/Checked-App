package com.example.checkedapp.fragments.favourites;

import android.os.Bundle;
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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        FavouritesViewModel =
                new ViewModelProvider(this).get(FavouritesViewModel.class);

        binding = FragmentFavouritesBinding.inflate(inflater, container, false);
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);

        //Temporary only for testing
        list = new ArrayList<ItemListing>();
        ArrayList<Item> test = new ArrayList<>();
        ArrayList<Item> test2 = new ArrayList<>();
        test.add(new Item("Test", 10.00, 4.5, true, "notalink", "notaurl"));
        test.add(new Item("test2", 15.00, 5.0, true, "", ""));
        test2.add(new Item("Test", 10.00, 4.5, true, "notalink", "notaurl"));
        test2.add(new Item("test2", 15.00, 5.0, true, "", ""));
        list.add(new ItemListing(test, "TestListing1"));
        list.add(new ItemListing(test2, "TestListing2"));

        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new ItemListingAdapter(view.getContext(), list));
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}