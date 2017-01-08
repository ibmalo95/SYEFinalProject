package com.example.setup.finalproject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity {

    public static final int ADD_REQUEST = 1;
    public static final int HOME_REQUEST = 2;
    public static final String LOG_TAG = MainActivity.class.getName();
    public static final String ID = "HOME";
    public static final String API_KEY = "AIzaSyC5oGSPPmvHwrZO1CsAnxyd_pVIky8F2pE";

    Button add = null;
    Button map = null;
    Button home = null;
    String college = null;
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
                Intent intent = new Intent(MainActivity.this, MapActivity.class);
                intent.putExtra("DATAMAP", mainFragment.collegeData);
                startActivity(intent);
            }
        });
    }

    // Result from AddActivity
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == ADD_REQUEST && resultCode == RESULT_OK) {
            college = result.getStringExtra(AddActivity.ADD);
            mainFragment.addCollege(college);
            //Log.i(LOG_TAG, college);
        }
        if (requestCode == HOME_REQUEST && resultCode == RESULT_OK) {
            String address = result.getStringExtra(HomeActivity.HOME);
            // TODO: Geocoding (get the lat and lon from address)

            Uri.Builder builder = new Uri.Builder();
            builder.scheme("https").authority("maps.googleapis.com").
                    appendPath("maps").
                    appendPath("api").
                    appendPath("geocode").
                    appendPath("json").
                    appendQueryParameter("address", address).
                    appendQueryParameter("api_key", API_KEY);

            String url = builder.build().toString();
            GetUniversityDataTask getUniversityDataTask = new GetUniversityDataTask(mainFragment, ID);
            getUniversityDataTask.execute();
        }
    }

}
