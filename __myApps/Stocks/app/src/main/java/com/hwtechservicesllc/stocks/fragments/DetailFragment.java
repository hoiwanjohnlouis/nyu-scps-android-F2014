package com.hwtechservicesllc.stocks.fragments;

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

import com.hwtechservicesllc.stocks.R;
import com.hwtechservicesllc.stocks.database.Database;
import com.hwtechservicesllc.stocks.database.DatabaseConnector;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {

    // the fragment bundle key 1: ARG_ITEM_STRING
    private static final String ARG_ROW_ID = FragmentConstants.ROW_ID;

    // the fragment bundle data 1: VALUE DATA
    private long mRowId;

    //
    // for callback methods implemented by caller/invoker, usually MainActivity
    //
    private OnFragmentInteractionListener mListener;


    /****************************************************************
     *
     * Start of non-template variables
     *
     ****************************************************************/

    // for logging purposes
    private final String DEBUG_TAG = this.getClass().getSimpleName();

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
    private TextView lastTradeDateTextView;
    private TextView lastTradeTimeTextView;
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
    int lastTradeDateIndex;
    int lastTradeTimeIndex;
    int insertDateTimeIndex;
    int modifyDateTimeIndex;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DetailFragment.
     */
    //
    // Standard function provided by fragment template
    //
    public static DetailFragment newInstance() {
        Log.i("DetailFragment", "in newInstance()");
        DetailFragment fragment = new DetailFragment();
        return fragment;
    }


    //
    // Standard function provided by fragment template
    //
    public DetailFragment() {
        Log.i(DEBUG_TAG, "in Constructor");
        // Required empty public constructor
    }


    //
    // Standard function provided by fragment template
    //
    @Override
    public void onAttach(Activity activity) {
        Log.i(DEBUG_TAG, "in onAttach()");
        super.onAttach(activity);
        try {
            //
            // for callback methods implemented by caller/invoker, usually MainActivity
            //
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    } // end method onAttach

    //
    // Standard function provided by fragment template
    //
    // handle deletes
    public void onButtonPressed() {
        Log.i(DEBUG_TAG, "in onButtonPressed()");
        if (mListener != null) {
            mListener.onDetailFragmentSymbolDeleted();
        }
    }

    // handle updates
    public void onButtonPressed(Bundle detailFragmentInterfaceBundle) {
        Log.i(DEBUG_TAG, "in onButtonPressed(Bundle detailFragmentInterfaceBundle)");
        if (mListener != null) {
            mListener.onDetailFragmentSymbolSelected(detailFragmentInterfaceBundle);
        }
    }

    //
    // Standard function provided by fragment template
    //
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(DEBUG_TAG, "in onCreate()");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRowId = getArguments().getLong(ARG_ROW_ID);
        }
    }

    //
    // Standard function provided by fragment template
    //  with changes for application
    //
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(DEBUG_TAG, "in onCreateView()");
        super.onCreateView(inflater, container, savedInstanceState);
        // save fragment across config changes
        setRetainInstance(true);

        // if DetailFragment is being restored, get saved row ID
        if (savedInstanceState != null) {
            mRowId = savedInstanceState.getLong(ARG_ROW_ID);
        }
        else {
            // get Bundle of arguments then extract the contact's row ID
            Bundle arguments = getArguments();
            if (arguments != null) {
                mRowId = arguments.getLong(ARG_ROW_ID);
            }
        }

        // inflate DetailFragment's layout
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
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
        lastTradeDateTextView = (TextView) view.findViewById(R.id.lastTradeDateTextView);
        lastTradeTimeTextView = (TextView) view.findViewById(R.id.lastTradeTimeTextView);
        insertDateTimeTextView = (TextView) view.findViewById(R.id.insert_date_time_text_view);
        modifyDateTimeTextView = (TextView) view.findViewById(R.id.modify_date_time_text_view);

        return view;
    } // end method onCreateView

    //
    // display this fragment's menu items
    //
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.i(DEBUG_TAG, "in onCreateOptionsMenu()");
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_detail_menu, menu);
    } // end method onCreateOptionsMenu()

    //
    // handle menu item selections
    //
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(DEBUG_TAG, "in onOptionsItemSelected()");
        Bundle arguments = new Bundle();
        switch (item.getItemId())
        {
            case R.id.action_edit:
                // create Bundle containing contact data to edit
                arguments.putLong(ARG_ROW_ID, mRowId);
                arguments.putCharSequence(Database.Portfolio.TICKER_SYMBOL, symbolTextView.getText());
                // pass Bundle to listener for processing
                onButtonPressed(arguments);
                return true;

            case R.id.action_delete:
                // create Bundle containing contact data to edit
                arguments.putLong(ARG_ROW_ID, mRowId);
                arguments.putCharSequence(Database.Portfolio.TICKER_SYMBOL, symbolTextView.getText());
                confirmDelete.setArguments(arguments);
                confirmDelete.show(getFragmentManager(), "confirm delete");
                return true;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    } // end method onOptionsItemSelected

    //
    // Standard function provided by fragment template
    //
    @Override
    public void onDetach() {
        Log.i(DEBUG_TAG, "in onDetach()");
        super.onDetach();
        //
        // clean up callback methods implemented by caller/invoker, usually MainActivity
        //
        mListener = null;
    } // end method onDetach

    //
    // called when the Fragment resumes
    //
    @Override
    public void onResume() {
        Log.i(DEBUG_TAG, "in onResume()");
        super.onResume();
        // load contact at rowID
        new LoadItemAsyncTask().execute(mRowId);
    } // end method onResume()

    //
    // save currently displayed contact's row ID
    //
    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i(DEBUG_TAG, "in onSaveInstanceState()");
        super.onSaveInstanceState(outState);
        outState.putLong(ARG_ROW_ID, mRowId);
    } // end method onSaveInstanceState()


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
    // Standard function provided by fragment template
    //
    public interface OnFragmentInteractionListener {

        // called when a item/symbol is deleted, so display can be refresh if needed
        public void onDetailFragmentSymbolDeleted();

        // called to pass Bundle of item/symbol's info for editing
        public void onDetailFragmentSymbolSelected(Bundle arguments);

    } // end interface OnFragmentInteractionListener

    /****************************************************************
     *
     * Start of non-template functions
     *
     ****************************************************************/

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
            Log.i(DEBUG_TAG, "in LoadItemAsyncTask.doInBackground()");
            databaseConnector.open();

            return databaseConnector.getOneItem(params[0]);
        } // end method doInBackground()


        //
        // use the Cursor returned from the doInBackground method
        //
        @Override
        protected void onPostExecute(Cursor result) {
            Log.i(DEBUG_TAG, "in LoadItemAsyncTask.onPostExecute()");
            super.onPostExecute(result);
            // move to the first item
            result.moveToFirst();

            // get the column index for each data item
            symbolIndex = result.getColumnIndex(Database.Portfolio.TICKER_SYMBOL);
            openingPriceIndex = result.getColumnIndex(Database.Portfolio.OPENING_PRICE);
            previousClosingPriceIndex = result.getColumnIndex(Database.Portfolio.PREVIOUS_CLOSING_PRICE);
            bidPriceIndex = result.getColumnIndex(Database.Portfolio.BID_PRICE);
            bidSizeIndex = result.getColumnIndex(Database.Portfolio.BID_SIZE);
            askPriceIndex = result.getColumnIndex(Database.Portfolio.ASK_PRICE);
            askSizeIndex = result.getColumnIndex(Database.Portfolio.ASK_SIZE);
            lastTradePriceIndex = result.getColumnIndex(Database.Portfolio.LAST_TRADE_PRICE);
            lastTradeQuantityIndex = result.getColumnIndex(Database.Portfolio.LAST_TRADE_QUANTITY);
            lastTradeDateIndex = result.getColumnIndex(Database.Portfolio.LAST_TRADE_DATE);
            lastTradeTimeIndex = result.getColumnIndex(Database.Portfolio.LAST_TRADE_TIME);
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
            lastTradeDateTextView.setText(result.getString(lastTradeDateIndex));
            lastTradeTimeTextView.setText(result.getString(lastTradeTimeIndex));
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
                    Log.i(DEBUG_TAG, "in confirmDelete.onCreateDialog()");
                    // create a new AlertDialog Builder
                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(getActivity());

                    builder.setTitle(R.string.fragment_detail_delete_title);
                    builder.setMessage(R.string.fragment_detail_delete_message);

                    // provide an OK button that simply dismisses the dialog
                    builder.setPositiveButton(R.string.fragment_detail_delete_button_delete,
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
                                                    Log.i(DEBUG_TAG, "in confirmDelete.setPositiveButton.doInBackground()");
                                                    databaseConnector.deleteOneItem(params[0], "some symbol");
                                                    return null;
                                                }

                                                @Override
                                                protected void onPostExecute(Object result) {
                                                    Log.i(DEBUG_TAG, "in confirmDelete.setPositiveButton.onPostExecute()");
                                                    // notify callback that a symbol has been deleted
                                                    onButtonPressed();
                                                }
                                            }; // end new AsyncTask

                                    // execute the AsyncTask to delete contact at rowID
                                    deleteTask.execute(new Long[] { mRowId });
                                } // end method onClick
                            } // end anonymous inner class
                    ); // end call to method setPositiveButton

                    // do nothing if cancel action
                    builder.setNegativeButton(R.string.fragment_detail_delete_button_cancel, null);

                    // return the AlertDialog
                    return builder.create();
                }
            }; // end DialogFragment anonymous inner class

}
