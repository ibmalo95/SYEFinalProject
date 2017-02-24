package com.example.setup.finalproject;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ina on 2/23/17.
 */

public class AccessDB extends AsyncTask<Void, Void, String> {
    private String id;
    private String row;
    private MainFragment fragment;
    private MainActivity act;
    private ArrayList<String> data;
    private HashMap<String, String[]> coordinates;


    public AccessDB(String id, String row, MainFragment fragment) {
        this.fragment = fragment;
        this.id = id;
        this.row = row;
    }

    public AccessDB(String id, String row, MainActivity act, MainFragment fragment) {
        this.act = act;
        this.fragment = fragment;
        this.id = id;
        this.row = row;
    }

    @Override
    protected String doInBackground(Void... params) {

        // TODO: Probably want to check that db isn't null
        if (id.equals("LIST")) {
            data = DBQueries.getRow(fragment.db, row); // returns the colleges info
        }
        else {
            coordinates = DBQueries.getLocations(fragment.db); // returns the locations
        }
        return null;
    }

    @Override
    protected void onPostExecute(String string) {
        super.onPostExecute(string);

        if (id.equals("LIST")) {
            fragment.startList(data);
        }
        else {
            act.startMap(coordinates);
        }
    }
}
