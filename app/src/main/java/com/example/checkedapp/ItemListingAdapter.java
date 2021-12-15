package com.example.checkedapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.checkedapp.Item;
import com.example.checkedapp.R;
import com.example.checkedapp.fragments.favourites.FavouritesFragment;

import java.util.List;

public class ItemListingAdapter extends RecyclerView.Adapter<ItemListingAdapter.ViewHolder> {

    private List<ItemListing> mData;
    private Context mContext;
    RequestOptions option;

    public ItemListingAdapter(Context mContext, List<ItemListing> mData) {
        this.mContext = mContext;
        this.mData = mData;
        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        // Inflate the custom layout
        view = inflater.inflate(R.layout.itemlisting_layout, parent, false);

        // Return a new holder instance
        return new ViewHolder(view);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the data model based on position
        ItemListing listing = mData.get(position);


        holder.nameTextView.setText(listing.getListingName());
        holder.lowestPriceTextView.setText("Lowest Price: $" + listing.getLowestPrice());
        holder.highestPriceTextView.setText("Highest Price: $" + listing.getHighestPrice());
        Glide.with(mContext).load(listing.getFirstImage()).apply(option).into(holder.img_thumbnail);
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
Log.d("Message","Hello");
            }
        });

    }


    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView lowestPriceTextView;
        TextView highestPriceTextView;
        ImageView img_thumbnail;
        Button deleteButton;

        public ViewHolder(View itemView) {

            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.listingName);
            lowestPriceTextView = (TextView) itemView.findViewById(R.id.lowestPrice);
            highestPriceTextView = (TextView) itemView.findViewById(R.id.highestPrice);
            img_thumbnail = (ImageView) itemView.findViewById(R.id.listingThumbnail);
            deleteButton = (Button) itemView.findViewById(R.id.deletelistBtn);
        }
    }
}