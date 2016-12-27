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
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    //https://inventory.data.gov/api/action/datastore_search?resource_id=38625c3d-5388-4c16-a30f-d105432553a4&fields=INSTNM,WEBADDR&q={%22INSTNM%22:%22clarkson%22}

    public static final String LOG_TAG = MainFragment.class.getName();
    public static final String RESOURCE_KEY = "38625c3d-5388-4c16-a30f-d105432553a4";
    public static final String FIELDS = "INSTNM,WEBADDR,LATITUDE,LONGITUD";
    ListView list = null;

    ArrayAdapter<String> collegesAdapter;
    List<String> colleges;
    HashMap<String, ArrayList<String>> collegeData = new HashMap();
    String college;

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
        list.setClickable(true);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LinearLayout listItem = (LinearLayout) view;
                String name = ((TextView)listItem.findViewById(R.id.list_item)).getText().toString();
                Log.i(LOG_TAG, name);
                Log.i(LOG_TAG, collegeData.get(name).toString());
                String url = collegeData.get(name).get(0);
                String[] info = {name, url};
                Intent intent = new Intent(getActivity(), ListItemActivity.class);
                intent.putExtra("INFO", info);
                startActivity(intent);
            }
        });



        return root;
    }

    // add college to the ListView
    // TODO get data from USED
    protected void addCollege(String college) {
        // TODO add college to SQL table?
        colleges.add(college);
        this.college = college;


        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https").authority("inventory.data.gov").
                appendPath("api").
                appendPath("action").
                appendPath("datastore_search").
                appendQueryParameter("resource_id", RESOURCE_KEY).
                appendQueryParameter("fields", FIELDS).
                appendQueryParameter("q", "{\"INSTNM\":\"" + college + "\"}");

        String url = builder.build().toString();
        //Log.v(LOG_TAG, url);

        GetUniversityDataTask getUniversityDataTask = new GetUniversityDataTask(this);
        getUniversityDataTask.execute(url);
    }

    // Storing college info without SQLite
    protected void setCollegeData(ArrayList<String> data) {
        ArrayList<String> universityData = new ArrayList();
        universityData.add(data.get(1));
        universityData.add(data.get(2));
        universityData.add(data.get(3));

        collegeData.put(this.college, universityData);
        Log.i(LOG_TAG, collegeData.toString());
    }



}
