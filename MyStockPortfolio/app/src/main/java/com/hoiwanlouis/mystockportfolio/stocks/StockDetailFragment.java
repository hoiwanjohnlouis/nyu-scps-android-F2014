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
package com.hoiwanlouis.mystockportfolio.stocks;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
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
import android.widget.Toast;

import com.hoiwanlouis.mystockportfolio.MainActivity;
import com.hoiwanlouis.mystockportfolio.R;
import com.hoiwanlouis.mystockportfolio.database.DatabaseColumns;
import com.hoiwanlouis.mystockportfolio.database.DatabaseConnector;
import com.hoiwanlouis.mystockportfolio.fields.Gui2Database;

import com.hoiwanlouis.mystockportfolio.fields.DateTimestamp;

public class StockDetailFragment extends Fragment {

    public interface StockDetailFragmentListener {
        // called when a contact is deleted
        void onDeleteStockComplete(Bundle arguments);

        // called to pass Bundle of contact's info for editing
        void onEditStockRequest(Bundle arguments);
    }

    private final String DEBUG_TAG = this.getClass().getSimpleName();
    private StockDetailFragmentListener listener;
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
        Log.i(DEBUG_TAG, "in StockDetailFragment()");
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment AddEditStockFragment.
     */
    public static StockDetailFragment newInstance() {
        return new StockDetailFragment();
    }


    // set StockDetailFragmentListener when fragment attached
    @Override
    public void onAttach(Context context) {
        Log.i(DEBUG_TAG, "in onAttach()");
        super.onAttach(context);
        // init callback to interface implementation
        listener = (StockDetailFragmentListener) context;
    }

    // remove StockDetailFragmentListener when fragment detached
    @Override
    public void onDetach() {
        Log.i(DEBUG_TAG, "in onDetach()");
        super.onDetach();
        listener = null;
    }

    // called when the StockDetailFragment resumes
    @Override
    public void onResume() {
        Log.i(DEBUG_TAG, "in onResume()");
        super.onResume();
        new LoadOneStockDetailAsyncTask().execute(rowID);
    } // end method onResume()

    @Override
    public void onStart() {
        Log.i(DEBUG_TAG, "in onStart()");
        super.onStart();
    }

    // clean up
    @Override
    public void onStop() {
        Log.i(DEBUG_TAG, "in onStop()");
        super.onStop();
    }

    // save currently displayed contact's row ID
    @Override
    public void onSaveInstanceState(final Bundle outState) {
        Log.i(DEBUG_TAG, "in onSaveInstanceState()");
        outState.putLong(Gui2Database.BUNDLE_KEY, rowID);
        super.onSaveInstanceState(outState);
    }

    // display this fragment's menu items
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
        switch (item.getItemId())
        {
            case R.id.action_edit:
                Toast.makeText(getActivity(),"Detail Edit Stock is not supported!", Toast.LENGTH_SHORT).show();
                editStock();
                return true;
            case R.id.action_delete:
                deleteStock();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    } // end method onOptionsItemSelected

    private void editStock() {
        Log.i(DEBUG_TAG, "in editStock()");
        // create Bundle containing contact data to edit
        Bundle arguments = new Bundle();
        arguments.putLong(Gui2Database.BUNDLE_KEY, rowID);

        arguments.putLong(MainActivity.ROW_ID, rowID);
        arguments.putCharSequence("name", nameTextView.getText());
        arguments.putCharSequence("phone", phoneTextView.getText());
        arguments.putCharSequence("email", emailTextView.getText());
        arguments.putCharSequence("street", streetTextView.getText());
        arguments.putCharSequence("city", cityTextView.getText());
        arguments.putCharSequence("state", stateTextView.getText());
        arguments.putCharSequence("zip", zipTextView.getText());
        // pass Bundle to listener for processing
        listener.onEditStockRequest(arguments);

    } // end method deleteContact

    private void deleteStock() {
        Log.i(DEBUG_TAG, "in deleteDelete()");
        // create Bundle containing contact data to edit
        Bundle arguments = new Bundle();
        arguments.putLong(Gui2Database.BUNDLE_KEY, rowID);
        // use FragmentManager to display the confirmDelete DialogFragment
        confirmDelete.show(getFragmentManager(), "confirm delete");
    } // end method deleteContact

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
                    builder.setMessage(R.string.fragment_delete_message + symbolTextView.getText().toString());

                    // provide an OK button that simply dismisses the dialog
                    builder.setPositiveButton(R.string.fragment_delete_button_positive,
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
                                                    databaseConnector.deleteOneStock(params[0]);
                                                    return null;
                                                }

                                                @Override
                                                protected void onPostExecute(Object result) {
                                                    Log.i(DEBUG_TAG, "in onPostExecute()");
                                                    onDeleteStockCompleteCallback();
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

    public void onDeleteStockCompleteCallback() {
        Log.i(DEBUG_TAG, "in onAddStockCompleteCallback()");
        Bundle arguments = new Bundle();
        arguments.putLong(Gui2Database.BUNDLE_KEY, rowID);
        listener.onDeleteStockComplete(arguments);
    }

    // called when StockDetailFragmentListener's view needs to be created
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(DEBUG_TAG, "in onCreateView()");
        // super.onCreateView(inflater, container, savedInstanceState);
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
        Log.i(DEBUG_TAG, "in bindTextViewsToLayout()");
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
        } // end method onPostExecute
    }

    //
    private void loadTextViewsFromCursor(Cursor result) {
        Log.i(DEBUG_TAG, "in loadTextViewsFromCursor()");
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

}
