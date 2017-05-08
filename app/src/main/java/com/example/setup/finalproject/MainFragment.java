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
 * A simple {@link Fragment} subclass.
 * Deals with adding to the listview and storing data into the table
 */
public class MainFragment extends Fragment {

    public static final String LOG_TAG = MainFragment.class.getName();
    public static final String ID = "NAMES";
    public static final String CONTAINS = "CONTAINS";

    private ListView list = null;
    private ArrayAdapter<String[]> collegesAdapter;
    private List<String[]> colleges;

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

        // input college names from database into colleges list
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        db.close();
    }
    // get the names and addresses of all schools in the database
    protected void getNames() {
        new AccessDB(ID, new ArrayList<String>(), this).execute();
    }

    // put colleges found in the database into the colleges list
    protected void populateColleges(ArrayList<String[]> names) {
        for (int i = 0; i < names.size(); i++) {
            colleges.add(names.get(i));
        }
        collegesAdapter.notifyDataSetChanged();
        list = (ListView) root.findViewById(R.id.college_list);
        list.setAdapter(collegesAdapter);
        list.setClickable(true);
    }

    // check if a school is already in the database
    // handles adding a college twice and colleges with the same name
    protected void contains(ArrayList<String[]> data, ArrayList<String> college_data) {
        String name = college_data.get(0);
        String addr = college_data.get(4);
        int duplicate = 0;
        for (int i = 0; i < data.size(); i++) {
            String rName = data.get(i)[0];
            String rAddr = data.get(i)[1];
            if (rName.equals(name) && rAddr.equals(addr)) {
                // College already in the list so don't add
                duplicate++;
            }
        }
        if (duplicate == 0) {
            String[] item = {name, addr};
            colleges.add(item);
            collegesAdapter.notifyDataSetChanged();
            String id = name + " " + addr;
            // Store with SQLite
            dbHelper = new DBHelper(getContext());
            new CreateDB(college_data, id).execute();
        }
    }

    // add college to the ListView and database
    protected void addCollege(ArrayList<String> college_data) {
        new AccessDB(CONTAINS, college_data, this).execute();
    }

    // bring up the college page
    protected void startList(ArrayList<String> data) {
        Intent intent = new Intent(this.getActivity(), ListItemActivity.class);
        intent.putExtra("INFO", data);
        startActivity(intent);
    }

    // *************************** Add college to database **************************************

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
                values.put(UniversityDataContract.UniversityEntry.COLUMN_NAME_ADM, data.get(5)); // admission rate
                values.put(UniversityDataContract.UniversityEntry.COLUMN_NAME_SIZE, data.get(6)); // size
                values.put(UniversityDataContract.UniversityEntry.COLUMN_NAME_TIN, data.get(7)); // in-state tuition
                values.put(UniversityDataContract.UniversityEntry.COLUMN_NAME_TOUT, data.get(8)); // out of state tuition
                values.put(UniversityDataContract.UniversityEntry.COLUMN_NAME_COMP, data.get(9)); // completion rate
                values.put(UniversityDataContract.UniversityEntry.COLUMN_NAME_RETEN, data.get(10)); // retention rate
                values.put(UniversityDataContract.UniversityEntry.COLUMN_NAME_DEBT, data.get(11)); // median debt

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
