package com.example.hoiwanlouis.hoiaddressbook;

import android.app.Activity;
//import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;


public class MainActivity extends Activity
        implements ContactListFragment.ContactListFragmentListener,
        DetailsFragment.DetailsFragmentListener,
        AddEditFragment.AddEditFragmentListener
{
    private final String DEBUG_TAG = "MainActivity";

    // keys for storing row ID in Bundle passed to a fragment
    public static final String ROW_ID = "row_id";

    // displays contact list
    ContactListFragment contactListFragment;

    // display ContactListFragment when MainActivity first loads
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(DEBUG_TAG, "in onCreate() ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // return if Activity is being restored, no need to recreate GUI
        if (savedInstanceState != null) {
            return;
        }

        // check whether layout contains fragmentContainer (phone layout);
        // ContactListFragment is always displayed
        if (findViewById(R.id.fragmentContainer) != null) {
            // create ContactListFragment
            contactListFragment = new ContactListFragment();

            // add the fragment to the FrameLayout
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.fragmentContainer, contactListFragment);
            // causes ContactListFragment to display
            transaction.commit();
        }
    } // end method onCreate()

    // called when MainActivity resumes
    @Override
    protected void onResume() {
        Log.i(DEBUG_TAG, "in onResume()");
        super.onResume();

        // if contactListFragment is null, activity running on tablet,
        // so get reference from FragmentManager
        if (contactListFragment == null) {
            contactListFragment =
                    (ContactListFragment) getFragmentManager().findFragmentById(
                            R.id.contactListFragment);
        }
    } // end method onResume

    // display DetailsFragment for selected contact
    @Override
    public void onContactSelected(long rowID) {
        Log.i(DEBUG_TAG, "in onContactSelected()");
        if (findViewById(R.id.fragmentContainer) != null) {
            // phone
            displayContact(rowID, R.id.fragmentContainer);
        }
        else {
            // tablet
            getFragmentManager().popBackStack(); // removes top of back stack
            displayContact(rowID, R.id.rightPaneContainer);
        }
    } // end method onContactSelected

    // display a contact
    private void displayContact(long rowID, int viewID) {
        Log.i(DEBUG_TAG, "in displayContact()");
        DetailsFragment detailsFragment = new DetailsFragment();

        // specify rowID as an argument to the DetailsFragment
        Bundle arguments = new Bundle();
        arguments.putLong(ROW_ID, rowID);
        detailsFragment.setArguments(arguments);

        // use a FragmentTransaction to display the DetailsFragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(viewID, detailsFragment);
        transaction.addToBackStack(null);
        // causes DetailsFragment to display
        transaction.commit();
    } // end method displayContact

    // display the AddEditFragment to add a new contact
    @Override
    public void onAddContact() {
        Log.i(DEBUG_TAG, "in onAddContact()");
        if (findViewById(R.id.fragmentContainer) != null) {
            displayAddEditFragment(R.id.fragmentContainer, null);
        }
        else {
            displayAddEditFragment(R.id.rightPaneContainer, null);
        }
    } // end method onAddContact

    // display fragment for adding a new or editing an existing contact
    private void displayAddEditFragment(int viewID, Bundle arguments) {
        Log.i(DEBUG_TAG, "in displayAddEditFragment()");
        AddEditFragment addEditFragment = new AddEditFragment();

        if (arguments != null) {
            // editing existing contact
            addEditFragment.setArguments(arguments);
        }

        // use a FragmentTransaction to display the AddEditFragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(viewID, addEditFragment);
        transaction.addToBackStack(null);
        // causes AddEditFragment to display
        transaction.commit();
    } // end method displayAddEditFragment

    // return to contact list when displayed contact deleted
    @Override
    public void onContactDeleted() {
        Log.i(DEBUG_TAG, "in onContactDeleted()");
        getFragmentManager().popBackStack(); // removes top of back stack

        if (findViewById(R.id.fragmentContainer) == null) {
            // tablet
            contactListFragment.updateContactList();
        }
    } // end method onContactDeleted

    // display the AddEditFragment to edit an existing contact
    @Override
    public void onEditContact(Bundle arguments) {
        Log.i(DEBUG_TAG, "in onEditContact()");
        if (findViewById(R.id.fragmentContainer) != null) {
            // phone
            displayAddEditFragment(R.id.fragmentContainer, arguments);
        }
        else {
            // must be a tablet
            displayAddEditFragment(R.id.rightPaneContainer, arguments);
        }
    } // end method onEditContact

    // update GUI after new contact or updated contact saved
    @Override
    public void onAddEditCompleted(long rowID) {
        Log.i(DEBUG_TAG, "in onAddEditCompleted()");
        // removes top of back stack
        getFragmentManager().popBackStack();

        // tablet?
        if (findViewById(R.id.fragmentContainer) == null) {
            // removes top of back stack
            getFragmentManager().popBackStack();
            // refresh contacts
            contactListFragment.updateContactList();

            // on tablet, display contact that was just added or edited
            displayContact(rowID, R.id.rightPaneContainer);
        }
    } // end method onAddEditCompleted
}


