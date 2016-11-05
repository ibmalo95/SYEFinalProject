package com.example.setup.finalproject;


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
 * TODO implement the ListView
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

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
    protected void addCollege(String college) {
        colleges.add(college);
    }

}
