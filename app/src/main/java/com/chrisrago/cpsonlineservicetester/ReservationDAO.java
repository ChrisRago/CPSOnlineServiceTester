package com.chrisrago.cpsonlineservicetester;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.service.restrictions.RestrictionsReceiver;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chris on 5/16/2015.
 */
public class ReservationDAO {

    public static final String TAG = "ReservationDAO";

    // Database Fields
    private SQLiteDatabase mDatabase;
    private DbHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = {DatabaseContract.ReservationsEntry._ID,
            DatabaseContract.ReservationsEntry.COLUMN_NAME_CONFIRMATION};

    public ReservationDAO(Context context) {
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

    public void close() {
        mDbHelper.close();
    }

    public Reservation createReservation(String confirmation) {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.ReservationsEntry.COLUMN_NAME_CONFIRMATION, confirmation);
        long insertId = mDatabase.insert(DatabaseContract.ReservationsEntry.TABLE_NAME, null,
                values);
        Cursor cursor = mDatabase.query(DatabaseContract.ReservationsEntry.TABLE_NAME, mAllColumns,
                DatabaseContract.ReservationsEntry._ID + " = " + insertId, null, null, null, null);
        cursor.moveToFirst();
        Reservation newReservation = cursorToReservation(cursor);
        cursor.close();
        return newReservation;
    }

    public void deleteReservation(Reservation reservation) {
        long id = reservation.getId();

        Log.i(TAG, "the deleted reservation has the id: " + id);
        mDatabase.delete(DatabaseContract.ReservationsEntry.TABLE_NAME,
                DatabaseContract.ReservationsEntry._ID + " = " + id, null);
    }

    public List<Reservation> getAllReservations() {
        List<Reservation> listReservations = new ArrayList<Reservation>();

        Cursor cursor = mDatabase.query(DatabaseContract.ReservationsEntry.TABLE_NAME, mAllColumns,
                null, null, null, null, null);
        if(cursor != null) {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()){
                Reservation reservation = cursorToReservation(cursor);
                listReservations.add(reservation);
                cursor.moveToNext();
            }

            // close the cursor
            cursor.close();
        }

        return listReservations;
    }

    public Reservation getReservationById(long id) {
        Cursor cursor = mDatabase.query(DatabaseContract.ReservationsEntry.TABLE_NAME, mAllColumns,
                DatabaseContract.ReservationsEntry._ID + " = " + id, null, null, null, null);
        if(cursor != null) {
            cursor.moveToFirst();
        }

        Reservation reservation = cursorToReservation(cursor);
        cursor.close();
        return  reservation;
    }

    protected  Reservation cursorToReservation(Cursor cursor) {

        Reservation reservation = new Reservation();

        int index = cursor.getColumnIndex(DatabaseContract.ReservationsEntry._ID);
        reservation.setId(cursor.getLong(index));

        index = cursor.getColumnIndex(DatabaseContract.ReservationsEntry.COLUMN_NAME_CONFIRMATION);
        reservation.setConfirmation(cursor.getString(index));

        return reservation;

    }

}
