package com.example.setup.finalproject;


import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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

    //https://inventory.data.gov/api/action/datastore_search?resource_id=38625c3d-5388-4c16-a30f-d105432553a4&fields=INSTNM,WEBADDR&q={%22INSTNM%22:%22clarkson%22}

    public static final String LOG_TAG = MainFragment.class.getName();
    public static final String RESOURCE_KEY = "38625c3d-5388-4c16-a30f-d105432553a4";
    public static final String FIELDS = "INSTNM,WEBADDR,LATITUDE,LONGITUD,ADDR,CITY,STABBR";
    public static final String ID = "COLLEGE";
    ListView list = null;

    ArrayAdapter<String> collegesAdapter;
    List<String> colleges;
    HashMap<String, ArrayList<String>> collegeData = new HashMap();

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
        // TODO add college to SQLite table?
        String key = college_data.get(0);
        colleges.add(key);
        college_data.remove(key);
        collegeData.put(key, college_data);
    }

    protected void addHome(ArrayList<String> homeLatLon) {
        collegeData.put("HOME", homeLatLon);
    }

    // TODO: Rework above 2 methods to use SQLite



}
