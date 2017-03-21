package com.example.setup.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/*
 * User can add home address
 */
public class HomeActivity extends Activity {


    public static final String HOME = "com.example.setup.HomeActivity.HOME";
    String home_address = null;
    EditText city = null;
    Spinner state = null;
    Button set = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        city = (EditText) findViewById(R.id.city);
        state = (Spinner) findViewById(R.id.state);
        set = (Button) findViewById(R.id.set_home);

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (city != null && state != null) {

                    String city_value = city.getText().toString();
                    city_value.replace(' ', '+');

                    String address = city_value + ",+" + state.getSelectedItem().toString();
                    Intent result = new Intent();
                    result.putExtra(HOME, address);
                    setResult(RESULT_OK, result);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        String saved_city = getPreferences(MODE_PRIVATE).getString("city", null);
        String saved_state = getPreferences(MODE_PRIVATE).getString("state", null);
        if (saved_city != null && saved_state != null) {
            city.setText(saved_city);
            ArrayAdapter<String> adapter = (ArrayAdapter) state.getAdapter();
            int spinnerPosition = adapter.getPosition(saved_state);
            state.setSelection(spinnerPosition);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        String city_name = city.getText().toString();
        String state_name = state.getSelectedItem().toString();
        getPreferences(MODE_PRIVATE).edit().putString("city", city_name).apply();
        getPreferences(MODE_PRIVATE).edit().putString("state", state_name).apply();

    }
}
