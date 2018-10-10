/**
 *
 */
package com.hoiwanlouis.mystockportfolio;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class SettingsActivity extends Activity {
    private final String DEBUG_TAG = this.getClass().getSimpleName();

    // use FragmentManager to display SettingsFragment
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(DEBUG_TAG, "in onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_settings_fragment);
    }

}

/***************************************************************************
 * Program Synopsis
 * <p/>
 * Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * <p/>
 * Change History
 * ------Who----- ---When--- ---------------------What----------------------
 * H. Melville    1851.01.31 Wooden whales, or whales cut in profile out of
 * the small dark slabs of the noble South Sea war-wood, are frequently met
 * with in the forecastles of American whalers.
 ***************************************************************************/
