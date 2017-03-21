package com.example.setup.finalproject;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ina on 2/23/17.
 */

public class AccessDB extends AsyncTask<Void, Void, String> {
    private String id;
    private MainFragment fragment;
    private MainActivity act;
    private ArrayList<String> data;
    private ArrayList<String[]> contains;
    private ArrayList<String> college_data;
    private HashMap<String, String[]> coordinates;


    public AccessDB(String id, ArrayList<String> college_data, MainFragment fragment) {
        this.fragment = fragment;
        this.id = id;
        this.college_data = college_data;
    }

    public AccessDB(String id, MainActivity act, MainFragment fragment) {
        this.act = act;
        this.fragment = fragment;
        this.id = id;
    }

    @Override
    protected String doInBackground(Void... params) {

        if (id.equals("NAMES")) {
            data = DBQueries.getNames(fragment.db);
        }
        else if (id.equals("LIST")) {
            data = DBQueries.getRow(fragment.db, college_data.get(0)); // returns the colleges info
        }
        else if (id.equals("CONTAINS")) {
            contains = DBQueries.contains(fragment.db, college_data.get(0));
        }
        else {
            coordinates = DBQueries.getLocations(fragment.db); // returns the locations
        }
        return null;
    }

    @Override
    protected void onPostExecute(String string) {
        super.onPostExecute(string);

        if (id.equals("NAMES")) {
            fragment.populateColleges(data);
        }
        else if (id.equals("LIST")) {
            fragment.startList(data);
        }
        else if (id.equals("CONTAINS")) {
            fragment.contains(contains, college_data);
        }
        else {
            act.startMap(coordinates);
        }
    }
}
