package com.example.vadym.bootcamplocator.model;

/**
 * Created by Vadym on 28.11.2017.
 */

public class MyAddress {

    private float longtitude;
    private float latitude;
    private String locationTitle;
    private String locationAddress;
    private String locationImageUrl;

    public MyAddress(float latitude, float longtitude, String locationTitle, String locationAddress, String locationImageUrl) {
        this.longtitude = longtitude;
        this.latitude = latitude;
        this.locationTitle = locationTitle;
        this.locationAddress = locationAddress;
        this.locationImageUrl = locationImageUrl;
    }

    public float getLongtitude() {
        return longtitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public String getLocationTitle() {
        return locationTitle;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public String getLocationImageUrl() {
        return locationImageUrl;
    }
}
