package com.chrisrago.cpsonlineservicetester;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DbHelper dbHelper = new DbHelper(this);

        // Check database to see if there are any ConnectionStrings already saved
        // If so, load them into the spinner
        ConnectionStringDAO conDAO = new ConnectionStringDAO(this);
        List<ConnectionString> conList = conDAO.getAllConnectionStrings();
        if(conList.size() <= 0) {
            Toast.makeText(this, "No Connection Strings found, please click add",
                    Toast.LENGTH_SHORT).show();
        }


        // Temporarily adding some functionality here to test database methods
        Button addAlias = (Button) findViewById(R.id.add_alias);
        addAlias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Going to add a new Connection String
                ConnectionStringDAO cs = new ConnectionStringDAO(MainActivity.this);
                ConnectionString c1 = cs.createConnectionString("Testalias", "Testvalue");
                Log.i(TAG, "\nNewly Created Connection:\nAlias: " + c1.getAlias() + "\nValue: " +
                c1.getValue());

                // get connection string by id
                c1 = cs.getConnectionStringById(1);
                Log.i(TAG, "ID of 1 is: " + c1.getValue());
                //delete all connection strings
                List<ConnectionString> list = cs.getAllConnectionStrings();

                for(int i = 0; i < list.size(); i++) {
                    c1 = list.get(i);
                    cs.deleteConnectionString(c1);
                }

            }
        });
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
}


/*
http://www.android-ios-tutorials.com/android/android-sqlite-database-example/
 */