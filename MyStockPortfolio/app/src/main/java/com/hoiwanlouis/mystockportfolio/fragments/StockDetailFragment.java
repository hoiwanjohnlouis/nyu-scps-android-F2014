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

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hoiwanlouis.mystockportfolio.R;
import com.hoiwanlouis.mystockportfolio.database.DatabaseColumns;
import com.hoiwanlouis.mystockportfolio.database.DatabaseConnector;
import com.hoiwanlouis.mystockportfolio.fields.Gui2Database;

import com.hoiwanlouis.mystockportfolio.fields.DateTimestamp;

public class StockDetailFragment extends Fragment {

    public interface StockDetailFragmentListener {
        // called when fragment is done
        void onStockDetailComplete();
    }

    private final String DEBUG_TAG = this.getClass().getSimpleName();
    private StockDetailFragmentListener stockDetailFragmentListener;
    private long rowID = 0; // selected contact's rowID

    // define the readonly TextView for display, must match the detail layout
    private TextView symbolTextView;
    private TextView openingPriceTextView;
    private TextView previousClosingPriceTextView;
    private TextView bidPriceTextView;
    private TextView bidSizeTextView;
    private TextView askPriceTextView;
    private TextView askSizeTextView;
    private TextView lastTradePriceTextView;
    private TextView lastTradeQuantityTextView;
    private TextView lastTradeDateTimeTextView;
    private TextView insertDateTimeTextView;
    private TextView modifyDateTimeTextView;

    public StockDetailFragment() {
        Log.i(DEBUG_TAG, "in StockDetailFragment(), required empty public constructor");
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment AddStockFragment.
     */
    public static StockDetailFragment newInstance() {
        StockDetailFragment fragment = new StockDetailFragment();
        return fragment;
    }


    // set StockDetailFragmentListener when fragment attached
    @Override
    public void onAttach(Activity context) {
        Log.i(DEBUG_TAG, "in onAttach()");
        super.onAttach(context);
        // init callback to interface implementation
        stockDetailFragmentListener = (StockDetailFragmentListener) context;
    }

    // remove StockDetailFragmentListener when fragment detached
    @Override
    public void onDetach() {
        Log.i(DEBUG_TAG, "in onDetach()");
        super.onDetach();
        stockDetailFragmentListener = null;
    }

    // called when the StockDetailFragment resumes
    @Override
    public void onResume() {
        Log.i(DEBUG_TAG, "in onResume()");
        super.onResume();
        new LoadOneStockDetailAsyncTask().execute(rowID);
    } // end method onResume()

    //
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(DEBUG_TAG, "in onCreate()");
        super.onCreate(savedInstanceState);
    }

