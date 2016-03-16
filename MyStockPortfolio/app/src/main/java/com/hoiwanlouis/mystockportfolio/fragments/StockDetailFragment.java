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

import android.content.Context;
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

import com.hoiwanlouis.mystockportfolio.MainActivity;
import com.hoiwanlouis.mystockportfolio.R;
import com.hoiwanlouis.mystockportfolio.database.DatabaseColumns;
import com.hoiwanlouis.mystockportfolio.database.DatabaseConnector;

/**
 **
 */
public class StockDetailFragment extends Fragment {

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    //
    // callback methods implemented by caller/invoker, usually Prototype
    //
    public interface StockDetailFragmentListener {

        // called when fragment is done
        void onSDFLCompleted();

    }

    // for logging purposes
    private final String DEBUG_TAG = this.getClass().getSimpleName();
    // for callback methods implemented by caller/invoker
    private StockDetailFragmentListener stockDetailFragmentListener;
    //
    private long rowID = -1; // selected contact's rowID

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

    // resource Ids for readonly TextViews, must match detail layout
    int symbolIndex;
    int openingPriceIndex;
    int previousClosingPriceIndex;
    int bidPriceIndex;
    int bidSizeIndex;
    int askPriceIndex;
    int askSizeIndex;
    int lastTradePriceIndex;
    int lastTradeQuantityIndex;
    int lastTradeDateTimeIndex;
    int insertDateTimeIndex;
    int modifyDateTimeIndex;


    public StockDetailFragment() {
        Log.i(DEBUG_TAG, "in Required empty public constructor()");
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddStockFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StockDetailFragment newInstance() {
        StockDetailFragment fragment = new StockDetailFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
        return fragment;
    }

    // set StockDetailFragmentListener when fragment attached
    @Override
    public void onAttach(Context context) {
        Log.i(DEBUG_TAG, "in onAttach()");
        super.onAttach(context);
        // init callback to interface implementation
        stockDetailFragmentListener = (StockDetailFragmentListener) context;
    } // end method onAttach


    // remove StockDetailFragmentListener when fragment detached
    @Override
    public void onDetach() {
        Log.i(DEBUG_TAG, "in onDetach()");
        super.onDetach();
        // clean up callback methods implemented by caller/invoker
        stockDetailFragmentListener = null;
    } // end method onDetach


    //
    // called when StockDetailFragmentListener's view needs to be created
    //
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(DEBUG_TAG, "in onCreateView()");
        super.onCreateView(inflater, container, savedInstanceState);
        // save fragment across config changes
        setRetainInstance(true);

        // if StockDetailFragment is being restored, get saved row ID
        if (savedInstanceState != null) {
            rowID = savedInstanceState.getLong(MainActivity.ROW_ID);
        }
        else {
            // else extract the contact's row ID from the arguments bundle
            Bundle arguments = getArguments();
            if (arguments != null) {
                rowID = arguments.getLong(MainActivity.ROW_ID);
            }
        }

        // inflate StockDetailFragment's layout
        View view = inflater.inflate(R.layout.fragment_stock_detail, container, false);
        // this fragment has menu items to display
        setHasOptionsMenu(true);

        // get the TextView data, must match the detail layout
        symbolTextView = (TextView) view.findViewById(R.id.symbolTextView);
        openingPriceTextView = (TextView) view.findViewById(R.id.openingPriceTextView);
        previousClosingPriceTextView = (TextView) view.findViewById(R.id.previousClosingPriceTextView);
        bidPriceTextView = (TextView) view.findViewById(R.id.bidPriceTextView);
        bidSizeTextView = (TextView) view.findViewById(R.id.bidSizeTextView);
        askPriceTextView = (TextView) view.findViewById(R.id.askPriceTextView);
        askSizeTextView = (TextView) view.findViewById(R.id.askSizeTextView);
        lastTradePriceTextView = (TextView) view.findViewById(R.id.lastTradePriceTextView);
        lastTradeQuantityTextView = (TextView) view.findViewById(R.id.lastTradeQuantityTextView);
        lastTradeDateTimeTextView = (TextView) view.findViewById(R.id.lastTradeTimeTextView); // ONLY TIME IS DISPLAYED
        insertDateTimeTextView = (TextView) view.findViewById(R.id.insertDateTimeTextView);
        modifyDateTimeTextView = (TextView) view.findViewById(R.id.modifyDateTimeTextView);

        return view;
    } // end method onCreateView

