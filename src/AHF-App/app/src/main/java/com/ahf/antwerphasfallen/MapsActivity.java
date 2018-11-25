package com.ahf.antwerphasfallen;

import android.content.DialogInterface;
import android.location.Location;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private LatLng currentLocation;
    private LatLng targetLocation;
    private String targetLocationTitle;
    private AlertDialog.Builder builder;
    private TextView txt_distance;

    public static final GameDataService service = RetrofitInstance.getRetrofitInstance().create(GameDataService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        int locationId = 5;
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            locationId = extras.getInt("locationId");
            Log.e("locationId", "" + locationId);

            Call<com.ahf.antwerphasfallen.Location> call = service.getLocation(locationId);
            call.enqueue(new Callback<com.ahf.antwerphasfallen.Location>() {
                @Override
                public void onResponse(Call<com.ahf.antwerphasfallen.Location> call, Response<com.ahf.antwerphasfallen.Location> response) {
                    targetLocation = new LatLng(response.body().getLat(), response.body().getLon());
                    targetLocationTitle = response.body().getName();
                }

                @Override
                public void onFailure(Call<com.ahf.antwerphasfallen.Location> call, Throwable t) {

                }
            });
        }

        txt_distance = (TextView)findViewById(R.id.txt_distance);

        //define the location request
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        builder = new AlertDialog.Builder(MapsActivity.this);
        builder.setTitle("Alert")
                .setMessage("You have arrived at your location")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        /*

                            code voor naar het volgende scherm te gaan

                         */
                    }
                });

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    //calculateDistance(currentLocation, targetLocation);
                }
            };
        };



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void startLocationUpdates(){
        try {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, null);
        }
        catch (SecurityException e)
        {
            Log.e("Security Exception", e.toString());
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        try {
            mFusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                //demo
                                //targetLocation = new LatLng(51.229852, 4.423083);
                                //startLocationUpdates();
                                currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                mMap.addMarker(new MarkerOptions().position(currentLocation).title("current location"));
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
                                mMap.addMarker(new MarkerOptions().position(targetLocation).title(targetLocationTitle));
                                //demo
                                calculateDistance(currentLocation,targetLocation);
                            }
                        }
                    });
        }
        catch (SecurityException e)
        {
            Log.e("Security Exception", e.toString());
        }
    }

    private void calculateDistance(LatLng currentLoc, LatLng targetLoc){
        double r = 6371;

        double dLat = Math.toRadians(targetLoc.latitude - currentLoc.latitude);
        double dLon = Math.toRadians(targetLoc.longitude - currentLoc.longitude);

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(Math.toRadians(currentLoc.latitude)) * Math.cos(Math.toRadians(targetLoc.latitude)) * Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double d = r * c;
        d = Math.round(d*1000);
        Log.e("distance", d + "m");
        txt_distance.setText(d + "m");

        if(d <= 50){
            builder.show();
        }
    }
}
