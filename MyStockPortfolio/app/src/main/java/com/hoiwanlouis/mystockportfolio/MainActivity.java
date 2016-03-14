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

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

import com.hoiwanlouis.mystockportfolio.fragments.AddFragment;
import com.hoiwanlouis.mystockportfolio.fragments.DetailFragment;

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
public class MainActivity extends Activity
        implements AddFragment.AddFragmentListener,
                   DetailFragment.DetailFragmentListener {

    // for logging purposes
    private final String DEBUG_TAG = this.getClass().getSimpleName();

    // keys for storing row ID in Bundle passed to a fragment
    public static final String ROW_ID = "row_id";

    // displays item/symbol list
    private AddFragment addFragment;

    //
    // display AddFragment when Activity first loads
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

        // check whether layout contains fragmentContainer (phone layout);
        // addFragment is always displayed, tablet devices will be initialized later in "onResume"
        if (isAPhoneDevice()) {
            // create ContactListFragment
            addFragment = new AddFragment();

            // add the fragment to the FrameLayout
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.fragmentContainer, addFragment);
            // causes ContactListFragment to display
            transaction.commit();
        }

    } // end method onCreate()


    //
    // called when Activity resumes
    //
    @Override
    protected void onResume() {
        Log.i(DEBUG_TAG, "in onResume()");
        super.onResume();

        // if this is a tablet, then addFragment is not set yet. i.e. it is null,
        // so get reference from FragmentManager and set it.
        if (isAPhoneDevice()) {
            ;
        } else {
            addFragment =
                    (AddFragment) getFragmentManager().findFragmentById(R.id.inventoryFragment);
        }
    } // end method onResume
////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////
    /***************************************************************
     *
     * Start of AddFragmentListener interfaces implementations
     *
     *      1.  public void onAFLSymbolSelected(long rowID)
     *      2.  public void onAFLSymbolDeleted();
     *
     ***************************************************************/
    //
    // implementing AddFragmentListener interfaces
    // show the DetailsFragment for selected item/symbol
    //
    @Override
    public void onAFLSymbolSelected(long rowID) {
        Log.i(DEBUG_TAG, "in onAFLSymbolSelected()");
        if (isAPhoneDevice()) {
            // phone
            showDetailFragment(rowID, R.id.fragmentContainer);
        }
        else {
            // tablet
            getFragmentManager().popBackStack(); // removes top of back stack
            showDetailFragment(rowID, R.id.rightPaneContainer);
        }
    } // end method onAFLSymbolSelected


    //
    // implementing AddFragmentListener interfaces
    // showAddFragment to add a new item/symbol
    //
    @Override
    public void onAFLSymbolDeleted() {
        Log.i(DEBUG_TAG, "in onAFLSymbolDeleted()");
        if (isAPhoneDevice()) {
            showAddFragment(R.id.fragmentContainer, null);
        }
        else {
            showAddFragment(R.id.rightPaneContainer, null);
        }
    } // end method onAFLSymbolDeleted
    /***************************************************************
     *
     * End of AddFragmentListener interfaces implementations
     *
     ***************************************************************/
////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////
    /***************************************************************
     *
     * Start of DetailFragmentListener interfaces implementations
     *
     *      1. public void onDFLCompleted();
     *
     ***************************************************************/
    //
    // implementing DetailFragmentListener interfaces
    // return to inventory when displayed item/symbol is deleted
    //
    @Override
    public void onDFLCompleted() {
        Log.i(DEBUG_TAG, "in onDFLCompleted()");
        getFragmentManager().popBackStack(); // removes top of back stack

        if (isAPhoneDevice()) {
            ;
        } else {
            // must be a tablet
            addFragment.updateStocksListView();
        }
    } // end method onDFLCompleted
    /***************************************************************
     *
     * End of DetailFragmentListener interfaces implementations
     *
     ***************************************************************/
////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////
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
     *      private void showAddFragment(int viewID, Bundle arguments)
     *
     *          display fragment after adding a new symbol
     *
     ***************************************************************/
    private void showAddFragment(int viewID, Bundle arguments) {
        Log.i(DEBUG_TAG, "in showAddFragment()");

        // set the bundled arguments into the DetailsFragment
        AddFragment fragment = new AddFragment();
        if (arguments != null) {
            // editing existing symbol?
            fragment.setArguments(arguments);
        }

        // use a FragmentTransaction to display the AddFragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(viewID, fragment);
        transaction.addToBackStack(null);
        // causes fragment to display
        transaction.commit();
    } // end method showAddFragment


    /***************************************************************
     *
     *  worker function:
     *      private void showDetailFragment(long rowID, int viewID)
     *
     *          display a item/symbol
     *
     ***************************************************************/
    private void showDetailFragment(long rowID, int viewID) {
        Log.i(DEBUG_TAG, "in showDetailFragment()");

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
    } // end method showDetailFragment


///////////////////////////////////////////////////////////////////////////////
}
