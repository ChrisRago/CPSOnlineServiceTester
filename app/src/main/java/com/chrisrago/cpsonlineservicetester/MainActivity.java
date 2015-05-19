package com.chrisrago.cpsonlineservicetester;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends ActionBarActivity {

    public static final String TAG = "MainActivity";
    public static final int ADD_CONNECTION_STRING_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set logo in action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.cps1);

        DbHelper dbHelper = new DbHelper(this);

        // Launch AddConnectionStringActivity
        Button addAlias = (Button) findViewById(R.id.AddConnectionString);
        addAlias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(this, AddConnectionStringActivity.class);

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

                //TODO build the other activity that adds the connection string.
                //TODO add code to update the spinner... maybe call a fucntion updateSpinner()
                //TODO retrieve the data (whatever it may be) from the intent
                // http://developer.android.com/training/basics/intents/result.html
                // http://developer.android.com/reference/android/app/Activity.html#startActivityForResult(android.content.Intent, int)

            }
        }
    }
}


/*
http://www.android-ios-tutorials.com/android/android-sqlite-database-example/
 */