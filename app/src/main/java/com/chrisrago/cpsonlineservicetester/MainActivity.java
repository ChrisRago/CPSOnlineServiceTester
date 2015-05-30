package com.chrisrago.cpsonlineservicetester;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;


import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    public static final int ADD_CONNECTION_STRING_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        DbHelper dbHelper = new DbHelper(this);

        // Launch AddConnectionStringActivity
        Button addAlias = (Button) findViewById(R.id.AddConnectionString);
        addAlias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, AddConnectionStringActivity.class);

                startActivityForResult(intent, ADD_CONNECTION_STRING_REQUEST);

            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();

        // Check to see if device has an internet connection
        boolean isConnected = false;

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo ni = cm.getActiveNetworkInfo();
            if (ni != null) {
                if (ni.isConnected()) {

                    // if we've gotten this far, there is a connection
                    isConnected = true;
                }
            }

        }

        // if not internet is available display an alert
        if (!isConnected) {

            AlertDialog ad = new AlertDialog.Builder(this).create();
            ad.setTitle("Internet Required");
            ad.setMessage("An internet connection is required to check Online Services!");
            ad.setButton(DialogInterface.BUTTON_NEUTRAL, "0K",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Does nothing
                        }
                    });
            ad.show();
        }


        // update the spinner
        updateSpinner();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.action_delete) {
            confirmDeleteConnectionString();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // which request are we responding to
        if (requestCode == ADD_CONNECTION_STRING_REQUEST) {
            // Make sure the connection string was added
            if (resultCode == RESULT_OK) {
                // The user added a connection string alias. Notify the user that the alias has
                // been saved successfully, and update the AliasSpinner
                Toast.makeText(MainActivity.this, "Connection String Saved",
                        Toast.LENGTH_SHORT).show();

                updateSpinner();

            }
        }
    }

    private void updateSpinner() {

        // first get a fresh query from the database of all the connection strings
        ConnectionStringDAO conDAO = new ConnectionStringDAO(this);
        List<ConnectionString> conList = conDAO.getAllConnectionStrings();
        if(conList.size() <= 0) {
            Toast.makeText(this, "No Connection String, please add a Connection String",
                    Toast.LENGTH_SHORT).show();
            Button loadTeesheet = (Button) findViewById(R.id.LoadTeeSheet);
            loadTeesheet.setVisibility(View.INVISIBLE);
            Spinner spinner = (Spinner) findViewById(R.id.ConnectionStringSpinner);
            spinner.setVisibility(View.INVISIBLE);
        } else {

            // create a String array to hold all the aliases
            String[] aliasList = new String[conList.size()];

            // parse the list of connection strings out into just the aliases
            for (int i = 0; i < aliasList.length; i++) {
                aliasList[i] = conList.get(i).getAlias();
            }

            // create the spinner adapter
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                    R.layout.support_simple_spinner_dropdown_item, aliasList);

            // get the spinner and set the adapter
            Spinner spinner = (Spinner) findViewById(R.id.ConnectionStringSpinner);
            spinner.setAdapter(spinnerAdapter);
            spinner.setVisibility(View.VISIBLE);

            Button loadTeesheet = (Button) findViewById(R.id.LoadTeeSheet);
            loadTeesheet.setVisibility(View.VISIBLE);

        }
    }

    private void deleteConnectionString(Spinner spinner) {

        // Get the selected index of the spinner
        int index = spinner.getSelectedItemPosition();

        String alias = spinner.getSelectedItem().toString();

        // Create a ConnectionStringDAO class and remove the connection string
        ConnectionStringDAO connStringDAO = new ConnectionStringDAO(this);
        ConnectionString connectionString = connStringDAO.getConnectionStringByAlias(alias);
        connStringDAO.deleteConnectionString(connectionString);

        Toast.makeText(this, "Connection String deleted", Toast.LENGTH_SHORT).show();

        updateSpinner();
    }

    private void confirmDeleteConnectionString(){

        // First make sure there's a connection string to delete
        // Get the spinner and the alias that is selected
        final Spinner spinner = (Spinner) findViewById(R.id.ConnectionStringSpinner);
        int index = spinner.getSelectedItemPosition();
        if (index == -1) {
            Toast.makeText(this, "No Connection String to delete", Toast.LENGTH_SHORT).show();
        } else {
            // Create a dialog prompting the user to confirm they want to delete the connection string
            AlertDialog ad = new AlertDialog.Builder(this).create();
            ad.setTitle("Delete Alias?");
            ad.setMessage("This cannot be undone!");
            ad.setButton(DialogInterface.BUTTON_POSITIVE, "Delete",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteConnectionString(spinner);
                        }
                    });
            ad.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do not delete the connection string
                        }
                    });
            ad.show();
        }


    }
}


//todo http://programmerguru.com/android-tutorial/android-webservice-example/