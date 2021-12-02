package com.example.checkedapp;

import android.content.Context;
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
    RequestOptions option;

    public ItemsAdapter(Context mContext, List<Item> mData) {
        this.mContext = mContext;
        this.mData = mData;

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

        holder.nameTextView.setText(item.getItemName());
        holder.priceTextView.setText("$" + item.getItemPrice());
        holder.starsTextView.setText(String.valueOf(item.getItemStars()));

        Glide.with(mContext).load(item.getImage_url()).apply(option).into(holder.img_thumbnail);

        holder.itemView.setTag(item.getItemId());

        holder.itemView.setBackgroundResource(item.isSelected() ? R.color.purple_200 : R.color.white);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                item.setSelected(!item.isSelected());
                Log.d("MyTag",String.valueOf(holder.itemView.getTag()));
                Log.d("Status:",String.valueOf(item.isSelected()));
                holder.itemView.setBackgroundResource(item.isSelected() ? R.color.purple_200 : R.color.white);
            }
        });

        // Set item views based on your views and data model
        /*TextView textView = holder.nameTextView;
        textView.setText(item.getItemName());
        TextView priceTextView = holder.priceTextView;
        priceTextView.setText("$" + item.getItemPrice());
        TextView starsTextView = holder.starsTextView;
        starsTextView.setText(item.getItemStars() + "*");
        */
        // Load Image from internet and set it into ImageView with Glide



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
         ImageView img_thumbnail;

        public ViewHolder(View itemView) {

            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.itemName);
            priceTextView = (TextView) itemView.findViewById(R.id.itemPrice);
            starsTextView = (TextView) itemView.findViewById(R.id.itemStars);
            img_thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
        }
    }
}