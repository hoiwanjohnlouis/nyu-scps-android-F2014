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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.hoiwanlouis.mystockportfolio.R;
import com.hoiwanlouis.mystockportfolio.SettingsActivity;
import com.hoiwanlouis.mystockportfolio.database.Database;
import com.hoiwanlouis.mystockportfolio.database.DatabaseConnector;


/**
 * A simple {@link Fragment} subclass.
 */
public class InventoryFragment extends ListFragment {

    private final String DEBUG_TAG = this.getClass().getSimpleName();

    private InventoryFragmentListener listener;

    // the ListActivity's ListView
    private ListView listView;
    // adapter for ListView
    private CursorAdapter cursorAdapter;

    private ImageButton saveButton;

    //
    //
    //
    public InventoryFragment() {
        Log.i(DEBUG_TAG, "in InventoryFragment()");
        // Required empty public constructor
    } // end constructor InventoryFragment()


    //
    // set InventoryFragmentListener when Fragment is attached
    //
    @Override
    public void onAttach(Activity activity) {
        Log.i(DEBUG_TAG, "in onAttach()");
        super.onAttach(activity);
        // init callback to interface implementation
        listener = (InventoryFragmentListener) activity;
    } // end method onAttach()


    //
    // remove InventoryFragmentListener when Fragment is detached
    //
    @Override
    public void onDetach() {
        Log.i(DEBUG_TAG, "in onDetach()");
        super.onDetach();
        // clean up callback for interface implementation
        listener = null;
    } // end method onDetach


    //
    // *****************************************************
    // respond to user touching an item/symbol in the ListView
    // *****************************************************
    AdapterView.OnItemClickListener viewItemListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.i(DEBUG_TAG, "in viewItemListener()/onItemClick()");

