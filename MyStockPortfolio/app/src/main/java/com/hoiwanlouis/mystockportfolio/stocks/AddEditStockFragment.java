package com.hoiwanlouis.mystockportfolio.stocks;

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
import android.widget.Button;
import android.widget.EditText;

import com.hoiwanlouis.mystockportfolio.R;
import com.hoiwanlouis.mystockportfolio.database.DatabaseConnector;
import com.hoiwanlouis.mystockportfolio.fields.Gui2Db;

public class AddEditStockFragment extends Fragment {

    public interface AddStockFragmentListener {
        void onAddStockComplete(Bundle arguments);
    }

    private final String DEBUG_TAG = this.getClass().getSimpleName();
    private AddStockFragmentListener addStockFragmentListener;
    private Button saveStockToDatabaseButton;
    private EditText stockSymbol;
    private long databaseRowID;

    public AddEditStockFragment() {
        Log.i(DEBUG_TAG, "in AddEditStockFragment()");
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddEditStockFragment.
     */
    public static AddEditStockFragment newInstance() {
        AddEditStockFragment fragment = new AddEditStockFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        Log.i(DEBUG_TAG, "in onAttach()");
        super.onAttach(context);
        if (context instanceof AddStockFragmentListener) {
            addStockFragmentListener = (AddStockFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement AddStockFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        Log.i(DEBUG_TAG, "in onDetach()");
        super.onDetach();
        addStockFragmentListener = null;
    }

    @Override
    public void onResume() {
        Log.i(DEBUG_TAG, "in onResume()");
        super.onResume();
    }

    @Override
    public void onStart() {
        Log.i(DEBUG_TAG, "in onStart()");
        super.onStart();
    }

    @Override
    public void onStop() {
        Log.i(DEBUG_TAG, "in onStop()");
        super.onStop();
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(DEBUG_TAG, "in onCreateView()");
        super.onCreateView(inflater, container, savedInstanceState);
        setRetainInstance(true);
        View v = inflater.inflate(R.layout.fragment_add_stock, container, false);
        stockSymbol = (EditText) v.findViewById(R.id.add_stock_edit_text);
        saveStockToDatabaseButton = (Button) v.findViewById(R.id.add_stock_save_button);
        saveStockToDatabaseButton.setOnClickListener(saveStockSymbolButtonClicked);
        return v;
    }

    View.OnClickListener saveStockSymbolButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (editTextHasData(stockSymbol)) {
                // create AsyncTask to save contact on different thread
                AsyncTask<Object, Object, Object> saveStockSymbolAsyncTask =
                        new AsyncTask<Object, Object, Object>() {
                            @Override
                            protected Object doInBackground(Object... params) {
                                saveStockSymbolToDatabase();
                                return null;
                            }

                            @Override
                            protected void onPostExecute(Object result) {
                                // clean up after the saveStockSymbolToDatabase() function
                                InputMethodManager imm = (InputMethodManager)
                                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                                stockSymbol.setText("");
                                onAddStockCompleteCallback();
                            }
                        }; // end AsyncTask definition
                // save the contact to the database using a separate thread
                saveStockSymbolAsyncTask.execute((Object[]) null);
            }
            else {
                // notify user that the stockSymbol (required) field is blank
                DialogFragment errorSaving =
                        new DialogFragment() {
                            @Override
                            public Dialog onCreateDialog(Bundle savedInstanceState) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setMessage(R.string.error_saving_message);
                                builder.setPositiveButton(R.string.error_saving_positive_button, null);
                                return builder.create();
                            }
                        };
                // invoke alert dialog
                errorSaving.show(getFragmentManager(), "error saving contact");
            }
        } // end method onClick
    };

    private boolean editTextHasData(final EditText stock) {
        Log.i(DEBUG_TAG, "in editTextHasData()");
        return ((stock != null) && (stock.getText().toString().trim().length() != 0));
    }

    private void saveStockSymbolToDatabase() {
        Log.i(DEBUG_TAG, "in saveStockSymbolToDatabase()");
        DatabaseConnector dbConnector = new DatabaseConnector(getActivity());
        databaseRowID = dbConnector.addOneStock(stockSymbol.getText().toString());
    }

    // callback to main to redisplay screen;
    public void onAddStockCompleteCallback() {
        Log.i(DEBUG_TAG, "in onAddStockCompleteCallback()");
        Bundle arguments = new Bundle();
        arguments.putLong(Gui2Db.BUNDLE_KEY, databaseRowID);
        addStockFragmentListener.onAddStockComplete(arguments);
    }

}