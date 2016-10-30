package com.example.setup.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    public static final int ADD_REQUEST = 1;
    public static final String LOG_TAG = MainActivity.class.getName();

    Button add = null;
    String college = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, ADD_REQUEST);
            }
        });
    }

    // Result from NoteActivity gets processed here
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == ADD_REQUEST && resultCode == RESULT_OK) {
            college = result.getStringExtra(AddActivity.ADD);
            Log.i(LOG_TAG, college);
        }
    }

    // TODO: add college to the ListView
    // Need a reference to the fragment
    protected void addCollege() {

    }
}
