package com.example.setup.finalproject;


import android.content.Context;
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
 */

public class GetUniversityDataTask extends AsyncTask<String, Void, String []>{

    private static final String LOG_TAG = GetUniversityDataTask.class.getName();
    private MainFragment ctx;
    private String id;

    public GetUniversityDataTask(MainFragment ctx, String id) {
        this.ctx = ctx;
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
                ArrayList<String> universityData = getRecordArrayFromJSON(UniversityJSON);
                ctx.setCollegeData(universityData);
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


    // parse the JSON returned from the US Department of Education
    protected ArrayList<String> getRecordArrayFromJSON(String JSON) throws JSONException {
        JSONObject universityInfo = null;
        // Full JSON
        universityInfo = new JSONObject(JSON);
        // Results object
        JSONObject results = universityInfo.getJSONObject("result");
        // Records array: has name, university url, and location data
        JSONArray records = results.getJSONArray("records");

        ArrayList<String> universityList = new ArrayList();
        JSONObject data = records.getJSONObject(0);
        universityList.add(data.getString("INSTNM")); // name
        universityList.add(data.getString("WEBADDR")); // url
        universityList.add(data.getString("LATITUDE")); // lat
        universityList.add(data.getString("LONGITUD")); // lng
        universityList.add(data.getString("ADDR")); // street
        universityList.add(data.getString("CITY")); // city
        universityList.add(data.getString("STABBR")); // state

        return universityList;
    }

    // parse the JSON returned from Google Geocoding api
    protected ArrayList<String> getGeometryArrayFromJSON(String JSON) throws JSONException {
        ArrayList<String> latlon = new ArrayList();
        JSONObject homeInfo = null;
        homeInfo = new JSONObject(JSON);
        JSONArray results = homeInfo.getJSONArray("results");
        JSONObject object = results.getJSONObject(0);
        JSONObject geometry = object.getJSONObject("geometry");
        JSONObject location = geometry.getJSONObject("location");

        latlon.add(location.getString("lat"));
        latlon.add(location.getString("lng"));

        return latlon;
    }

}
