package com.hwtechservicesllc.stocks.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hwtechservicesllc.stocks.R;
import com.hwtechservicesllc.stocks.database.Database;
import com.hwtechservicesllc.stocks.database.DatabaseConnector;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UpdateFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UpdateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateFragment extends Fragment {

    // the fragment bundle key 1: ARG_ITEM_STRING
    private static final String ARG_ROW_ID = FragmentConstants.ROW_ID;

    // the fragment bundle data 1: VALUE DATA
    private long mRowId;

    //
    // callback interface/method
    //
    private OnFragmentInteractionListener mListener;

    /****************************************************************
     *
     * Start of non-template variables
     *
     ****************************************************************/

    //
    // logging purposes
    //
    private final String DEBUG_TAG = this.getClass().getSimpleName();

    // define the EditText for display, must match the detail layout
    private EditText symbolEditText;
    private EditText openingPriceEditText;
    private EditText previousClosingPriceEditText;
    private EditText bidPriceEditText;
    private EditText bidSizeEditText;
    private EditText askPriceEditText;
    private EditText askSizeEditText;
    private EditText lastTradePriceEditText;
    private EditText lastTradeQuantityEditText;
    private EditText lastTradeDateEditText;
    private EditText lastTradeTimeEditText;
    // timestamps cannot be changed
    private TextView insertDateTimeTextView;
    private TextView modifyDateTimeTextView;

    // resource Ids for EditText and TextViews, must match detail layout
    private int symbolIndex;
    private int openingPriceIndex;
    private int previousClosingPriceIndex;
    private int bidPriceIndex;
    private int bidSizeIndex;
    private int askPriceIndex;
    private int askSizeIndex;
    private int lastTradePriceIndex;
    private int lastTradeQuantityIndex;
    private int lastTradeDateIndex;
    private int lastTradeTimeIndex;
    private int insertDateTimeIndex;
    private int modifyDateTimeIndex;

    private Button saveButton;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment UpdateFragment.
     */
    public static UpdateFragment newInstance() {
        Log.i("UpdateFragment", "in newInstance()");
        UpdateFragment fragment = new UpdateFragment();
        return fragment;
    }

    //
    // Standard function provided by fragment template
    //
    public UpdateFragment() {
        // Required empty public constructor
        Log.i(DEBUG_TAG, "in UpdateFragment()");
    } // end method UpdateFragment

    //
    // Standard function provided by fragment template
    //
    @Override
    public void onAttach(Activity activity) {
        Log.i(DEBUG_TAG, "in onAttach()");
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    //
    // Standard function provided by fragment template
    //
    public void onButtonPressed(Bundle updateFragmentInterfaceBundle) {
        Log.i(DEBUG_TAG, "in onButtonPressed()");
        if (mListener != null) {
            mListener.onUpdateFragmentSymbolCompleted(updateFragmentInterfaceBundle);
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
        // save fragment across configuration changes
        setRetainInstance(true);
        // fragment has menu items to display
        setHasOptionsMenu(true);

        // Inflate the GUI and get references to EditText(s)
        View view = inflater.inflate(R.layout.fragment_update, container, false);

        // get the EditText data, must match the detail layout
        symbolEditText = (EditText) view.findViewById(R.id.symbol_edit_text);
        openingPriceEditText = (EditText) view.findViewById(R.id.opening_price_edit_text);
        previousClosingPriceEditText = (EditText) view.findViewById(R.id.previous_closing_price_edit_text);
        bidPriceEditText = (EditText) view.findViewById(R.id.bid_price_edit_text);
        bidSizeEditText = (EditText) view.findViewById(R.id.bid_size_edit_text);
        askPriceEditText = (EditText) view.findViewById(R.id.ask_price_edit_text);
        askSizeEditText = (EditText) view.findViewById(R.id.ask_size_edit_text);
        lastTradePriceEditText = (EditText) view.findViewById(R.id.last_trade_price_edit_text);
        lastTradeQuantityEditText = (EditText) view.findViewById(R.id.last_trade_quantity_edit_text);
        lastTradeDateEditText = (EditText) view.findViewById(R.id.last_trade_date_edit_text);
        lastTradeTimeEditText = (EditText) view.findViewById(R.id.last_trade_time_edit_text);

        // the following are readonly fields.
        insertDateTimeTextView = (TextView) view.findViewById(R.id.insert_datetime_text_view);
        modifyDateTimeTextView = (TextView) view.findViewById(R.id.modify_datetime_text_view);

        // infoBundle should always contain data since this is anupdate
        Bundle infoBundle = getArguments();
        if (infoBundle != null) {
            mRowId = infoBundle.getLong(FragmentConstants.ROW_ID);
            symbolEditText.setText(infoBundle.getString(Database.Portfolio.TICKER_SYMBOL));
        }

        // set Save Contact Button's event listener
        saveButton = (Button) view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(saveButtonClicked);

        return view;
    } // end method onCreateView

    //
    // Standard function provided by fragment template
    //
    @Override
    public void onDetach() {
        Log.i(DEBUG_TAG, "in onDetach()");
        super.onDetach();
        mListener = null;
    }


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

        // called after update completed so the item/symbol can be redisplayed
        public void onUpdateFragmentSymbolCompleted(Bundle updateFragmentInterfaceBundle);

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
            Log.i(DEBUG_TAG, "in LoadItemAsyncTask()/doInBackground()");
            databaseConnector.open();

            return databaseConnector.getOneItem(params[0]);
        } // end method doInBackground()


        //
        // use the Cursor returned from the doInBackground method
        //
        @Override
        protected void onPostExecute(Cursor result) {
            Log.i(DEBUG_TAG, "in LoadItemAsyncTask()/onPostExecute()");
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

            // fill EditText and TextViews with the retrieved data
            symbolEditText.setText(result.getString(symbolIndex));
            openingPriceEditText.setText(result.getString(openingPriceIndex));
            previousClosingPriceEditText.setText(result.getString(previousClosingPriceIndex));
            bidPriceEditText.setText(result.getString(bidPriceIndex));
            bidSizeEditText.setText(result.getString(bidSizeIndex));
            askPriceEditText.setText(result.getString(askPriceIndex));
            askSizeEditText.setText(result.getString(askSizeIndex));
            lastTradePriceEditText.setText(result.getString(lastTradePriceIndex));
            lastTradeQuantityEditText.setText(result.getString(lastTradeQuantityIndex));
            lastTradeDateEditText.setText(result.getString(lastTradeDateIndex));
            lastTradeTimeEditText.setText(result.getString(lastTradeTimeIndex));
            insertDateTimeTextView.setText(result.getString(insertDateTimeIndex));
            modifyDateTimeTextView.setText(result.getString(modifyDateTimeIndex));

            // close the result cursor
            result.close();
            // close database connection
            databaseConnector.close();
        } // end method onPostExecute
    } // end class LoadItemAsyncTask


    //
    // responds to event generated when user saves a contact
    //
    View.OnClickListener saveButtonClicked = new View.OnClickListener()
    {
        @Override
        public void onClick(View v) {
            if (symbolEditText.getText().toString().trim().length() != 0) {
                // AsyncTask to save contact, then notify listener
                AsyncTask<Object, Object, Object> saveContactTask =
                        new AsyncTask<Object, Object, Object>() {
                            @Override
                            protected Object doInBackground(Object... params) {

                                // get DatabaseConnector to interact with the SQLite database
                                DatabaseConnector databaseConnector = new DatabaseConnector(getActivity());

                                databaseConnector.updateItem(
                                        mRowId,
                                        symbolEditText.getText().toString(),
                                        openingPriceEditText.getText().toString(),
                                        previousClosingPriceEditText.getText().toString(),
                                        bidPriceEditText.getText().toString(),
                                        bidSizeEditText.getText().toString(),
                                        askPriceEditText.getText().toString(),
                                        askSizeEditText.getText().toString(),
                                        lastTradePriceEditText.getText().toString(),
                                        lastTradeQuantityEditText.getText().toString(),
                                        lastTradeDateEditText.getText().toString(),
                                        lastTradeTimeEditText.getText().toString()
                                );

                                return null;
                            }

                            @Override
                            protected void onPostExecute(Object result) {
                                // hide soft keyboard
                                InputMethodManager imm = (InputMethodManager)
                                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

                                //
                                // rowID is returned in a bundle for consistency
                                //
                                Bundle updateFragmentInterfaceBundle = new Bundle();
                                updateFragmentInterfaceBundle.putLong(FragmentConstants.ROW_ID, mRowId);
                                // inform callback on what has happened
                                onButtonPressed(updateFragmentInterfaceBundle);
                            }
                        }; // end AsyncTask

                // save the contact to the database using a separate thread
                saveContactTask.execute((Object[]) null);
            }
            // required field is blank, so display error dialog
            else {
                DialogFragment errorSaving =
                        new DialogFragment() {
                            @Override
                            public Dialog onCreateDialog(Bundle savedInstanceState) {
                                AlertDialog.Builder builder =
                                        new AlertDialog.Builder(getActivity());
                                builder.setMessage(R.string.fragment_update_error_saving_message);
                                builder.setPositiveButton(R.string.fragment_update_error_saving_button, null);
                                return builder.create();
                            }
                        };

                errorSaving.show(getFragmentManager(), "error saving item/symbol");
            }
        } // end method onClick
    }; // end OnClickListener saveButtonClicked

}
