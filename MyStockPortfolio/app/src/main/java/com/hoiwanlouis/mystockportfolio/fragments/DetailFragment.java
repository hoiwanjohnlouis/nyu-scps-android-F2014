package com.hoiwanlouis.mystockportfolio.fragments;

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
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
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

import com.hoiwanlouis.mystockportfolio.PrimoActivity;
import com.hoiwanlouis.mystockportfolio.R;
import com.hoiwanlouis.mystockportfolio.database.Database;
import com.hoiwanlouis.mystockportfolio.database.DatabaseConnector;


/**
 **
 */
public class DetailFragment extends Fragment {

    // for logging purposes
    private final String DEBUG_TAG = this.getClass().getSimpleName();

    //
    // for callback methods implemented by caller/invoker, usually PrototypeActivity
    //
    private DetailFragmentListener listener;


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


    // set DetailFragmentListener when fragment attached
    @Override
    public void onAttach(Activity activity) {
        Log.i(DEBUG_TAG, "in onAttach()");
        super.onAttach(activity);

        //
        // for callback methods implemented by caller/invoker, usually PrototypeActivity
        //
        listener = (DetailFragmentListener) activity;

    } // end method onAttach


    // remove DetailFragmentListener when fragment detached
    @Override
    public void onDetach() {
        Log.i(DEBUG_TAG, "in onDetach()");
        super.onDetach();

        //
        // clean up callback methods implemented by caller/invoker, usually PrototypeActivity
        //
        listener = null;

    } // end method onDetach


    //
    // called when DetailFragmentListener's view needs to be created
    //
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(DEBUG_TAG, "in onCreateView()");
        super.onCreateView(inflater, container, savedInstanceState);
        // save fragment across config changes
        setRetainInstance(true);

        // if DetailFragment is being restored, get saved row ID
        if (savedInstanceState != null) {
            rowID = savedInstanceState.getLong(PrimoActivity.ROW_ID);
        }
        else {
            // get Bundle of arguments then extract the contact's row ID
            Bundle arguments = getArguments();

            if (arguments != null) {
                rowID = arguments.getLong(PrimoActivity.ROW_ID);
            }
        }

        // inflate DetailFragment's layout
        View view = inflater.inflate(R.layout.fragment_details, container, false);
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
    // called when the DetailFragment resumes
    //
    @Override
    public void onResume() {
        Log.i(DEBUG_TAG, "in onResume()");
        super.onResume();
        // load contact at rowID
        new LoadItemAsyncTask().execute(rowID);
    } // end method onResume()

