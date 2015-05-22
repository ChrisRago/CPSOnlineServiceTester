package com.chrisrago.cpsonlineservicetester;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
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

        // Check database to see if there are any ConnectionStrings already saved
        // If so, load them into the spinner
        ConnectionStringDAO conDAO = new ConnectionStringDAO(this);
        List<ConnectionString> conList = conDAO.getAllConnectionStrings();
        if(conList.size() <= 0) {
            Toast.makeText(this, "No Connection Strings found, please add a new Connection String",
                    Toast.LENGTH_SHORT).show();
            Button loadTeesheet = (Button) findViewById(R.id.LoadTeeSheet);
            loadTeesheet.setVisibility(View.INVISIBLE);
        }
        else{
            updateSpinner();
            Button loadTeesheet = (Button) findViewById(R.id.LoadTeeSheet);
            loadTeesheet.setVisibility(View.VISIBLE);
        }
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

        // create a String array to hold all the aliases
        String[] aliasList = new String[conList.size()];

        // parse the list of connection strings out into just the aliases
        for(int i = 0; i < aliasList.length; i++) {
            aliasList[i] = conList.get(i).getAlias();
        }

        // create the spinner adapter
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                R.layout.support_simple_spinner_dropdown_item, aliasList);

        // get the spinner and set the adapter
        Spinner spinner = (Spinner) findViewById(R.id.ConnectionStringSpinner);
        spinner.setAdapter(spinnerAdapter);

    }
}



/*
//TODO Read this https://chris.banes.me/2014/11/12/theme-vs-style/
//TODO and this https://chris.banes.me/2015/04/22/support-libraries-v22-1-0/
//TODO get teh theme working correctly so it loads the nice green toolbar
http://www.android-ios-tutorials.com/android/android-sqlite-database-example/
 */