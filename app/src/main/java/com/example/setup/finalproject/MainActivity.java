package com.example.setup.finalproject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends Activity {

    public static final int ADD_REQUEST = 1;
    public static final String LOG_TAG = MainActivity.class.getName();

    Button add = null;
    Button map = null;
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

        // TODO: Add activity for the map view
        map  = (Button) findViewById(R.id.map);
    }

    // Result from AddActivity
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == ADD_REQUEST && resultCode == RESULT_OK) {
            college = result.getStringExtra(AddActivity.ADD);
            mainFragment.addCollege(college);
            Log.i(LOG_TAG, college);
        }
    }

}
