package com.hoiwanlouis.mystockportfolio;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

import com.hoiwanlouis.mystockportfolio.fragments.AddFragment;
import com.hoiwanlouis.mystockportfolio.fragments.EditFragment;
import com.hoiwanlouis.mystockportfolio.fragments.DetailFragment;
import com.hoiwanlouis.mystockportfolio.fragments.InventoryFragment;


public class PrimoActivity extends Activity
        implements  InventoryFragment.InventoryFragmentListener,
                    AddFragment.AddFragmentListener,
                    EditFragment.EditFragmentListener,
                    DetailFragment.DetailFragmentListener {

    // for logging purposes
    private final String DEBUG_TAG = this.getClass().getSimpleName();

    // keys for storing row ID in Bundle passed to a fragment
    public static final String ROW_ID = "row_id";

    // displays item/symbol list
    InventoryFragment inventoryFragment;


    //
    // display InventoryFragment when PrototypeActivity first loads
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(DEBUG_TAG, "in onCreate() ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primo);

        // return if Activity is being restored, no need to recreate GUI
        if (savedInstanceState != null) {
            return;
        }

        // check whether layout contains fragmentContainer (phone layout);
        // inventoryFragment is always displayed
        if (isAPhoneDevice()) {
            // create ContactListFragment
            inventoryFragment = new InventoryFragment();

            // add the fragment to the FrameLayout
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.fragmentContainer, inventoryFragment);
            // causes ContactListFragment to display
            transaction.commit();
        }

    } // end method onCreate()


    //
    // called when PrototypeActivity resumes
    //
    @Override
    protected void onResume() {
        Log.i(DEBUG_TAG, "in onResume()");
        super.onResume();

        // if this is a tablet, then inventoryFragment is null,
        // so get reference from FragmentManager
        if (isATabletDevice()) {
            inventoryFragment =
                    (InventoryFragment) getFragmentManager().findFragmentById(R.id.inventoryFragment);
        }
    } // end method onResume



    /***************************************************************
     *
     * Start of InventoryFragmentListener interfaces implementations
     *
     *      1.  public void onIFLSymbolSelected(long rowID)
     *      2.  public void onIFLAddSymbolSelected();
     *
     ***************************************************************/
    //
    // implementing InventoryFragmentListener interfaces
    // show the DetailsFragment for selected item/symbol
    //
    @Override
    public void onIFLSymbolSelected(long rowID) {
        Log.i(DEBUG_TAG, "in onIFLSymbolSelected()");
        if (isAPhoneDevice()) {
            // phone
            displaySymbol(rowID, R.id.fragmentContainer);
        }
        else {
            // tablet
            getFragmentManager().popBackStack(); // removes top of back stack
            displaySymbol(rowID, R.id.rightPaneContainer);
        }

    } // end method onIFLSymbolSelected


    //
    // implementing InventoryFragmentListener interfaces
    // showAddFragment to add a new item/symbol
    //
    @Override
    public void onIFLAddSymbolSelected() {
        Log.i(DEBUG_TAG, "in onIFLAddSymbolSelected()");
        if (isAPhoneDevice()) {
            showAddFragment(R.id.fragmentContainer, null);
        }
        else {
            showAddFragment(R.id.rightPaneContainer, null);
        }
    } // end method onIFLAddSymbolSelected
    /***************************************************************
     *
     * End of InventoryFragmentListener interfaces implementations
     *
     ***************************************************************/



    /***************************************************************
     *
     * Start of AddFragmentListener interfaces implementations
     *
     *      1.  onAFLAddSymbolCompleted
     *
     ***************************************************************/
    //
    // implementing AddFragmentListener interfaces
    // update GUI after new item/symbol is saved
    //
    @Override
    public void onAFLAddSymbolCompleted(long rowID) {
        Log.i(DEBUG_TAG, "in onAFLAddSymbolCompleted()");
        // removes top of back stack
        getFragmentManager().popBackStack();

        // tablet?
        if (isATabletDevice()) {
            // removes top of back stack
            getFragmentManager().popBackStack();
            // refresh inventory
            inventoryFragment.updateListView();

            // on tablet, display contact that was just added or edited
            displaySymbol(rowID, R.id.rightPaneContainer);
        }
    } // end method onAFLAddSymbolCompleted
    /***************************************************************
     *
     * End of AddFragmentListener interfaces implementations
     *
     ***************************************************************/



    /***************************************************************
     *
     * Start of EditFragmentListener interfaces implementations
     *
     *      1.  onEFLEditSymbolCompleted
     *
     ***************************************************************/
    //
    // implementing EditFragmentListener interfaces
    // update GUI after updated item/symbol saved
    //
    @Override
    public void onEFLEditSymbolCompleted(long rowID) {
        Log.i(DEBUG_TAG, "in onEFLEditSymbolCompleted()");
        // removes top of back stack
        getFragmentManager().popBackStack();

        // tablet?
        if (isATabletDevice()) {
            // removes top of back stack
            getFragmentManager().popBackStack();
            // refresh inventory
            inventoryFragment.updateListView();

            // on tablet, display contact that was just added or edited
            displaySymbol(rowID, R.id.rightPaneContainer);
        }
    } // end method onEFLEditSymbolCompleted
    /***************************************************************
     *
     * End of EditFragmentListener interfaces implementations
     *
     ***************************************************************/



    /***************************************************************
     *
     * Start of DetailFragmentListener interfaces implementations
     *
     *      1. public void onDFLDeleteSymbolCompleted();
     *      2. public void onDFLEditSymbolSelected(Bundle arguments);
     *
     ***************************************************************/
    //
    // implementing DetailFragmentListener interfaces
    // return to inventory when displayed item/symbol is deleted
    //
    @Override
    public void onDFLDeleteSymbolCompleted() {
        Log.i(DEBUG_TAG, "in onDFLDeleteSymbolCompleted()");
        getFragmentManager().popBackStack(); // removes top of back stack

        if (isATabletDevice()) {
            // must be a tablet
            inventoryFragment.updateListView();
        }
    } // end method onDFLDeleteSymbolCompleted


    //
    // implementing DetailFragmentListener interfaces
    // display the updated data after edit is complete
    //
    @Override
    public void onDFLEditSymbolSelected(Bundle arguments) {
        Log.i(DEBUG_TAG, "in onDFLEditSymbolSelected");
        if (isAPhoneDevice()) {
            // phone
            showEditFragment(R.id.fragmentContainer, arguments);
        }
        else {
            // must be a tablet
            showEditFragment(R.id.rightPaneContainer, arguments);
        }
    } // end method onDFLEditSymbolSelected
    /***************************************************************
     *
     * End of DetailFragmentListener interfaces implementations
     *
     ***************************************************************/




    /***************************************************************
     *
     *  worker function:
     *      public boolean isAPhoneDevice()
     *
     ***************************************************************/
    public boolean isAPhoneDevice() {
        return (findViewById(R.id.fragmentContainer) != null);
    }


    /***************************************************************
     *
     *  worker function:
     *      public boolean isATabletDevice()
     *
     ***************************************************************/
    public boolean isATabletDevice() {
        return (findViewById(R.id.fragmentContainer) == null);
    }


    /***************************************************************
     *
     *  worker function:
     *      private void displaySymbol(long rowID, int viewID)
     *
     *          display a item/symbol
     *
     ***************************************************************/
    private void displaySymbol(long rowID, int viewID) {
        Log.i(DEBUG_TAG, "in displayItem()");

        // save the rowID into a bundle for the DetailsFragment
        Bundle arguments = new Bundle();
        arguments.putLong(ROW_ID, rowID);

        // set the bundle as arguments into the DetailsFragment
        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(arguments);

        // use a FragmentTransaction to display the DetailsFragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(viewID, detailFragment);
        transaction.addToBackStack(null);
        // causes DetailsFragment to display
        transaction.commit();
    } // end method displaySymbol


    /***************************************************************
     *
     *  worker function:
     *      private void showAddFragment(int viewID, Bundle arguments)
     *
     *          display fragment for adding a new symbol
     *
     ***************************************************************/
    private void showAddFragment(int viewID, Bundle arguments) {
        Log.i(DEBUG_TAG, "in showAddFragment()");

        // set the bundled arguments into the DetailsFragment
        AddFragment addFragment = new AddFragment();
        if (arguments != null) {
            // editing existing symbol?
            addFragment.setArguments(arguments);
        }

        // use a FragmentTransaction to display the AddFragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(viewID, addFragment);
        transaction.addToBackStack(null);
        // causes AddFragment to display
        transaction.commit();
    } // end method showAddFragment


    /***************************************************************
     *
     *  worker function:
     *      private void showEditFragment(int viewID, Bundle arguments)
     *
     *          display fragment for editing an existing contact
     *
     ***************************************************************/
    private void showEditFragment(int viewID, Bundle arguments) {
        Log.i(DEBUG_TAG, "in showEditFragment()");

        // set the bundled arguments into the DetailsFragment
        EditFragment editFragment = new EditFragment();
        if (arguments != null) {
            // editing existing contact
            editFragment.setArguments(arguments);
        }

        // use a FragmentTransaction to display the EditFragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(viewID, editFragment);
        transaction.addToBackStack(null);
        // causes EditFragment to display
        transaction.commit();
    } // end method showEditFragment

}
