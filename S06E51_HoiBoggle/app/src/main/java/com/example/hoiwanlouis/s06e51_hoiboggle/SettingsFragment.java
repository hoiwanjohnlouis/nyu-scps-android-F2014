package com.example.hoiwanlouis.s06e51_hoiboggle;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.util.Log;

/**
 * Created by hoiwanlouis on 10/28/14.
 */
public class SettingsFragment extends PreferenceFragment {
    private final String DEBUG_TAG = this.getClass().getSimpleName();

    // creates preferences GUI from preferences.xml file in res/xml
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.v(DEBUG_TAG, "in onCreate()");
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        return;
    }

}   // end of SettingsFragment
