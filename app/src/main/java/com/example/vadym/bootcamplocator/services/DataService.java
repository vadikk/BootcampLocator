package com.example.vadym.bootcamplocator.services;

import com.example.vadym.bootcamplocator.model.MyAddress;

import java.util.ArrayList;

/**
 * Created by Vadym on 28.11.2017.
 */

public class DataService {
    private static final DataService instance = new DataService();

    public static DataService getInstance() {
        return instance;
    }

    private DataService() {

    }

    public ArrayList<MyAddress> getBootcampLocationWithin10MilesOfZip(int zipcode){
        //pretending we are downloading data from server

        ArrayList<MyAddress> list = new ArrayList<>();

        list.add(new MyAddress(50.256635f,28.675129f,"BusStation",
                "Khlibna St, 14, Zhytomyr, Zhytomyrs'ka oblast, Ukraine ,10000","slo"));
        list.add(new MyAddress(50.25711f,28.673803f,"HouseDiligence",
                "Khlibna St, 19, Zhytomyr, Zhytomyrs'ka oblast, Ukraine ,10002","slo"));
        list.add(new MyAddress(50.253371f,28.677726f,"CosmosMuseum",
                "Dmytrivska St, 2, Zhytomyr, Zhytomyrs'ka oblast, Ukraine ,10000","slo"));


        return list;
    }
}
