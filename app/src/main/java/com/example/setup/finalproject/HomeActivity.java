package com.example.setup.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class HomeActivity extends Activity {

    public static final String HOME = "com.example.setup.HomeActivity.HOME";
    String home_address = null;
    EditText street = null;
    EditText city = null;
    Spinner state = null;
    Button set = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        street = (EditText) findViewById(R.id.street);
        city = (EditText) findViewById(R.id.city);
        state = (Spinner) findViewById(R.id.state);
        set = (Button) findViewById(R.id.set_home);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.states, android.R.layout.simple_spinner_dropdown_item);
        state.setAdapter(adapter);

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (street != null && city != null && state != null) {
                    String address = street.getText().toString() + city.getText().toString() + state.
                    Intent result = new Intent();
                    result.putExtra(HOME, address);
                    setResult(RESULT_OK, result);
                    finish();
                }
            }
        });
    }
}
