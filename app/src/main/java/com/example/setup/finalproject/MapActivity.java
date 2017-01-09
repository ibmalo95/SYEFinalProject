package com.example.setup.finalproject;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private HashMap<String, ArrayList<String>> collegeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        collegeData = (HashMap<String, ArrayList<String>>) getIntent().getSerializableExtra("DATAMAP");
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

        // TODO: access data using SQLite
        ArrayList<LatLng> places = new ArrayList();
        for (String key: collegeData.keySet()) {
            double lat = 0.0;
            double lon = 0.0;
            ArrayList<String> data = collegeData.get(key);
            if (key.equals("HOME")) {
                lat = Double.parseDouble(data.get(0));
                lon = Double.parseDouble(data.get(1));
            }
            else {
                lat = Double.parseDouble(data.get(1));
                lon = Double.parseDouble(data.get(2));
            }
            LatLng place = new LatLng(lat, lon);
            places.add(place);
            mMap.addMarker(new MarkerOptions().position(place).title(key));
        }

        if (places.size() > 0) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            for (LatLng place: places) {
                builder.include(place);
            }

            LatLngBounds bounds = builder.build();
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 200));
        }
        else {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.0902, 95.7129), 100));
        }

    }
}
