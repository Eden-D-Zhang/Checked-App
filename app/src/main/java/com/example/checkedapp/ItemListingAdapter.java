/*
 * Adapter file for the favourites page RecyclerView. Controls behaviour of the RecyclerView.
 */

package com.example.checkedapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.checkedapp.Item;
import com.example.checkedapp.R;

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

        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                listing.setIsExpanded();

                notifyItemChanged(holder.getAdapterPosition());
            }
        });

        boolean expanded = listing.getIsExpanded();
        holder.details.setVisibility(expanded ? View.VISIBLE : View.GONE);

        holder.link.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String url = listing.getCheapestItem().getItemLink();

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                mContext.startActivity(intent);
            }

        });
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = mContext.getSharedPreferences("9762313f3fmsh261831e1ac2a541p11b3d8jsna6690dad2326", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                String keyword = listing.getListingName();
                String prefs = sharedPreferences.getString("keyName", "defaultValue");

                Log.d("keyword",keyword);
                Log.d("prefs", prefs);
                Log.d("Index", prefs.indexOf(keyword) + "!");

                //Remove the keyword from the string by taking the characters before and after it

                //NOTE: FIX THIS STRING PARSING
                String newprefs = prefs.substring(0, prefs.indexOf(keyword)-1) + prefs.substring(prefs.indexOf(keyword) + keyword.length());
                Log.d("Updated prefs", newprefs);
                editor.putString("keyName", newprefs);
                editor.apply();

                mData.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());

            }
        });
        holder.updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
            Intent i = new Intent(mContext.getApplicationContext(), ProductsActivity.class);
            i.putExtra("fragmentNumber", 2);
            i.putExtra("name",listing.getListingName());
            mContext.startActivity(i);
            }
        });

        holder.nameTextView.setText(listing.getListingName());
        holder.lowestPriceTextView.setText("Lowest Price: $" + listing.getLowestPrice());
        holder.highestPriceTextView.setText("Highest Price: $" + listing.getHighestPrice());
        holder.numItems.setText(listing.getItemList().size() + " Items");
        Glide.with(mContext).load(listing.getFirstImage()).apply(option).into(holder.img_thumbnail);


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
        TextView numItems;
        ImageView img_thumbnail;
        LinearLayout details;
        Button link;
        Button deleteButton;
        Button updateButton;

        public ViewHolder(View itemView) {

            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.listingName);
            lowestPriceTextView = (TextView) itemView.findViewById(R.id.lowestPrice);
            highestPriceTextView = (TextView) itemView.findViewById(R.id.highestPrice);
            img_thumbnail = (ImageView) itemView.findViewById(R.id.listingThumbnail);
            numItems = (TextView) itemView.findViewById(R.id.numItems);
            details = (LinearLayout) itemView.findViewById(R.id.expandedListing);
            link = (Button) itemView.findViewById(R.id.cheapestLink);
            deleteButton = (Button) itemView.findViewById(R.id.deletelistBtn);
            updateButton = (Button) itemView.findViewById(R.id.updateBtn);
        }
    }
}