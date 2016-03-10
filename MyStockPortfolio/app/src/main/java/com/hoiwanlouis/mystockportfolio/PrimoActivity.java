/***************************************************************************
 * Copyright March, 2016 HW Tech Services, LLC
 * <p/>
 * Login   HW Tech Services, LLC
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ***************************************************************************/
package com.hoiwanlouis.mystockportfolio;

// import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.hoiwanlouis.mystockportfolio.database.DatabaseAbstractActivity;
import com.hoiwanlouis.mystockportfolio.fragments.AddFragment;
import com.hoiwanlouis.mystockportfolio.fragments.EditFragment;
import com.hoiwanlouis.mystockportfolio.fragments.DetailFragment;
import com.hoiwanlouis.mystockportfolio.fragments.InventoryFragment;


/***************************************************************************
 * Program Synopsis
 * <p/>
 * Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * <p/>
 * Change History
 * ------Who----- ---When--- ---------------------What----------------------
 * H. Melville    1851.01.31 Wooden whales, or whales cut in profile out of
 * the small dark slabs of the noble South Sea war-wood, are frequently met
 * with in the forecastles of American whalers.
 ***************************************************************************/
public class PrimoActivity  extends DatabaseAbstractActivity
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
    // display InventoryFragment when Activity first loads
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        Log.i(DEBUG_TAG, "onCreateOptionsMenu Starting...");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.i(DEBUG_TAG, "onCreateOptionsMenu Ends");
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Log.i(DEBUG_TAG, "onOptionsItemSelected Starting...");

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        boolean selected;
        int id = item.getItemId();
        if (id == R.id.main_activity_settings_id)
        {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            selected = true;
        }
        else
        if (id == R.id.main_activity_help_id) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.main_activity_help_title);
            builder.setMessage(R.string.main_activity_help_data);
            AlertDialog dialog=builder.create();
            dialog.show();
            selected = true;
        }
        else
        if (id == R.id.main_activity_about_id) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.main_activity_about_title);
            builder.setMessage(R.string.main_activity_about_data);
            AlertDialog dialog=builder.create();
            dialog.show();
            selected = true;
        }
        else {
            selected = super.onOptionsItemSelected(item);
        }

        Log.i(DEBUG_TAG, "onOptionsItemSelected Ends: " + selected);
        return selected;
    }


    //
    // called when Activity resumes
    //
    @Override
    protected void onResume() {
        Log.i(DEBUG_TAG, "in onResume()");
        super.onResume();

        // if this is a tablet, then inventoryFragment is not set yet. i.e. it is null,
        // so get reference from FragmentManager and set it.
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
            displayTickerSymbolDetail(rowID, R.id.fragmentContainer);
        }
        else {
            // tablet
            getFragmentManager().popBackStack(); // removes top of back stack
            displayTickerSymbolDetail(rowID, R.id.rightPaneContainer);
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
            displayTickerSymbolDetail(rowID, R.id.rightPaneContainer);
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
            displayTickerSymbolDetail(rowID, R.id.rightPaneContainer);
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
     *      private void displayTickerSymbolDetail(long rowID, int viewID)
     *
     *          display a item/symbol
     *
     ***************************************************************/
    private void displayTickerSymbolDetail(long rowID, int viewID) {
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
    } // end method displayTickerSymbolDetail


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