    //
    // display this fragment's menu items
    //
    @Override
    public void onCreateOptionsMenu(final Menu menu, final MenuInflater inflater) {
        Log.i(DEBUG_TAG, "in onCreateOptionsMenu()");
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_stock_detail_menu, menu);
    } // end method onCreateOptionsMenu()

    // handle menu item selections
    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        Log.i(DEBUG_TAG, "in onOptionsItemSelected()");
        Bundle arguments = new Bundle();
        switch (item.getItemId())
        {
            case R.id.action_edit:
                // create Bundle containing contact data to edit
                arguments.putLong(Gui2Database.BUNDLE_KEY, rowID);
                // pass Bundle to stockDetailFragmentListener for processing
                //stockDetailFragmentListener.onEditContact(arguments);
                Toast.makeText(getActivity(),"Detail Edit Stock is not supported!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_delete:
                // create Bundle containing contact data to edit
                arguments.putLong(Gui2Database.BUNDLE_KEY, rowID);
                Toast.makeText(getActivity(),"Detail Delete Stock is not supported at this time!", Toast.LENGTH_SHORT).show();
                //deleteContact();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    } // end method onOptionsItemSelected

    // called when StockDetailFragmentListener's view needs to be created
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(DEBUG_TAG, "in onCreateView()");
        super.onCreateView(inflater, container, savedInstanceState);
        setRetainInstance(true);        // save fragment across config changes
        setHasOptionsMenu(true);        // this fragment has menu items to display

        // if StockDetailFragment is being restored, get saved row ID
        if (savedInstanceState != null) {
            rowID = savedInstanceState.getLong(Gui2Database.BUNDLE_KEY);
        }
        else {
            // else extract the contact's row ID from the arguments bundle
            Bundle arguments = getArguments();
            if (arguments != null) {
                rowID = arguments.getLong(Gui2Database.BUNDLE_KEY);
            }
        }

        // inflate StockDetailFragment's layout and bind the data: must match the detail layout
        View v = inflater.inflate(R.layout.fragment_stock_detail, container, false);
        bindTextViewsToLayout(v);

        return v;
    }

    private void bindTextViewsToLayout(View v) {
        symbolTextView = (TextView) v.findViewById(R.id.symbolTextView);
        openingPriceTextView = (TextView) v.findViewById(R.id.openingPriceTextView);
        previousClosingPriceTextView = (TextView) v.findViewById(R.id.previousClosingPriceTextView);
        bidPriceTextView = (TextView) v.findViewById(R.id.bidPriceTextView);
        bidSizeTextView = (TextView) v.findViewById(R.id.bidSizeTextView);
        askPriceTextView = (TextView) v.findViewById(R.id.askPriceTextView);
        askSizeTextView = (TextView) v.findViewById(R.id.askSizeTextView);
        lastTradePriceTextView = (TextView) v.findViewById(R.id.lastTradePriceTextView);
        lastTradeQuantityTextView = (TextView) v.findViewById(R.id.lastTradeQuantityTextView);
        lastTradeDateTimeTextView = (TextView) v.findViewById(R.id.lastTradeDateTimeTextView); // ONLY TIME IS DISPLAYED
        insertDateTimeTextView = (TextView) v.findViewById(R.id.insertDateTimeTextView);
        modifyDateTimeTextView = (TextView) v.findViewById(R.id.modifyDateTimeTextView);
    }

    // save currently displayed contact's row ID
    @Override
    public void onSaveInstanceState(final Bundle outState) {
        Log.i(DEBUG_TAG, "in onSaveInstanceState()");
        super.onSaveInstanceState(outState);
        outState.putLong(Gui2Database.BUNDLE_KEY, rowID);
    }

    // *****************************************************
    // performs database query outside GUI thread
    // *****************************************************
    private class LoadOneStockDetailAsyncTask extends AsyncTask<Long, Object, Cursor>
    {
        DatabaseConnector databaseConnector = new DatabaseConnector(getActivity());

        // openForUpdate database & get Cursor representing specified contact's data
        @Override
        protected Cursor doInBackground(Long... params) {
            Log.i(DEBUG_TAG, "in doInBackground()");
            databaseConnector.openForRead();
            return databaseConnector.getOneStockUsingId(params[0]);
        }

        // use the Cursor returned from the doInBackground method
        @Override
        protected void onPostExecute(Cursor result) {
            Log.i(DEBUG_TAG, "in onPostExecute()");
            super.onPostExecute(result);
            result.moveToFirst();
            loadTextViewsFromCursor(result);
            result.close();
            databaseConnector.close();
            onButtonPressed();
        } // end method onPostExecute
    }

    //
    private void loadTextViewsFromCursor(Cursor result) {
        // fetch the column indices for each data item to shorten code lines
        int symbolIndex = result.getColumnIndex(DatabaseColumns.Portfolio.SYMBOL);
        int openingPriceIndex = result.getColumnIndex(DatabaseColumns.Portfolio.OPENING_PRICE);
        int previousClosingPriceIndex = result.getColumnIndex(DatabaseColumns.Portfolio.PREVIOUS_CLOSING_PRICE);
        int bidPriceIndex = result.getColumnIndex(DatabaseColumns.Portfolio.BID_PRICE);
        int bidSizeIndex = result.getColumnIndex(DatabaseColumns.Portfolio.BID_SIZE);
        int askPriceIndex = result.getColumnIndex(DatabaseColumns.Portfolio.ASK_PRICE);
        int askSizeIndex = result.getColumnIndex(DatabaseColumns.Portfolio.ASK_SIZE);
        int lastTradePriceIndex = result.getColumnIndex(DatabaseColumns.Portfolio.LAST_TRADE_PRICE);
        int lastTradeQuantityIndex = result.getColumnIndex(DatabaseColumns.Portfolio.LAST_TRADE_QUANTITY);
        int lastTradeDateTimeIndex = result.getColumnIndex(DatabaseColumns.Portfolio.LAST_TRADE_DATETIME);
        int insertDateTimeIndex = result.getColumnIndex(DatabaseColumns.Portfolio.INSERT_DATETIME);
        int modifyDateTimeIndex = result.getColumnIndex(DatabaseColumns.Portfolio.MODIFY_DATETIME);

        // fetch the data and load into the TextViews
        symbolTextView.setText(result.getString(symbolIndex));
        openingPriceTextView.setText(result.getString(openingPriceIndex));
        previousClosingPriceTextView.setText(result.getString(previousClosingPriceIndex));
        bidPriceTextView.setText(result.getString(bidPriceIndex));
        bidSizeTextView.setText(result.getString(bidSizeIndex));
        askPriceTextView.setText(result.getString(askPriceIndex));
        askSizeTextView.setText(result.getString(askSizeIndex));
        lastTradePriceTextView.setText(result.getString(lastTradePriceIndex));
        lastTradeQuantityTextView.setText(result.getString(lastTradeQuantityIndex));
        lastTradeDateTimeTextView.setText(DateTimestamp.extractTimestampSegment(result.getString(lastTradeDateTimeIndex)));
        insertDateTimeTextView.setText(DateTimestamp.extractTimestampSegment(result.getString(insertDateTimeIndex)));
        modifyDateTimeTextView.setText(DateTimestamp.extractTimestampSegment(result.getString(modifyDateTimeIndex)));
    }

    // callback to main to redisplay screen;
    private void onButtonPressed() {
        Log.i(DEBUG_TAG, "in onAddStockCompleteCallback()");
        if (stockDetailFragmentListener != null) {
            stockDetailFragmentListener.onStockDetailComplete();
        }
    }


}
