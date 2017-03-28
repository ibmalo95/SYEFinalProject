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
 * Created by Ina on 1/9/17.
 * Custom Adapter used to populate the list view
 */

public class CustomAdapter extends ArrayAdapter<String[]>{

    private static final String LOG_TAG = CustomAdapter.class.getName();
    private static final String ID = "LIST";

    protected List<String[]> colleges = null;
    protected MainFragment fragment = null;
    private Handler h = null;

    public CustomAdapter(Context ctx, int resource, List<String[]> colleges, MainFragment fragment) {
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
    public String[] getItem(int position) {
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
        final TextView addrItem = (TextView)view.findViewById(R.id.list_item_address);

        listItemText.setText(colleges.get(position)[0]);
        addrItem.setText(colleges.get(position)[1]);
        ImageButton delete = (ImageButton)view.findViewById(R.id.delete);

        // Remove school from listview and database
        delete.setOnClickListener(new View.OnClickListener(){
            int i = position;
            @Override
            public void onClick(View v) {
                String[] remove = colleges.get(position);
                colleges.remove(i);
                final String data_remove = remove[0] + " " + remove[1];
                notifyDataSetChanged();
                h.post(new Runnable() {
                    @Override
                    public void run() {
                        DBQueries.deleteRow(fragment.db, data_remove);
                    }
                });
            }
        });
        // Access individual colleges information
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> location = new ArrayList();
                String id = colleges.get(position)[0] + " " + colleges.get(position)[1];
                location.add(id);
                AccessDB accessDB = new AccessDB(ID, location, fragment);
                accessDB.execute();
            }
        });

        return view;
    }
}
