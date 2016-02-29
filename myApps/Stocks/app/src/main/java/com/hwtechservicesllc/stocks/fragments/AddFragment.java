package com.hwtechservicesllc.stocks.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.hwtechservicesllc.stocks.R;
import com.hwtechservicesllc.stocks.database.DatabaseConnector;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends Fragment {

    // default fragment bundle key 1: ARG_ITEM_STRING, aka ARG_PARAM1
    private static final String ARG_ROW_ID = FragmentConstants.ROW_ID;

    // the fragment bundle data 1: VALUE DATA, aka mParam1
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

    // database row id of the current contact
    private long rowID;

    // define the EditText and default database values for new symbol
    private EditText inputSymbolEditText;

    // default values used when adding new symbols
    private final String defaultOpeningPrice = getString(R.string.default_opening_price);
    private final String defaultPreviousClosingPrice = getString(R.string.default_closing_price);
    private final String defaultBidPrice = getString(R.string.default_bid_price);
    private final String defaultBidSize = getString(R.string.default_bid_size);
    private final String defaultAskPrice = getString(R.string.default_ask_price);
    private final String defaultAskSize = getString(R.string.default_ask_size);
    private final String defaultLastTradePrice = getString(R.string.default_trade_price);
    private final String defaultLastTradeQuantity = getString(R.string.default_trade_quantity);
    private final String defaultLastTradeDate = getString(R.string.default_trade_date);
    private final String defaultLastTradeTime = getString(R.string.default_trade_time);

    private ImageButton saveButton;


    public static AddFragment newInstance() {
        Log.i("AddFragment", "in newInstance()");
        AddFragment fragment = new AddFragment();
        return fragment;
    }


    //
    // Standard function provided by fragment template
    //
    public AddFragment() {
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
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    //
    // Standard function provided by fragment template
    //
    public void onButtonPressed(Bundle addFragmentInterfaceBundle) {
        Log.i(DEBUG_TAG, "in onButtonPressed()");
        if (mListener != null) {
            mListener.onAddFragmentSymbolCompleted(addFragmentInterfaceBundle);
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
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        // get the EditText data, must match the detail layout
        inputSymbolEditText = (EditText) view.findViewById(R.id.fragment_add_input_symbol_edit_text);

        // set Save Contact Button's event listener
        saveButton = (ImageButton) view.findViewById(R.id.add_button);
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

        // called after add is complete so the item/symbol can be redisplayed
        public void onAddFragmentSymbolCompleted(Bundle addFragmentInterfaceBundle);

    } // end interface OnFragmentInteractionListener


    /****************************************************************
     *
     * Start of non-template functions
     *
     ****************************************************************/

    //
    // responds to event generated when user saves a contact
    //
    View.OnClickListener saveButtonClicked = new View.OnClickListener()
    {
        @Override
        public void onClick(View v) {
            final String symbolString = inputSymbolEditText.getText().toString().trim().toUpperCase();
            Log.i(DEBUG_TAG, "in saveButtonClicked(): Symbol[" + symbolString + "]");
            if (symbolString.length() != 0) {
                // AsyncTask to save contact, then notify listener
                AsyncTask<Object, Object, Object> saveContactTask =
                        new AsyncTask<Object, Object, Object>() {
                            @Override
                            protected Object doInBackground(Object... params) {

                                // get DatabaseConnector to interact with the SQLite database
                                DatabaseConnector databaseConnector = new DatabaseConnector(getActivity());

                                // insert the symbols into the database
                                // symbols should always be upper case
                                rowID = databaseConnector.insertItem(
                                        symbolString,
                                        defaultOpeningPrice,
                                        defaultPreviousClosingPrice,
                                        defaultBidPrice,
                                        defaultBidSize,
                                        defaultAskPrice,
                                        defaultAskSize,
                                        defaultLastTradePrice,
                                        defaultLastTradeQuantity,
                                        defaultLastTradeDate,
                                        defaultLastTradeTime
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
                                Bundle addFragmentInterfaceBundle = new Bundle();
                                addFragmentInterfaceBundle.putLong(FragmentConstants.ROW_ID, rowID);
                                // inform callback on what has happened
                                onButtonPressed(addFragmentInterfaceBundle);
                            }
                        }; // end AsyncTask

                // save the contact to the database using a separate thread
                saveContactTask.execute((Object[]) null);
                // clear out the input field for next input
                inputSymbolEditText.setText("");
            }
            // required field is blank, so display error dialog
            else {
                DialogFragment errorSaving =
                        new DialogFragment() {
                            @Override
                            public Dialog onCreateDialog(Bundle savedInstanceState) {
                                AlertDialog.Builder builder =
                                        new AlertDialog.Builder(getActivity());
                                builder.setMessage(R.string.fragment_add_error_saving_message);
                                builder.setPositiveButton(R.string.fragment_add_error_saving_button, null);
                                return builder.create();
                            }
                        };

                errorSaving.show(getFragmentManager(), "error saving item/symbol");
            }
        } // end method onClick
    }; // end OnClickListener saveButtonClicked


}
