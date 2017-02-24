package com.example.setup.finalproject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * TODO: Requires change from hashmap to SQLite
 * Created by Ina on 1/9/17.
 * Custom Adapter used to populate the list view
 */

// TODO: when sql is implemented will be able to access data directly from here (not dependent on fragment)
public class CustomAdapter extends ArrayAdapter<String>{

    private static final String LOG_TAG = CustomAdapter.class.getName();
    private static final String ID = "LIST";

    protected List<String> colleges = null;
    protected MainFragment fragment = null;
    private Handler h = null;
    private ArrayList<String> items = new ArrayList();

    public CustomAdapter(Context ctx, int resource, List<String> colleges, MainFragment fragment) {
        super(ctx, resource, colleges);

        this.colleges = colleges;
        this.fragment = fragment;
        h = new Handler();
    }

    @Override
    public int getCount() {
        return colleges.size();
    }

    @Override
    public String getItem(int position) {
        return colleges.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View reusedView, ViewGroup parent) {
        View view = reusedView;

        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // Handle TextView and display string from your list
        final TextView listItemText = (TextView)view.findViewById(R.id.list_item);
        listItemText.setText(colleges.get(position));
        ImageButton delete = (ImageButton)view.findViewById(R.id.delete);

        delete.setOnClickListener(new View.OnClickListener(){
            int i = position;
            @Override
            public void onClick(View v) {
                colleges.remove(i);
                notifyDataSetChanged();

                // SQLITE
                h.post(new Runnable() {
                    @Override
                    public void run() {
                        String id = "" + position;
                        DBQueries.deleteRow(fragment.db, id);
                    }
                });
            }
        });

        listItemText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // SQLITE
                AccessDB accessDB = new AccessDB(ID, "" + position, fragment);
                accessDB.execute();
            }
        });

        return view;
    }
}
