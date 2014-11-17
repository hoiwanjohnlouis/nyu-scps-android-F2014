package com.hoiwanlouis.s11e52_hoisimpleaccessprovider;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class MainActivity extends Activity {

    private final String DEBUG_TAG = this.getClass().getSimpleName();

    // data
    public static final String AUTHORITY = "com.hoiwanlouis.s11e51_hoisimplesearchprovider.SimpleFieldnotesContentProvider";

    // content mime types
    public static final String BASE_DATA_NAME = "fieldnotes_provider";

    // common URIs
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + BASE_DATA_NAME);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(DEBUG_TAG, "in OnCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        Cursor cursor = getContentResolver().query(CONTENT_URI, null, null, null,
                "fieldnotes_title");

        ListView listView = (ListView) findViewById(R.id.provider);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                android.R.layout.two_line_list_item,
                cursor,
                new String[] { "FIELDNOTES_TITLE", "FIELDNOTES_BODY" },
                new int[] { android.R.id.text1, android.R.id.text2 },
                0);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(DEBUG_TAG, "in OnCreateOptionsMenu()");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(DEBUG_TAG, "in onOptionsItemSelected()");
        boolean found;
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            found = true;
        }
        else {
            found = super.onOptionsItemSelected(item);
        }

        return found;
    }
}
