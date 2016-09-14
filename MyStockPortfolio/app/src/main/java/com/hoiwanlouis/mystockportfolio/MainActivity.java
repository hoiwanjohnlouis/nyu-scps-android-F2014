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
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

import com.hoiwanlouis.mystockportfolio.fields.Gui2Database;
import com.hoiwanlouis.mystockportfolio.fragments.AddStockFragment;
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
        AddStockFragment.AddStockFragmentListener,
        StockListFragment.StockListFragmentListener,
        StockDetailFragment.StockDetailFragmentListener {

    // for logging purposes
    private final String DEBUG_TAG = this.getClass().getSimpleName();
    // displays item/symbol list
    private AddStockFragment addStockFragment;
    private StockListFragment stockListFragment;
    private StockDetailFragment stockDetailFragment;
    //
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    //
    // display StockListFragment when Activity first loads
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(DEBUG_TAG, "in onCreate() ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Access the FragmentManager.
        fragmentManager = getFragmentManager();

        // return if Activity is being restored, no need to recreate GUI
        if (savedInstanceState != null) {
            return;
        }

        // check whether layout contains fragmentContainer (phone layout);
        // stockListFragment is always displayed, tablet devices will be initialized later in "onResume"
        if (isAPhoneDevice()) {
            addStockFragment = AddStockFragment.newInstance();
            stockDetailFragment = StockDetailFragment.newInstance();

            // create StockListFragment and add the fragment to the FrameLayout
            stockListFragment = StockListFragment.newInstance();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragmentContainer, stockListFragment);

            // causes ContactListFragment to display
            fragmentTransaction.commit();
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
        if (stockListFragment == null) {
            stockListFragment = (StockListFragment) fragmentManager.findFragmentById(R.id.stockListFragment);
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

        fragmentManager.popBackStack(); // removes the addFragment from top of back stack
        if (isAPhoneDevice()) {
            ;
        } else {
            // tablet
            fragmentManager.popBackStack(); // ensure we have removed all back stack entries
            stockListFragment.updateStockListView();
            showDetailFragment(R.id.rightPaneContainer, arguments);
        }
    }
    /***************************************************************
     * End of AddStockFragmentListener interfaces implementations
     ***************************************************************/

    /***************************************************************
     * Start of StockDetailFragmentListener interfaces implementations
     * <p>
     * 1. public void onStockDetailComplete();
     ***************************************************************/
    @Override
    public void onStockDetailComplete() {
        Log.i(DEBUG_TAG, "in onStockDetailComplete()");
        getFragmentManager().popBackStack(); // removes top of back stack

        if (isAPhoneDevice()) {
            ;
        } else {
            // must be a tablet
            stockListFragment.updateStockListView();
        }
    }
    /***************************************************************
     * End of StockDetailFragmentListener interfaces implementations
     ***************************************************************/

    /***************************************************************
     * Start of StockListFragmentListener interfaces implementations
     * <p>
     * 1.  public void onAddStockToDatabaseRequest();
     * 2.  public void onDeleteStockFromDatabaseComplete(final Bundle arguments)
     * 3.  public void onDisplayStockDetailRequest(final Bundle arguments)
     ***************************************************************/
    @Override
    public void onAddStockToDatabaseRequest() {
        Log.i(DEBUG_TAG, "in onAddStockToDatabaseRequest()");
        if (isAPhoneDevice()) {
            showAddFragment(R.id.fragmentContainer, null);
        } else {
            showAddFragment(R.id.rightPaneContainer, null);
        }
    }

    @Override
    public void onDeleteStockFromDatabaseComplete(final Bundle arguments) {
        Log.i(DEBUG_TAG, "in onDeleteStockFromDatabaseComplete()");
        fragmentManager.popBackStack(); // removes top of back stack
        if (isAPhoneDevice()) {
            ;
        } else {
            // tablet
            stockListFragment.updateStockListView();
        }
    }

    @Override
    public void onDisplayStockDetailRequest(final Bundle arguments) {
        Log.i(DEBUG_TAG, "in onDisplayStockDetailRequest()");
        if (isAPhoneDevice()) {
            // phone
            showDetailFragment(R.id.fragmentContainer, arguments);
        } else {
            fragmentManager.popBackStack(); // ensure we have removed all back stack entries
            showDetailFragment(R.id.rightPaneContainer, arguments);
        }
    }
    /***************************************************************
     * End of StockListFragmentListener interfaces implementations
     ***************************************************************/

    /***************************************************************
     * worker function:
     * public boolean isAPhoneDevice()
     ***************************************************************/
    public boolean isAPhoneDevice() {
        Log.i(DEBUG_TAG, "in isAPhoneDevice()");
        return (findViewById(R.id.fragmentContainer) != null);
    }

    /***************************************************************
     * worker function:
     * private void showAddFragment(int viewID, Bundle arguments)
     * <p>
     * display fragment after adding a new symbol
     ***************************************************************/
    private void showAddFragment(final int viewID, final Bundle arguments) {
        Log.i(DEBUG_TAG, "in showAddFragment()");
        AddStockFragment fragment = AddStockFragment.newInstance();
        addBundleToAddFragment(fragment, arguments);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(viewID, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();   // causes fragment to display
    } // end method showAddFragment

    private void addBundleToAddFragment(final AddStockFragment fragment, final Bundle arguments) {
        final StringBuilder sb = new StringBuilder();
        if (arguments != null) {
            sb.append("requesting AddStockFragment for [");
            sb.append(Gui2Database.BUNDLE_KEY);
            sb.append("]=[");
            sb.append(arguments.getLong(Gui2Database.BUNDLE_KEY));
            sb.append("]");
            // editing existing symbol?
            fragment.setArguments(arguments);
        } else {
            sb.append("requesting AddStockFragment for [");
            sb.append(Gui2Database.BUNDLE_KEY);
            sb.append("]=[NULL KEY VALUE]");
        }
        Log.i(DEBUG_TAG, sb.toString());
    }


    /***************************************************************
     * worker function:
     * private void showDetailFragment(int viewID, Bundle arguments)
     * <p>
     * display a item/symbol
     ***************************************************************/
    private void showDetailFragment(final int viewID, final Bundle arguments) {
        Log.i(DEBUG_TAG, "in showDetailFragment()");
        StockDetailFragment fragment = StockDetailFragment.newInstance();
        addBundleToStockDetailFragment(fragment, arguments);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(viewID, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();   // display fragment
    } // end method showDetailFragment

    private void addBundleToStockDetailFragment(final StockDetailFragment fragment, final Bundle arguments) {
        Log.i(DEBUG_TAG, "in addBundleToStockDetailFragment()");
        StringBuilder sb = new StringBuilder();
        if (arguments != null) {
            sb.append("requesting StockDetailFragment for [");
            sb.append(Gui2Database.BUNDLE_KEY);
            sb.append("]=[");
            sb.append(arguments.getLong(Gui2Database.BUNDLE_KEY));
            sb.append("]");
            fragment.setArguments(arguments);
        } else {
            sb.append("requesting StockDetailFragment for [");
            sb.append(Gui2Database.BUNDLE_KEY);
            sb.append("]=[NULL KEY VALUE]");
        }
        Log.i(DEBUG_TAG, sb.toString());
    }

}
