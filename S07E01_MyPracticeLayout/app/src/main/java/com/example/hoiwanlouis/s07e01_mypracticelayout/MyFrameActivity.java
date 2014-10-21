package com.example.hoiwanlouis.s07e01_mypracticelayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MyFrameActivity extends Activity {

    private final String TAG = this.getClass().getSimpleName();
    private Button prevButton;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate Starting...");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_frame);

        // there isn't a Next screen to access, disable the button
        nextButton = (Button) findViewById(R.id.prevButton);
        nextButton.setVisibility(View.INVISIBLE);

        // set up listeners for the buttons
        addListenerOnPrevButton();
        // addListenerOnNextButton();

        Log.i(TAG, "onCreate Ends");
        return;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(TAG, "onCreateOptionsMenu Starting...");

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_frame, menu);

        Log.i(TAG, "onCreateOptionsMenu Ends");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, "onOptionsItemSelected Starting...");

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        boolean foundMatch;
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            foundMatch = true;
        }
        else {
            foundMatch = super.onOptionsItemSelected(item);
        }

        Log.i(TAG, "onOptionsItemSelected Ends");
        return foundMatch;
    }

    public void addListenerOnPrevButton() {
        Log.i(TAG, "addListenerOnPrevButton Starting...");

        prevButton = (Button) findViewById(R.id.prevButton);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gridIntent;
                gridIntent = new Intent(MyFrameActivity.this, MyTableActivity.class);
                startActivity(gridIntent);
            }
        });

        Log.i(TAG, "addListenerOnPrevButton Ends");
        return;
    }

}
