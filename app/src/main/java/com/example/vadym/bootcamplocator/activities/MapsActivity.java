package com.example.vadym.bootcamplocator.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.vadym.bootcamplocator.R;
import com.example.vadym.bootcamplocator.fragments.MainFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;


public class MapsActivity extends FragmentActivity implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks, LocationListener {

    final int PERMESSION_LOCATION = 111;

    private GoogleApiClient mGoogleApiClient;

    private FusedLocationProviderClient locationProviderClient;

    // TODO: 02.12.17 Видаляй, якщо не потрібне [1]
    private LocationCallback mLocationCallback = new LocationCallback();
    // TODO: 02.12.17 Видаляй, якщо не потрібне [2]
    private LocationRequest mLocationRequest;
    private MainFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();


        FragmentManager manager = getSupportFragmentManager();
        fragment = (MainFragment) manager.findFragmentById(R.id.container_main);

        if(fragment==null){
            fragment = MainFragment.newInstance();
            // TODO: 02.12.17 Тут краще юзати replace.
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.container_main,fragment).commit();
        }



    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMESSION_LOCATION);
            Log.v("Map","Requesting permission");
        }else{
            Log.v("Map","Starting Location Services from onConnected");
            startLocationServices();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        //Log.v("Map","Long: " + location.getLongitude() + " - Lat: " + location.getLatitude() + " 1 ");
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case PERMESSION_LOCATION:{
                /*
                  Тут ти красава просто, тому що я раз зловив на проді помилку,
                  бо не перевіряв кількість елементів в цьому масиві.
                 */
                if(grantResults.length>0 && grantResults[0]==PERMESSION_LOCATION){
                    startLocationServices();
                    Log.v("Map", "Permission granted - starting services");
                }else {
                    //Show message like this: "Denied permission";
                    Log.v("Map", "Permission not granted");
                }
            }
        }
    }

    public void startLocationServices(){
        Log.v("Map","Starting Location Services Called");

        try{
            LocationRequest req = LocationRequest.create().setPriority(LocationRequest.PRIORITY_LOW_POWER);
            locationProviderClient.requestLocationUpdates(req,new LocationCallback(){
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    fragment.setUserMarker(new LatLng(locationResult.getLastLocation().getLatitude(),locationResult.getLastLocation().getLongitude()));
                    printLastLocation(locationResult.getLastLocation());
                }
            } ,null);
//            locationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
//                @Override
//                public void onSuccess(Location location) {
//
//                   printLastLocation(location);
//                }
//            });
            //LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,req,this);
            Log.v("Map","Requesting location update");
        } catch (SecurityException exception){
            Log.v("Map",exception.toString());
        }
    }

    private void printLastLocation(Location location){
        Log.v("Map","Location: " + location.getLatitude() + " " + location.getLongitude());

        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

    }
}
