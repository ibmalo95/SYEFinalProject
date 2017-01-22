package com.example.setup.finalproject;

import android.provider.BaseColumns;

/**
 * Created by ibmalo13 on 11/29/2016.
 */

public class UniversityDataContract {

    private UniversityDataContract(){}

    public static class UniversityEntry implements BaseColumns {
        public static final String TABLE_NAME = "university's";
        public static final String COLUMN_NAME_ID = "_id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_URL = "url";
        public static final String COLUMN_NAME_LAT = "latitude";
        public static final String COLUMN_NAME_LON = "longitude";
        public static final String COLUMN_NAME_ADDR = "address";
    }
}
