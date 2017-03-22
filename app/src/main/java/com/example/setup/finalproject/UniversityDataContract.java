package com.example.setup.finalproject;

import android.provider.BaseColumns;

/**
 * Created by ibmalo13 on 11/29/2016.
 */

public class UniversityDataContract {

    private UniversityDataContract(){}

    public static class UniversityEntry implements BaseColumns {
        public static final String TABLE_NAME = "universitys";
        public static final String COLUMN_NAME_ID = "_id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_URL = "url";
        public static final String COLUMN_NAME_LAT = "latitude";
        public static final String COLUMN_NAME_LON = "longitude";
        public static final String COLUMN_NAME_ADDR = "address";
        public static final String COLUMN_NAME_ADM = "admission_rate";
        public static final String COLUMN_NAME_SIZE = "size";
        public static final String COLUMN_NAME_TIN = "tuition_in_state";
        public static final String COLUMN_NAME_TOUT = "tuition_out_of_state";
        public static final String COLUMN_NAME_COMP = "completion_rate";
        public static final String COLUMN_NAME_RETEN = "retention_rate";
        public static final String COLUMN_NAME_DEBT = "debt";

    }
}
