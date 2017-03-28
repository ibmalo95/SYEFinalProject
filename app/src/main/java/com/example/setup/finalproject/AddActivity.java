package com.example.setup.finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/*
 * User can search and add colleges to their list
 */
public class AddActivity extends Activity {

    public static final String ADD = "com.example.setup.AddActivity.ADD";
    public static final String API_KEY = "zTvIDBIb2oguISKw9f0NSMLesMx3TLM4Q8m5MCAk";
    public static final String FIELDS = "id,school.name,school.city,school.state,location.lat,location.lon,school.school_url," +
            "2014.admissions.admission_rate.overall,2014.student.size,2014.cost.tuition.in_state,2014.cost.tuition.out_of_state," +
            "2014.completion.completion_rate_4yr_150nt,2014.student.retention_rate.four_year.full_time,2014.aid.median_debt.completers.overall";

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

                ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = cm.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
                    // Search for school using name given by user
                    college_name = (EditText) findViewById(R.id.college_name);
                    String name = college_name.getText().toString();
                    if (!name.isEmpty()) {

                        Uri.Builder builder = new Uri.Builder();
                        builder.scheme("https").authority("api.data.gov").
                                appendPath("ed").
                                appendPath("collegescorecard").
                                appendPath("v1").
                                appendPath("schools").
                                appendPath("json").
                                appendQueryParameter("school.name", name).
                                appendQueryParameter("fields", FIELDS).
                                appendQueryParameter("api_key", API_KEY);

                        String url = builder.build().toString();

                        GetUniversityDataTask getUniversityDataTask = new GetUniversityDataTask(add, ID);
                        getUniversityDataTask.execute(url);
                    }
                    else {
                        Toast.makeText(add, "Enter a school", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(add, "No network connection", Toast.LENGTH_SHORT).show();
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
                    String key = data.getString("school.name"); // name
                    ArrayList<String> dataInfo = new ArrayList();
                    dataInfo.add(key);
                    dataInfo.add(data.getString("school.school_url")); // url
                    dataInfo.add(data.getString("location.lat")); // lat
                    dataInfo.add(data.getString("location.lon")); // lng
                    String address = data.getString("school.city") + ", " + data.getString("school.state");
                    dataInfo.add(address); // address
                    dataInfo.add(data.getString("2014.admissions.admission_rate.overall")); // admission rate
                    dataInfo.add(data.getString("2014.student.size")); // number of undergraduates enrolled
                    dataInfo.add(data.getString("2014.cost.tuition.in_state")); // instate tuition
                    dataInfo.add(data.getString("2014.cost.tuition.out_of_state")); // out of state tuition
                    dataInfo.add(data.getString("2014.completion.completion_rate_4yr_150nt")); // completion rate
                    dataInfo.add(data.getString("2014.student.retention_rate.four_year.full_time")); // retention rate
                    dataInfo.add(data.getString("2014.aid.median_debt.completers.overall")); // median debt upon completion

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
        if (array == null) {
            Toast.makeText(add, "No network connection", Toast.LENGTH_SHORT).show();
        }
        else if (array.length() == 0) {
            // TODO: Manually enter data maybe?
            Toast.makeText(add, "No college found.", Toast.LENGTH_SHORT).show();
        }
        else {
            // set the contents of the spinner to colleges found through search
            list = array;
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, entries);
            spinner.setAdapter(adapter);
            spinner.setVisibility(View.VISIBLE);
            add_college.setVisibility(View.VISIBLE);
        }
    }
}
