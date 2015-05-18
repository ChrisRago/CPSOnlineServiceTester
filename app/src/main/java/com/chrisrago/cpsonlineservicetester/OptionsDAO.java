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
public class OptionsDAO {

    public static final String TAG = "OptionsDAO";

    // Database Fields
    private SQLiteDatabase mDatabase;
    private DbHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = { DatabaseContract.OptionsEntry._ID,
            DatabaseContract.OptionsEntry.COLUMN_NAME_OPTION_NAME,
            DatabaseContract.OptionsEntry.COLUMN_NAME_OPTION_VALUE};

    public OptionsDAO(Context context) {
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

    public Options createOption(String optionName, String optionValue) {
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.OptionsEntry.COLUMN_NAME_OPTION_NAME, optionName);
        values.put(DatabaseContract.OptionsEntry.COLUMN_NAME_OPTION_VALUE, optionValue);
        long insertId = mDatabase.insert(DatabaseContract.OptionsEntry.TABLE_NAME, null,
                values);
        Cursor cursor = mDatabase.query(DatabaseContract.OptionsEntry.TABLE_NAME,
                mAllColumns, DatabaseContract.OptionsEntry._ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Options newOption = cursorToOption(cursor);
        cursor.close();
        return newOption;
    }

    public void deleteOption(Options option) {
        long id = option.getOptionId();

        Log.i(TAG, "the deleted option has the id: " + id);
        mDatabase.delete(DatabaseContract.OptionsEntry.TABLE_NAME,
                DatabaseContract.OptionsEntry._ID + " = " + id, null);
    }

    public List<Options> getAllOptions() {
        List<Options> listOptions = new ArrayList<Options>();

        Cursor cursor = mDatabase.query(DatabaseContract.OptionsEntry.TABLE_NAME,
                mAllColumns, null, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                Options option = cursorToOption(cursor);
                listOptions.add(option);
                cursor.moveToNext();
            }

            // close the cursor
            cursor.close();
        }

        return listOptions;
    }

    public Options getOptionById(long id) {
        Cursor cursor = mDatabase.query(DatabaseContract.OptionsEntry.TABLE_NAME,
                mAllColumns, DatabaseContract.OptionsEntry._ID + " = " + id, null, null,
                null, null);
        if(cursor != null) {
            cursor.moveToFirst();
        }

        Options option = cursorToOption(cursor);
        cursor.close();

        return option;
    }

    public Options getOptionByOptionName(String optionName) {
        Cursor cursor = mDatabase.query(DatabaseContract.OptionsEntry.TABLE_NAME,
                mAllColumns, DatabaseContract.OptionsEntry.COLUMN_NAME_OPTION_NAME + " = " +
                optionName, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Options option = cursorToOption(cursor);
        cursor.close();

        return option;
    }

    protected Options cursorToOption(Cursor cursor) {

        Options option = new Options();

        int index = cursor.getColumnIndex(DatabaseContract.OptionsEntry._ID);
        option.setOptionId(cursor.getLong(index));

        index = cursor.getColumnIndex(DatabaseContract.OptionsEntry.COLUMN_NAME_OPTION_NAME);
        option.setOptionName(cursor.getString(index));

        index = cursor.getColumnIndex(DatabaseContract.OptionsEntry.COLUMN_NAME_OPTION_VALUE);
        option.setOptionValue(cursor.getString(index));
        return option;
    }
}
