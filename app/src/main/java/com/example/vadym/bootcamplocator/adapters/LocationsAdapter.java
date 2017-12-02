package com.example.vadym.bootcamplocator.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vadym.bootcamplocator.R;
import com.example.vadym.bootcamplocator.holders.LocationViewHolder;
import com.example.vadym.bootcamplocator.model.MyAddress;

import java.util.ArrayList;

/**
 * Created by Vadym on 02.12.2017.
 */

public class LocationsAdapter extends RecyclerView.Adapter<LocationViewHolder> {

    private ArrayList<MyAddress> addresses;

    public LocationsAdapter(ArrayList<MyAddress> addresses) {
        this.addresses = addresses;
    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_location,parent,false);
        return new LocationViewHolder(card);
    }

    @Override
    public void onBindViewHolder(LocationViewHolder holder, int position) {
            final MyAddress location = addresses.get(position);
            holder.updateUI(location);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Load  details page
                }
            });
    }

    @Override
    public int getItemCount() {
        return addresses.size();
    }
}
