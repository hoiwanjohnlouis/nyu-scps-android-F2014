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
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.hoiwanlouis.mystockportfolio.R;
import com.hoiwanlouis.mystockportfolio.SettingsActivity;
import com.hoiwanlouis.mystockportfolio.database.DatabaseConnector;
import com.hoiwanlouis.mystockportfolio.fields.Gui2Database;


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
        saveButton.setOnClickListener(new View.OnClickListener() {

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
    // called after Gui2Database is created
    //
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.i(DEBUG_TAG, "in onViewCreated()");
        super.onViewCreated(view, savedInstanceState);

        // save fragment across configuration changes
        setRetainInstance(true);
        // this fragment has menu items to display
        setHasOptionsMenu(true);
        // set text to display when there is no data
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
                        Gui2Database.fromDBColumns,
                        Gui2Database.toRIds,
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

}
