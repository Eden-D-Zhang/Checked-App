/*
 * Adapter file for search result RecyclerView. Controls behaviour of the RecyclerView.
 */

package com.example.checkedapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

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

        //Retrieves information from the Item to be displayed
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
        Glide.with(mContext).load(item.getImage_url()).apply(option).into(holder.img_thumbnail);

        holder.itemView.setTag(item.getItemId());

        //Sets the colour of the Item in RecyclerView to white if unselected and green if selected.
        holder.itemView.setBackgroundResource(item.isSelected() ? R.color.green_light : R.color.white);

        //Sets click behaviour for Items in the RecyclerView. If within the ProductsActivity, the Item expands when clicked, displaying a button that redirects to the product's web page
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                item.setExpanded();

                notifyItemChanged(holder.getAdapterPosition());
            }
        });

        boolean expanded = item.getIsExpanded();
        holder.details.setVisibility(expanded ? View.VISIBLE : View.GONE);

        holder.linkButton.setOnClickListener(new View.OnClickListener() {

            //Starts the browser activity using the Item's link
            @Override
            public void onClick(View v) {
                String url = item.getItemLink();

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                mContext.startActivity(intent);
            }

        });

        //If within the SearchResultsActivity, sets click behaviour to select the Item for creating a new ItemListing.
        if (mIntent.getIntExtra("fragmentNumber",0)!=5) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    item.setSelected(!item.isSelected());
                    Log.d("MyTag", String.valueOf(holder.itemView.getTag()));
                    Log.d("Status:", String.valueOf(item.isSelected()));
                    holder.itemView.setBackgroundResource(item.isSelected() ? R.color.green_light : R.color.white);
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

        //Initializes views that display Item attributes within the RecyclerView.
        TextView nameTextView;
        TextView priceTextView;
        TextView starsTextView;
        TextView stockTextView;
        ImageView img_thumbnail;
        Button linkButton;
        LinearLayout details;

        public ViewHolder(View itemView) {

            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.itemName);
            priceTextView = (TextView) itemView.findViewById(R.id.itemPrice);
            starsTextView = (TextView) itemView.findViewById(R.id.itemStars);
            stockTextView = (TextView) itemView.findViewById(R.id.itemStock);
            img_thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            linkButton = (Button) itemView.findViewById(R.id.linkButton);
            details = (LinearLayout) itemView.findViewById(R.id.expandedItem);
        }
    }
}