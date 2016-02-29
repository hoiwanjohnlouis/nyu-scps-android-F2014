package com.hoiwanlouis.mystockportfolio;

/*
    Copyright (c) 2014, 2015  Hoi Wan Louis

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

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.hoiwanlouis.mystockportfolio.database.Database;
import com.hoiwanlouis.mystockportfolio.database.PortfolioActivity;

import java.util.Locale;


public class PrototypeActivity extends PortfolioActivity
{

    // for logging purposes
    private final String DEBUG_TAG = this.getClass().getSimpleName();


    private final String asColumnsToReturn[] = {
            Database.Portfolio.PORTFOLIO_TABLE_NAME + "." + Database.Portfolio._ID,
            Database.Portfolio.PORTFOLIO_TABLE_NAME + "." + Database.Portfolio.SYMBOL,
            Database.Portfolio.PORTFOLIO_TABLE_NAME + "." + Database.Portfolio.OPENING_PRICE,
            Database.Portfolio.PORTFOLIO_TABLE_NAME + "." + Database.Portfolio.PREVIOUS_CLOSING_PRICE,
            Database.Portfolio.PORTFOLIO_TABLE_NAME + "." + Database.Portfolio.BID_PRICE,
            Database.Portfolio.PORTFOLIO_TABLE_NAME + "." + Database.Portfolio.ASK_PRICE,
            Database.Portfolio.PORTFOLIO_TABLE_NAME + "." + Database.Portfolio.LAST_TRADE_PRICE,
            Database.Portfolio.PORTFOLIO_TABLE_NAME + "." + Database.Portfolio.LAST_TRADE_TIME
    };

    private final String fromDBColumns[] =  {
            Database.Portfolio.SYMBOL,
            Database.Portfolio.OPENING_PRICE,
            Database.Portfolio.PREVIOUS_CLOSING_PRICE,
            Database.Portfolio.BID_PRICE,
            Database.Portfolio.ASK_PRICE,
            Database.Portfolio.LAST_TRADE_PRICE,
            Database.Portfolio.LAST_TRADE_TIME
    };

    private final int toRIds[] = {
            R.id.TextView_symbol,
            R.id.TextView_opening_price,
            R.id.TextView_previous_closing_price,
            R.id.TextView_bid_price,
            R.id.TextView_ask_price,
            R.id.TextView_last_trade_price,
            R.id.TextView_last_trade_time
    };

    private ListAdapter adapter = null;
    private ListView listView = null;

    private ImageButton saveButton;

    private int iNumberOfSymbols;   // keep track of how records were extracted from SQLite.


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.i(DEBUG_TAG, "onCreate Starting...");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.portfolio_entry);

        // Fill portfolio list from database
        fillFromDatabase();

        // setup Save Button listener, etc
        saveButton = (ImageButton) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(saveButtonOnClickListener);

        Log.i(DEBUG_TAG, "onCreate Ends");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        Log.i(DEBUG_TAG, "onCreateOptionsMenu Starting...");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.i(DEBUG_TAG, "onCreateOptionsMenu Ends");
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Log.i(DEBUG_TAG, "onOptionsItemSelected Starting...");

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        boolean selected;
        int id = item.getItemId();
        if (id == R.id.main_activity_settings_id)
        {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            selected = true;
        }
        else
        if (id == R.id.main_activity_help_id) {
            Builder builder = new Builder(this);
            builder.setTitle(R.string.main_activity_help_title);
            builder.setMessage(R.string.main_activity_help_data);
            AlertDialog dialog=builder.create();
            dialog.show();
            selected = true;
        }
        else
        if (id == R.id.main_activity_about_id) {
            Builder builder = new Builder(this);
            builder.setTitle(R.string.main_activity_about_title);
            builder.setMessage(R.string.main_activity_about_data);
            AlertDialog dialog=builder.create();
            dialog.show();
            selected = true;
        }
        else {
            selected = super.onOptionsItemSelected(item);
        }

        Log.i(DEBUG_TAG, "onOptionsItemSelected Ends: " + selected);
        return selected;
    }


    //
    // using a separate subroutine instead of inline to remove clutter.
    //
    View.OnClickListener saveButtonOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            final EditText tickerSymbol = (EditText) findViewById(R.id.inputSymbolEditText);
            long rowID;

            // Save new records
            mDB.beginTransaction();
            try {

                // check if species type exists already
                rowID = 0;
                String tmpTicker = tickerSymbol.getText().toString()
                        .toUpperCase(Locale.getDefault());

                // Build SQL Query to check if the (ticker) symbol already exists?
                // buttonQB is an abbreviation for saveButtonQueryBuilder
                SQLiteQueryBuilder buttonQB = new SQLiteQueryBuilder();
                buttonQB.setTables(Database.Portfolio.PORTFOLIO_TABLE_NAME);
                buttonQB.appendWhere(Database.Portfolio.SYMBOL
                        + "='"
                        + tmpTicker
                        + "'");

                // run the query since it's all ready to go
                Cursor c = buttonQB.query(
                        mDB,
                        null,
                        null, null, null, null,
                        null,   // sort order
                        null);

                // did we find a record in symbol table?
                if (c.getCount() == 0) {
                    //
                    // add the new symbol to db
                    //
                    ContentValues cv = new ContentValues();
                    cv.put(Database.Portfolio.SYMBOL,
                            tmpTicker);
                    cv.put(Database.Portfolio.OPENING_PRICE,
                            0.11);
                    cv.put(Database.Portfolio.PREVIOUS_CLOSING_PRICE,
                            0.22);
                    cv.put(Database.Portfolio.BID_PRICE,
                            0.35);
                    cv.put(Database.Portfolio.BID_SIZE,
                            0.35);
                    cv.put(Database.Portfolio.ASK_PRICE,
                            0.37);
                    cv.put(Database.Portfolio.LAST_TRADE_PRICE,
                            0.362);
                    cv.put(Database.Portfolio.LAST_TRADE_QUANTITY,
                            -1);
                    cv.put(Database.Portfolio.LAST_TRADE_DATE,
                            getString(R.string.default_trade_date));
                    cv.put(Database.Portfolio.LAST_TRADE_TIME,
                            "00:00:00");

                    rowID = mDB.insert(Database.Portfolio.PORTFOLIO_TABLE_NAME,
                            Database.Portfolio.SYMBOL,
                            cv);

                    Log.i(DEBUG_TAG, "mDB.insert: Symbol[" + tmpTicker + "], rowID[" + rowID + "]");

                } else {
                    // existing symbol
                    c.moveToFirst();
                    rowID = c.getLong(c.getColumnIndex(Database.Portfolio._ID));
                    Log.i(DEBUG_TAG, "buttonQB.query: Existing symbol[" + tmpTicker + "], rowID[" + rowID + "].");
                }

                c.close();

                // Update display with new record
                fillFromDatabase();

                mDB.setTransactionSuccessful();
            } finally {
                mDB.endTransaction();
            }

            // reset form
            tickerSymbol.setText(null);
        }

    };


    //
    // from PortfolioListActivity
    //
    public void fillFromDatabase() {
        Log.i(DEBUG_TAG, "fillFromDatabase Starts...");

        SQLiteQueryBuilder portfolioQB = new SQLiteQueryBuilder();

        // SQL Query
        portfolioQB.setTables(Database.Portfolio.PORTFOLIO_TABLE_NAME);

        // refresh cursor with current data
        mCursor = portfolioQB.query(
                mDB,
                asColumnsToReturn,
                null, null, null, null,
                Database.Portfolio.DEFAULT_SORT_ORDER,
                null);

        mCursor.moveToFirst();

        // make some toasties
        iNumberOfSymbols = mCursor.getCount();
        if (iNumberOfSymbols >= 0) {

            Toast.makeText(this.getApplicationContext(), "portfolioQB.query: " + iNumberOfSymbols + " records",Toast.LENGTH_SHORT)
                    .show();

            // Use an adapter to bind the data to a ListView where the data will be displayed

            // refresh the adapter with updated cursor information
            adapter = new SimpleCursorAdapter(
                    getApplicationContext(),
                    R.layout.app_item,
                    mCursor,
                    fromDBColumns,
                    toRIds,
                    1);

            // refresh the ListView to the adapter
            listView = (ListView) findViewById(R.id.symbolList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(symbolListListener);
            listView.setOnItemLongClickListener(symbolListLongListener);

        } else  {
            // Alert user that the query failed
            Toast.makeText(this.getApplicationContext(), "portfolioQB.query: failed", Toast.LENGTH_SHORT).show();
        }

        Log.i(DEBUG_TAG, "fillFromDatabase, iNumberOfSymbols[" + iNumberOfSymbols + "] Ends");
    }


    // normal click will display company info, currently doing deletes
    AdapterView.OnItemClickListener symbolListListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Log.i(DEBUG_TAG, "in symbolListListener");

            // Check for delete button
            final long deleteSymbolId = id;

            LinearLayout item = (LinearLayout) view;
            TextView nameView = (TextView) item.findViewById(R.id.TextView_symbol);
            final String name = nameView.getText().toString();

            // Use an Alert dialog to confirm delete operation
            new AlertDialog.Builder(PrototypeActivity.this)
                    .setMessage("Delete Symbol Record for " + name + "?")
                    .setPositiveButton("Delete",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                    // Delete the Symbol
                                    deleteSymbol(deleteSymbolId, name);

                                    // a symbol was deleted, refresh the data in our cursor, therefore our List
                                    fillFromDatabase();

                                }
                            }).show();

        }

    };


    // long click will allow delete symbol
    // todo: add edit option
    AdapterView.OnItemLongClickListener symbolListLongListener = new AdapterView.OnItemLongClickListener() {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

            Log.i(DEBUG_TAG, "in symbolListLongListener");

            // Check for delete button
            final long deleteSymbolId = id;

            LinearLayout item = (LinearLayout) view;
            TextView nameView = (TextView) item.findViewById(R.id.TextView_symbol);
            final String name = nameView.getText().toString();

            // Use an Alert dialog to confirm delete operation
            new AlertDialog.Builder(PrototypeActivity.this)
                    .setMessage("Delete Symbol Record for " + name + "?")
                    .setPositiveButton("Delete",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                    // Delete the Symbol
                                    deleteSymbol(deleteSymbolId, name);

                                    // a symbol was deleted, refresh the data in our cursor, therefore our List
                                    fillFromDatabase();

                                }
                            }).show();
            return false;
        }

    };


    //
    public void deleteSymbol(Long id, String symbol) {
        Log.i(DEBUG_TAG, "deleteSymbol[" + symbol + "], _ID[" + id.toString() + "] Starts...");
        String deleteArgs[] = { id.toString() };

        // todo: should add triggers to handle multiple tables

        long rc = mDB.delete(
                Database.Portfolio.PORTFOLIO_TABLE_NAME,
                Database.Portfolio._ID + "=?",
                deleteArgs
        );

        Log.i(DEBUG_TAG, "deleteSymbol[" + symbol + "], _ID[" + id.toString() + "], delete_code[" + rc + "] Ends");
    }

}
