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
 * Created by chris on 5/16/2015.
 */
public class ConnectionStringDAO {

    public static final String TAG = "ConnectionStringDAO";

    // Database Fields
    private SQLiteDatabase mDatabase;
    private DbHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = { DatabaseContract.ConnectionStringEntry._ID,
            DatabaseContract.ConnectionStringEntry.COLUMN_NAME_VALUE,
            DatabaseContract.ConnectionStringEntry.COLUMN_NAME_ALIAS};

    public ConnectionStringDAO(Context context) {
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

    public ConnectionString createConnectionString(String alias, String value) {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.ConnectionStringEntry.COLUMN_NAME_ALIAS, alias);
        values.put(DatabaseContract.ConnectionStringEntry.COLUMN_NAME_VALUE, value);
        long insertId = mDatabase.insert(DatabaseContract.ConnectionStringEntry.TABLE_NAME, null,
                values);
        Cursor cursor = mDatabase.query(DatabaseContract.ConnectionStringEntry.TABLE_NAME,
                mAllColumns, DatabaseContract.ConnectionStringEntry._ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        ConnectionString newConnectionString = cursorToConnectionString(cursor);
        cursor.close();
        return newConnectionString;
    }

    public void deleteConnectionString(ConnectionString connectionString) {
        long id = connectionString.getConnectionId();

        Log.i(TAG, "the deleted connection string has the id: " + id);
        mDatabase.delete(DatabaseContract.ConnectionStringEntry.TABLE_NAME,
                DatabaseContract.ConnectionStringEntry._ID + " = " + id, null);
    }

    public List<ConnectionString> getAllConnectionStrings() {
        List<ConnectionString> listConnectionStrings = new ArrayList<ConnectionString>();

        Cursor cursor = mDatabase.query(DatabaseContract.ConnectionStringEntry.TABLE_NAME,
                mAllColumns, null, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                ConnectionString connectionString = cursorToConnectionString(cursor);
                listConnectionStrings.add(connectionString);
                cursor.moveToNext();
            }

            // close the cursor
            cursor.close();
        }

         return listConnectionStrings;
    }

    public ConnectionString getConnectionStringById(long id) {
        Cursor cursor = mDatabase.query(DatabaseContract.ConnectionStringEntry.TABLE_NAME,
                mAllColumns, DatabaseContract.ConnectionStringEntry._ID + " = " + id, null, null,
                null, null);
        if(cursor != null) {
            cursor.moveToFirst();
        }

        ConnectionString connectionString = cursorToConnectionString(cursor);
        cursor.close();

        return connectionString;
    }

    protected ConnectionString cursorToConnectionString(Cursor cursor) {


        ConnectionString connectionString = new ConnectionString();

        int index = cursor.getColumnIndex(DatabaseContract.ConnectionStringEntry._ID);
        connectionString.setConnectionId(cursor.getLong(index));

        index = cursor.getColumnIndex(DatabaseContract.ConnectionStringEntry.COLUMN_NAME_ALIAS);
        connectionString.setAlias(cursor.getString(index));

        index = cursor.getColumnIndex(DatabaseContract.ConnectionStringEntry.COLUMN_NAME_VALUE);
        connectionString.setValue(cursor.getString(index));
        return connectionString;
    }

}
