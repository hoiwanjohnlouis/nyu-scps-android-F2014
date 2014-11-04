package com.example.hoiwanlouis.s06e51_hoiboggle;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class WelcomeActivity extends Activity {

    private final String DEBUG_TAG = this.getClass().getSimpleName();

    // Keys for reading grid data from SharedPreferences
    private String BOGGLE_GRID_SHARED_PREFERENCES;
    private String DEFAULT_GRID_SIZE;
    private SharedPreferences saveGridSize;
    private String boggleGridSize;

    // trigger switch to denote a grid change
    protected boolean preferencesChanged = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(DEBUG_TAG,"in onCreate()");
        super.onCreate(savedInstanceState);

        // init vars from strings.xml resources
        initStringResources();

        // set default values in the apps SharedPreferences
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        // register listener for SharedPreferences changes
        PreferenceManager.getDefaultSharedPreferences(this).
                registerOnSharedPreferenceChangeListener(preferenceChangeListener);

        setContentView(R.layout.activity_welcome);

        return;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.v(DEBUG_TAG,"in onCreateOptionsMenu");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.v(DEBUG_TAG,"in onOptionsItemSelected");
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        boolean isItemSelected = false;

        Intent preferencesIntent = new Intent(this, SettingsActivity.class);
        startActivity(preferencesIntent);
        isItemSelected = super.onOptionsItemSelected(item);

        return isItemSelected;
    }

    public void goToGameSetup(View v) {
        Log.v(DEBUG_TAG,"in goToGameSetup");

        Intent intent = new Intent(this, BoardActivity.class);
        startActivity(intent);

    }

    //
    public void initStringResources() {
        Log.v(DEBUG_TAG,"initStringResources()");

        BOGGLE_GRID_SHARED_PREFERENCES = getString(R.string.boggle_grid_shared_preferences);
        DEFAULT_GRID_SIZE = getString(R.string.default_number_of_boggle_dice);
        Log.i(DEBUG_TAG,"BOGGLE_GRID_SHARED_PREFERENCES:" + BOGGLE_GRID_SHARED_PREFERENCES);
        Log.i(DEBUG_TAG,"DEFAULT_GRID_SIZE:" + DEFAULT_GRID_SIZE);

        return;
    }

    //
    private void connectSharedPreferences() {
        Log.v(DEBUG_TAG,"connectSharedPreferences()");
        saveGridSize = getSharedPreferences(BOGGLE_GRID_SHARED_PREFERENCES, 0);

        // for debugging purposes
        SharedPreferences.Editor newEdit = saveGridSize.edit();
        newEdit.clear();
        newEdit.commit();
    }


    // retrieve the grid size from shared preferences
    private String getBoggleGridSize() {
        Log.v(DEBUG_TAG,"getBoggleGridSize()");
        boggleGridSize = saveGridSize.getString(BOGGLE_GRID_SHARED_PREFERENCES, DEFAULT_GRID_SIZE);
        Log.i(DEBUG_TAG,"Using grid size of:" + boggleGridSize);
        return boggleGridSize;
    }

    // we have a new high score
    public void setBoggleGridSize(String newGridSize) {
        Log.v(DEBUG_TAG,"getBoggleGridSize()");
        SharedPreferences.Editor saveGridSizeEditor = saveGridSize.edit();
        saveGridSizeEditor.putString(BOGGLE_GRID_SHARED_PREFERENCES, newGridSize);
        saveGridSizeEditor.commit();
        Log.i(DEBUG_TAG,"New Grid Size is:" + newGridSize);
    }

    // anonymous listener for settings
    private OnSharedPreferenceChangeListener preferenceChangeListener =
            new OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                    Log.v(DEBUG_TAG, "in preferenceChangeListener()");

                    // user changed app settings
                    preferencesChanged = true;

                    // # of choices to display has changed
                    Log.i(DEBUG_TAG, "key:" + key.toString());
                    if (key.equals(BOGGLE_GRID_SHARED_PREFERENCES)) {
                        String choices = sharedPreferences.getString(BOGGLE_GRID_SHARED_PREFERENCES, null);
                        Log.i(DEBUG_TAG, "Boggle Grid changed to " + choices);
                        setBoggleGridSize(choices);
                    }

                    //
                    Toast.makeText(WelcomeActivity.this, R.string.reconfiguring_boggle, Toast.LENGTH_LONG).show();

                    return;
                }   // end of onSharedPreferenceChanged
            };  // end of preferenceChangeListener, don't forget the ";"

}
