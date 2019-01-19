package com.ahf.antwerphasfallen.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ahf.antwerphasfallen.Helpers.GameDataService;
import com.ahf.antwerphasfallen.InGameActivity;
import com.ahf.antwerphasfallen.R;
import com.ahf.antwerphasfallen.Helpers.RetrofitInstance;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;


/*
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment {

    private LatLng currentLocation;
    private LatLng targetLocation;
    private String targetLocationTitle;
    private int targetLocationTime;
    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private AlertDialog.Builder builder;
    InGameActivity listener;
    MapView mMapView;
    private GoogleMap googleMap;
    private boolean newLocation;

    private int x = 60;

    public static final GameDataService service = RetrofitInstance.getRetrofitInstance().create(GameDataService.class);

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof InGameActivity){
            this.listener = (InGameActivity) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_map, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            targetLocationTime = bundle.getInt("locationTime");
            targetLocation = new LatLng(bundle.getDouble("lat"), bundle.getDouble("lon"));
            targetLocationTitle = bundle.getString("locationTitle");
            newLocation = bundle.getBoolean("newLocation");
            //getTargetLocation(locationId);
        }

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(3000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(listener);

        builder = new AlertDialog.Builder(listener);
        builder.setTitle("Alert")
                .setMessage("You have arrived at your location")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        listener.ShowPuzzles(true);
                    }
                });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                listener.ShowPuzzles(true);
            }
        });

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Log.e("locationcallback", "locationcallback is called and location = " + locationResult);
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                   // x -= 5;
                    currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    calculateDistance(currentLocation, targetLocation);
                }
            };
        };

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                try {
                    mFusedLocationClient.getLastLocation()
                            .addOnSuccessListener(listener, new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    if (location != null) {
                                        if(newLocation){
                                            startLocationUpdates();
                                        }
                                        currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                                        googleMap.addMarker(new MarkerOptions().position(currentLocation).title("Previous location"));
                                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));
                                        googleMap.addMarker(new MarkerOptions().position(targetLocation).title(targetLocationTitle));
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
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
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

    private void calculateDistance(LatLng currentLoc, LatLng targetLoc){
        double r = 6371;

        double dLat = Math.toRadians(targetLoc.latitude - currentLoc.latitude);
        double dLon = Math.toRadians(targetLoc.longitude - currentLoc.longitude);

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(Math.toRadians(currentLoc.latitude)) * Math.cos(Math.toRadians(targetLoc.latitude)) * Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double d = r * c;
        d = Math.round(d*1000);
        Log.e("distance", d + "m");
        //txt_distance.setText(d + "m");
        d = 40;
        if(d <= 50){
            builder.show();
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }

    /*private void getTargetLocation(int id){
        Call<com.ahf.antwerphasfallen.Model.Location> call = service.getLocation(id);
        call.enqueue(new Callback<com.ahf.antwerphasfallen.Model.Location>() {
            @Override
            public void onResponse(Call<com.ahf.antwerphasfallen.Model.Location> call, Response<com.ahf.antwerphasfallen.Model.Location> response) {
                targetLocation = new LatLng(response.body().getLat(), response.body().getLon());
                targetLocationTitle = response.body().getName();
                targetLocationTime = response.body().getTime();
            }

            @Override
            public void onFailure(Call<com.ahf.antwerphasfallen.Model.Location> call, Throwable t) {

            }
        });
    }*/
}
