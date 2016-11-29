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
 */

public class GetUniversityDataTask extends AsyncTask<String, Void, String []>{

    private static final String LOG_TAG = GetUniversityDataTask.class.getName();

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
            // JSON of institution with INSTNM, WEBADDR, LATITUDE, & LONGITUD
            String UniversityJSON = build.toString();
            //Log.i(LOG_TAG, UniversityJSON);
            // Get an list comprised of the university name, url, and coordinates
            ArrayList<String> UniversityList = getRecordArrayFromJSON(UniversityJSON);
            Log.i(LOG_TAG, UniversityList.toString());

        } catch (IOException e) {
            e.printStackTrace();
            Log.wtf(LOG_TAG, e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
            Log.wtf(LOG_TAG, e.toString());
        }


        return new String[0];
    }


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
        universityList.add(data.getString("INSTNM"));
        universityList.add(data.getString("WEBADDR"));
        universityList.add(data.getString("LATITUDE"));
        universityList.add(data.getString("LONGITUD"));

        return universityList;
    }

}
