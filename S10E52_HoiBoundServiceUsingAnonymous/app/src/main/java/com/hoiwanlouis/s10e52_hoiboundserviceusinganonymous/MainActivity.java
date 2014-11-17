package com.hoiwanlouis.s10e52_hoiboundserviceusinganonymous;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity {
    private final String DEBUG_TAG = this.getClass().getSimpleName();
    LocalService mService;
    private boolean mBound = false;

    private Button button_local;
    private Button button_messenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(DEBUG_TAG, "in onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupButtonLocal();
        setupButtonMessenger();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(DEBUG_TAG, "in onCreateOptionsMenu()");
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
            Log.d(DEBUG_TAG, "in onOptionsItemSelected();id=action_setting");
            found = true;
        }
        else {
            Log.d(DEBUG_TAG, "in onOptionsItemSelected();id=DEFAULT");
            found = super.onOptionsItemSelected(item);
        }
        return found;
    }

    //
    public void setupButtonLocal() {
        Log.i(DEBUG_TAG, "setupButtonLocal()");

        button_local = (Button) findViewById(R.id.button_local);
        button_local.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent laIntent = new Intent(MainActivity.this, LocalActivity.class);
                startActivity(laIntent);
            }
        });

    }

    //
    public void setupButtonMessenger() {
        Log.i(DEBUG_TAG, "setupButtonMessenger()");

        button_messenger = (Button) findViewById(R.id.button_messenger);
        button_messenger.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent maIntent = new Intent(MainActivity.this,MessengerActivity.class);
                startActivity(maIntent);
            }
        });

    }

}
