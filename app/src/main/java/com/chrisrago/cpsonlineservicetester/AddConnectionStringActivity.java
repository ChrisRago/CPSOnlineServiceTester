package com.chrisrago.cpsonlineservicetester;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AddConnectionStringActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_connection_string);

        // set title
        //getSupportActionBar().setTitle(R.string.add_connection_string);

        Button saveButton = (Button) findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveConnectionString();
            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_connection_string, menu);
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

    private void saveConnectionString() {

        String alias = ((EditText) findViewById(R.id.AliasInput)).getText().toString();
        String value = ((EditText) findViewById(R.id.ValueInput)).getText().toString();

        // Make sure both alias and value have something in it.
        if(alias.isEmpty() || value.isEmpty()){
            Toast.makeText(this, "both alias and value cannot be blank", Toast.LENGTH_LONG).show();
        }
        else {

            // Create the new connection string and return to the main activity
            ConnectionStringDAO connStringDAO = new ConnectionStringDAO(this);
            ConnectionString connString = connStringDAO.createConnectionString(alias, value);

            setResult(Activity.RESULT_OK);
            finish();
        }
    }
}
