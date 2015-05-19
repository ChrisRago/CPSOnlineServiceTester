package com.chrisrago.cpsonlineservicetester;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by chris on 5/12/2015.
 */
public class DbHelper extends SQLiteOpenHelper {

    // SQL statement to create tblTeeTimes
    private static final String SQL_CREATE_TABLE_TEETIMES =
        "CREATE TABLE " + DatabaseContract.TeeTimeEntry.TABLE_NAME + " ("
        + DatabaseContract.TeeTimeEntry._ID + " INTEGER PRIMARY KEY, "
        + DatabaseContract.TeeTimeEntry.COLUMN_NAME_CPS_TEETIMEID + " TEXT, "
        + DatabaseContract.TeeTimeEntry.COLUMN_NAME_START_TIME + " TEXT, "
        + DatabaseContract.TeeTimeEntry.COLUMN_NAME_SLOTS_AVAILABLE + " TEXT)";

    // SQL statement to create tblSettings
    private static final String SQL_CREATE_TABLE_Options =
        "CREATE TABLE " + DatabaseContract.OptionsEntry.TABLE_NAME + " ("
        + DatabaseContract.OptionsEntry._ID + " INTEGER PRIMARY KEY, "
        + DatabaseContract.OptionsEntry.COLUMN_NAME_OPTION_NAME + " TEXT, "
        + DatabaseContract.OptionsEntry.COLUMN_NAME_OPTION_VALUE + " TEXT)";

    // SQL statement to create tblConnectionString
    private static final String SQL_CREATE_TABLE_CONNECTION_STRING =
        "CREATE TABLE " + DatabaseContract.ConnectionStringEntry.TABLE_NAME + " ("
        + DatabaseContract.ConnectionStringEntry._ID + " INTEGER PRIMARY KEY, "
        + DatabaseContract.ConnectionStringEntry.COLUMN_NAME_ALIAS + " TEXT, "
        + DatabaseContract.ConnectionStringEntry.COLUMN_NAME_VALUE + " TEXT)";

    // SQL statement to create tblReservations
    private static final String SQL_CREATE_TABLE_RESERVATIONS =
        "CREATE TABLE " + DatabaseContract.ReservationsEntry.TABLE_NAME + " ("
        + DatabaseContract.ReservationsEntry._ID + " INTEGER PRIMARY KEY, "
        + DatabaseContract.ReservationsEntry.COLUMN_NAME_CONFIRMATION + " TEXT)";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ServiceTester.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_TEETIMES);
        db.execSQL(SQL_CREATE_TABLE_Options);
        db.execSQL(SQL_CREATE_TABLE_CONNECTION_STRING);
        db.execSQL(SQL_CREATE_TABLE_RESERVATIONS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Clear all data
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TeeTimeEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.OptionsEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.ConnectionStringEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.ReservationsEntry.TABLE_NAME);


        // Recreate the tables
        onCreate(db);

    }

}
