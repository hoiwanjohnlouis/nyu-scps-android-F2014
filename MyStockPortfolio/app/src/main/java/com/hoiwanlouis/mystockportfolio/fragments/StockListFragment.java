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
package com.hoiwanlouis.mystockportfolio.fragments;

import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hoiwanlouis.mystockportfolio.R;
import com.hoiwanlouis.mystockportfolio.SettingsActivity;
import com.hoiwanlouis.mystockportfolio.adapters.StockListCursorAdapter;
import com.hoiwanlouis.mystockportfolio.database.DatabaseConnector;


/**
 * A simple {@link Fragment} subclass.
 */
public class StockListFragment extends ListFragment {

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    //
    // callback methods implemented by caller/invoker
    //
    public interface StockListFragmentListener {
        // called when user selects a Stock for detail display
        void onSLFLStockSelected(long rowID);

        // called when user deletes a Stock
        void onSLFLStockDeleted();
    }


    //
    private final String DEBUG_TAG = this.getClass().getSimpleName();
    //
    private StockListFragmentListener mListener;
    //
    private ListView mListView;
    // adapter for ListView
    private CursorAdapter mCursorAdapter;
    //
//    private ImageButton addStockSaveButton;

    //
    //
    //
    public StockListFragment() {
        Log.i(DEBUG_TAG, "in StockListFragment()");
        // Required empty public constructor
    } // end constructor StockListFragment()


    // set StockListFragmentListener when Fragment is attached
    @Override
    public void onAttach(Context context) {
        Log.i(DEBUG_TAG, "in onAttach()");
        super.onAttach(context);
        // init callback to interface implementation
        mListener = (StockListFragmentListener) context;
    } // end method onAttach


    //
    // remove StockListFragmentListener when Fragment is detached
    //
    @Override
    public void onDetach() {
        Log.i(DEBUG_TAG, "in onDetach()");
        super.onDetach();
        // clean up callback for interface implementation
        mListener = null;
    } // end method onDetach


    //
    // respond to user touching an item/symbol in the ListView
    //
    AdapterView.OnItemClickListener onItemClickListener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.i(DEBUG_TAG, "in onItemClickListener()");
                    // let the callback take care of this, normally for StockDetailFragment to handle
                    mListener.onSLFLStockSelected(id);
                }
    };


    // long click will allow edit symbol
    AdapterView.OnItemLongClickListener onItemLongClickListener =
            new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.i(DEBUG_TAG, "in onItemLongClickListener()");
                    final DatabaseConnector databaseConnector = new DatabaseConnector(getActivity());
                    // need tickerId and tickerSymbol for logging
                    final long tickerId = id;
                    TextView nameView = (TextView) view.findViewById(R.id.TextView_symbol);
                    final String tickerSymbol = nameView.getText().toString();
                    // Use an Alert dialog to confirm delete operation
                    new AlertDialog.Builder(getActivity())
                            .setMessage("Delete Symbol Record for " + tickerSymbol + "?")
                            .setPositiveButton("Delete",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog,
                                                            int which) {
                                            // Delete the Symbol
                                            databaseConnector.deleteOneStock(tickerId, tickerSymbol);
                                            // a symbol was deleted, refresh the data in our symbolList
                                            mListener.onSLFLStockDeleted();
                                        }
                                    }).show();
                    return false;
                }
    };


    //
    // called after Gui2Database is created
    //
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.i(DEBUG_TAG, "in onViewCreated()");
        super.onViewCreated(view, savedInstanceState);

        setRetainInstance(true);
        setHasOptionsMenu(true);
        // set text to display when there is no data
        setEmptyText(getResources().getString(R.string.fragment_no_items));

        // get ListView reference and configure it (the ListView)
