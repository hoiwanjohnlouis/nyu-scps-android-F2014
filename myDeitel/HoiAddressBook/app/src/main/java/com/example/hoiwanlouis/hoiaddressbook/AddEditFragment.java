package com.example.hoiwanlouis.hoiaddressbook;

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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;


public class AddEditFragment extends Fragment {

    private final String DEBUG_TAG = this.getClass().getSimpleName();

    // callback method
    public interface AddEditFragmentListener {
        // called after edit completed so contact can be redisplayed
        void onAddEditContactComplete(long rowID);
    }

    private AddEditFragmentListener listener;

    // database row id of the current contact
    private long rowID;
    // argument for editing a contact
    private Bundle contactInfoBundle;

    // EditText for contact information
    private EditText nameEditText;
    private EditText phoneEditText;
    private EditText emailEditText;
    private EditText streetEditText;
    private EditText cityEditText;
    private EditText stateEditText;
    private EditText zipEditText;


    public AddEditFragment() {
        // Required empty public constructor
        Log.i(DEBUG_TAG, "in AddEditFragment()");
    } // end method AddEditFragment

    // set AddEditFragmentListener when Fragment attached
    @Override
    public void onAttach(Context context) {
        Log.i(DEBUG_TAG, "in onAttach()");
        super.onAttach(context);
        listener = (AddEditFragmentListener) context;
    } // end method onAttach()

    @Override
    @SuppressWarnings("deprecation")
    public void onAttach(Activity activity) {
        Log.i(DEBUG_TAG, "in onAttach()");
        super.onAttach(activity);
        listener = (AddEditFragmentListener) activity;
    } // end method onAttach

    // remove AddEditFragmentListener when Fragment detached
    @Override
    public void onDetach() {
        Log.i(DEBUG_TAG, "in onDetach()");
        super.onDetach();
        listener = null;
    } // end method onDetach

    // called when Fragment's view needs creation
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(DEBUG_TAG, "in onCreateView()");
        super.onCreateView(inflater, container, savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);

        // Inflate the GUI and bind references to EditText fields
        View view = inflater.inflate(R.layout.fragment_add_edit, container, false);

        nameEditText = (EditText)view.findViewById(R.id.nameEditText);
        phoneEditText = (EditText)view.findViewById(R.id.phoneEditText);
        emailEditText = (EditText)view.findViewById(R.id.emailEditText);
        streetEditText = (EditText)view.findViewById(R.id.streetEditText);
        cityEditText = (EditText)view.findViewById(R.id.cityEditText);
        stateEditText = (EditText)view.findViewById(R.id.stateEditText);
        zipEditText = (EditText)view.findViewById(R.id.zipEditText);

        // load info from arguments if they exist.
        contactInfoBundle = getArguments();
        if (contactInfoBundle != null) {
            rowID = contactInfoBundle.getLong(MainActivity.ROW_ID);
            nameEditText.setText(contactInfoBundle.getString("name"));
            phoneEditText.setText(contactInfoBundle.getString("phone"));
            emailEditText.setText(contactInfoBundle.getString("email"));
            streetEditText.setText(contactInfoBundle.getString("street"));
            cityEditText.setText(contactInfoBundle.getString("city"));
            stateEditText.setText(contactInfoBundle.getString("state"));
            zipEditText.setText(contactInfoBundle.getString("zip"));
        }

        // bind Save Contact Button's to event listener
        Button saveContactButton = (Button)view.findViewById(R.id.saveContactButton);
        saveContactButton.setOnClickListener(saveContactButtonClicked);

        return view;
    } // end method onCreateView

    // saves contact information to the database
    private void saveContact() {
        Log.i(DEBUG_TAG, "in saveContact()");

        // get DatabaseConnector to interact with the SQLite database
        DatabaseConnector databaseConnector = new DatabaseConnector(getActivity());

        if (contactInfoBundle == null) {
            // insert the contact information into the database
            rowID = databaseConnector.insertContact(
                    nameEditText.getText().toString(),
                    phoneEditText.getText().toString(),
                    emailEditText.getText().toString(),
                    streetEditText.getText().toString(),
                    cityEditText.getText().toString(),
                    stateEditText.getText().toString(),
                    zipEditText.getText().toString());
        }
        else
        {
            databaseConnector.updateContact(rowID,
                    nameEditText.getText().toString(),
                    phoneEditText.getText().toString(),
                    emailEditText.getText().toString(),
                    streetEditText.getText().toString(),
                    cityEditText.getText().toString(),
                    stateEditText.getText().toString(),
                    zipEditText.getText().toString());
        }
    } // end method saveContact

    // responds to event generated when user saves a contact
    OnClickListener saveContactButtonClicked = new OnClickListener()
    {
        @Override
        public void onClick(View v) {
            if (nameEditText.getText().toString().trim().length() != 0) {
                // AsyncTask to save contact, then notify listener
                AsyncTask<Object, Object, Object> saveContactTask =
                        new AsyncTask<Object, Object, Object>() {
                            @Override
                            protected Object doInBackground(Object... params) {
                                // save contact to the database
                                saveContact();
                                return null;
                            }

                            @Override
                            protected void onPostExecute(Object result) {
                                // hide soft keyboard
                                InputMethodManager imm = (InputMethodManager)
                                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

                                listener.onAddEditContactComplete(rowID);
                            }
                        }; // end AsyncTask

                // save the contact to the database using a separate thread
                saveContactTask.execute((Object[]) null);
            }
            // required contact name is blank, so display error dialog
            else {
                DialogFragment errorSaving =
                        new DialogFragment() {
                            @Override
                            public Dialog onCreateDialog(Bundle savedInstanceState) {
                                AlertDialog.Builder builder =
                                        new AlertDialog.Builder(getActivity());
                                builder.setMessage(R.string.error_message);
                                builder.setPositiveButton(R.string.ok, null);
                                return builder.create();
                            }
                        }.;

                errorSaving.show(getFragmentManager(), "error saving contact");
            }
        } // end method onClick
    }; // end OnClickListener saveContactButtonClicked

} // end class AddEditFragment


