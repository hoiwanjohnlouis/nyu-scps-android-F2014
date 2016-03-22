package com.hoiwanlouis.hoicannongame;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;


public class MainActivity extends Activity
{

    private final String DEBUG_TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.i(DEBUG_TAG, "in onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


}
