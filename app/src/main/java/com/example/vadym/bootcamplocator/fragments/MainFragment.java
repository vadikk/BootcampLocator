package com.example.vadym.bootcamplocator.fragments;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.vadym.bootcamplocator.R;
import com.example.vadym.bootcamplocator.model.MyAddress;
import com.example.vadym.bootcamplocator.services.DataService;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MarkerOptions userMarker;
    private LocationsListFragment locationsListFragment;

    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance() {
        // TODO: 02.12.17 Заюзай тут однолінійне представлення, тобі ж студія підсвічує його.
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // TODO: 02.12.17 Це взагалі збочення. Не варто робити прив'язки до активіті + ще й робити такі махінації.
        // TODO: 02.12.17 Подумай, як краще це організувати.
        locationsListFragment = (LocationsListFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.container_location_list);

        if(locationsListFragment==null){
            locationsListFragment = LocationsListFragment.newInstance();
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container_location_list,locationsListFragment)
                    .commit();
        }

        final EditText zipText = (EditText)view.findViewById(R.id.zip_text);
        // TODO: 02.12.17 Організуй цей код в середині фрагменту, а не тут.
        zipText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {

                if((keyEvent.getAction()==KeyEvent.ACTION_DOWN) && keyCode==KeyEvent.KEYCODE_ENTER){

                    //You should make sure this is a valid zipcode - check total count and character
                    String text = zipText.getText().toString();
                    int zip = Integer.parseInt(text);

                    // TODO: 02.12.17 Винеси в окремий клас, бажано. Або в метод.

                    InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(zipText.getWindowToken(),0);

                    showList();
                    updateMapForZip(zip);
                    return true;
                }

                return false;
            }
        });

        hideList();
        return view;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public void setUserMarker(LatLng latLng){
        if(userMarker==null){
            userMarker = new MarkerOptions().position(latLng).title("Current location");
            mMap.addMarker(userMarker);
            Log.v("Map","Current location " + latLng.latitude + " Long: " + latLng.longitude);
        }
        int zip = 0;
        //update our location
        // TODO: 02.12.17 Невірна логіка тут. В тебе перетирає код на строчці 162 всю роботу, яка тут виконується.
        try{
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
//            Log.v("Map",String.valueOf(addresses.size()));
//
//            for (Address addres: addresses) {
//                Log.v("Map",String.valueOf(addres));
//            }

            if(zip!=0)
                zip = Integer.parseInt(addresses.get(0).getPostalCode());

            updateMapForZip(zip);
            
        }catch (IOException exception){
            Log.v("Map",exception.toString());
        }


        updateMapForZip(00000);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
    }

    public void updateMapForZip(int zipcode){
        // TODO: 02.12.17 Яка тут різниця ,який zip прийде, якщо все одно один і той же результат?
        ArrayList<MyAddress> list = DataService.getInstance().getBootcampLocationWithin10MilesOfZip(zipcode);

        for(int i=0;i<list.size();i++){
            MyAddress address = list.get(i);
            MarkerOptions marker = new MarkerOptions().position(new LatLng(address.getLatitude(),address.getLongtitude()));
            marker.title(address.getLocationTitle());
            marker.snippet(address.getLocationAddress());
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin));
            mMap.addMarker(marker);
        }
    }

    private void hideList(){
        getActivity().getSupportFragmentManager().beginTransaction().hide(locationsListFragment);
    }

    private void showList(){
        getActivity().getSupportFragmentManager().beginTransaction().show(locationsListFragment);
    }

}
