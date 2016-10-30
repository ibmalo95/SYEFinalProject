package com.example.setup.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends Activity {

    public static final String ADD = "com.example.setup.AddActivity.ADD";
    EditText college_name = null;
    Button add_college = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        add_college = (Button) findViewById(R.id.add_college);
        add_college.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the college that the user put in and send it back to MainActivity
                college_name = (EditText) findViewById(R.id.college_name);
                String college = college_name.getText().toString();
                if (!college.isEmpty()) {
                    Intent result = new Intent();
                    result.putExtra(ADD, college);
                    setResult(RESULT_OK, result);
                    finish();
                }
            }
        });




    }
}
