package com.example.setup.finalproject;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
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
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

// TODO: Requires change from hashmap to SQLite
/*
 * displays map and markers
 */
public class MapActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String ID = "MAP";
    private static final String LOG_TAG = MapActivity.class.getName();

    private GoogleMap mMap;
    private HashMap<String,String[]> coordinates;

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
            for (String key: coordinates.keySet()) {
                double lat = 0.0;
                double lon = 0.0;
                String[] data = coordinates.get(key);

                lat = Double.parseDouble(data[0]);
                lon = Double.parseDouble(data[1]);

                LatLng place = new LatLng(lat, lon);
                places.add(place);
                if (key.equals("HOME")) {

                    mMap.addMarker(new MarkerOptions().position(place).title(key)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                }
                else {
                    mMap.addMarker(new MarkerOptions().position(place).title(key));
                }

            }
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