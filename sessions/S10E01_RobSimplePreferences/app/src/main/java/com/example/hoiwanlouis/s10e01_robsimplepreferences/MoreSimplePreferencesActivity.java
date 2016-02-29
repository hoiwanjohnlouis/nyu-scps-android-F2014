package com.example.hoiwanlouis.s10e01_robsimplepreferences;

import android.content.SharedPreferences;
import android.os.Bundle;

// Second activity screen 
public class MoreSimplePreferencesActivity extends SuperSimplePreferencesActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Set some prefs for this specific activity
		SharedPreferences settingsActivity = getPreferences(MODE_PRIVATE); 
		if(settingsActivity.contains(PREFERENCE_STRING_NAME) == false) {
			SharedPreferences.Editor prefEditor = settingsActivity.edit();
			prefEditor.putString(PREFERENCE_STRING_NAME, this.getLocalClassName()); 
			prefEditor.putLong("SomeLong", Long.MIN_VALUE);
			prefEditor.commit();
		}
		super.onCreate(savedInstanceState);
	}

	@Override
	Class<?> GetTargetClass() {
		// Where the "Go to other activity" action will send us
		return SimplePreferencesActivity.class;
	}
}