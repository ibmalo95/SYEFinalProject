package com.example.setup.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import java.util.ArrayList;
import java.util.HashMap;

import android.os.Handler;

public class MainActivity extends Activity {

    public static final int ADD_REQUEST = 1;
    public static final int HOME_REQUEST = 2;
    public static final String LOG_TAG = MainActivity.class.getName();
    public static final String ID = "HOME";
    public static final String API_KEY = "AIzaSyC5oGSPPmvHwrZO1CsAnxyd_pVIky8F2pE";

    Button add = null;
    Button map = null;
    Button home = null;

    ArrayList<String> locations = new ArrayList(); // TODO: Save
    private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainFragment = (MainFragment) getFragmentManager().findFragmentById(R.id.main_fragment);

        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, ADD_REQUEST);
            }
        });

        home = (Button) findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivityForResult(intent, HOME_REQUEST);
            }
        });

        map  = (Button) findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SQLite
                AccessDB accessDB = new AccessDB(ID, null, MainActivity.this, mainFragment);
                accessDB.execute();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        String lat = getPreferences(MODE_PRIVATE).getString("lat", null);
        String lon = getPreferences(MODE_PRIVATE).getString("lon", null);
        if (lat != null && lon != null) {
            locations.add(lat);
            locations.add(lon);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!locations.isEmpty()) {
            getPreferences(MODE_PRIVATE).edit().putString("lat", locations.get(0)).apply();
            getPreferences(MODE_PRIVATE).edit().putString("lon", locations.get(1)).apply();
        }
    }

    protected void startMap(HashMap<String, String[]> coordinates) {
        if (locations.size() > 0) {
            String[] home = {locations.get(0), locations.get(1)};
            coordinates.put("HOME", home);
        }
        Intent intent = new Intent(MainActivity.this, MapActivity.class);
        intent.putExtra("DATA", coordinates);
        startActivity(intent);
    }

    protected void addHome(ArrayList<String> homeLatLon) {
        locations = homeLatLon;
    }

    // Result from AddActivity or HomeActivity
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {

        // store the data retrieved from the add activity
        if (requestCode == ADD_REQUEST && resultCode == RESULT_OK) {
            ArrayList<String> college_data = (ArrayList<String>) result.getSerializableExtra(AddActivity.ADD);
            mainFragment.addCollege(college_data);

        }
        if (requestCode == HOME_REQUEST && resultCode == RESULT_OK) {
            String address = result.getStringExtra(HomeActivity.HOME);

            Uri.Builder builder = new Uri.Builder();
            builder.scheme("https").authority("maps.googleapis.com").
                    appendPath("maps").
                    appendPath("api").
                    appendPath("geocode").
                    appendPath("json").
                    appendQueryParameter("address", address).
                    appendQueryParameter("api_key", API_KEY);

            String url = builder.build().toString();
            GetUniversityDataTask getUniversityDataTask = new GetUniversityDataTask(MainActivity.this, ID);
            getUniversityDataTask.execute(url);
        }
    }
}