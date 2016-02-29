package com.hoiwanlouis.mystockportfolio.database;

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

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;


public class PortfolioActivity extends Activity {

    // for logging purposes
    private final String DEBUG_TAG = this.getClass().getSimpleName();

    protected DatabaseHelper mDatabase = null;
    protected Cursor mCursor = null;
    protected SQLiteDatabase mDB = null;


    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(DEBUG_TAG, "in onCreate");
        super.onCreate(savedInstanceState);
        mDatabase = new DatabaseHelper(
                this.getApplicationContext(),
                Database.DATABASE_NAME,
                null,   // cursor factory
                Database.DATABASE_VERSION);
        mDB = mDatabase.getWritableDatabase();
    }


    //
    @Override
    protected void onDestroy() {
        Log.v(DEBUG_TAG, "in onDestroy");
        super.onDestroy();

        // close cursor
        if(mDB != null)
        {
            mDB.close();
        }

        // close the database
        if(mDatabase != null)
        {
            mDatabase.close();
        }
    }


    // open a database connection
    public void open() {
        Log.v(DEBUG_TAG, "in open");

        // open the database for update
        if(mDatabase != null) {
            mDB = mDatabase.getWritableDatabase();
        }

    }


    // close a database connection
    public void close() {
        Log.v(DEBUG_TAG, "in close");

        // close cursor
        if(mDB != null)
        {
            mDB.close();
        }

        // close the database
        if(mDatabase != null)
        {
            mDatabase.close();
        }

    }

}
