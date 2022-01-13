/*
 * Adapter file for search result RecyclerView. Controls behaviour of the RecyclerView.
 */

package com.example.checkedapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    private List<Item> mData;
    private Context mContext;
    private Intent mIntent;
    RequestOptions option;

    public ItemsAdapter(Intent intent, Context mContext, List<Item> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.mIntent = intent;

        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        // Inflate the custom layout
        view = inflater.inflate(R.layout.item_layout, parent, false);

        // Return a new holder instance
        return new ViewHolder(view);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Get the data model based on position
        Item item = mData.get(position);

        holder.nameTextView.setText(item.getItemName().substring(0, Math.min(item.getItemName().length(), 40))+"...");
        if (item.getItemPrice()==0){
            holder.priceTextView.setText("Unlisted");
        }
        else {
            holder.priceTextView.setText("$" + item.getItemPrice());
        }
        holder.starsTextView.setText(String.valueOf(item.getItemStars()));

        if (item.getItemQuantity()==0){
            holder.stockTextView.setText("Out of stock.");
        }
        else if (item.getItemQuantity()==-1){
            holder.stockTextView.setText("");
        }
        else {
            holder.stockTextView.setText(item.getItemQuantity() + " left in stock");
        }

        holder.linkTextView.setText(item.getItemLink().substring(0, Math.min(item.getItemLink().length(), 20))+"...");
        Glide.with(mContext).load(item.getImage_url()).apply(option).into(holder.img_thumbnail);

        holder.itemView.setTag(item.getItemId());

        holder.itemView.setBackgroundResource(item.isSelected() ? R.color.purple_200 : R.color.white);

        //Items can only be clicked/selected if it is the SearchResultsActivity and not the ProductsActivity
        if (mIntent.getIntExtra("fragmentNumber",0)!=5) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    item.setSelected(!item.isSelected());
                    Log.d("MyTag", String.valueOf(holder.itemView.getTag()));
                    Log.d("Status:", String.valueOf(item.isSelected()));
                    holder.itemView.setBackgroundResource(item.isSelected() ? R.color.purple_200 : R.color.white);
                }
            });
        }

    }


    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

         TextView nameTextView;
         TextView priceTextView;
         TextView starsTextView;
         TextView stockTextView;
         TextView linkTextView;
         ImageView img_thumbnail;

        public ViewHolder(View itemView) {

            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.itemName);
            priceTextView = (TextView) itemView.findViewById(R.id.itemPrice);
            starsTextView = (TextView) itemView.findViewById(R.id.itemStars);
            stockTextView = (TextView) itemView.findViewById(R.id.itemStock);
            linkTextView = (TextView) itemView.findViewById(R.id.itemLink);
            img_thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
        }
    }
}