package com.example.setup.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/*
 * User can search and add colleges to their list
 */
public class AddActivity extends Activity {

    public static final String ADD = "com.example.setup.AddActivity.ADD";
    public static final String RESOURCE_KEY = "38625c3d-5388-4c16-a30f-d105432553a4";
    public static final String FIELDS = "INSTNM,WEBADDR,LATITUDE,LONGITUD,ADDR,CITY,STABBR";
    public static final String ID = "COLLEGE";

    EditText college_name = null;
    Button search_college = null;
    Button add_college = null;
    Spinner spinner = null;
    JSONArray list = null;
    AddActivity add = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        spinner = (Spinner) findViewById(R.id.spinner);

        search_college = (Button) findViewById(R.id.search_college);
        search_college.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                college_name = (EditText) findViewById(R.id.college_name);
                String name = college_name.getText().toString();
                if (!name.isEmpty()) {


                    Uri.Builder builder = new Uri.Builder();
                    builder.scheme("https").authority("inventory.data.gov").
                            appendPath("api").
                            appendPath("action").
                            appendPath("datastore_search").
                            appendQueryParameter("resource_id", RESOURCE_KEY).
                            appendQueryParameter("fields", FIELDS).
                            appendQueryParameter("q", "{\"INSTNM\":\"" + name + "\"}");

                    String url = builder.build().toString();

                    GetUniversityDataTask getUniversityDataTask = new GetUniversityDataTask(add, ID);
                    getUniversityDataTask.execute(url);
                }
                else {
                    // TODO: Report to user that they need to enter a college
                }
            }
        });

        add_college = (Button) findViewById(R.id.add_college);
        add_college.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the college that the user selected, store the corresponding data and return to mainactivity

                int index = spinner.getSelectedItemPosition();
                try {
                    JSONObject data = list.getJSONObject(index);

                    // Store the data
                    String key = data.getString("INSTNM"); // name
                    ArrayList<String> dataInfo = new ArrayList();
                    dataInfo.add(key);
                    dataInfo.add(data.getString("WEBADDR")); // url
                    dataInfo.add(data.getString("LATITUDE")); // lat
                    dataInfo.add(data.getString("LONGITUD")); // lng
                    String address = data.getString("ADDR") + ", " + data.getString("CITY") + ", " + data.getString("STABBR");
                    dataInfo.add(address); // address

                    // return college name
                    Intent result = new Intent();
                    result.putExtra(ADD, dataInfo);
                    setResult(RESULT_OK, result);
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected void setEntries(String[] entries, JSONArray array) {
        if (array.length() == 0) {
            // Manually enter data
        }
        else {
            list = array;
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, entries);
            spinner.setAdapter(adapter);
            spinner.setVisibility(View.VISIBLE);
            add_college.setVisibility(View.VISIBLE);
        }
        add_college.setVisibility(View.VISIBLE);

    }
}
