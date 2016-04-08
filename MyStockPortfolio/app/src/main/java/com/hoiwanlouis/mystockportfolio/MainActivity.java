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
import android.net.Uri;
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
    private FragmentManager mFM;
    private FragmentTransaction mFT;

    //
    // display StockListFragment when Activity first loads
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(DEBUG_TAG, "in onCreate() ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Access the FragmentManager.
        mFM = getFragmentManager();

        // return if Activity is being restored, no need to recreate GUI
        if (savedInstanceState != null) {
            return;
        }

        // check whether layout contains fragmentContainer (phone layout);
        // stockListFragment is always displayed, tablet devices will be initialized later in "onResume"
        if (isAPhoneDevice()) {
            // create StockListFragment and add the fragment to the FrameLayout
            stockListFragment = StockListFragment.newInstance();
            mFT = mFM.beginTransaction();
            mFT.add(R.id.fragmentContainer, stockListFragment);

            // causes ContactListFragment to display
            mFT.commit();
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
            stockListFragment = (StockListFragment) mFM.findFragmentById(R.id.stockListFragment);
        }
    } // end method onResume

    //
    //
    //
    @Override
    public void onStart() {
        Log.i(DEBUG_TAG, "in onStart()");
        super.onStart();
    }

    //
    //
    //
    @Override
    public void onStop() {
        Log.i(DEBUG_TAG, "in onStop()");
        super.onStop();
    }

    @Override
    protected void onRestart() {
        Log.i(DEBUG_TAG, "in onRestart()");
        super.onRestart();
    }

    @Override
    protected void onPause() {
        Log.i(DEBUG_TAG, "in onPause()");
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.i(DEBUG_TAG, "in onDestroy()");
        super.onDestroy();
    }

    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////

    /***************************************************************
     * Start of AddStockFragmentListener interfaces implementations
     * <p>
     * 1. public void onASFLAddStockComplete(final Bundle arguments);
     ***************************************************************/
    //
    // implementing AddStockFragmentListener interfaces
    //
    @Override
    public void onASFLAddStockComplete(final Bundle arguments) {
        Log.i(DEBUG_TAG, "in onASFLCompleted()");

        mFM.popBackStack(); // removes the addFragment from top of back stack
        if (isAPhoneDevice()) {
            ;
        } else {
            // tablet
            mFM.popBackStack(); // ensure we have removed all back stack entries
            stockListFragment.updateStocksListView();
            showDetailFragment(R.id.rightPaneContainer, arguments);
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
     * Start of StockDetailFragmentListener interfaces implementations
     * <p>
     * 1. public void onSDFLStockDetailComplete();
     ***************************************************************/
    //
    // implementing StockDetailFragmentListener interfaces
    // return to inventory when displayed item/symbol is deleted
    //
    @Override
    public void onSDFLStockDetailComplete() {
        Log.i(DEBUG_TAG, "in onSDFLStockDetailComplete()");
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
     * Start of StockListFragmentListener interfaces implementations
     * <p>
     * 1.  public void onSLFLAddStockRequest();
     * 2.  public void onSLFLDeleteStockComplete(final Bundle arguments)
     * 3.  public void onSLFLStockDetailRequest(final Bundle arguments)
     ***************************************************************/
    //
    // implementing StockListFragmentListener interfaces
    // onSLFLAddStockRequest will add a new item/symbol
    //
    @Override
    public void onSLFLAddStockRequest() {
        Log.i(DEBUG_TAG, "in onSLFLAddStockRequest()");
        if (isAPhoneDevice()) {
            showAddFragment(R.id.fragmentContainer, null);
        } else {
            showAddFragment(R.id.rightPaneContainer, null);
        }
    } // end method onSLFLAddStockRequest

    //
    // implementing StockListFragmentListener interfaces
    // onSLFLDeleteStockComplete, just redisplay the screen
    //
    @Override
    public void onSLFLDeleteStockComplete(final Bundle arguments) {
        Log.i(DEBUG_TAG, "in onSLFLDeleteStockComplete()");

        mFM.popBackStack(); // removes top of back stack
        if (isAPhoneDevice()) {
            ;
        } else {
            // tablet
            stockListFragment.updateStocksListView();
        }
    } // end method onSLFLDeleteStockComplete

    //
    // implementing StockListFragmentListener interfaces
    // onSLFLStockDetailRequest will display the detail screen for selected stock
    //
    @Override
    public void onSLFLStockDetailRequest(final Bundle arguments) {
        Log.i(DEBUG_TAG, "in onSLFLStockDetailRequest()");
        if (isAPhoneDevice()) {
            // phone
            showDetailFragment(R.id.fragmentContainer, arguments);
        } else {
            mFM.popBackStack(); // ensure we have removed all back stack entries
            showDetailFragment(R.id.rightPaneContainer, arguments);
        }
    } // end method onSLFLStockDetailRequest

    /***************************************************************
     *
     * End of StockListFragmentListener interfaces implementations
     *
     ***************************************************************/
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////

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

        // set the bundled arguments into the DetailsFragment
        final AddStockFragment fragment = AddStockFragment.newInstance();
        final StringBuilder sb = new StringBuilder();
        // Bundle arguments is normally null
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

        // use a FragmentTransaction to display the fragment
        mFT = mFM.beginTransaction();
        mFT.replace(viewID, fragment);
        mFT.addToBackStack(null);
        // causes fragment to display
        mFT.commit();
    } // end method showAddFragment


    /***************************************************************
     * worker function:
     * private void showDetailFragment(long rowID, int viewID)
     * <p>
     * display a item/symbol
     ***************************************************************/
    private void showDetailFragment(final int viewID, final Bundle arguments) {
        Log.i(DEBUG_TAG, "in showDetailFragment()");

        // set the bundle as arguments into the Fragment
        final StockDetailFragment fragment = StockDetailFragment.newInstance();
        final StringBuilder sb = new StringBuilder();
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

        // use a FragmentTransaction to display the DetailsFragment
        mFT = mFM.beginTransaction();
        mFT.replace(viewID, fragment);
        mFT.addToBackStack(null);
        // causes DetailsFragment to display
        mFT.commit();
    } // end method showDetailFragment

///////////////////////////////////////////////////////////////////////////////
}
