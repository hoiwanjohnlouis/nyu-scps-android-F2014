package com.example.hoiwanlouis.s06e51_hoiboggle;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by hoiwanlouis on 11/3/14.
 */
public class SettingsActivity extends Activity {
    private final String DEBUG_TAG = this.getClass().getSimpleName();

    // use FragmentManager to display SettingsFragment
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(DEBUG_TAG, "in onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        return;
    }

}
