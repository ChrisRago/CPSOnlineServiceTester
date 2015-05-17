package com.chrisrago.cpsonlineservicetester;

import android.provider.BaseColumns;

/**
 * Created by chris on 5/6/2015.
 */
public final class DatabaseContract {
    // prevent accidental instantiation of the contract class,
    // give it an empty constructor
    public DatabaseContract() {}

    /* Inner class that defines the table contents for tblTeeTimes */
    public static abstract class TeeTimeEntry implements BaseColumns {
        public static final String TABLE_NAME = "tblTeeTimes";
        public static final String COLUMN_NAME_START_TIME = "startTime";
        public static final String COLUMN_NAME_SLOTS_AVAILABLE = "slotsAvailable";

    }

    /* Inner class that defines the table contents for tblSettings */
    public static abstract class OptionsEntry implements BaseColumns {
        public static final String TABLE_NAME = "tblOptions";
        public static final String COLUMN_NAME_OPTION_NAME = "optionName";
        public static final String COLUMN_NAME_OPTION_VALUE = "optionValue";
    }

    /* Inner class that defines the table contents for tblConnectionString */
    public static abstract class ConnectionStringEntry implements BaseColumns {
        public static final String TABLE_NAME = "tblConnectionString";
        public static final String COLUMN_NAME_ALIAS = "alias";
        public static final String COLUMN_NAME_VALUE = "value";
    }

    /* Inner class that defines the table contents for tblReservations */
    public static abstract class ReservationsEntry implements BaseColumns {
        public static final String TABLE_NAME = "tblReservations";
        public static final String COLUMN_NAME_CONFIRMATION = "Confirmation";
    }
}

// http://developer.android.com/training/basics/data-storage/databases.html

