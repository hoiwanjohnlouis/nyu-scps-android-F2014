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
package com.hwtechservices.s99e02equityyostocks.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


/***************************************************************************
 * Program Synopsis
 * <p>
 * Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * <p>
 * Change History
 * ------Who----- ---When--- ---------------------What----------------------
 * H. Melville    1851.01.31 Wooden whales, or whales cut in profile out of 
 * the small dark slabs of the noble South Sea war-wood, are frequently met
 * with in the forecastles of American whalers.
 *
 ***************************************************************************/
public class DatabaseHelper extends SQLiteOpenHelper {

    // for logging purposes
    private final String DEBUG_TAG = this.getClass().getSimpleName();


    public DatabaseHelper(Context context, String database, CursorFactory factory, int version) {
        super(context, database, factory, version);
        Log.v(DEBUG_TAG, "in Constructor");
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.v(DEBUG_TAG, "creating tables...");

        Log.v(DEBUG_TAG, "creating tables done.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v(DEBUG_TAG, "in onUpgrade");
    }

    // Housekeeping here. Implement how "move" your application data during a downgrade of schema versions
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.v(DEBUG_TAG, "in onDowngrade");
    }

    //
    @Override
    public void onOpen(SQLiteDatabase db) {
        Log.v(DEBUG_TAG, "in onOpen");
        super.onOpen(db);
    }

    //
    @Override
    public SQLiteDatabase getReadableDatabase() {
        Log.v(DEBUG_TAG, "in getReadableDatabase");
        return super.getReadableDatabase();
    }

    //
    @Override
    public SQLiteDatabase getWritableDatabase() {
        Log.v(DEBUG_TAG, "in getWritableDatabase");
        return super.getWritableDatabase();
    }

    //
    @Override
    public void close() {
        Log.v(DEBUG_TAG, "in onOpen");
        super.close();
    }

}
