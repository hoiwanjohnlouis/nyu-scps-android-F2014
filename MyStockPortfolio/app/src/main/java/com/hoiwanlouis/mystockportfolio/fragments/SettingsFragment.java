/***************************************************************************
 * Copyright March, 2016 HW Tech Services, LLC
 * <p/>
 * Login   HW Tech Services, LLC
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ***************************************************************************/
package com.hoiwanlouis.mystockportfolio.fragments;

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

import com.hoiwanlouis.mystockportfolio.R;

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
    }

}   // end of SettingsFragment
