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
import android.content.Context;
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

import com.hoiwanlouis.mystockportfolio.database.Database;
import com.hoiwanlouis.mystockportfolio.database.DatabaseConnector;
import com.hoiwanlouis.mystockportfolio.PrimoActivity;
import com.hoiwanlouis.mystockportfolio.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditFragment extends Fragment {

    private final String DEBUG_TAG = this.getClass().getSimpleName();

    //
    // callback method
    //
    private EditFragmentListener listener;

    // database row id of the current contact
    private long rowID;

    // argument for editing a contact
    private Bundle infoBundle;

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
    private EditText insertDateTimeEditText;
    private EditText modifyDateTimeEditText;


    //
    //
    //
    public EditFragment() {
        // Required empty public constructor
        Log.i(DEBUG_TAG, "in EditFragment()");
    } // end method EditFragment


    //
    // set EditFragmentListener when Fragment attached
    //
    @Override
    public void onAttach(Activity activity) {
        Log.i(DEBUG_TAG, "in onAttach()");
        super.onAttach(activity);
        listener = (EditFragmentListener) activity;
    } // end method onAttach


    //
    // remove EditFragmentListener when Fragment detached
    //
    @Override
    public void onDetach() {
        Log.i(DEBUG_TAG, "in onDetach()");
        super.onDetach();
        listener = null;
    } // end method onDetach


    //
    // called when Fragment's view needs creation
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
        View view = inflater.inflate(R.layout.fragment_add_edit, container, false);

        // get the EditText data, must match the detail layout
        symbolEditText = (EditText) view.findViewById(R.id.symbolEditText);
        openingPriceEditText = (EditText) view.findViewById(R.id.openingPriceEditText);
        previousClosingPriceEditText = (EditText) view.findViewById(R.id.previousClosingPriceEditText);
        bidPriceEditText = (EditText) view.findViewById(R.id.bidPriceEditText);
        bidSizeEditText = (EditText) view.findViewById(R.id.bidSizeEditText);
        askPriceEditText = (EditText) view.findViewById(R.id.askPriceEditText);
        askSizeEditText = (EditText) view.findViewById(R.id.askSizeEditText);
        lastTradePriceEditText = (EditText) view.findViewById(R.id.lastTradePriceEditText);
        lastTradeQuantityEditText = (EditText) view.findViewById(R.id.lastTradeQuantityEditText);
        lastTradeDateEditText = (EditText) view.findViewById(R.id.lastTradeDateEditText);
        lastTradeTimeEditText = (EditText) view.findViewById(R.id.lastTradeTimeEditText);
        insertDateTimeEditText = (EditText) view.findViewById(R.id.insertDateTimeEditText);
        modifyDateTimeEditText = (EditText) view.findViewById(R.id.modifyDateTimeEditText);


        // can be null if creating new contact.
        infoBundle = getArguments();

        if (infoBundle != null) {
            rowID = infoBundle.getLong(PrimoActivity.ROW_ID);
            symbolEditText.setText(infoBundle.getString(Database.Portfolio.SYMBOL));
            openingPriceEditText.setText(infoBundle.getString(Database.Portfolio.OPENING_PRICE));
            previousClosingPriceEditText.setText(infoBundle.getString(Database.Portfolio.PREVIOUS_CLOSING_PRICE));
            bidPriceEditText.setText(infoBundle.getString(Database.Portfolio.BID_PRICE));
            bidSizeEditText.setText(infoBundle.getString(Database.Portfolio.BID_SIZE));
            askPriceEditText.setText(infoBundle.getString(Database.Portfolio.ASK_PRICE));
            askSizeEditText.setText(infoBundle.getString(Database.Portfolio.ASK_SIZE));
            lastTradePriceEditText.setText(infoBundle.getString(Database.Portfolio.LAST_TRADE_PRICE));
            lastTradeQuantityEditText.setText(infoBundle.getString(Database.Portfolio.LAST_TRADE_QUANTITY));
            lastTradeDateEditText.setText(infoBundle.getString(Database.Portfolio.LAST_TRADE_DATE));
            lastTradeTimeEditText.setText(infoBundle.getString(Database.Portfolio.LAST_TRADE_TIME));
            insertDateTimeEditText.setText(infoBundle.getString(Database.Portfolio.INSERT_DATETIME));
            modifyDateTimeEditText.setText(infoBundle.getString(Database.Portfolio.MODIFY_DATETIME));
        }

        // set Save Contact Button's event listener
        Button saveButton = (Button) view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(saveButtonClicked);

        return view;
    } // end method onCreateView


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
    public interface EditFragmentListener {

        // called after edit completed so the item/symbol can be redisplayed
        void onEditSymbolCompleted(long rowID);

    }


    //
    // saves information to the database
    //
    private void saveInformation() {
        Log.i(DEBUG_TAG, "in saveInformation()");

        // get DatabaseConnector to interact with the SQLite database
        DatabaseConnector databaseConnector = new DatabaseConnector(getActivity());

        if (infoBundle == null) {
            // insert the contact information into the database
            rowID = databaseConnector.insertItem(
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
                    lastTradeTimeEditText.getText().toString(),
                    insertDateTimeEditText.getText().toString()
            );
        }
        else
        {
            databaseConnector.updateItem(
                    rowID,
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
                    lastTradeTimeEditText.getText().toString(),
                    modifyDateTimeEditText.getText().toString()
            );
        }
    } // end method saveContact


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
                                // save contact to the database
                                saveInformation();
                                return null;
                            }

                            @Override
                            protected void onPostExecute(Object result) {
                                // hide soft keyboard
                                InputMethodManager imm = (InputMethodManager)
                                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

                                listener.onEditSymbolCompleted(rowID);
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
                                builder.setMessage(R.string.fragment_error_saving_message);
                                builder.setPositiveButton(R.string.fragment_error_saving_button, null);
                                return builder.create();
                            }
                        };

                errorSaving.show(getFragmentManager(), "error saving item/symbol");
            }
        } // end method onClick
    }; // end OnClickListener saveButtonClicked


}
