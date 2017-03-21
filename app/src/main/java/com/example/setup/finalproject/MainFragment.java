package com.example.setup.finalproject;


import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * TODO: Requires change from hashmap to SQLite
 * A simple {@link Fragment} subclass.
 * Deals with adding to the listview and storing data into the table
 */
public class MainFragment extends Fragment {

    public static final String LOG_TAG = MainFragment.class.getName();
    public static final String ID = "NAMES";
    public static final String CONTAINS = "CONTAINS";

    ListView list = null;
    ArrayAdapter<String> collegesAdapter;
    List<String> colleges;

    // SQLite instance data
    private DBHelper dbHelper = null;
    private View root;
    protected SQLiteDatabase db = null;

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
        root = inflater.inflate(R.layout.fragment_main, container, false);

        // input college names from SQLite table into colleges list
        // need to open the database again
        if (db == null) {
            dbHelper = new DBHelper(getContext());
            new CreateDB(null, null).execute();
        }
        else {
            list = (ListView) root.findViewById(R.id.college_list);
            list.setAdapter(collegesAdapter);
            list.setClickable(true);
        }

        return root;
    }

    protected void getNames() {
        new AccessDB(ID, new ArrayList<String>(), this).execute();
    }

    protected void populateColleges(ArrayList<String> names) {
        for (int i = 0; i < names.size(); i++) {
            colleges.add(names.get(i));
        }
        list = (ListView) root.findViewById(R.id.college_list);
        list.setAdapter(collegesAdapter);
        list.setClickable(true);
    }
    protected void contains(ArrayList<String[]> data, ArrayList<String> college_data) {
        String name = college_data.get(0);
        String addr = college_data.get(4);
        int duplicate = 0;
        if (!data.isEmpty()) {
            for (int i = 0; i < data.size(); i++) {
                String rName = data.get(i)[0];
                String rAddr = data.get(i)[1];
                if (rName.equals(name) && rAddr.equals(addr)) {
                    // College already in the list so don't add
                    break;
                } else if (rName.equals(name) && !rAddr.equals(addr)) {
                    duplicate++;
                }
            }
        }
        else if (duplicate > 0) {
            colleges.add(name + ": " + addr);
            String id = name + ": " + addr;
            // Store with SQLite
            dbHelper = new DBHelper(getContext());
            new CreateDB(college_data, id).execute();
        }
        else {
            colleges.add(name);
            String id = name;
            dbHelper = new DBHelper(getContext());
            new CreateDB(college_data, id).execute();
        }
    }

    // add college to the ListView
    protected void addCollege(ArrayList<String> college_data) {
        // TODO: Check two cases: 1. Name and Address are in the database. 2. If just the name is in the database
        new AccessDB(CONTAINS, college_data, this).execute();
    }

    protected void startList(ArrayList<String> data) {
        Intent intent = new Intent(this.getActivity(), ListItemActivity.class);
        intent.putExtra("INFO", data);
        startActivity(intent);
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

            if (data != null) {
                // Insert data into the database
                values.put(UniversityDataContract.UniversityEntry.COLUMN_NAME_ID, id); // ID college name or college name plus address
                values.put(UniversityDataContract.UniversityEntry.COLUMN_NAME_NAME, data.get(0)); // name
                values.put(UniversityDataContract.UniversityEntry.COLUMN_NAME_URL, data.get(1)); // URL
                values.put(UniversityDataContract.UniversityEntry.COLUMN_NAME_LAT, data.get(2)); // Lat
                values.put(UniversityDataContract.UniversityEntry.COLUMN_NAME_LON, data.get(3)); // Long
                values.put(UniversityDataContract.UniversityEntry.COLUMN_NAME_ADDR, data.get(4)); // Address

                long rowID = db.insert(UniversityDataContract.UniversityEntry.TABLE_NAME, null, values);
            }

            return db;
        }

        @Override
        protected void onPostExecute(SQLiteDatabase db) {
            super.onPostExecute(db);
            // set db to this database
            MainFragment.this.db = db;
            if (data == null) {
                getNames();
            }
        }
    }

}
