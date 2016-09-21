package com.hoiwanlouis.mystockportfolio.database;

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

import com.hoiwanlouis.mystockportfolio.fields.Gui2Database;

public class DatabaseConnector {

    private final String DEBUG_TAG = this.getClass().getSimpleName();

    // for interacting with the database
    protected SQLiteDatabase sqLiteDatabase;

    // creates the database
    protected StockOpenHelper stockOpenHelper;

    // public constructor for DatabaseConnector
    public DatabaseConnector(Context context) {
        Log.i(DEBUG_TAG, "in DatabaseConnector()");
        stockOpenHelper = new StockOpenHelper(
                context,
                DatabaseColumns.DATABASE_NAME,
                null,   // cursor factory
                DatabaseColumns.DATABASE_VERSION);
    }


    // openForRead : getReadableDatabase()
    public void openForRead() throws SQLException {
        Log.i(DEBUG_TAG, "in openForRead()");
        if(stockOpenHelper != null) {
            sqLiteDatabase = stockOpenHelper.getReadableDatabase();
        }
    }


    // openForUpdate : getWritableDatabase()
    public void openForUpdate() throws SQLException {
        Log.i(DEBUG_TAG, "in openForUpdate()");
        if(stockOpenHelper != null) {
            sqLiteDatabase = stockOpenHelper.getWritableDatabase();
        }
    }


    // **************************************************************
    // we are done, so close the database connection
    // **************************************************************
    public void close() {
        Log.i(DEBUG_TAG, "in close()");

        // close the database connection
        if (sqLiteDatabase != null) {
            sqLiteDatabase.close();
        }

        // todo: may not be necessary to close the stockOpenHelper. if problems remove this close.
        if(stockOpenHelper != null) {
            stockOpenHelper.close();
        }
    }


    // **************************************************************
    // inserts a new item/symbol row into the database
    // **************************************************************
    public long addOneStock(final String tickerSymbol) {
        Log.i(DEBUG_TAG, "in addOneStock()");

        // load data and apply update
        ContentValues cv = new ContentValues();
        cv.put(DatabaseColumns.Portfolio.SYMBOL, tickerSymbol);

        openForUpdate();
        long rowID = sqLiteDatabase.insert(
                DatabaseColumns.Portfolio.PORTFOLIO_TABLE_NAME,
                DatabaseColumns.Portfolio.SYMBOL,
                cv
        );
        close();
        Log.i(DEBUG_TAG, "sqLiteDatabase.insert: Symbol[" + tickerSymbol + "], portfolio rowID[" + rowID + "]");

        return rowID;
    }


    // **************************************************************
    // updates an existing item/symbol row in the database
    // **************************************************************
    public void updateOneStock(
            final long id,                        // 1 used to access the portfolio object from database
            final String tickerSymbol,
            final String openingPrice,
            final String closingPrice,
            final String bidPrice,                // 5
            final String bidSize,
            final String askPrice,
            final String askSize,
            final String tradePrice,
            final String tradeQuantity,           // 10
            final String tradeDateTime
    ) {
        Log.i(DEBUG_TAG, "in updateOneStock()");

        // load data and apply update
        ContentValues cv = new ContentValues();
        cv.put(DatabaseColumns.Portfolio._ID, id);                              // 1
        cv.put(DatabaseColumns.Portfolio.SYMBOL, tickerSymbol);
        cv.put(DatabaseColumns.Portfolio.OPENING_PRICE, openingPrice);
        cv.put(DatabaseColumns.Portfolio.PREVIOUS_CLOSING_PRICE, closingPrice);
        cv.put(DatabaseColumns.Portfolio.BID_PRICE, bidPrice);                  // 5
        cv.put(DatabaseColumns.Portfolio.BID_SIZE, bidSize);
        cv.put(DatabaseColumns.Portfolio.ASK_PRICE, askPrice);
        cv.put(DatabaseColumns.Portfolio.ASK_SIZE, askSize);
        cv.put(DatabaseColumns.Portfolio.LAST_TRADE_PRICE, tradePrice);
        cv.put(DatabaseColumns.Portfolio.LAST_TRADE_QUANTITY, tradeQuantity);   // 10
        cv.put(DatabaseColumns.Portfolio.LAST_TRADE_DATETIME, tradeDateTime);

        openForUpdate();
        long rowID = sqLiteDatabase.update(
                DatabaseColumns.Portfolio.PORTFOLIO_TABLE_NAME,
                cv,
                "_id=" + id,
                null
        );
        close();
        Log.i(DEBUG_TAG, "sqLiteDatabase.update: Symbol[" + tickerSymbol + "], portfolio rowID[" + rowID + "], id[" + id + "]");

    }


    // **************************************************************
    // return a Cursor with all items/symbols in the database
    // **************************************************************
    public Cursor getAllStocks() {
        Log.i(DEBUG_TAG, "in getAllStocks()");

        return sqLiteDatabase.query(
                DatabaseColumns.Portfolio.PORTFOLIO_TABLE_NAME,
                Gui2Database.asColumnsToReturn,
                null, null, null, null,
                DatabaseColumns.Portfolio.DEFAULT_SORT_ORDER);
    }


    // **************************************************************
    // return a Cursor containing specified item/symbol's information
    // **************************************************************
    public Cursor getOneStockUsingId(final long id) {
        Log.i(DEBUG_TAG, "in getOneStockUsingId()");

        return sqLiteDatabase.query(
                DatabaseColumns.Portfolio.PORTFOLIO_TABLE_NAME,
                Gui2Database.asColumnsToReturn,
                "_id=" + id, null, null, null,
                null);
    }


    // **************************************************************
    // todo: remove this function if it's no longer needed
    // return a Cursor containing specified item/symbol's information
    // **************************************************************
    public Cursor getOneStockUsingString(final String searchSymbol) {
        Log.i(DEBUG_TAG, "in getOneStockUsingString()");

        final StringBuilder tmpSelection = new StringBuilder();
        tmpSelection.append(DatabaseColumns.Portfolio.SYMBOL);
        tmpSelection.append("=");
        tmpSelection.append(searchSymbol);
        return sqLiteDatabase.query(
                DatabaseColumns.Portfolio.PORTFOLIO_TABLE_NAME,
                Gui2Database.asColumnsToReturn,
                tmpSelection.toString(), null, null, null,
                null);
    }


    // **************************************************************
    // delete the specified ticker symbol using database _ID field
    // **************************************************************
    public void deleteOneStock(final Long id) {
        Log.i(DEBUG_TAG, "deleteOneStock()");

        final String deleteArgs[] = { id.toString() };
        // todo: should add triggers to handle multiple tables
        // openForUpdate the database
        openForUpdate();
        final long rc = sqLiteDatabase.delete(
                DatabaseColumns.Portfolio.PORTFOLIO_TABLE_NAME,
                DatabaseColumns.Portfolio._ID + "=?",
                deleteArgs
        );
        close();  // always free resources when done. this is not batch processing.
        Log.i(DEBUG_TAG, "deleteOneStock _ID[" + id.toString() + "], delete_code[" + rc + "] Ends");
    }

}
