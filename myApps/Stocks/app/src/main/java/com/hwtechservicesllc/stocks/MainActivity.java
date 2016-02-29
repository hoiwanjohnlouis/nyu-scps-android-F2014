package com.hwtechservicesllc.stocks;

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
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.hwtechservicesllc.stocks.fragments.AddFragment;
import com.hwtechservicesllc.stocks.fragments.CopyrightFragment;
import com.hwtechservicesllc.stocks.fragments.DetailFragment;
import com.hwtechservicesllc.stocks.fragments.FragmentConstants;
import com.hwtechservicesllc.stocks.fragments.HeadingFragment;
import com.hwtechservicesllc.stocks.fragments.SymbolsFragment;
import com.hwtechservicesllc.stocks.fragments.UpdateFragment;


public class MainActivity extends Activity
        implements
        AddFragment.OnFragmentInteractionListener,
        CopyrightFragment.OnFragmentInteractionListener,
        DetailFragment.OnFragmentInteractionListener,
        HeadingFragment.OnFragmentInteractionListener,
        SymbolsFragment.OnFragmentInteractionListener,
        UpdateFragment.OnFragmentInteractionListener {


    // the fragment bundle key 1: ARG_ITEM_STRING
    private static final String ARG_ROW_ID = FragmentConstants.ROW_ID;

    // the fragment bundle data 1: VALUE DATA
    private long mRowId;


    // for logging purposes
    private final String DEBUG_TAG = this.getClass().getSimpleName();

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    // displays item/symbol list
    AddFragment addFragment;
    CopyrightFragment copyrightFragment;
    DetailFragment detailFragment;
    HeadingFragment headingFragment;
    SymbolsFragment symbolsFragment;
    UpdateFragment updateFragment;


    //
    // display SymbolsFragment when Activity first loads
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(DEBUG_TAG, "in onCreate() ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // return if Activity is being restored, no need to recreate GUI
        if (savedInstanceState != null) {
            return;
        }

        // reusable fragmentManager
        fragmentManager = getFragmentManager();

        // check whether layout contains  fragment_container (phone layout);
        // symbolsFragment is always displayed
        if (m000_isAPhoneDevice()) {
            // worker function to load standard fragments
            m100_loadMainFragments();
        }

    } // end method onCreate()


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(DEBUG_TAG, "in onOptionsItemSelected()");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    } // end method onCreateOptionsMenu()


    //
    // Handle menu item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    //
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(DEBUG_TAG, "in onOptionsItemSelected()");

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        AlertDialog dialog;

        boolean selected;
        int id = item.getItemId();

        switch (id)
        {
            case R.id.main_activity_settings_id:
                // maybe back stack should be used?
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                selected = true;
                break;
            case R.id.main_activity_help_id:
                builder.setTitle(R.string.main_activity_help_title);
                builder.setMessage(R.string.main_activity_help_data);
                dialog=builder.create();
                dialog.show();
                selected = true;
                break;
            case R.id.main_activity_about_id:
                builder.setTitle(R.string.main_activity_about_title);
                builder.setMessage(R.string.main_activity_about_data);
                dialog=builder.create();
                dialog.show();
                selected = true;
                break;
            default:
                selected = super.onOptionsItemSelected(item);
                break;
        }

        return selected;
    } // end method onOptionsItemSelected()


    //
    // called when Activity resumes
    //
    @Override
    protected void onResume() {
        Log.i(DEBUG_TAG, "in onResume()");
        super.onResume();

        // symbolsFragment is always displayed
        if (m000_isAPhoneDevice()) {
            // refresh fragments
            m110_deleteMainFragments();
            m100_loadMainFragments();
        }

        // if this is a tablet, then symbolsFragment is null,
        // so get reference from FragmentManager
        if (m010_isATabletDevice()) {
            // todo: how to handle tablets?
            m110_deleteMainFragments();
            m100_loadMainFragments();
        }
    } // end method onResume


    /**
     * ************************************************************
     * <p/>
     * Start of AddFragment.OnFragmentInteractionListener
     * interface(s) implementations
     * <p/>
     * 1.  onAddFragmentSymbolCompleted
     * <p/>
     * *************************************************************
     */
    //
    // implementing AddFragmentListener interfaces
    // update GUI after new item/symbol is saved
    //
    @Override
    public void onAddFragmentSymbolCompleted(Bundle addFragmentInterfaceBundle) {
        Log.i(DEBUG_TAG, "in onAddFragmentSymbolCompleted()");

        //
        mRowId = m020_getRowId(addFragmentInterfaceBundle);

        // refresh symbol list
        symbolsFragment.updateListView();

        // symbolsFragment is always displayed
/*
        if (m000_isAPhoneDevice()) {
            // refresh fragments
            m110_deleteMainFragments();
            m100_loadMainFragments();
        }

        if (m010_isATabletDevice()) {
            // todo: this logic may be incorrect, need tablet to check
            // removes top of back stack
            getFragmentManager().popBackStack();
            // on tablet, update display symbol details to reflect new symbol
            m200_displayDetailFragment(R.id.right_pane_container_id, addFragmentInterfaceBundle);
        }
*/
    } // end method
    /***************************************************************
     *
     * End of AddFragment.OnFragmentInteractionListener
     * interface(s) implementations
     *
     ***************************************************************/


    /**
     * ************************************************************
     * <p/>
     * Start of CopyrightFragment.OnFragmentInteractionListener
     * interface(s) implementations
     * <p/>
     * 1.  onCopyrightFragmentSymbolCompleted(Bundle copyrightFragmentInterfaceBundle)
     * <p/>
     * *************************************************************
     */
    public void onCopyrightFragmentSymbolCompleted(Bundle copyrightFragmentInterfaceBundle) {
        // nothing to do since this is a display only fragment
    }
    /***************************************************************
     *
     * Start of CopyrightFragment.OnFragmentInteractionListener
     * interface(s) implementations
     *
     ***************************************************************/


    /**
     * ************************************************************
     * <p/>
     * Start of DetailFragment.OnFragmentInteractionListener
     * interface(s) implementations
     * <p/>
     * 1. public void onDeleteSymbolCompleted();
     * 2. public void onDetailFragmentSymbolSelected(Bundle arguments);
     * <p/>
     * *************************************************************
     */
    //
    // implementing DetailFragmentListener interfaces
    // return to inventory when displayed item/symbol is deleted
    //
    @Override
    public void onDetailFragmentSymbolDeleted() {
        Log.i(DEBUG_TAG, "in onDetailFragmentSymbolDeleted()");

        getFragmentManager().popBackStack(); // removes top of back stack

        if (m010_isATabletDevice()) {
            // must be a tablet
            symbolsFragment.updateListView();
        }
    } // end method onSymbolDeleted


    //
    // implementing DetailFragmentListener interfaces
    // display the updated data after edit is complete
    //
    @Override
    public void onDetailFragmentSymbolSelected(Bundle detailFragmentInterfaceBundle) {
        Log.i(DEBUG_TAG, "in onDetailFragmentSymbolSelected");

        //
        mRowId = m020_getRowId(detailFragmentInterfaceBundle);
        if (m000_isAPhoneDevice()) {
            m200_displayDetailFragment(R.id.main_container_id, detailFragmentInterfaceBundle);
        }
        if (m010_isATabletDevice()) {
            getFragmentManager().popBackStack(); // removes top of back stack
            m200_displayDetailFragment(R.id.right_pane_container_id, detailFragmentInterfaceBundle);
        }

    } // end method onEditSymbol
    /***************************************************************
     *
     * Start of DetailFragment.OnFragmentInteractionListener
     * interface(s) implementations
     *
     ***************************************************************/


    /**
     * ************************************************************
     * <p/>
     * Start of HeadingFragment.OnFragmentInteractionListener
     * interface(s) implementations
     * <p/>
     * 1.  onHeadingFragmentSymbolCompleted(Bundle headingFragmentInterfaceBundle)
     * <p/>
     * *************************************************************
     */
    public void onHeadingFragmentSymbolCompleted(Bundle headingFragmentInterfaceBundle) {
        // nothing to do since this is a display only fragment
    }
    /***************************************************************
     *
     * Start of CopyrightFragment.OnFragmentInteractionListener
     * interface(s) implementations
     *
     ***************************************************************/

    /**
     * ************************************************************
     * <p/>
     * Start of SymbolsFragment.OnFragmentInteractionListener
     * interface(s) implementations
     * <p/>
     * 1.  public void onSymbolsFragmentSymbolSelected(Bundle inventoryInterfaceBundle)
     * <p/>
     * *************************************************************
     */
    //
    // implementing SymbolsFragmentListener interfaces
    // display DetailsFragment for selected item/symbol
    //
    @Override
    public void onSymbolsFragmentSymbolSelected(Bundle symbolsFragmentInterfaceBundle) {
        Log.i(DEBUG_TAG, "in onSymbolsFragmentSymbolSelected()");

        //
        mRowId = m020_getRowId(symbolsFragmentInterfaceBundle);
        if (m000_isAPhoneDevice()) {
            m200_displayDetailFragment(R.id.main_container_id, symbolsFragmentInterfaceBundle);
        }
        if (m010_isATabletDevice()) {
            getFragmentManager().popBackStack(); // removes top of back stack
            m200_displayDetailFragment(R.id.right_pane_container_id, symbolsFragmentInterfaceBundle);
        }

    } // end method onSymbolsFragmentSymbolSelected
    /***************************************************************
     *
     * Start of SymbolsFragment.OnFragmentInteractionListener
     * interface(s) implementations
     *
     ***************************************************************/


    /**
     * ************************************************************
     * <p/>
     * Start of UpdateFragment.OnFragmentInteractionListener
     * interface(s) implementations
     * <p/>
     * 1.  onUpdateFragmentSymbolCompleted
     * <p/>
     * *************************************************************
     */
    //
    // implementing UpdateFragmentListener interfaces
    // update GUI after updated item/symbol saved
    //
    @Override
    public void onUpdateFragmentSymbolCompleted(Bundle updateFragmentInterfaceBundle) {
        Log.i(DEBUG_TAG, "in onUpdateFragmentSymbolCompleted()");

        //
        mRowId = m020_getRowId(updateFragmentInterfaceBundle);

        // removes top of back stack
        getFragmentManager().popBackStack();

        // always refresh inventory display after any update to the database
        symbolsFragment.updateListView();

        // tablet?
        if (m010_isATabletDevice()) {
            getFragmentManager().popBackStack();        // removes top of back stack
            symbolsFragment.updateListView();           // refresh inventory
            // on tablet, display contact that was just added or edited
            m200_displayDetailFragment(R.id.right_pane_container_id, updateFragmentInterfaceBundle);
        }

    } // end method onUpdateSymbolCompleted
    /***************************************************************
     *
     * Start of UpdateFragment.OnFragmentInteractionListener
     * interface(s) implementations
     *
     ***************************************************************/


    /**
     * ************************************************************
     * <p/>
     * worker function:
     * public boolean m000_isAPhoneDevice()
     * <p/>
     * *************************************************************
     */
    public boolean m000_isAPhoneDevice() {
        return (findViewById(R.id.main_container_id) != null);
    }


    /**
     * ************************************************************
     * <p/>
     * worker function:
     * public boolean m010_isATabletDevice()
     * <p/>
     * *************************************************************
     */
    public boolean m010_isATabletDevice() {
        return (findViewById(R.id.main_container_id) == null);
    }


    /**
     * ************************************************************
     * <p/>
     * worker function:
     * public long m020_getRowId(Bundle bundle)
     * <p/>
     * @param bundle, bundle containing data returned from fragment
     * *************************************************************
     */
    public long m020_getRowId(Bundle bundle) {
        long lRowId = -1;
        if (bundle != null) {
            lRowId = bundle.getLong(ARG_ROW_ID);
        }
        return lRowId;
    }


    /**
     * ************************************************************
     * <p/>
     * worker function:
     * public void m100_loadMainFragments()
     * <p/>
     * Method to load the fragments for main activity.
     * <p/>
     * *************************************************************
     */
    private void m100_loadMainFragments() {
        Log.i(DEBUG_TAG, "in m100_loadMainFragments()");

        // set up args as needed
        Bundle arguments = new Bundle();
        arguments.putLong(ARG_ROW_ID,mRowId);

        // create the fragments to be displayed
        addFragment = AddFragment.newInstance();
        headingFragment = HeadingFragment.newInstance();
        symbolsFragment = SymbolsFragment.newInstance();
        copyrightFragment = CopyrightFragment.newInstance();

        // add the fragments to the container
        fragmentTransaction = getFragmentManager().beginTransaction();

        fragmentTransaction.add(R.id.add_fragment_id, addFragment);
        fragmentTransaction.add(R.id.heading_fragment_id, headingFragment);
        fragmentTransaction.add(R.id.symbols_fragment_id, symbolsFragment);
        fragmentTransaction.add(R.id.copyright_fragment_id, copyrightFragment);

        // causes fragments to display
        fragmentTransaction.commit();
    }


    /**
     * ************************************************************
     * <p/>
     * worker function:
     * public void m110_deleteMainFragments()
     * <p/>
     * Method to delete the fragments, cleanup old fragments for main activity
     * <p/>
     * *************************************************************
     */
    private void m110_deleteMainFragments() {
        Log.i(DEBUG_TAG, "in m110_deleteMainFragments()");

        fragmentTransaction = getFragmentManager().beginTransaction();

        // clean up fragments in the container
        fragmentTransaction.remove(copyrightFragment);
        fragmentTransaction.remove(symbolsFragment);
        fragmentTransaction.remove(headingFragment);
        fragmentTransaction.remove(addFragment);

        // causes fragments to display
        fragmentTransaction.commit();
    }


    /**
     * ************************************************************
     * <p/>
     * worker function:
     * private void m200_displayDetailFragment(int viewID, Bundle interfaceBundle)
     * <p/>
     * display a item/symbol detail
     * <p/>
     * *************************************************************
     */
    private void m200_displayDetailFragment(int viewID, Bundle fragmentInterfaceBundle) {
        Log.i(DEBUG_TAG, "in m200_displayDetailFragment()");

        // the rowID is already in a bundle from:
        //          AddFragment/onAddSymbolCompleted
        //          SymbolsFragment/onSymbolSelected interface
        //          UpdateFragment/onUpdateSymbolCompleted interface
        // pass the bundle as arguments into the DetailsFragment
        detailFragment = DetailFragment.newInstance();
        if (fragmentInterfaceBundle != null) {
        detailFragment.setArguments(fragmentInterfaceBundle);
        }

        // use a FragmentTransaction to display the DetailsFragment
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(viewID, detailFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();  // causes DetailsFragment to display

    } // end method displaySymbolDetail


    /**
     * ************************************************************
     * <p/>
     * worker function:
     * private void m210_displayUpdateFragment(int viewID, Bundle arguments)
     * <p/>
     * display fragment for editing an existing contact
     * <p/>
     * *************************************************************
     */
    private void m210_displayUpdateFragment(int viewID, Bundle arguments) {
        Log.i(DEBUG_TAG, "in m210_displayUpdateFragment()");

        // set the bundled arguments into the DetailsFragment
        updateFragment = UpdateFragment.newInstance();
        if (arguments != null) {
            // editing existing contact
            updateFragment.setArguments(arguments);
        }

        // use a FragmentTransaction to display the UpdateFragment
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(viewID, updateFragment);
        fragmentTransaction.addToBackStack(null);

        // causes UpdateFragment to display
        fragmentTransaction.commit();

    } // end method m210_displayUpdateFragment


}