package com.example.setup.finalproject;


import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * TODO: Requires change from hashmap to SQLite
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    public static final String LOG_TAG = MainFragment.class.getName();

    ListView list = null;
    ArrayAdapter<String> collegesAdapter;
    List<String> colleges; // TODO: Saving... go through SQLite table and store college names
    HashMap<String, ArrayList<String>> collegeData = new HashMap();

    // SQLite instance data
    private DBHelper dbHelper = null;
    protected SQLiteDatabase db = null;
    //private Handler handler = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        colleges = new ArrayList();
        collegesAdapter = new CustomAdapter(getContext(), R.layout.list_item, colleges, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        list = (ListView) root.findViewById(R.id.college_list);
        list.setAdapter(collegesAdapter);
        list.setClickable(true);
        return root;
    }

    // add college to the ListView
    protected void addCollege(ArrayList<String> college_data) {

        String key = college_data.get(0);
        colleges.add(key);

        // Store with HashMap
//        college_data.remove(key);
//        collegeData.put(key, college_data);


        // Store with SQLite
        //handler = new Handler();
        dbHelper = new DBHelper(getContext());
        String id = "" + colleges.indexOf(key);
        new CreateDB(college_data, id).execute();
    }

    protected void addHome(ArrayList<String> homeLatLon) {

        // TODO: Store with preferences or in table?
        collegeData.put("HOME", homeLatLon);
    }

    // TODO: Rework above 2 methods to use SQLite


    // ********************************** SQLite ***************************************************


    private class CreateDB extends AsyncTask<Void, Void, SQLiteDatabase> {

        ArrayList<String> data = null;
        String id;

        public CreateDB(ArrayList<String> data, String id) {
            this.data = data;
            this.id = id;
        }

        @Override
        protected SQLiteDatabase doInBackground(Void... params) {
            // Creates or opens the database
            // The first time it is called onCreate, onUpgrade run
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();

            // Insert data into the database
            values.put(UniversityDataContract.UniversityEntry.COLUMN_NAME_ID, id); // make the id be the index of where it is in colleges list
            values.put(UniversityDataContract.UniversityEntry.COLUMN_NAME_NAME, data.get(0)); // name
            values.put(UniversityDataContract.UniversityEntry.COLUMN_NAME_URL, data.get(1)); // URL
            values.put(UniversityDataContract.UniversityEntry.COLUMN_NAME_LAT, data.get(2)); // Lat
            values.put(UniversityDataContract.UniversityEntry.COLUMN_NAME_LON, data.get(3)); // Long
            values.put(UniversityDataContract.UniversityEntry.COLUMN_NAME_ADDR, data.get(4)); // Address

            long rowID = db.insert(UniversityDataContract.UniversityEntry.TABLE_NAME, null, values);

            return db;
        }


        @Override
        protected void onPostExecute(SQLiteDatabase db) {
            super.onPostExecute(db);


            // set db to this database
            MainFragment.this.db = db;
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    // queries
//                    // Does this need to happen here?
//                    // When we are initially adding a college we just need to add a row to the database
//                    // Need to query when a listview item is tapped (in CursorAdapter) or in map activity
//                }
//            });
        }
    }

}