    //
    // called when the StockDetailFragment resumes
    //
    @Override
    public void onResume() {
        Log.i(DEBUG_TAG, "in onResume()");
        super.onResume();
        // load contact at rowID
        new LoadStockDetailAsyncTask().execute(rowID);
    } // end method onResume()


    //
    // save currently displayed contact's row ID
    //
    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i(DEBUG_TAG, "in onSaveInstanceState()");
        super.onSaveInstanceState(outState);
        outState.putLong(MainActivity.ROW_ID, rowID);
    } // end method onSaveInstanceState()


    // display this fragment's menu items
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.i(DEBUG_TAG, "in onCreateOptionsMenu()");
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_details_menu, menu);
    } // end method onCreateOptionsMenu()


    // handle menu item selections
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(DEBUG_TAG, "in onOptionsItemSelected()");
        // create Bundle containing contact data to edit
        Bundle arguments = new Bundle();
        switch (item.getItemId())
        {
            case R.id.action_edit:
                stockDetailFragmentListener.onSDFLCompleted();
                return true;

            case R.id.action_delete:
                stockDetailFragmentListener.onSDFLCompleted();
                return true;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    } // end method onOptionsItemSelected


    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    //
    // *****************************************************
    // performs database query outside GUI thread
    // *****************************************************
    private class LoadStockDetailAsyncTask extends AsyncTask<Long, Object, Cursor>
    {
        DatabaseConnector databaseConnector = new DatabaseConnector(getActivity());

        //
        // openForUpdate database & get Cursor representing specified contact's data
        //
        @Override
        protected Cursor doInBackground(Long... params) {
            Log.i(DEBUG_TAG, "in doInBackground()");
            databaseConnector.openForRead();
            return databaseConnector.getOneStockUsingId(params[0]);
        } // end method doInBackground()


        //
        // use the Cursor returned from the doInBackground method
        //
        @Override
        protected void onPostExecute(Cursor result) {
            Log.i(DEBUG_TAG, "in onPostExecute()");
            super.onPostExecute(result);
            // move to the first item
            result.moveToFirst();

            // get the column index for each data item
            symbolIndex = result.getColumnIndex(DatabaseColumns.Portfolio.SYMBOL);
            openingPriceIndex = result.getColumnIndex(DatabaseColumns.Portfolio.OPENING_PRICE);
            previousClosingPriceIndex = result.getColumnIndex(DatabaseColumns.Portfolio.PREVIOUS_CLOSING_PRICE);
            bidPriceIndex = result.getColumnIndex(DatabaseColumns.Portfolio.BID_PRICE);
            bidSizeIndex = result.getColumnIndex(DatabaseColumns.Portfolio.BID_SIZE);
            askPriceIndex = result.getColumnIndex(DatabaseColumns.Portfolio.ASK_PRICE);
            askSizeIndex = result.getColumnIndex(DatabaseColumns.Portfolio.ASK_SIZE);
            lastTradePriceIndex = result.getColumnIndex(DatabaseColumns.Portfolio.LAST_TRADE_PRICE);
            lastTradeQuantityIndex = result.getColumnIndex(DatabaseColumns.Portfolio.LAST_TRADE_QUANTITY);
            lastTradeDateTimeIndex = result.getColumnIndex(DatabaseColumns.Portfolio.LAST_TRADE_DATETIME);
            insertDateTimeIndex = result.getColumnIndex(DatabaseColumns.Portfolio.INSERT_DATETIME);
            modifyDateTimeIndex = result.getColumnIndex(DatabaseColumns.Portfolio.MODIFY_DATETIME);

            // fill TextViews with the retrieved data
            symbolTextView.setText(result.getString(symbolIndex));
            openingPriceTextView.setText(result.getString(openingPriceIndex));
            previousClosingPriceTextView.setText(result.getString(previousClosingPriceIndex));
            bidPriceTextView.setText(result.getString(bidPriceIndex));
            bidSizeTextView.setText(result.getString(bidSizeIndex));
            askPriceTextView.setText(result.getString(askPriceIndex));
            askSizeTextView.setText(result.getString(askSizeIndex));
            lastTradePriceTextView.setText(result.getString(lastTradePriceIndex));
            lastTradeQuantityTextView.setText(result.getString(lastTradeQuantityIndex));
            lastTradeDateTimeTextView.setText(result.getString(lastTradeDateTimeIndex));
            insertDateTimeTextView.setText(result.getString(insertDateTimeIndex));
            modifyDateTimeTextView.setText(result.getString(modifyDateTimeIndex));

            // close the result cursor
            result.close();
            // close database connection
            databaseConnector.close();
        } // end method onPostExecute
    } // end class LoadStockDetailAsyncTask

}
