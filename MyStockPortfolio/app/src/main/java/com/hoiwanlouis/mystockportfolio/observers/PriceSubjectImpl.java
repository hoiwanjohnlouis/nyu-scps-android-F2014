package com.hoiwanlouis.mystockportfolio.observers;

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

import android.util.Log;

import java.util.ArrayList;

public class PriceSubjectImpl implements Subject {

    // for logging purposes
    private final String DEBUG_TAG = this.getClass().getSimpleName();

    ArrayList<Observer> observers;


    public PriceSubjectImpl() {
        Log.v(DEBUG_TAG, "in PriceSubjectImpl");
        observers = new ArrayList<Observer>();
    }

    @Override
    public void register(Observer o) {
        Log.v(DEBUG_TAG, "in register");
        observers.add(o);
    }

    @Override
    public void unregister(Observer o) {
        Log.v(DEBUG_TAG, "in unregister");

        int observerIndex = observers.indexOf(o);
        if (observerIndex >= 0) {
            Log.i(DEBUG_TAG, "Observer #" + (observerIndex + 1) + " deleted");
            observers.remove(observerIndex);
        }
    }

    @Override
    public void notifyObserver() {
        // call update method for each Observer
        for (Observer observer : observers) {
            observer.update(0.1, 0.2, 0.3);
        }
    }
}
