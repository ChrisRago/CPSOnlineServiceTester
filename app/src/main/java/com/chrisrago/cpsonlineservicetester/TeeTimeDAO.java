package com.chrisrago.cpsonlineservicetester;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chris on 5/18/2015.
 */
public class TeeTimeDAO {

    public static final String TAG = "TeeTimeDAO";

    // Database Fields
    private SQLiteDatabase mDatabase;
    private DbHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = { DatabaseContract.TeeTimeEntry._ID,
            DatabaseContract.TeeTimeEntry.COLUMN_NAME_CPS_TEETIMEID,
            DatabaseContract.TeeTimeEntry.COLUMN_NAME_SLOTS_AVAILABLE,
            DatabaseContract.TeeTimeEntry.COLUMN_NAME_START_TIME};

    public TeeTimeDAO(Context context) {
        this.mContext = context;
        mDbHelper = new DbHelper(context);
        // open the database
        try {
            open();
        } catch (SQLException e) {
            Log.e(TAG, "SQLException on openning database " + e.getMessage());
        }
    }

    public void open() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close(){
        mDbHelper.close();
    }

    public TeeTime createTeeTime(String teeTimeId, String slotsAvailable, String startTime) {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.TeeTimeEntry.COLUMN_NAME_CPS_TEETIMEID, teeTimeId);
        values.put(DatabaseContract.TeeTimeEntry.COLUMN_NAME_SLOTS_AVAILABLE, slotsAvailable);
        values.put(DatabaseContract.TeeTimeEntry.COLUMN_NAME_START_TIME, startTime);
        long insertId = mDatabase.insert(DatabaseContract.TeeTimeEntry.TABLE_NAME, null,
                values);
        Cursor cursor = mDatabase.query(DatabaseContract.TeeTimeEntry.TABLE_NAME,
                mAllColumns, DatabaseContract.TeeTimeEntry._ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        TeeTime newTeeTime = cursorToTeeTime(cursor);
        cursor.close();
        return newTeeTime;
    }

    public void deleteTeeTime(TeeTime teeTime) {
        long id = teeTime.getTeeTimeId();

        Log.i(TAG, "the deleted tee time has the id: " + id);
        mDatabase.delete(DatabaseContract.TeeTimeEntry.TABLE_NAME,
                DatabaseContract.TeeTimeEntry._ID + " = " + id, null);
    }

    public List<TeeTime> getAllTeeTimes() {
        List<TeeTime> listTeeTimes = new ArrayList<TeeTime>();

        Cursor cursor = mDatabase.query(DatabaseContract.TeeTimeEntry.TABLE_NAME,
                mAllColumns, null, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                TeeTime teeTime = cursorToTeeTime(cursor);
                listTeeTimes.add(teeTime);
                cursor.moveToNext();
            }

            // close the cursor
            cursor.close();
        }

        return listTeeTimes;
    }

    public TeeTime getTeeTimeById(long id) {
        Cursor cursor = mDatabase.query(DatabaseContract.TeeTimeEntry.TABLE_NAME,
                mAllColumns, DatabaseContract.TeeTimeEntry._ID + " = " + id, null, null,
                null, null);
        if(cursor != null) {
            cursor.moveToFirst();
        }

        TeeTime teeTime = cursorToTeeTime(cursor);
        cursor.close();

        return teeTime;
    }

    public TeeTime getTeeTimeByCPSId(String cpsId) {
        Cursor cursor = mDatabase.query(DatabaseContract.TeeTimeEntry.TABLE_NAME,
                mAllColumns, DatabaseContract.TeeTimeEntry.COLUMN_NAME_CPS_TEETIMEID + " = " + cpsId, null, null,
                null, null);
        if(cursor != null) {
            cursor.moveToFirst();
        }

        TeeTime teeTime = cursorToTeeTime(cursor);
        cursor.close();

        return teeTime;
    }

    protected TeeTime cursorToTeeTime(Cursor cursor) {

        TeeTime teeTime = new TeeTime();

        int index = cursor.getColumnIndex(DatabaseContract.TeeTimeEntry._ID);
        teeTime.setTeeTimeId(cursor.getLong(index));

        index = cursor.getColumnIndex(DatabaseContract.TeeTimeEntry.COLUMN_NAME_CPS_TEETIMEID);
        teeTime.setCPSTeeTimeId(cursor.getString(index));

        index = cursor.getColumnIndex(DatabaseContract.TeeTimeEntry.COLUMN_NAME_SLOTS_AVAILABLE);
        teeTime.setSlotsAvailable(cursor.getString(index));

        index = cursor.getColumnIndex(DatabaseContract.TeeTimeEntry.COLUMN_NAME_START_TIME);
        teeTime.setStartTime(cursor.getString(index));
        return teeTime;
    }
}
