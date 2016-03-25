package com.example.hoiwanlouis.hoiaddressbook;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class DetailsFragment extends Fragment {

    private final String DEBUG_TAG = this.getClass().getSimpleName();


    // callback methods implemented by MainActivity
    public interface DetailsFragmentListener {
        // called when a contact is deleted
        public void onDeleteContactComplete();

        // called to pass Bundle of contact's info for editing
        public void onEditContactRequest(Bundle arguments);
    } // end interface DetailsFragmentListener


    private DetailsFragmentListener listener;

    private long rowID = -1; // selected contact's rowID
    private TextView nameTextView; // displays contact's name
    private TextView phoneTextView; // displays contact's phone
    private TextView emailTextView; // displays contact's email
    private TextView streetTextView; // displays contact's street
    private TextView cityTextView; // displays contact's city
    private TextView stateTextView; // displays contact's state
    private TextView zipTextView; // displays contact's zip


    // set DetailsFragmentListener when fragment attached
    @Override
    public void onAttach(Activity activity) {
        Log.i(DEBUG_TAG, "in onAttach()");
        super.onAttach(activity);
        listener = (DetailsFragmentListener) activity;
    } // end method onAttach

    // remove DetailsFragmentListener when fragment detached
    @Override
    public void onDetach() {
        Log.i(DEBUG_TAG, "in onDetach()");
        super.onDetach();
        listener = null;
    } // end method onDetach

    // called when DetailsFragmentListener's view needs to be created
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(DEBUG_TAG, "in onCreateView()");
        super.onCreateView(inflater, container, savedInstanceState);
        // save fragment across config changes
        setRetainInstance(true);

        // if DetailsFragment is being restored, get saved row ID
        if (savedInstanceState != null) {
            rowID = savedInstanceState.getLong(MainActivity.ROW_ID);
        }
        else {
            // get Bundle of arguments then extract the contact's row ID
            Bundle arguments = getArguments();

            if (arguments != null) {
                rowID = arguments.getLong(MainActivity.ROW_ID);
            }
        }

        // inflate DetailsFragment's layout
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        // this fragment has menu items to display
        setHasOptionsMenu(true);

        // get the EditTexts
        nameTextView = (TextView) view.findViewById(R.id.nameTextView);
        phoneTextView = (TextView) view.findViewById(R.id.phoneTextView);
        emailTextView = (TextView) view.findViewById(R.id.emailTextView);
        streetTextView = (TextView) view.findViewById(R.id.streetTextView);
        cityTextView = (TextView) view.findViewById(R.id.cityTextView);
        stateTextView = (TextView) view.findViewById(R.id.stateTextView);
        zipTextView = (TextView) view.findViewById(R.id.zipTextView);

        return view;
    } // end method onCreateView

    // called when the DetailsFragment resumes
    @Override
    public void onResume() {
        Log.i(DEBUG_TAG, "in onResume()");
        super.onResume();
        // load contact at rowID
        new LoadContactTask().execute(rowID);
    } // end method onResume()

    // save currently displayed contact's row ID
    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.i(DEBUG_TAG, "in onSaveInstanceState()");
        super.onSaveInstanceState(outState);
        outState.putLong(MainActivity.ROW_ID, rowID);
    } // end method onSaveInstanceState()

    // display this fragment's menu items
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.i(DEBUG_TAG, "in onCreateOptionsMenu()");
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_details_menu, menu);
    } // end method onCreateOptionsMenu()

    // handle menu item selections
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(DEBUG_TAG, "in onOptionsItemSelected()");
        switch (item.getItemId())
        {
            case R.id.action_edit:
                // create Bundle containing contact data to edit
                Bundle arguments = new Bundle();
                arguments.putLong(MainActivity.ROW_ID, rowID);
                arguments.putCharSequence("name", nameTextView.getText());
                arguments.putCharSequence("phone", phoneTextView.getText());
                arguments.putCharSequence("email", emailTextView.getText());
                arguments.putCharSequence("street", streetTextView.getText());
                arguments.putCharSequence("city", cityTextView.getText());
                arguments.putCharSequence("state", stateTextView.getText());
                arguments.putCharSequence("zip", zipTextView.getText());
                // pass Bundle to listener for processing
                listener.onEditContactRequest(arguments);
                return true;

            case R.id.action_delete:
                deleteContact();
                return true;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    } // end method onOptionsItemSelected

    // delete a contact
    private void deleteContact() {
        Log.i(DEBUG_TAG, "in deleteContact()");
        // use FragmentManager to display the confirmDelete DialogFragment
        confirmDelete.show(getFragmentManager(), "confirm delete");
    } // end method deleteContact


    // *****************************************************
    // performs database query outside GUI thread
    // *****************************************************
    private class LoadContactTask extends AsyncTask<Long, Object, Cursor>
    {
        DatabaseConnector databaseConnector = new DatabaseConnector(getActivity());

        // open database & get Cursor representing specified contact's data
        @Override
        protected Cursor doInBackground(Long... params) {
            Log.i(DEBUG_TAG, "in doInBackground()");
            databaseConnector.open();
            return databaseConnector.getOneContact(params[0]);
        } // end method doInBackground()

        // use the Cursor returned from the doInBackground method
        @Override
        protected void onPostExecute(Cursor result) {
            Log.i(DEBUG_TAG, "in onPostExecute()");
            super.onPostExecute(result);
            // move to the first item
            result.moveToFirst();

            // get the column index for each data item
            int nameIndex = result.getColumnIndex("name");
            int phoneIndex = result.getColumnIndex("phone");
            int emailIndex = result.getColumnIndex("email");
            int streetIndex = result.getColumnIndex("street");
            int cityIndex = result.getColumnIndex("city");
            int stateIndex = result.getColumnIndex("state");
            int zipIndex = result.getColumnIndex("zip");

            // fill TextViews with the retrieved data
            nameTextView.setText(result.getString(nameIndex));
            phoneTextView.setText(result.getString(phoneIndex));
            emailTextView.setText(result.getString(emailIndex));
            streetTextView.setText(result.getString(streetIndex));
            cityTextView.setText(result.getString(cityIndex));
            stateTextView.setText(result.getString(stateIndex));
            zipTextView.setText(result.getString(zipIndex));

            // close the result cursor
            result.close();
            // close database connection
            databaseConnector.close();
        } // end method onPostExecute
    } // end class LoadContactTask

    // DialogFragment to confirm deletion of contact
    private DialogFragment confirmDelete =
            new DialogFragment() {
                // create an AlertDialog and return it
                @Override
                public Dialog onCreateDialog(Bundle bundle) {
                    Log.i(DEBUG_TAG, "in onCreateDialog()");
                    // create a new AlertDialog Builder
                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(getActivity());

                    builder.setTitle(R.string.confirm_title);
                    builder.setMessage(R.string.confirm_message);

                    // provide an OK button that simply dismisses the dialog
                    builder.setPositiveButton(R.string.button_delete,
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
                                                    databaseConnector.deleteContact(params[0]);
                                                    return null;
                                                }

                                                @Override
                                                protected void onPostExecute(Object result) {
                                                    Log.i(DEBUG_TAG, "in onPostExecute()");
                                                    listener.onDeleteContactComplete();
                                                }
                                            }; // end new AsyncTask

                                    // execute the AsyncTask to delete contact at rowID
                                    deleteTask.execute(new Long[] { rowID });
                                } // end method onClick
                            } // end anonymous inner class
                    ); // end call to method setPositiveButton

                    // do nothing if cancel action
                    builder.setNegativeButton(R.string.button_cancel, null);

                    // return the AlertDialog
                    return builder.create();
                }
            }; // end DialogFragment anonymous inner class

} // end class DetailsFragment

