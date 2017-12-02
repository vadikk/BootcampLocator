package com.example.vadym.bootcamplocator.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vadym.bootcamplocator.R;
import com.example.vadym.bootcamplocator.model.MyAddress;

/**
 * Created by Vadym on 02.12.2017.
 */

public class LocationViewHolder extends RecyclerView.ViewHolder {

    private TextView locationTitle;
    private TextView locationAddress;
    private ImageView locationImage;

    public LocationViewHolder(View itemView) {
        super(itemView);

        locationTitle = (TextView) itemView.findViewById(R.id.location_title);
        locationAddress = (TextView) itemView.findViewById(R.id.location_address);
        locationImage = (ImageView) itemView.findViewById(R.id.location_img);
    }

    public void updateUI(MyAddress location){

        String url = location.getImgUrl();
        int resource = locationImage.getResources().getIdentifier(url,null,locationImage.getContext().getPackageName());
        locationImage.setImageResource(resource);
        locationTitle.setText(location.getLocationTitle());
        locationAddress.setText(location.getLocationAddress());
    }
}
