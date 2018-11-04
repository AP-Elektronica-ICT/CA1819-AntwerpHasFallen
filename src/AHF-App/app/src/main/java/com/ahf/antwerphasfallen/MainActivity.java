package com.ahf.antwerphasfallen;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient mFusedLocationClient;
    private Button btnStart;
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1;
    private LocationCallback mLocationCallback;
    private LocationRequest mLocationRequest;
    private boolean allowLocation = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        btnStart = findViewById(R.id.btn_start);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLocationUpdates();
            }
        });


        //define the location request
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        //Check if the app has permission to use location.
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            //If the app doesn't have permission to use location ask if it can.
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_FINE_LOCATION);
        }
        else{
            allowLocation = true;
            //If the app already has permission get the location.
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                // Handle the location object
                                Log.e("locatie", location.toString());
                            }
                        }
                    });


        }

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
                    // ...
                    Log.e("location", location.toString());
                }
            };
        };
    }
    /*
    @Override
    protected void onResume(){
        super.onResume();
        startLocationUpdates();

    }
    */
    private void startLocationUpdates(){
        if(allowLocation){
            try {
                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
            }
            catch (SecurityException e)
            {
                Log.e("Security Exception", e.toString());
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    allowLocation= true;
                    /*

                        Code that needs to be executed if the permission is granted.

                    */
                    
                } else {
                    allowLocation = false;
                    /*

                        Code that needs to be executed if the permission is denied.

                    */
                }
                return;
            }

            /*

                Other permissions the app needs

            */
        }
    }
}
