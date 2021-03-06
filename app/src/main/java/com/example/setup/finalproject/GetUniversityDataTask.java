package com.example.setup.finalproject;


import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;


/**
 * Created by Ina on 11/15/16.
 * Access either USED College Scorecard or Geocoding API in order to retrieve data
 * College Scorecard: https://collegescorecard.ed.gov/data/documentation/
 * Geocoding: https://developers.google.com/maps/documentation/geocoding/intro
 */

public class GetUniversityDataTask extends AsyncTask<String, Void, String []>{

    private static final String LOG_TAG = GetUniversityDataTask.class.getName();
    private MainActivity ctx;
    private AddActivity act;
    private String id;
    private String[] entries;
    private JSONArray results;

    // Constructor for accessing home lat and long
    public GetUniversityDataTask(MainActivity ctx, String id) {
        this.ctx = ctx;
        this.id = id;
    }

    // Constructor for searching the USED colleges/universities database
    public GetUniversityDataTask(AddActivity act, String id) {
        this.act = act;
        this.id = id;
    }

    @Override
    protected String[] doInBackground(String... url) {

        HttpsURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {
            // get the URL and make a connection
            URL uri = new URL(url[0]);
            urlConnection = (HttpsURLConnection) uri.openConnection();
            urlConnection.connect();

            // read the input stream
            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            // StringBuilder to represent the data
            String line = null;
            StringBuilder build = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                build.append(line);
            }
            // Returned JSON object
            String UniversityJSON = build.toString();

            // Get a list comprised of the university name, url, coordinates, and address
            if (id.equals("COLLEGE")) {
                getRecordArrayFromJSON(UniversityJSON);
            }

            // Google Geocoding api: get the lat and lng from address
            if (id.equals("HOME")) {
                ArrayList<String> homeLatLon = getGeometryArrayFromJSON(UniversityJSON);
                ctx.addHome(homeLatLon);
            }

        } catch (IOException e) {
            e.printStackTrace();
            Log.wtf(LOG_TAG, e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.wtf(LOG_TAG, e.toString());
        }

        return new String[0];
    }

    @Override
    protected void onPostExecute(String[] strings) {
        super.onPostExecute(strings);
        // set the spinner object in the AddActivity
        if (id.equals("COLLEGE")) {
            act.setEntries(entries, results);
        }
    }

    // parse the JSON returned from the US Department of Education College Scorecard
    protected void getRecordArrayFromJSON(String JSON) throws JSONException {
        JSONObject universityInfo = null;
        // Full JSON
        universityInfo = new JSONObject(JSON);
        // Results object
        results = universityInfo.getJSONArray("results");

        entries = new String[results.length()];

        for (int i = 0; i < results.length(); i++) {
            JSONObject data = results.getJSONObject(i);
            String name = data.getString("school.name"); // name
            String state = data.getString("school.state"); // state
            String spinner_entry = name + "-" + state;
            entries[i] = spinner_entry;
        }
    }


    // parse the JSON returned from Google Geocoding api
    protected ArrayList<String> getGeometryArrayFromJSON(String JSON) throws JSONException {
        JSONObject homeInfo = null;
        homeInfo = new JSONObject(JSON);
        JSONArray results = homeInfo.getJSONArray("results");
        JSONObject object = results.getJSONObject(0);
        JSONObject geometry = object.getJSONObject("geometry");
        JSONObject location = geometry.getJSONObject("location");

        ArrayList<String> latlon = new ArrayList();
        latlon.add(location.getString("lat"));
        latlon.add(location.getString("lng"));

        return latlon;
    }
}
