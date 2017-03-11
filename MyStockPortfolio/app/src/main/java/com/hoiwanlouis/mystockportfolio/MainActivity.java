/***************************************************************************
 * Copyright March, 2016 HW Tech Services, LLC
 * <p>
 * Login   HW Tech Services, LLC
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
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

import com.hoiwanlouis.mystockportfolio.fields.Gui2Database;
import com.hoiwanlouis.mystockportfolio.fragments.AddEditStockFragment;
import com.hoiwanlouis.mystockportfolio.fragments.StockListFragment;
import com.hoiwanlouis.mystockportfolio.fragments.StockDetailFragment;

/***************************************************************************
 * Program Synopsis
 * <p>
 * Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * <p>
 * Change History
 * ------Who----- ---When--- ---------------------What----------------------
 * H. Melville    1851.01.31 Wooden whales, or whales cut in profile out of
 * the small dark slabs of the noble South Sea war-wood, are frequently met
 * with in the forecastles of American whalers.
 ***************************************************************************/
public class MainActivity extends Activity
        implements
        AddEditStockFragment.AddStockFragmentListener,
        StockListFragment.StockListFragmentListener,
        StockDetailFragment.StockDetailFragmentListener {

    private final String DEBUG_TAG = this.getClass().getSimpleName();

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

        // create StockListFragment, add the fragment to the FrameLayout
        if (isAPhoneDevice()) {
            stockListFragment = StockListFragment.newInstance();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.fragmentContainer, stockListFragment);
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
        if (isATabletDevice()) {
            // if this is a tablet, then stockListFragment is not set yet. i.e. it is null,
            // so get reference from FragmentManager and set it.
            stockListFragment = (StockListFragment) getFragmentManager().findFragmentById(R.id.stockListFragment);
        }
    } // end method onResume


    /***************************************************************
     * Start of AddStockFragmentListener interfaces implementations
     * <p>
     * 1. public void onAddStockComplete(final Bundle arguments);
     ***************************************************************/
    @Override
    public void onAddStockComplete(final Bundle arguments) {
        Log.i(DEBUG_TAG, "in onASFLCompleted()");
        getFragmentManager().popBackStack(); // removes the fragment from top of back stack
        if (isATabletDevice()) {
            getFragmentManager().popBackStack(); // removes the fragment from top of back stack
            stockListFragment.updateStockListView();
            showDetailFragment(R.id.rightPaneContainer, arguments);
        }
    }
    /***************************************************************
     * End of AddStockFragmentListener interfaces implementations
     ***************************************************************/

    ////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////

    /***************************************************************
     * Start of StockDetailFragmentListener interfaces implementations
     * <p>
     * 1. public void onDeleteStockComplete(); reuse code from StockListFragmentLister
     ***************************************************************/
    // display the AddEditFragment to edit an existing contact
    @Override
    public void onEditStockRequest(Bundle arguments) {
        Log.i(DEBUG_TAG, "in onEditContactRequest()");
        if (isAPhoneDevice()) {
            showAddEditFragment(R.id.fragmentContainer, arguments);
        }
        else {
            showAddEditFragment(R.id.rightPaneContainer, arguments);
        }
    }
    /***************************************************************
     * End of StockDetailFragmentListener interfaces implementations
     ***************************************************************/

    ////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////

    /***************************************************************
     * Start of StockListFragmentListener interfaces implementations
     * <p>
     * 1.  public void onAddStockRequest();
     * 2.  public void onDeleteStockComplete(final Bundle arguments)
     * 3.  public void onDisplayStockDetailRequest(final Bundle arguments)
     ***************************************************************/
    @Override
    public void onAddStockRequest() {
        Log.i(DEBUG_TAG, "in onAddStockRequest()");
        // Adding a stock always sends an empty bundle
        if (isAPhoneDevice()) {
            showAddEditFragment(R.id.fragmentContainer, null);
        } else {
            showAddEditFragment(R.id.rightPaneContainer, null);
        }
    }

    // StockDetailListener also uses this method
    @Override
    public void onDeleteStockComplete(final Bundle arguments) {
        Log.i(DEBUG_TAG, "in onDeleteStockComplete()");
        getFragmentManager().popBackStack(); // removes top of back stack
        if (isATabletDevice()) {
            stockListFragment.updateStockListView();
        }
    }

    @Override
    public void onDisplayStockDetailRequest(final Bundle arguments) {
        Log.i(DEBUG_TAG, "in onDisplayStockDetailRequest()");
        if (isAPhoneDevice()) {
            showDetailFragment(R.id.fragmentContainer, arguments);
        } else {
            showDetailFragment(R.id.rightPaneContainer, arguments);
        }
    }
    /***************************************************************
     * End of StockListFragmentListener interfaces implementations
     ***************************************************************/

    ////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////

    /***************************************************************
     * start of worker functions
     ***************************************************************/

    /***************************************************************
     * worker function:
     * private boolean isAPhoneDevice()
     ***************************************************************/
    private boolean isAPhoneDevice() {
        Log.i(DEBUG_TAG, "in isAPhoneDevice()");
        return (null != findViewById(R.id.fragmentContainer));
    }

    /***************************************************************
     * worker function:
     * private boolean isATabletDevice()
     ***************************************************************/
    private boolean isATabletDevice() {
        Log.i(DEBUG_TAG, "in isATabletDevice()");
        return (null == findViewById(R.id.fragmentContainer));
    }

    /***************************************************************
     * worker function:
     * showAddEditFragment(int viewID, Bundle arguments)
     * <p>
     * display fragment after adding a new symbol
     ***************************************************************/
    private void showAddEditFragment(final int viewID, final Bundle arguments) {
        Log.i(DEBUG_TAG, "in showAddEditFragment()");
        AddEditStockFragment fragment = AddEditStockFragment.newInstance();
        addBundleToAddFragment(fragment, arguments);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(viewID, fragment);
        transaction.addToBackStack("showAddEditFragment(final int viewID{" + viewID + "}, final Bundle arguments{" + arguments + "})");
        transaction.commit();   // causes fragment to display
    }

    /***************************************************************
     * worker function:
     * addBundleToAddFragment(final AddEditStockFragment fragment, final Bundle arguments)
     * <p>
     * load bundle into fragment and print log msg
     ***************************************************************/
    private void addBundleToAddFragment(final AddEditStockFragment fragment, final Bundle arguments) {
        final StringBuilder logMessage = new StringBuilder();
        if (arguments != null) {
            logMessage.append("requesting AddEditStockFragment for [");
            logMessage.append(Gui2Database.BUNDLE_KEY);
            logMessage.append("]=[");
            logMessage.append(arguments.getLong(Gui2Database.BUNDLE_KEY));
            logMessage.append("]");
            // editing existing symbol?
            fragment.setArguments(arguments);
        } else {
            logMessage.append("requesting AddEditStockFragment for [");
            logMessage.append(Gui2Database.BUNDLE_KEY);
            logMessage.append("]=[NULL KEY VALUE]");
        }
        Log.i(DEBUG_TAG, logMessage.toString());
    }

    /***************************************************************
     * worker function:
     * showDetailFragment(int viewID, Bundle arguments)
     * <p>
     * display a item/symbol
     ***************************************************************/
    private void showDetailFragment(final int viewID, final Bundle arguments) {
        Log.i(DEBUG_TAG, "in showDetailFragment()");
        StockDetailFragment fragment = StockDetailFragment.newInstance();
        addBundleToStockDetailFragment(fragment, arguments);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(viewID, fragment);
        transaction.addToBackStack("showDetailFragment(final int viewID{" + viewID + "}, final Bundle arguments{" + arguments + "})");
        transaction.commit();   // display fragment
    }

    /***************************************************************
     * worker function:
     * addBundleToStockDetailFragment(final StockDetailFragment fragment, final Bundle arguments)
     * <p>
     * load bundle into fragment and print log msg
     ***************************************************************/
    private void addBundleToStockDetailFragment(final StockDetailFragment fragment, final Bundle arguments) {
        Log.i(DEBUG_TAG, "in addBundleToStockDetailFragment()");
        StringBuilder logMessage = new StringBuilder();
        if (arguments != null) {
            logMessage.append("requesting StockDetailFragment for [");
            logMessage.append(Gui2Database.BUNDLE_KEY);
            logMessage.append("]=[");
            logMessage.append(arguments.getLong(Gui2Database.BUNDLE_KEY));
            logMessage.append("]");
            fragment.setArguments(arguments);
        } else {
            logMessage.append("requesting StockDetailFragment for [");
            logMessage.append(Gui2Database.BUNDLE_KEY);
            logMessage.append("]=[NULL KEY VALUE]");
        }
        Log.i(DEBUG_TAG, logMessage.toString());
    }

    /***************************************************************
     * End of worker functions
     ***************************************************************/
    ////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////

}