//        mListView = getListView();
        mListView = (ListView) view.findViewById(R.id.symbolList);
        mListView.setOnItemClickListener(onItemClickListener);
        mListView.setOnItemLongClickListener(onItemLongClickListener);
        mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mListView.addFooterView(view.findViewById(R.id.copyright_layout));

        // map each item/symbol to a TextView in the ListView layout
        mCursorAdapter =
                new StockListCursorAdapter(
                        getActivity(),
                        null,
                        0);
//        mCursorAdapter =
//                new mCursorAdapter(
//                        getActivity(),
//                        R.layout.app_item,
//                        null,
//                        Gui2Database.fromDBColumns,
//                        Gui2Database.toRIds,
//                        0);

        // set adapter that supplies data
        mListView.setAdapter(mCursorAdapter);
        setListAdapter(mCursorAdapter);
    } // end method onViewCreated()


    //
    // when fragment resumes, use  GetContactsTask to load contacts
    //
    @Override
    public void onResume() {
        Log.i(DEBUG_TAG, "in onResume()");
        super.onResume();
        updateStocksListView();  // calls new AsyncTask().execute((Object[]) null);
    } // end method onResume()


    //
    // when fragment resumes, use  GetContactsTask to load contacts
    //
    @Override
    public void onStop() {
        Log.i(DEBUG_TAG, "in onStop()");
        // get current database cursor
        Cursor cursor = mCursorAdapter.getCursor();
        // adapter now has no cursor (basically housekeeping and cleanup
        mCursorAdapter.changeCursor(null);
        // release the cursor
        if (cursor != null) {
            cursor.close();
        }
        super.onStop();
    } // end method onStop()


    //
    // display this fragment's menu items
    //
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.i(DEBUG_TAG, "in onCreateOptionsMenu()");
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_add_menu, menu);
    } // end method onCreateOptionsMenu()


    //
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    //
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(DEBUG_TAG, "in onOptionsItemSelected()");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        AlertDialog dialog;

        boolean selected;
        int id = item.getItemId();

        switch (id)
        {
            case R.id.main_activity_settings_id:;
                Intent settingsIntent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(settingsIntent);
                selected = true;
                break;
            case R.id.main_activity_help_id:;
                builder.setTitle(R.string.main_activity_help_title);
                builder.setMessage(R.string.main_activity_help_data);
                dialog=builder.create();
                dialog.show();
                selected = true;
                break;
            case R.id.main_activity_about_id:;
                builder.setTitle(R.string.main_activity_about_title);
                builder.setMessage(R.string.main_activity_about_data);
                dialog=builder.create();
                dialog.show();
                selected = true;
                break;

//            case R.id.action_add:;
//                mListener.onSLFLStockDeleted();
//                selected = true;
//                break;

            default:;
                selected = super.onOptionsItemSelected(item);
                break;
        }

        return selected;
    } // end method onOptionsItemSelected()
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    //
    // update data set
    //
    public void updateStocksListView() {
        Log.i(DEBUG_TAG, "in updateStocksListView()");
        new GetAllStocksAsyncTask().execute((Object[]) null);
    } // end method updateStocksListView()


    //
    // *****************************************************
    // perform database query outside the GUI thread
    // *****************************************************
    private class GetAllStocksAsyncTask extends AsyncTask<Object, Object, Cursor> {

        DatabaseConnector databaseConnector = new DatabaseConnector(getActivity());

        //
        // openForUpdate DB and return a cursor for all contacts
        //
        @Override
        protected Cursor doInBackground(Object... params) {
            Log.i(DEBUG_TAG, "in GetAllStocksAsyncTask()/doInBackground()");
            databaseConnector.openForRead();
            return databaseConnector.getAllStocks();
        } // end method doInBackground()

        //
        // use cursor returned from the doInBackground method
        //
        @Override
        protected void onPostExecute(Cursor results) {
            Log.i(DEBUG_TAG, "in GetAllStocksAsyncTask()/onPostExecute()");
            mCursorAdapter.changeCursor(results);
//            setListAdapter(mCursorAdapter);
            databaseConnector.close();
        } // end method onPostExecute()

    } // end inner class GetContactsTask()

}