            // let the callback take care of this
            listener.onIFLSymbolSelected(id);
        }
    };


    //
    // called when Fragment's view needs creation
    //
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(DEBUG_TAG, "in onCreateView()");
        super.onCreateView(inflater, container, savedInstanceState);

        // inflate DetailFragment's layout
        View view = inflater.inflate(R.layout.fragment_inventory, container, false);
        // save fragment across configuration changes
        setRetainInstance(true);
        // this fragment has menu items to display
        setHasOptionsMenu(true);

        // setup Save Button listener, etc
        saveButton = (ImageButton) view.findViewById(R.id.saveButton);
        // saveButton.setOnClickListener(saveButtonOnClickListener);
        saveButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final EditText tickerSymbol = (EditText) v.findViewById(R.id.inputSymbolEditText);
                // get DatabaseConnector to interact with the SQLite database
                DatabaseConnector databaseConnector = new DatabaseConnector(getActivity());
                // insert the contact information into the database
                long rowID = databaseConnector.addTickerSymbol(tickerSymbol.getText().toString());
                // reset form
                tickerSymbol.setText(null);
            }

        }
        );

        return view;
    } // end method onCreateView


    //
    // called after LayoutHelper is created
    //
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.i(DEBUG_TAG, "in onViewCreated()");
        super.onViewCreated(view, savedInstanceState);

        // save fragment across configuration changes
        setRetainInstance(true);
        // this fragment has menu items to display
        setHasOptionsMenu(true);
        // set text to display when there are no contacts
        setEmptyText(getResources().getString(R.string.fragment_no_items));

        // get ListView reference and configure it (the ListView)
        listView = getListView();
        listView.setOnItemClickListener(viewItemListener);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // map each item/symbol to a TextView in the ListView layout
        cursorAdapter =
                new SimpleCursorAdapter(
                        getActivity(),
                        R.layout.app_item,
                        null,
                        Database.fromDBColumns,
                        LayoutHelper.toRIds,
                        0);
        // set adapter that supplies data
        setListAdapter(cursorAdapter);
    } // end method onViewCreated()


    //
    // when fragment resumes, use  GetContactsTask to load contacts
    //
    @Override
    public void onResume() {
        Log.i(DEBUG_TAG, "in onResume()");
        super.onResume();
        updateListView();  // calls new GetAllTickerSymbolsAsyncTask().execute((Object[]) null);
    } // end method onResume()


    //
    // when fragment resumes, use  GetContactsTask to load contacts
    //
    @Override
    public void onStop() {
        Log.i(DEBUG_TAG, "in onStop()");

        // get current database cursor
        Cursor cursor = cursorAdapter.getCursor();
        // adapter now has no cursor (basically housekeeping and cleanup
        cursorAdapter.changeCursor(null);

        // release the cursor if needed
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
        inflater.inflate(R.menu.fragment_inventory_menu, menu);
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

            case R.id.action_add:;
                listener.onIFLAddSymbolSelected();
                selected = true;
                break;

            default:;
                selected = super.onOptionsItemSelected(item);
                break;
        }

        return selected;
    } // end method onOptionsItemSelected()


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
    // callback methods implemented by caller/invoker, usually Prototype
    //
    public interface InventoryFragmentListener {

        // called when user selects an item/symbol
        void onIFLSymbolSelected(long rowID);

        // called when user adds a item/symbol
        void onIFLAddSymbolSelected();

    }


    //
    // update data set
    //
    public void updateListView() {
        Log.i(DEBUG_TAG, "in updateListView()");
        new GetAllTickerSymbolsAsyncTask().execute((Object[]) null);
    } // end method updateListView()


    //
    // *****************************************************
    // perform database query outside the GUI thread
    // *****************************************************
    private class GetAllTickerSymbolsAsyncTask extends AsyncTask<Object, Object, Cursor> {

        DatabaseConnector databaseConnector = new DatabaseConnector(getActivity());

        //
        // openForUpdate DB and return a cursor for all contacts
        //
        @Override
        protected Cursor doInBackground(Object... params) {
            Log.i(DEBUG_TAG, "in GetAllTickerSymbolsAsyncTask()/doInBackground()");
            databaseConnector.openForUpdate();
            return databaseConnector.getAllTickerSymbols();
        } // end method doInBackground()

        //
        // use cursor returned from the doInBackground method
        //
        @Override
        protected void onPostExecute(Cursor results) {
            Log.i(DEBUG_TAG, "in GetAllTickerSymbolsAsyncTask()/onPostExecute()");
            cursorAdapter.changeCursor(results);
            databaseConnector.close();
        } // end method onPostExecute()

    } // end inner class GetContactsTask()

    //
    // from PortfolioListActivity
    //
    public void fillFromDatabase() {
        Log.i(DEBUG_TAG, "fillFromDatabase Starts...");

        // get DatabaseConnector to interact with the SQLite database
        DatabaseConnector databaseConnector = new DatabaseConnector(getActivity());
        Cursor mCursor = null;

        ListAdapter adapter = null;
        ListView listView = null;
        ImageButton saveButton;

        int numberOfSymbols;   // keep track of how records were extracted from SQLite.
        SQLiteQueryBuilder portfolioQB = new SQLiteQueryBuilder();

        // SQL Query
        portfolioQB.setTables(Database.Portfolio.PORTFOLIO_TABLE_NAME);

        // refresh cursor with current data
        mCursor = portfolioQB.query(
                databaseConnector.,
                Database.asColumnsToReturn,
                null, null, null, null,
                Database.Portfolio.DEFAULT_SORT_ORDER,
                null);

        mCursor.moveToFirst();

        // make some toasties
        numberOfSymbols = mCursor.getCount();
        if (numberOfSymbols >= 0) {

            Toast.makeText(this.getActivity(),
                    "portfolioQB.query: " + numberOfSymbols + " records", Toast.LENGTH_SHORT)
                    .show();

            // Use an adapter to bind the data to a ListView where the data will be displayed

            // refresh the adapter with updated cursor information
            adapter = new SimpleCursorAdapter(
                    getActivity(),
                    R.layout.app_item,
                    mCursor,
                    Database.fromDBColumns,
                    LayoutHelper.toRIds,
                    1);

            // refresh the ListView to the adapter
            listView = (ListView) findViewById(R.id.symbolList);
            listView.setAdapter(adapter);

            // normal click will display company info, currently doing deletes
            // listView.setOnItemClickListener(symbolListListener);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                                @Override
                                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                                    Log.i(DEBUG_TAG, "in symbolListListener");

                                                    // Check for delete button
                                                    final long symbolId = id;

                                                    LinearLayout item = (LinearLayout) view;
                                                    TextView nameView = (TextView) item.findViewById(R.id.TextView_symbol);
                                                    final String symbolName = nameView.getText().toString();

                                                    // Use an Alert dialog to confirm delete operation
                                                    new AlertDialog.Builder(Prototype.this)
                                                            .setMessage("Delete Symbol Record for " + symbolName + "?")
                                                            .setPositiveButton("Delete",
                                                                    new DialogInterface.OnClickListener() {
                                                                        public void onClick(DialogInterface dialog,
                                                                                            int which) {
                                                                            // Delete the Symbol
                                                                            deleteSymbol(symbolId, symbolName);
                                                                            // a symbol was deleted, refresh the data in our cursor, therefore our List
                                                                            fillFromDatabase();
                                                                        }
                                                                    }).show();
                                                }

                                            }
            );

            // todo: replace delete symbol with edit option
            // long click will allow edit symbol
            // listView.setOnItemLongClickListener(symbolListLongListener);
            listView.setOnItemLongClickListener( new AdapterView.OnItemLongClickListener() {

                                                     @Override
                                                     public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                                         Log.i(DEBUG_TAG, "in symbolListLongListener");
                                                         // Check for delete button
                                                         final long symbolId = id;
                                                         LinearLayout item = (LinearLayout) view;
                                                         TextView nameView = (TextView) item.findViewById(R.id.TextView_symbol);
                                                         final String symbolName = nameView.getText().toString();
                                                         // Use an Alert dialog to confirm delete operation
                                                         new AlertDialog.Builder(Prototype.this)
                                                                 .setMessage("Delete Symbol Record for " + symbolName + "?")
                                                                 .setPositiveButton("Delete",
                                                                         new DialogInterface.OnClickListener() {
                                                                             public void onClick(DialogInterface dialog,
                                                                                                 int which) {
                                                                                 // Delete the Symbol
                                                                                 deleteSymbol(symbolId, symbolName);
                                                                                 // a symbol was deleted, refresh the data in our cursor, therefore our List
                                                                                 fillFromDatabase();
                                                                             }
                                                                         }).show();
                                                         return false;
                                                     }

                                                 }
            );
        } else  {
            // Alert user that the query failed
            Toast.makeText(this.getActivity(), "portfolioQB.query: failed", Toast.LENGTH_SHORT).show();
        }

        Log.i(DEBUG_TAG, "fillFromDatabase, iNumberOfSymbols[" + numberOfSymbols + "] Ends");
    }


    //
    public void deleteSymbol(Long symbolId, String symbolName) {
        Log.i(DEBUG_TAG, "deleteSymbol[" + symbolName + "], _ID[" + symbolId.toString() + "] Starts...");

        // todo: should add triggers to handle multiple tables
        String deleteArgs[] = { symbolId.toString() };
        long rc = mDB.delete(
                Database.Portfolio.PORTFOLIO_TABLE_NAME,
                Database.Portfolio._ID + "=?",
                deleteArgs
        );

        Log.i(DEBUG_TAG, "deleteSymbol[" + symbolName + "], _ID[" + symbolId.toString() + "], delete_code[" + rc + "] Ends");
    }

}
