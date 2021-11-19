package com.example.checkedapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.checkedapp.Item;
import com.example.checkedapp.R;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTextView;
        public TextView priceTextView;
        public TextView starsTextView;

        public ViewHolder(View itemView) {

            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.itemName);
            priceTextView = (TextView) itemView.findViewById(R.id.itemPrice);
            starsTextView = (TextView) itemView.findViewById(R.id.itemStars);
        }
    }

    private List<Item> mItems;

    public ItemsAdapter(List<Item> items) {
        mItems = items;
    }

    @Override
    public ItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);

        // Return a new holder instance
        return new ViewHolder(contactView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ItemsAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Item item = mItems.get(position);

        holder.itemView.setBackgroundResource(item.isSelected() ? R.color.purple_200 : R.color.white);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                item.setSelected(!item.isSelected());
                holder.itemView.setBackgroundResource(item.isSelected() ? R.color.purple_200 : R.color.white);
            }
        });

        // Set item views based on your views and data model
        TextView textView = holder.nameTextView;
        textView.setText(item.getItemName());
        TextView priceTextView = holder.priceTextView;
        priceTextView.setText("$" + item.getItemPrice());
        TextView starsTextView = holder.starsTextView;
        starsTextView.setText(item.getItemStars() + "*");

    }


    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mItems.size();
    }
}