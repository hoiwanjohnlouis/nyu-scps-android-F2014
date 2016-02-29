package com.hwtechservicesllc.stocks.database;

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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class DatabaseConnector {

    private final String DEBUG_TAG = this.getClass().getSimpleName();

    // for interacting with the database
    private SQLiteDatabase mSQL;

    // creates the database
    private DatabaseHelper mDBHelper;


    // public constructor for DatabaseConnector
    public DatabaseConnector(Context context) {
        Log.i(DEBUG_TAG, "in DatabaseConnector()");

        // create a new DatabaseHelper
        mDBHelper = new DatabaseHelper(
                context,
                Database.DATABASE_NAME,
                null,   // cursor factory
                Database.DATABASE_VERSION);

    } // end constructor DatabaseConnector


    //
    // open the database connection
    //
    public void open() throws SQLException {
        Log.i(DEBUG_TAG, "in open()");

        // create or open a mDB for reading/writing
        mSQL = mDBHelper.getWritableDatabase();

    } // end method open()


    // **************************************************************
    // close the database connection
    // **************************************************************
    public void close() {
        Log.i(DEBUG_TAG, "in close()");

        if (mSQL != null) {
            // close the database connection
            mSQL.close();
        }

    } // end method close()


    // **************************************************************
    // inserts a new item/symbol row into the database
    // **************************************************************
    public long insertItem(
                    String tickerSymbol,            // 1
                    String openingPrice,
                    String closingPrice,
                    String bidPrice,
                    String bidSize,                 // 5
                    String askPrice,
                    String askSize,
                    String tradePrice,
                    String tradeQuantity,
                    String tradeDate,               // 10
                    String tradeTime
                ) {
        Log.i(DEBUG_TAG, "in insertItem()");

        // create a content object
        ContentValues cv = new ContentValues();
        cv.put(Database.Portfolio.TICKER_SYMBOL,                       tickerSymbol);  // 1
        cv.put(Database.Portfolio.OPENING_PRICE,                openingPrice);
        cv.put(Database.Portfolio.PREVIOUS_CLOSING_PRICE,       closingPrice);
        cv.put(Database.Portfolio.BID_PRICE,                    bidPrice);
        cv.put(Database.Portfolio.BID_SIZE,                     bidSize);       // 5
        cv.put(Database.Portfolio.ASK_PRICE,                    askPrice);
        cv.put(Database.Portfolio.ASK_SIZE,                     askSize);
        cv.put(Database.Portfolio.LAST_TRADE_PRICE,             tradePrice);
        cv.put(Database.Portfolio.LAST_TRADE_QUANTITY,          tradeQuantity);
        cv.put(Database.Portfolio.LAST_TRADE_DATE,              tradeDate);     // 10
        cv.put(Database.Portfolio.LAST_TRADE_TIME,              tradeTime);

        // open the database
        open();
        long rowID = mSQL.insert(Database.Portfolio.PORTFOLIO_TABLE_NAME, Database.Portfolio.TICKER_SYMBOL, cv);
        Log.i(DEBUG_TAG, "mSQL.insert: Symbol[" + tickerSymbol + "], portfolio rowID[" + rowID + "]");
        // close the database, always free resources when done. this is not batch processing.
        close();

        return rowID;
    } // end method insertItem


    // **************************************************************
    // updates an existing item/symbol row in the database
    // **************************************************************
    public void updateItem(
                    long id,                        // 1 used to access the portfolio object from database
                    String tickerSymbol,
                    String openingPrice,
                    String closingPrice,
                    String bidPrice,                // 5
                    String bidSize,
                    String askPrice,
                    String askSize,
                    String tradePrice,
                    String tradeQuantity,           // 10
                    String tradeDate,
                    String tradeTime
                ) {
        Log.i(DEBUG_TAG, "in updateItem()");

        // create a content object
        ContentValues cv = new ContentValues();
        cv.put(Database.Portfolio._ID,                          id);            // 1
        cv.put(Database.Portfolio.TICKER_SYMBOL,                tickerSymbol);
        cv.put(Database.Portfolio.OPENING_PRICE,                openingPrice);
        cv.put(Database.Portfolio.PREVIOUS_CLOSING_PRICE,       closingPrice);
        cv.put(Database.Portfolio.BID_PRICE,                    bidPrice);      // 5
        cv.put(Database.Portfolio.BID_SIZE,                     bidSize);
        cv.put(Database.Portfolio.ASK_PRICE,                    askPrice);
        cv.put(Database.Portfolio.ASK_SIZE,                     askSize);
        cv.put(Database.Portfolio.LAST_TRADE_PRICE,             tradePrice);
        cv.put(Database.Portfolio.LAST_TRADE_QUANTITY,          tradeQuantity); // 10
        cv.put(Database.Portfolio.LAST_TRADE_DATE,              tradeDate);
        cv.put(Database.Portfolio.LAST_TRADE_TIME,              tradeTime);

        // get the modify datetime
        Date dateTimeNow = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZ", Locale.ENGLISH);
        String modifyDateTime = simpleDateFormat.format(dateTimeNow);
        cv.put(Database.Portfolio.MODIFY_DATETIME,              modifyDateTime);

        open(); // open the database
        long rowID = mSQL.update(Database.Portfolio.PORTFOLIO_TABLE_NAME, cv, "_id=" + id, null);
        Log.i(DEBUG_TAG, "mSQL.update: Symbol[" + tickerSymbol + "], portfolio rowID[" + rowID + "], id[" + id + "]");
        close(); // close the database

    } // end method updateItem


    // **************************************************************
    // return a Cursor with all items/symbols in the database
    // **************************************************************
    public Cursor getAllItems() {
        Log.i(DEBUG_TAG, "in getAllItems()");

        return mSQL.query(
                Database.Portfolio.PORTFOLIO_TABLE_NAME,
                Database.Query.asColumnsToReturn,
                null, null, null, null,
                Database.Portfolio.DEFAULT_SORT_ORDER);
    } // end method getAllContacts


    // **************************************************************
    // return a Cursor containing specified item/symbol's information
    // **************************************************************
    public Cursor getOneItem(long id) {
        Log.i(DEBUG_TAG, "in getOneItem()");

        StringBuilder tmpSelection = new StringBuilder();
        tmpSelection.append("_id=");
        tmpSelection.append(id);
        return mSQL.query(
                Database.Portfolio.PORTFOLIO_TABLE_NAME,
                Database.Query.asColumnsToReturn,
                tmpSelection.toString(), null, null, null,
                null);
    } // end method getOneItem


    // **************************************************************
    // todo: remove this function if it's no longer needed
    // return a Cursor containing specified item/symbol's information
    // **************************************************************
    public Cursor getOneSymbol(String searchSymbol) {
        Log.i(DEBUG_TAG, "in getOneItemBySymbol()");

        StringBuilder tmpSelection = new StringBuilder();
        tmpSelection.append(Database.Portfolio.TICKER_SYMBOL);
        tmpSelection.append("=");
        tmpSelection.append(searchSymbol);
        return mSQL.query(
                Database.Portfolio.PORTFOLIO_TABLE_NAME,
                Database.Query.asColumnsToReturn,
                tmpSelection.toString(), null, null, null,
                null);
    } // end method getOneItemBySymbol


    // **************************************************************
    //  ported from PrototypeActivity
    // **************************************************************
    public void deleteOneItem(Long id, String symbol) {
        Log.i(DEBUG_TAG, "deleteOneItem[" + symbol + "], _ID[" + id.toString() + "] Starts...");
        String deleteArgs[] = { id.toString() };

        // todo: should add triggers to handle multiple tables

        StringBuilder tmpSelection = new StringBuilder();
        tmpSelection.append(Database.Portfolio._ID);
        tmpSelection.append("=?");
        long rc = mSQL.delete(
                Database.Portfolio.PORTFOLIO_TABLE_NAME,
                tmpSelection.toString(),
                deleteArgs
        );

        Log.i(DEBUG_TAG, "deleteOneItem[" + symbol + "], _ID[" + id.toString() + "], delete_code[" + rc + "] Ends");
    }


}
