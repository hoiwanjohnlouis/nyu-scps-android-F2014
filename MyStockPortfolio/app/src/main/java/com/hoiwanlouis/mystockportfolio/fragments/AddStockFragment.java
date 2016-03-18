package com.hoiwanlouis.mystockportfolio.fragments;

import android.app.Activity;
import android.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.hoiwanlouis.mystockportfolio.R;
import com.hoiwanlouis.mystockportfolio.database.DatabaseConnector;

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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnAddStockFragmentListener {
        // TODO: Update argument type and name
        void onASFLStockAdded();
    }

    //
    private final String DEBUG_TAG = this.getClass().getSimpleName();
    //
    private OnAddStockFragmentListener mListener;

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

        // Save the stock to database;
        ImageButton mSaveButton;
        mSaveButton = (ImageButton) v.findViewById(R.id.add_stock_save_button);
        mSaveButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // todo: add check for no data in edittext field
                        EditText tickerSymbol = (EditText) v.findViewById(R.id.add_stock_edit_text);
                        if (editTextIsNull(tickerSymbol)) {
                            // let's have a toast
                            Toast.makeText(getActivity(), "Please enter a Stock Symbol.", Toast.LENGTH_SHORT).show();
                        } else {
                            // get DatabaseConnector to interact with the SQLite database
                            DatabaseConnector dbConnector = new DatabaseConnector(getActivity());
                            // insert the contact information into the database
                            long rowID = dbConnector.addOneStock(tickerSymbol.getText().toString());
                            // reset form
                            tickerSymbol.setText(null);
                        }
                        // callback to main;
                        onButtonPressed();
                    }
                }
        );

        return v;
    }

    private boolean editTextIsNull(final EditText stock) {
        boolean isNull = true;
        if (stock != null) {
            isNull = false;
        }
        return isNull;
    }
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
    // callback to main to redisplay screen;
    public void onButtonPressed() {
        Log.i(DEBUG_TAG, "in onButtonPressed()");
        if (mListener != null) {
            mListener.onASFLStockAdded();
        }
    }

}
