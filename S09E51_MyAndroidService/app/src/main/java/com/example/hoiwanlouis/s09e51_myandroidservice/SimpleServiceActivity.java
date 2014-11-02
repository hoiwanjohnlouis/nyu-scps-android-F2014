package com.example.hoiwanlouis.s09e51_myandroidservice;

import android.util.Log;

public class SimpleServiceActivity extends MenuActivity {
    private static final String DEBUG_TAG = "SimpleServiceActivity";
	@Override
	void prepareMenu() {
        Log.v(DEBUG_TAG, "prepareMenuClick() called");
        addMenuItem("1. Service Control", ServiceControlActivity.class);
	}
}