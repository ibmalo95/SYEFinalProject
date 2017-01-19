package com.example.setup.finalproject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ibmalo13 on 11/29/2016.
 * TODO: finish implementing SQLite
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String LOG_TAG = DBHelper.class.getName();
    public static final String DATABASE_NAME = "university's.db";
    public static final int DATABASE_VERSION = 1;
    // create table
    public static final String SQL_CREATE_UNIVERSITY_TABLE =
            "CREATE TABLE "
                    + UniversityDataContract.UniversityEntry.TABLE_NAME + " ("
                    + UniversityDataContract.UniversityEntry.COLUMN_NAME_ID + " varchar not null, "
                    + UniversityDataContract.UniversityEntry.COLUMN_NAME_NAME + " varchar not null, "
                    + UniversityDataContract.UniversityEntry.COLUMN_NAME_URL + " varchar not null, "
                    + UniversityDataContract.UniversityEntry.COLUMN_NAME_LAT + " varchar not null, "
                    + UniversityDataContract.UniversityEntry.COLUMN_NAME_LON + " varchar not null, "
                    + "PRIMARY KEY (" + UniversityDataContract.UniversityEntry.COLUMN_NAME_ID + "))";

    // drop table
    public static final String SQL_DROP_UNIVERSITY_TABLE =
            "DROP TABLE IF EXISTS " + UniversityDataContract.UniversityEntry.TABLE_NAME;



    public DBHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION );
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
