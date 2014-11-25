package com.hoiwanlouis.s11e04_mikebirthdaycontentprovider;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
// import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {

    public final String DEBUG_TAG = this.getClass().getSimpleName();

    protected void onCreate(Bundle savedInstanceState) {
        Log.i(DEBUG_TAG, "in onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(DEBUG_TAG, "in onCreateOptionsMenu()");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(DEBUG_TAG, "in onOptionsItemSelected()");
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


    public void deleteAllBirthdays (View view) {
        Log.i(DEBUG_TAG, "in deleteAllBirthdays()");
        // delete all the records and the table of the database provider
        int count = getContentResolver().delete(
                BirthProvider.CONTENT_URI, null, null);
        String countNum = count +" records are deleted.";
        Toast.makeText(getBaseContext(),
                countNum, Toast.LENGTH_LONG).show();

    }

    public void addBirthday(View view) {
        Log.i(DEBUG_TAG, "in addBirthday()");
        // Add a new birthday record
        ContentValues values = new ContentValues();

        values.put(BirthProvider.NAME,
                ((EditText)findViewById(R.id.name)).getText().toString());

        values.put(BirthProvider.BIRTHDAY,
                ((EditText)findViewById(R.id.birthday)).getText().toString());

        Uri uri = getContentResolver().insert(
                BirthProvider.CONTENT_URI, values);

        Toast.makeText(getBaseContext(),
                uri.toString() + " inserted!", Toast.LENGTH_LONG).show();
    }


    public void showAllBirthdays(View view) {
        Log.i(DEBUG_TAG, "in showAllBirthdays()");
        // Show all the birthdays sorted by friend's name
        Cursor c = getContentResolver().query(BirthProvider.CONTENT_URI, null, null, null, "name");
        String result = "Results:";

        if (c == null) {
            Toast.makeText(this, result + " no birthdays found! Add a birthday.", Toast.LENGTH_LONG).show();
        } else if (!c.moveToFirst()) {
            Toast.makeText(this, result + " no birthdays found, add a birthday!", Toast.LENGTH_LONG).show();
        }else{
            do{
                result = result + "\n" + c.getString(c.getColumnIndex(BirthProvider.NAME)) +
                        " with id " +  c.getString(c.getColumnIndex(BirthProvider.ID)) +
                        " has birthday: " + c.getString(c.getColumnIndex(BirthProvider.BIRTHDAY));
            } while (c.moveToNext());
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        }

    }

}
