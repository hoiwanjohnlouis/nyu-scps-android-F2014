package com.example.hoiwanlouis.myflagquiz;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends Activity {
    private final String DEBUG_TAG = this.getClass().getSimpleName();

    // Keys for reading data from SharedPreferences
    public static final String CHOICES = "pref_numberOfChoices";
    public static final String REGIONS = "pref_regionsToInclude";

    private boolean phoneDevice = true;         // used to force portrait mode
    private boolean preferencesChanged = true;  //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(DEBUG_TAG, "in onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // set default values in the apps SharedPreferences
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        // register listener for SharedPreferences changes
        PreferenceManager.getDefaultSharedPreferences(this).
                registerOnSharedPreferenceChangeListener(preferenceChangeListener);

        // determine screen size
        int screenSize =
                getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;

        // if the device is a tablet, then set phoneDevice to false
        if (screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE ||
            screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            phoneDevice = false;
        }

        // if phoneDevice, only allow PORTRAIT mode
        if (phoneDevice) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        return;
    }

    @Override
    protected void onStart() {
        Log.v(DEBUG_TAG, "in onOptionsItemSelected()");
        super.onStart();

        if (preferencesChanged) {
            // defaults are currently set,
            // change them to reflect new values
            QuizFragment quizFragment =
                    (QuizFragment) getFragmentManager().findFragmentById(R.id.quizFragment);

            quizFragment.updateGuessRows(
                    PreferenceManager.getDefaultSharedPreferences(this)
            );

            quizFragment.updateRegions(
                    PreferenceManager.getDefaultSharedPreferences(this)
            );

            quizFragment.resetQuiz();
            preferencesChanged = false;
        }

        return;
    }


    // show menu if app is running on a phone or portrait-oriented tablet.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.v(DEBUG_TAG, "in onCreateOptionsMenu()");
        boolean isCreateMenu = false;

        // get default display object
        Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        // object to store screen size
        Point screenSize = new Point();
        // store screen size into screenSize
        display.getRealSize(screenSize);

        // display the apps menu only in portrait orientation
        if (screenSize.x < screenSize.y) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.main, menu);
            isCreateMenu = true;
        }
        else {
            isCreateMenu = false;
        }

        return isCreateMenu;
    }

    // display SettingsActivity when running on a phone;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.v(DEBUG_TAG, "in onOptionsItemSelected()");
        boolean isItemSelected = false;

        Intent preferencesIntent = new Intent(this, SettingsActivity.class);
        startActivity(preferencesIntent);
        isItemSelected = super.onOptionsItemSelected(item);

        return isItemSelected;
    }

    private OnSharedPreferenceChangeListener preferenceChangeListener =
            new OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            Log.v(DEBUG_TAG, "in preferenceChangeListener()");

            // user changed app settings
            preferencesChanged = true;

            //
            QuizFragment quizFragment =
                    (QuizFragment) getFragmentManager().findFragmentById(R.id.quizFragment);

            // # of choices to display has changed
            if (key.equals(CHOICES)) {
                quizFragment.updateGuessRows(sharedPreferences);
                quizFragment.resetQuiz();
            }

            // regions to include has changed
            if (key.equals(REGIONS)) {
                Set<String> regions =
                        sharedPreferences.getStringSet(REGIONS, null);

                if (regions != null && regions.size() > 0)
                {
                    quizFragment.updateRegions(sharedPreferences);
                    quizFragment.resetQuiz();
                }
                else // must select one region--set North America as default
                {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    regions.add(
                            getResources().getString(R.string.default_region));
                    editor.putStringSet(REGIONS, regions);
                    editor.commit();
                    Toast.makeText(MainActivity.this,
                            R.string.default_region_message,
                            Toast.LENGTH_SHORT).show();
                }
            }

            //
            Toast.makeText(MainActivity.this, R.string.restarting_quiz, Toast.LENGTH_SHORT).show();

            return;
        }   // end of onSharedPreferenceChanged
    };  // end of preferenceChangeListener, don't forget the ";"

}   // end of MainActivity
