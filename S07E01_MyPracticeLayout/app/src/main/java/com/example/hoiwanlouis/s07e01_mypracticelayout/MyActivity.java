package com.example.hoiwanlouis.s07e01_mypracticelayout;

import com.example.hoiwanlouis.s07e01_mypracticelayout.MyGridActivity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MyActivity extends Activity {

    private Button prevButton;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        // set up listeners for the buttons
        addListenerOnPrevButton();
        addListenerOnNextButton();

        return;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addListenerOnPrevButton() {

        Button prevButton = (Button) findViewById(R.id.prevButton);
        prevButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent gridIntent = new Intent(this, MyGridActivity.class);
//                startActivity(gridIntent);
                return;
            }
        });


    }

    public void addListenerOnNextButton() {

        Button nextButton = (Button) findViewById(R.id.nextButton);
        prevButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gridIntent;
                gridIntent = new Intent(this, MyGridActivity);
                startActivity(gridIntent);

                return;
            }
        });

    }

}


