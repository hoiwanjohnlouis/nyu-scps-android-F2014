package com.hwtechservicesllc.stocks.fragments;

/*
    Copyright (c) 2014  Hoi Wan Louis

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/


import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.util.Log;

import com.hwtechservicesllc.stocks.R;

public class SettingsFragment extends PreferenceFragment {

    //
    // logging purposes
    //
    private final String DEBUG_TAG = this.getClass().getSimpleName();

    // creates preferences GUI from preferences.xml file in res/xml
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.v(DEBUG_TAG, "in onCreate()");
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

}   // end of SettingsFragment
