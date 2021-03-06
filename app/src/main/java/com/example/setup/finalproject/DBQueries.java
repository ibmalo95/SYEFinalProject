package com.example.setup.finalproject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Query the database
 * Created by Ina on 2/5/17.
 */

// Lets you read from the database
public class DBQueries {

    private static final String LOG_TAG = DBQueries.class.getName();

    // Get data needed for college page
    // Find the row that matches the given id
    public static ArrayList<String> getRow(SQLiteDatabase db, String id) {

        // columns to return
        String[] projection = new String[]{
                UniversityDataContract.UniversityEntry.COLUMN_NAME_NAME,
                UniversityDataContract.UniversityEntry.COLUMN_NAME_URL,
                UniversityDataContract.UniversityEntry.COLUMN_NAME_ADDR,
                UniversityDataContract.UniversityEntry.COLUMN_NAME_ADM,
                UniversityDataContract.UniversityEntry.COLUMN_NAME_SIZE,
                UniversityDataContract.UniversityEntry.COLUMN_NAME_TIN,
                UniversityDataContract.UniversityEntry.COLUMN_NAME_TOUT,
                UniversityDataContract.UniversityEntry.COLUMN_NAME_COMP,
                UniversityDataContract.UniversityEntry.COLUMN_NAME_RETEN,
                UniversityDataContract.UniversityEntry.COLUMN_NAME_DEBT,

        };
        // WHERE "id" = id
        String selection = UniversityDataContract.UniversityEntry.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = new String[]{id};

        Cursor cursor = db.query(
                UniversityDataContract.UniversityEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        // return the data from query
        ArrayList<String> items = new ArrayList();
        if (cursor != null) {
            cursor.moveToFirst();
            for (String col: projection) {
                String value = cursor.getString(cursor.getColumnIndexOrThrow(col));
                items.add(value);
            }
        }
        return items;
    }

    // Get the coordinates on each college in the database
    public static HashMap<String, String[]> getLocations(SQLiteDatabase db) {
        // columns to return
        String [] projection = {
                UniversityDataContract.UniversityEntry.COLUMN_NAME_ID,
                UniversityDataContract.UniversityEntry.COLUMN_NAME_NAME,
                UniversityDataContract.UniversityEntry.COLUMN_NAME_LAT,
                UniversityDataContract.UniversityEntry.COLUMN_NAME_LON
        };

        Cursor cursor = db.query(
                UniversityDataContract.UniversityEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        // return the data from query
        HashMap<String, String[]> items = new HashMap();
        while (cursor.moveToNext()) {

            String key = cursor.getString(cursor.getColumnIndexOrThrow(UniversityDataContract.UniversityEntry.COLUMN_NAME_ID));
            String[] coordinates = {
                    cursor.getString(cursor.getColumnIndexOrThrow(UniversityDataContract.UniversityEntry.COLUMN_NAME_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(UniversityDataContract.UniversityEntry.COLUMN_NAME_LAT)),
                    cursor.getString(cursor.getColumnIndexOrThrow(UniversityDataContract.UniversityEntry.COLUMN_NAME_LON))
            };
            items.put(key, coordinates);
        }

        return items;
    }

    // retrieve all the names and addresses in database
    // used to populate the listview when app is first open
    public static ArrayList<String[]> getNames(SQLiteDatabase db) {
        // columns to return
        String [] projection = {
                UniversityDataContract.UniversityEntry.COLUMN_NAME_NAME,
                UniversityDataContract.UniversityEntry.COLUMN_NAME_ADDR
        };

        Cursor cursor = db.query(
                UniversityDataContract.UniversityEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        // return the data from query
        ArrayList<String[]> items = new ArrayList<>();
        while (cursor.moveToNext()) {
            String[] item = {
                    cursor.getString(cursor.getColumnIndexOrThrow(UniversityDataContract.UniversityEntry.COLUMN_NAME_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(UniversityDataContract.UniversityEntry.COLUMN_NAME_ADDR))
            };

            items.add(item);
        }
        return items;
    }

    // Determine if the database already has any names that match the given name
    public static ArrayList<String[]> contains(SQLiteDatabase db, String name) {
        // columns to return
        String [] projection = {
                UniversityDataContract.UniversityEntry.COLUMN_NAME_NAME,
                UniversityDataContract.UniversityEntry.COLUMN_NAME_ADDR
        };

        // WHERE "id" = id
        String selection = UniversityDataContract.UniversityEntry.COLUMN_NAME_NAME + " = ?";
        String[] selectionArgs = new String[]{name};

        Cursor cursor = db.query(
                UniversityDataContract.UniversityEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        // return the data from query
        ArrayList<String[]> items = new ArrayList();
        while (cursor.moveToNext()) {
            String[] data = {null, null};
            int count = 0;
            for (String col: projection) {
                String value = cursor.getString(cursor.getColumnIndexOrThrow(col));
                data[count] = value;
                count++;
            }
            items.add(data);
        }
        return items;
    }

    // Delete a college from the database
    public static void deleteRow(SQLiteDatabase db, String id) {
        try {
            String selection = UniversityDataContract.UniversityEntry.COLUMN_NAME_ID + " = ?";
            String[] args = {id};
            db.delete(UniversityDataContract.UniversityEntry.TABLE_NAME, selection, args);
        }
        catch (Exception e) {
            Log.e(LOG_TAG, e.toString());
        }
    }

}
