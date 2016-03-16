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

import com.hoiwanlouis.mystockportfolio.fragments.AddStockFragment;
import com.hoiwanlouis.mystockportfolio.fragments.StockListFragment;
import com.hoiwanlouis.mystockportfolio.fragments.StockDetailFragment;

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
        implements
        AddStockFragment.OnAddStockFragmentListener,
        StockListFragment.StockListFragmentListener,
        StockDetailFragment.StockDetailFragmentListener {

    // for logging purposes
    private final String DEBUG_TAG = this.getClass().getSimpleName();
    // keys for storing row ID in Bundle passed to a fragment
    public static final String ROW_ID = "row_id";
    // displays item/symbol list
    private StockListFragment stockListFragment;

    //
    // display StockListFragment when Activity first loads
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
        // stockListFragment is always displayed, tablet devices will be initialized later in "onResume"
        if (isAPhoneDevice()) {
            // create ContactListFragment
            stockListFragment = new StockListFragment();

            // add the fragment to the FrameLayout
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.fragmentContainer, stockListFragment);
            transaction.add(R.id.fragmentContainer, stockListFragment);
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

        // if this is a tablet, then stockListFragment is not set yet. i.e. it is null,
        // so get reference from FragmentManager and set it.
        if (isAPhoneDevice()) {
            ;
        } else {
            stockListFragment =
                    (StockListFragment) getFragmentManager().findFragmentById(R.id.stockListFragment);
        }
    } // end method onResume
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    /***************************************************************
     *
     * Start of AddStockFragmentListener interfaces implementations
     *
     *      1. public void onASFLStockAdded();
     *
     ***************************************************************/
    //
    // implementing AddStockFragmentListener interfaces
    //
    @Override
    public void onASFLStockAdded() {
        Log.i(DEBUG_TAG, "in onASFLCompleted()");
        getFragmentManager().popBackStack(); // removes top of back stack

        if (isAPhoneDevice()) {
            ;
        } else {
            // must be a tablet
            stockListFragment.updateStocksListView();
        }
    }
    /***************************************************************
     *
     * End of AddStockFragmentListener interfaces implementations
     *
     ***************************************************************/
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    /***************************************************************
     *
     * Start of StockDetailFragmentListener interfaces implementations
     *
     *      1. public void onSDFLCompleted();
     *
     ***************************************************************/
    //
    // implementing StockDetailFragmentListener interfaces
    // return to inventory when displayed item/symbol is deleted
    //
    @Override
    public void onSDFLCompleted() {
        Log.i(DEBUG_TAG, "in onSDFLCompleted()");
        getFragmentManager().popBackStack(); // removes top of back stack

        if (isAPhoneDevice()) {
            ;
        } else {
            // must be a tablet
            stockListFragment.updateStocksListView();
        }
    }
    /***************************************************************
     *
     * End of StockDetailFragmentListener interfaces implementations
     *
     ***************************************************************/
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    /***************************************************************
     *
     * Start of StockListFragmentListener interfaces implementations
     *
     *      1.  public void onSLFLStockSelected(long rowID)
     *      2.  public void onSLFLStockDeleted();
     *
     ***************************************************************/
    //
    // implementing StockListFragmentListener interfaces
    // show the DetailsFragment for selected item/symbol
    //
    @Override
    public void onSLFLStockSelected(long rowID) {
        Log.i(DEBUG_TAG, "in onSLFLStockSelected()");
        if (isAPhoneDevice()) {
            // phone
            showDetailFragment(rowID, R.id.fragmentContainer);
        }
        else {
            // tablet
            getFragmentManager().popBackStack(); // removes top of back stack
            showDetailFragment(rowID, R.id.rightPaneContainer);
        }
    } // end method onSLFLStockSelected


    //
    // implementing StockListFragmentListener interfaces
    // showMainFragment to add a new item/symbol
    //
    @Override
    public void onSLFLStockDeleted() {
        Log.i(DEBUG_TAG, "in onSLFLStockDeleted()");
        if (isAPhoneDevice()) {
            showMainFragment(R.id.fragmentContainer, null);
        }
        else {
            showMainFragment(R.id.rightPaneContainer, null);
        }
    } // end method onSLFLStockDeleted
    /***************************************************************
     *
     * End of StockListFragmentListener interfaces implementations
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
     *      private void showMainFragment(int viewID, Bundle arguments)
     *
     *          display fragment after adding a new symbol
     *
     ***************************************************************/
    private void showMainFragment(int viewID, Bundle arguments) {
        Log.i(DEBUG_TAG, "in showMainFragment()");

        // set the bundled arguments into the DetailsFragment
        StockListFragment fragment = new StockListFragment();
        if (arguments != null) {
            // editing existing symbol?
            fragment.setArguments(arguments);
        }

        // use a FragmentTransaction to display the StockListFragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(viewID, fragment);
        transaction.addToBackStack(null);
        // causes fragment to display
        transaction.commit();
    } // end method showMainFragment


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
        StockDetailFragment stockDetailFragment = new StockDetailFragment();
        stockDetailFragment.setArguments(arguments);

        // use a FragmentTransaction to display the DetailsFragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(viewID, stockDetailFragment);
        transaction.addToBackStack(null);
        // causes DetailsFragment to display
        transaction.commit();
    } // end method showDetailFragment

///////////////////////////////////////////////////////////////////////////////
}