    //
    // save currently displayed contact's row ID
    //
    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i(DEBUG_TAG, "in onSaveInstanceState()");
        super.onSaveInstanceState(outState);
        outState.putLong(PrimoActivity.ROW_ID, rowID);
    } // end method onSaveInstanceState()


    //
    // display this fragment's menu items
    //
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.i(DEBUG_TAG, "in onCreateOptionsMenu()");
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_details_menu, menu);
    } // end method onCreateOptionsMenu()


    //
    // handle menu item selections
    //
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(DEBUG_TAG, "in onOptionsItemSelected()");
        switch (item.getItemId())
        {
            case R.id.action_edit:
                // create Bundle containing contact data to edit
                Bundle arguments = new Bundle();
                arguments.putLong(PrimoActivity.ROW_ID, rowID);

                arguments.putCharSequence(Database.Portfolio.SYMBOL, symbolTextView.getText());
                arguments.putCharSequence(Database.Portfolio.OPENING_PRICE, openingPriceTextView.getText());
                arguments.putCharSequence(Database.Portfolio.PREVIOUS_CLOSING_PRICE, previousClosingPriceTextView.getText());
                arguments.putCharSequence(Database.Portfolio.BID_PRICE, bidPriceTextView.getText());
                arguments.putCharSequence(Database.Portfolio.BID_SIZE, bidSizeTextView.getText());
                arguments.putCharSequence(Database.Portfolio.ASK_PRICE, askPriceTextView.getText());
                arguments.putCharSequence(Database.Portfolio.ASK_SIZE, askPriceTextView.getText());
                arguments.putCharSequence(Database.Portfolio.LAST_TRADE_PRICE, lastTradePriceTextView.getText());
                arguments.putCharSequence(Database.Portfolio.LAST_TRADE_QUANTITY, lastTradeQuantityTextView.getText());
                arguments.putCharSequence(Database.Portfolio.LAST_TRADE_DATETIME, lastTradeDateTimeTextView.getText());
                arguments.putCharSequence(Database.Portfolio.INSERT_DATETIME, insertDateTimeTextView.getText());
                arguments.putCharSequence(Database.Portfolio.MODIFY_DATETIME, modifyDateTimeTextView.getText());

                // pass Bundle to listener for processing
                listener.onDFLEditSymbolSelected(arguments);
                return true;

            case R.id.action_delete:
                deleteContact();
                return true;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    } // end method onOptionsItemSelected


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
    // callback methods implemented by caller/invoker, usually PrototypeActivity
    //
    public interface DetailFragmentListener {

        // called when a item/symbol is deleted
        void onDFLDeleteSymbolCompleted();

        // called to pass Bundle of item/symbol's info for editing
        void onDFLEditSymbolSelected(Bundle arguments);

    }


    //
    // delete a contact
    //
    private void deleteContact() {
        Log.i(DEBUG_TAG, "in deleteContact()");
        // use FragmentManager to display the confirmDelete DialogFragment
        confirmDelete.show(getFragmentManager(), "confirm delete");
    } // end method deleteContact


    //
    // *****************************************************
    // performs database query outside GUI thread
    // *****************************************************
    private class LoadItemAsyncTask extends AsyncTask<Long, Object, Cursor>
    {
        DatabaseConnector databaseConnector = new DatabaseConnector(getActivity());

        //
        // open database & get Cursor representing specified contact's data
        //
        @Override
        protected Cursor doInBackground(Long... params) {
            Log.i(DEBUG_TAG, "in doInBackground()");
            databaseConnector.open();

            return databaseConnector.getTickerSymbolUsingId(params[0]);
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
            symbolIndex = result.getColumnIndex(Database.Portfolio.SYMBOL);
            openingPriceIndex = result.getColumnIndex(Database.Portfolio.OPENING_PRICE);
            previousClosingPriceIndex = result.getColumnIndex(Database.Portfolio.PREVIOUS_CLOSING_PRICE);
            bidPriceIndex = result.getColumnIndex(Database.Portfolio.BID_PRICE);
            bidSizeIndex = result.getColumnIndex(Database.Portfolio.BID_SIZE);
            askPriceIndex = result.getColumnIndex(Database.Portfolio.ASK_PRICE);
            askSizeIndex = result.getColumnIndex(Database.Portfolio.ASK_SIZE);
            lastTradePriceIndex = result.getColumnIndex(Database.Portfolio.LAST_TRADE_PRICE);
            lastTradeQuantityIndex = result.getColumnIndex(Database.Portfolio.LAST_TRADE_QUANTITY);
            lastTradeDateTimeIndex = result.getColumnIndex(Database.Portfolio.LAST_TRADE_DATETIME);
            insertDateTimeIndex = result.getColumnIndex(Database.Portfolio.INSERT_DATETIME);
            modifyDateTimeIndex = result.getColumnIndex(Database.Portfolio.MODIFY_DATETIME);

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
    } // end class LoadItemAsyncTask


    //
    // DialogFragment to confirm deletion of contact
    //
    private DialogFragment confirmDelete =
            new DialogFragment() {
                // create an AlertDialog and return it
                @Override
                public Dialog onCreateDialog(Bundle bundle) {
                    Log.i(DEBUG_TAG, "in onCreateDialog()");
                    // create a new AlertDialog Builder
                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(getActivity());

                    builder.setTitle(R.string.fragment_delete_title);
                    builder.setMessage(R.string.fragment_delete_message);

                    // provide an OK button that simply dismisses the dialog
                    builder.setPositiveButton(R.string.fragment_delete_button_delete,
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int button) {
                                    final DatabaseConnector databaseConnector =
                                            new DatabaseConnector(getActivity());

                                    // AsyncTask deletes contact and notifies listener
                                    AsyncTask<Long, Object, Object> deleteTask =
                                            new AsyncTask<Long, Object, Object>() {
                                                @Override
                                                protected Object doInBackground(Long... params) {
                                                    Log.i(DEBUG_TAG, "in doInBackground()");
                                                    databaseConnector.deleteTickerSymbol(params[0], "some symbol");
                                                    return null;
                                                }

                                                @Override
                                                protected void onPostExecute(Object result) {
                                                    Log.i(DEBUG_TAG, "in onPostExecute()");
                                                    listener.onDFLDeleteSymbolCompleted();
                                                }
                                            }; // end new AsyncTask

                                    // execute the AsyncTask to delete contact at rowID
                                    deleteTask.execute(new Long[] { rowID });
                                } // end method onClick
                            } // end anonymous inner class
                    ); // end call to method setPositiveButton

                    // do nothing if cancel action
                    builder.setNegativeButton(R.string.fragment_delete_button_cancel, null);

                    // return the AlertDialog
                    return builder.create();
                }
            }; // end DialogFragment anonymous inner class


}
