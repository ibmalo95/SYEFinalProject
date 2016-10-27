package com.example.setup.finalproject;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class AddActivity extends Activity {

    EditText college_name = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // Get the college that the user put in and send it back to MainActivity
        college_name = (EditText) findViewById(R.id.college_name);
        String result = college_name.getText().toString();

    }
}
