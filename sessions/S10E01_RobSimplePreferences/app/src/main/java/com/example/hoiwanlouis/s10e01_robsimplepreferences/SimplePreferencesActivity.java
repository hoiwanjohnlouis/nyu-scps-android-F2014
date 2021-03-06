package com.example.hoiwanlouis.s10e01_robsimplepreferences;

import android.content.SharedPreferences;
import android.os.Bundle;

// First activity screen (start screen)
public class SimplePreferencesActivity extends SuperSimplePreferencesActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Set some prefs for this specific activity
    	SharedPreferences settingsActivity = getPreferences(MODE_PRIVATE); 
    	if(settingsActivity.contains(PREFERENCE_STRING_NAME) == false) {
    		// Set some new prefs
            SharedPreferences.Editor prefEditor = settingsActivity.edit();
            prefEditor.putBoolean("Boolean_Pref", false); 
            prefEditor.putFloat("Float_Pref", Float.NEGATIVE_INFINITY);
            prefEditor.putInt("Int_Pref", Integer.MIN_VALUE);
            prefEditor.putString(PREFERENCE_STRING_NAME, this.getLocalClassName()); 
            prefEditor.commit();
    	}
    	super.onCreate(savedInstanceState);
    }

	@Override
	Class<?> GetTargetClass() {
		// Where the "Go to other activity" action will send us
		return MoreSimplePreferencesActivity.class;
	}
}