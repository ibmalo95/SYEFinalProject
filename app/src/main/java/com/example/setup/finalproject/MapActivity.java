package com.example.setup.finalproject;


import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

/*
 * displays map and markers
 */
public class MapActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

    private static final String LIST = "LIST";
    private static final String LOG_TAG = MapActivity.class.getName();

    private GoogleMap mMap;
    private HashMap<String,String[]> coordinates;
    private boolean zoom = true;
    private LatLngBounds bounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        coordinates = (HashMap<String,String[]>) getIntent().getSerializableExtra("DATA");
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

        ArrayList<LatLng> places = new ArrayList();
        if (coordinates != null) {
            for (String key : coordinates.keySet()) {
                double lat = 0.0;
                double lon = 0.0;
                String[] data = coordinates.get(key);

                if (key.equals("HOME")) {
                    lat = Double.parseDouble(data[0]);
                    lon = Double.parseDouble(data[1]);

                    LatLng place = new LatLng(lat, lon);
                    places.add(place);
                    mMap.addMarker(new MarkerOptions().position(place).title(key)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                }
                else {
                    String name = data[0];
                    lat = Double.parseDouble(data[1]);
                    lon = Double.parseDouble(data[2]);
                    LatLng place = new LatLng(lat, lon);
                    places.add(place);
                    mMap.addMarker(new MarkerOptions().position(place).title(name));
                }
            }
        }

        if (places.size() > 0) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            if (places.size() == 1) {
                double lat = places.get(0).latitude;
                double lon = places.get(0).longitude;
                builder.include(new LatLng(lat + 0.01, lon + 0.01));
                builder.include(new LatLng(lat - 0.01, lon - 0.01));
            }
            else {
                for (LatLng place : places) {
                    builder.include(place);
                }
            }

            bounds = builder.build();
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 200));
        } else {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.0902, 95.7129), 100));
        }
        mMap.setOnMarkerClickListener(this);

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        // Zoom in on marker
        if (zoom) {
            marker.showInfoWindow();
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            double lat = marker.getPosition().latitude;
            double lon = marker.getPosition().longitude;
            builder.include(new LatLng(lat + 0.01, lon + 0.01));
            builder.include(new LatLng(lat - 0.01, lon - 0.01));
            LatLngBounds marker_bounds = builder.build();
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(marker_bounds, 50));
            zoom = false;
        }
        // Zoom out to show all markers
        else {
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 200));
            zoom = true;
        }
        return true;
    }
}