package com.hoiwanlouis.mystockportfolio.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.hoiwanlouis.mystockportfolio.R;
import com.hoiwanlouis.mystockportfolio.database.DatabaseConnector;
import com.hoiwanlouis.mystockportfolio.fields.Gui2Database;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnAddStockFragmentListener} interface
 * to handle interaction events.
 * Use the {@link AddStockFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddStockFragment extends Fragment {
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnAddStockFragmentListener {
        // TODO: Update argument type and name
        void onASFLAddStockComplete(Bundle arguments);
    }

    //
    private final String DEBUG_TAG = this.getClass().getSimpleName();
    //
    private OnAddStockFragmentListener mListener;
    //
    private ImageButton mSaveStockSymbolButton;
    //
    private EditText stockSymbol;
    // database row id of the current contact
    private long rowID;

    public AddStockFragment() {
        Log.i(DEBUG_TAG, "in AddStockFragment(), required empty public constructor");
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddStockFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddStockFragment newInstance() {
        AddStockFragment fragment = new AddStockFragment();
        //Bundle args = new Bundle();
        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(DEBUG_TAG, "in onCreate()");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(DEBUG_TAG, "in onCreateView()");
        super.onCreateView(inflater, container, savedInstanceState);
        // save fragment across config changes
        setRetainInstance(true);

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_add_stock, container, false);
        //
        stockSymbol = (EditText) v.findViewById(R.id.add_stock_edit_text);

        // Save the stockSymbol to database;
        mSaveStockSymbolButton = (ImageButton) v.findViewById(R.id.add_stock_save_button);
        mSaveStockSymbolButton.setOnClickListener(saveStockSymbolButtonClicked);

        return v;
    }


    //
    // responds to event generated when user saves a contact
    //
    View.OnClickListener saveStockSymbolButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (editTextHasData(stockSymbol)) {
                // AsyncTask to save contact, then notify listener
                AsyncTask<Object, Object, Object> saveStockSymbolAsyncTask =
                        new AsyncTask<Object, Object, Object>() {
                            @Override
                            protected Object doInBackground(Object... params) {
                                // save contact to the database
                                saveStockSymbol();
                                return null;
                            }

                            @Override
                            protected void onPostExecute(Object result) {
                                // hide soft keyboard
                                InputMethodManager imm = (InputMethodManager)
                                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                                // reset form
                                stockSymbol.setText(null);
                                // notify caller the stockSymbol was added
                                onButtonPressed();
                            }
                        }; // end AsyncTask

                // save the contact to the database using a separate thread
                saveStockSymbolAsyncTask.execute((Object[]) null);
            }
            // required contact name is blank, so display error dialog
            else {
                DialogFragment errorSaving =
                        new DialogFragment() {
                            @Override
                            public Dialog onCreateDialog(Bundle savedInstanceState) {
                                AlertDialog.Builder builder =
                                        new AlertDialog.Builder(getActivity());
                                builder.setMessage(R.string.error_saving_message);
                                builder.setPositiveButton(R.string.error_saving_positive_button, null);
                                return builder.create();
                            }
                        };

                errorSaving.show(getFragmentManager(), "error saving contact");
            }
        } // end method onClick
    }; // end OnClickListener saveContactButtonClicked

    //
    //
    //
    @Override
    public void onAttach(Activity context) {
        Log.i(DEBUG_TAG, "in onAttach()");
        super.onAttach(context);
        if (context instanceof OnAddStockFragmentListener) {
            mListener = (OnAddStockFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnAddStockFragmentListener");
        }
    }

    //
    //
    //
    @Override
    public void onDetach() {
        Log.i(DEBUG_TAG, "in onDetach()");
        super.onDetach();
        mListener = null;
    }

    //
    // when fragment starts
    //
    @Override
    public void onStart() {
        Log.i(DEBUG_TAG, "in onStart()");
        super.onStart();
    } // end method onStart()

    //
    // when fragment resumes, clean up
    //
    @Override
    public void onStop() {
        Log.i(DEBUG_TAG, "in onStop()");
        super.onStop();
    } // end method onStop()

    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    //
    //
    //
    private boolean editTextHasData(final EditText stock) {
        boolean hasData = false;
        Log.i(DEBUG_TAG, "in editTextHasData()");
        if ((stock != null) && (stock.getText().toString().trim().length() != 0)) {
            hasData = true;
        }
        return hasData;
    }

    //
    // callback to main to redisplay screen;
    //
    public void onButtonPressed() {
        Log.i(DEBUG_TAG, "in onButtonPressed()");
        if (mListener != null) {
            Bundle arguments = new Bundle();
            arguments.putLong(Gui2Database.BUNDLE_KEY, rowID);
            mListener.onASFLAddStockComplete(arguments);
        }
    }

    //
    // saves stockSymbol to the database
    //
    private void saveStockSymbol() {
        Log.i(DEBUG_TAG, "in saveStockSymbol()");
        // get DatabaseConnector to interact with the SQLite database
        DatabaseConnector dbConnector = new DatabaseConnector(getActivity());
        // insert the contact information into the database
        rowID = dbConnector.addOneStock(stockSymbol.getText().toString());
    }

}