package com.hwtechservicesllc.stocks;

/*
    Copyright (c) 2015  HW Tech Services, Inc., LLC
 
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

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

import com.hwtechservicesllc.stocks.fragments.AddFragment;
import com.hwtechservicesllc.stocks.fragments.CopyrightFragment;
import com.hwtechservicesllc.stocks.fragments.FragmentConstants;
import com.hwtechservicesllc.stocks.fragments.HeadingFragment;
import com.hwtechservicesllc.stocks.fragments.SettingsFragment;

public class SettingsActivity extends Activity {

    // for logging purposes
    private final String DEBUG_TAG = this.getClass().getSimpleName();


    // use FragmentManager to display SettingsFragment
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(DEBUG_TAG, "in onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

}

