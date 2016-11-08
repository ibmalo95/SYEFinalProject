package com.example.setup.finalproject;


import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    //https://inventory.data.gov/api/action/datastore_search?resource_id=38625c3d-5388-4c16-a30f-d105432553a4&fields=INSTNM,WEBADDR&q={%22INSTNM%22:%22clarkson%22}

    public static final String APIKEY = "38625c3d-5388-4c16-a30f-d105432553a4";
    ListView list = null;

    ArrayAdapter<String> collegesAdapter;
    List<String> colleges;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        colleges = new ArrayList();
        collegesAdapter = new ArrayAdapter(getContext(), R.layout.list_item, R.id.list_item, colleges);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        list = (ListView) root.findViewById(R.id.college_list);
        list.setAdapter(collegesAdapter);
        return root;
    }

    // add college to the ListView
    // TODO get data from USED and put url and location in a HashMap
    protected void addCollege(String college) {
        colleges.add(college);

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https").authority("inventory.data.gov").
                appendPath()
    }

}
