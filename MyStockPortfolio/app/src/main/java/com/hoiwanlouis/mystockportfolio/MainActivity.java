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
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import android.view.MenuItem;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
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
        AddStockFragment.OnAddStockFragmentListener,
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
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

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
            // create ContactListFragment
            addStockFragment = AddStockFragment.newInstance();
            stockListFragment = StockListFragment.newInstance();
            stockDetailFragment = StockDetailFragment.newInstance();

            // add the fragment to the FrameLayout
            mFT = mFM.beginTransaction();
            mFT.add(R.id.fragmentContainer, addStockFragment);
            mFT.add(R.id.fragmentContainer, stockListFragment);
//            mFT.add(R.id.fragmentContainer, stockDetailFragment);

            // causes ContactListFragment to display
            mFT.commit();
        }

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    } // end method onCreate()


    //
    // display this fragment's menu items
    //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(DEBUG_TAG, "in onCreateOptionsMenu()");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    } // end method onCreateOptionsMenu()


    //
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    //
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(DEBUG_TAG, "in onOptionsItemSelected()");

        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        AlertDialog dialog;

        boolean selected;
        int id = item.getItemId();

        switch (id) {
            case R.id.main_activity_settings_id:
                startActivity( new Intent(getApplicationContext(), SettingsActivity.class));
                selected = true;
                break;
            case R.id.main_activity_help_id:
                builder.setTitle(R.string.main_activity_help_title);
                builder.setMessage(R.string.main_activity_help_data);
                dialog = builder.create();
                dialog.show();
                selected = true;
                break;
            case R.id.main_activity_about_id:
                builder.setTitle(R.string.main_activity_about_title);
                builder.setMessage(R.string.main_activity_about_data);
                dialog = builder.create();
                dialog.show();
                selected = true;
                break;

//            case R.id.action_add:;
//                mListener.onSLFLStockDeleted();
//                selected = true;
//                break;

            default:
                ;
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

        // if this is a tablet, then stockListFragment is not set yet. i.e. it is null,
        // so get reference from FragmentManager and set it.
        if (isAPhoneDevice()) {
            ;
        } else {
            stockListFragment = (StockListFragment) mFM.findFragmentById(R.id.stockListFragment);
        }
    } // end method onResume

    //
    //
    //
    @Override
    public void onStart() {
        super.onStart();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.hoiwanlouis.mystockportfolio/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    //
    //
    //
    @Override
    public void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.hoiwanlouis.mystockportfolio/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.disconnect();
    }

    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////

    /***************************************************************
     * Start of AddStockFragmentListener interfaces implementations
     * <p>
     * 1. public void onASFLStockAdded();
     ***************************************************************/
    //
    // implementing AddStockFragmentListener interfaces
    //
    @Override
    public void onASFLStockAdded() {
        Log.i(DEBUG_TAG, "in onASFLCompleted()");
        mFM.popBackStack(); // removes top of back stack

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
     * Start of StockDetailFragmentListener interfaces implementations
     * <p>
     * 1. public void onSDFLCompleted();
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
     * Start of StockListFragmentListener interfaces implementations
     * <p>
     * 1.  public void onSLFLStockSelected(long rowID)
     * 2.  public void onSLFLStockDeleted();
     ***************************************************************/
    //
    // implementing StockListFragmentListener interfaces
    // show the DetailsFragment for selected item/symbol
    //
    @Override
    public void onSLFLStockSelected(Bundle arguments) {
        Log.i(DEBUG_TAG, "in onSLFLStockSelected()");
        if (isAPhoneDevice()) {
            // phone
            showDetailFragment(R.id.fragmentContainer, arguments);
        } else {
            // tablet
            mFM.popBackStack(); // removes top of back stack
            showDetailFragment(R.id.rightPaneContainer, arguments);
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
        } else {
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
     * worker function:
     * public boolean isAPhoneDevice()
     ***************************************************************/
    public boolean isAPhoneDevice() {
        return (findViewById(R.id.fragmentContainer) != null);
    }


    /***************************************************************
     * worker function:
     * private void showMainFragment(int viewID, Bundle arguments)
     * <p>
     * display fragment after adding a new symbol
     ***************************************************************/
    private void showMainFragment(int viewID, Bundle arguments) {
        Log.i(DEBUG_TAG, "in showMainFragment()");

        // set the bundled arguments into the DetailsFragment
        StockListFragment fragment = StockListFragment.newInstance();
        if (arguments != null) {
            // editing existing symbol?
            fragment.setArguments(arguments);
        }

        // use a FragmentTransaction to display the StockListFragment
        mFT = mFM.beginTransaction();
        mFT.replace(viewID, fragment);
        mFT.addToBackStack(null);

        // causes fragment to display
        mFT.commit();
    } // end method showMainFragment


    /***************************************************************
     * worker function:
     * private void showDetailFragment(long rowID, int viewID)
     * <p>
     * display a item/symbol
     ***************************************************************/
    private void showDetailFragment(int viewID, Bundle arguments) {
        Log.i(DEBUG_TAG, "in showDetailFragment()");

        // set the bundle as arguments into the Fragment
        StockDetailFragment fragment = StockDetailFragment.newInstance();

        StringBuilder sb = new StringBuilder();
        if (arguments != null) {
            sb.append("requesting StockDetailFragment for [");
            sb.append(Gui2Database.BUNDLE_KEY);
            sb.append("]=[");
            sb.append(arguments.getLong(Gui2Database.BUNDLE_KEY));
            sb.append("]");
            Log.i(DEBUG_TAG, sb.toString());
            fragment.setArguments(arguments);
        } else {
            sb.append("requesting StockDetailFragment for [");
            sb.append(Gui2Database.BUNDLE_KEY);
            sb.append("]=[NULL KEY VALUE]");
            Log.i(DEBUG_TAG, sb.toString());
        }

        // use a FragmentTransaction to display the DetailsFragment
        mFT = mFM.beginTransaction();
        mFT.replace(viewID, fragment);
        mFT.addToBackStack(null);

        // causes DetailsFragment to display
        mFT.commit();
    } // end method showDetailFragment

///////////////////////////////////////////////////////////////////////////////
}
