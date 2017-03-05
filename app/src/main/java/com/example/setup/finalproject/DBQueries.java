package com.example.setup.finalproject;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Ina on 2/5/17.
 */

// Lets you read from the database
public class DBQueries {

    private static final String LOG_TAG = DBQueries.class.getName();

    public static ArrayList<String> getRow(SQLiteDatabase db, String id) {

        // Tapping a listview item
        String[] projection = new String[]{
                UniversityDataContract.UniversityEntry.COLUMN_NAME_NAME,
                UniversityDataContract.UniversityEntry.COLUMN_NAME_URL,
                UniversityDataContract.UniversityEntry.COLUMN_NAME_ADDR
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

    public static HashMap<String, String[]> getLocations(SQLiteDatabase db) {
        String [] projection = {
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

            String key = cursor.getString(cursor.getColumnIndexOrThrow(UniversityDataContract.UniversityEntry.COLUMN_NAME_NAME));
            String[] coordinates = {
                    cursor.getString(cursor.getColumnIndexOrThrow(UniversityDataContract.UniversityEntry.COLUMN_NAME_LAT)),
                    cursor.getString(cursor.getColumnIndexOrThrow(UniversityDataContract.UniversityEntry.COLUMN_NAME_LON))
            };
            items.put(key, coordinates);
        }

        return items;
    }

    public static ArrayList<String> getNames(SQLiteDatabase db) {
        String [] projection = {
                UniversityDataContract.UniversityEntry.COLUMN_NAME_NAME
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

        ArrayList<String> items = new ArrayList<>();

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(UniversityDataContract.UniversityEntry.COLUMN_NAME_NAME));
            items.add(name);
        }
        return items;
    }

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
