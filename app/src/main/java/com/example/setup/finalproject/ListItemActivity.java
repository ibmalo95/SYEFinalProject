package com.example.setup.finalproject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ListItemActivity extends Activity {

    public static final String LOG_TAG = ListItemActivity.class.getName();

    TextView collegeName = null;
    String url = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);

        collegeName = (TextView) findViewById(R.id.name);
        String[] info = getIntent().getStringArrayExtra("INFO");
        collegeName.setText(info[0]);
        url = info[1];
        Log.i(LOG_TAG, url);
    }
}
