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
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.hoiwanlouis.mystockportfolio.R;
import com.hoiwanlouis.mystockportfolio.database.DatabaseConnector;
import com.hoiwanlouis.mystockportfolio.fields.Gui2Database;

public class StockListFragment extends ListFragment {

    // callback methods implemented by caller/invoker
    public interface StockListFragmentListener {
        void onAddStockToDatabaseRequest();

        void onDeleteStockFromDatabaseComplete(Bundle arguments);

        void onDisplayStockDetailRequest(Bundle arguments);
    }

    private final String DEBUG_TAG = this.getClass().getSimpleName();
    private StockListFragmentListener stockListFragmentListener;
    private ListView stockListView;
    private CursorAdapter stockCursorAdapter;

    public StockListFragment() {
        Log.i(DEBUG_TAG, "in StockListFragment()");
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddStockFragment.
     */
    public static StockListFragment newInstance() {
        StockListFragment fragment = new StockListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    // set StockListFragmentListener when Fragment is attached
    @Override
    public void onAttach(Context context) {
        Log.i(DEBUG_TAG, "in onAttach()");
        super.onAttach(context);
        stockListFragmentListener = (StockListFragmentListener) context;
    } // end method onAttach

    // clean up StockListFragmentListener when Fragment is detached
    @Override
    public void onDetach() {
        Log.i(DEBUG_TAG, "in onDetach()");
        super.onDetach();
        stockListFragmentListener = null;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(DEBUG_TAG, "in onActivityCreated()");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        Log.i(DEBUG_TAG, "in onStart()");
        super.onStart();
    }

    // when fragment resumes, reload contacts
    @Override
    public void onResume() {
        Log.i(DEBUG_TAG, "in onResume()");
        super.onResume();
        updateStockListView();
    }

    @Override
    public void onPause() {
        Log.i(DEBUG_TAG, "in onPause()");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.i(DEBUG_TAG, "in onStop()");
        Cursor cursor = stockCursorAdapter.getCursor();
        stockCursorAdapter.changeCursor(null);
        if (cursor != null) {
            cursor.close();
        }
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.i(DEBUG_TAG, "in onDestroyView()");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.i(DEBUG_TAG, "in onDestroy()");
        super.onDestroy();
    }

    // display this fragment's menu items
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.i(DEBUG_TAG, "in onCreateOptionsMenu()");
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_stock_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(DEBUG_TAG, "in onOptionsItemSelected()");
        int id = item.getItemId();
        switch (id) {
            case R.id.action_add:
                // let the callback take care of this
                onAddStockRequest();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(DEBUG_TAG, "in onCreate()");
        super.onCreate(savedInstanceState);
    }

    // configures the QuizFragment when its View is created
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(DEBUG_TAG, "in onCreateView()");
        // inflate StockDetailFragment's layout and bind the data: must match the detail layout
        // View v = inflater.inflate(R.layout.fragment_stock_list, container, false);
        View v = super.onCreateView(inflater, container, savedInstanceState);
        return v;
    }

    //
    // called after StockListFragment is created
    //
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.i(DEBUG_TAG, "in onViewCreated()");
        super.onViewCreated(view, savedInstanceState);

        setRetainInstance(true);
        setHasOptionsMenu(true);
        setEmptyText(getResources().getString(R.string.fragment_no_items));

        // get ListView reference and configure it (the ListView)
        stockListView = getListView();
//        stockListView = (ListView) view.findViewById(R.id.list);
        stockListView.setOnItemClickListener(onItemClickListener);
        stockListView.setOnItemLongClickListener(onItemLongClickListener);
        stockListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//        stockListView.addFooterView(view.findViewById(R.id.copyright_layout));

        /*
        stockCursorAdapter =
                new StockListCursorAdapter(
                        getActivity(),
                        null,
                        0);
         */
        String[] from;
        int[] to;
        from = Gui2Database.fromDBColumns;
        to = Gui2Database.toRIds;
//        from = new String[] {DatabaseColumns.Portfolio.SYMBOL};
        to = new int[]{android.R.id.text1};
        stockCursorAdapter =
                new SimpleCursorAdapter(
                        getActivity(),
                        android.R.layout.simple_list_item_1,
                        null,
                        from,
                        to,
                        0);

//        stockListView.setAdapter(stockCursorAdapter);
        setListAdapter(stockCursorAdapter);
    }

    // respond to user touching an item/symbol in the ListView
    private AdapterView.OnItemClickListener onItemClickListener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.i(DEBUG_TAG, "in onItemClickListener()");
                    // let the callback take care of this
                    onStockDetailRequest(id);
                }
            };

    // long click will allow edit symbol
    private AdapterView.OnItemLongClickListener onItemLongClickListener =
            new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.i(DEBUG_TAG, "in onItemLongClickListener()");
                    // let the callback take care of this
                    onDeleteStockRequest(parent, view, position, id);
                    return false;
                }
            };

    // callback to main to redisplay screen;
    public void onStockDetailRequest(long id) {
        Log.i(DEBUG_TAG, "in onDeleteStockRequest()");
        final Bundle arguments = new Bundle();
        arguments.putLong(Gui2Database.BUNDLE_KEY, id);
        // let the callback take care of this
        stockListFragmentListener.onDisplayStockDetailRequest(arguments);
    }

    //
    public void onDeleteStockRequest(AdapterView<?> parent, View view, int position, long id) {
        Log.i(DEBUG_TAG, "in onDeleteStockRequest()");
        final long tickerId = id;
        final TextView nameView = (TextView) view.findViewById(R.id.TextView_symbol);
        final String tickerSymbol = nameView.getText().toString();

        // Use an Alert dialog to confirm delete operation
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());  // is getActivity() usage correct?
        builder.setTitle(R.string.fragment_delete_title);
        builder.setMessage(R.string.fragment_delete_message);
        builder.setPositiveButton(R.string.fragment_delete_button_delete,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        final DatabaseConnector databaseConnector = new DatabaseConnector(getActivity());

                        // create an AsyncTask to delete a contact and notify listener
                        AsyncTask<Long, Object, Object> deleteTask =
                                new AsyncTask<Long, Object, Object>() {
                                    @Override
                                    protected Object doInBackground(Long... params) {
                                        Log.i(DEBUG_TAG, "in doInBackground()");
                                        databaseConnector.deleteOneStock(params[0]);
                                        return null;
                                    }

                                    @Override
                                    protected void onPostExecute(Object result) {
                                        Log.i(DEBUG_TAG, "in onPostExecute()");
                                        final Bundle arguments = new Bundle();
                                        arguments.putLong(Gui2Database.BUNDLE_KEY, (long) result);
                                        // let the callback take care of this
                                        stockListFragmentListener.onDeleteStockFromDatabaseComplete(arguments);
                                    }
                                }; // end new AsyncTask definition

                        // execute the AsyncTask to delete the stock
                        deleteTask.execute(tickerId);
                        Log.i(DEBUG_TAG, "tickerSymbol[" + tickerSymbol + "] deleted.");

                    } // end method DialogInterface.OnClickListener.onClick
                });
        builder.setNegativeButton(R.string.fragment_delete_button_cancel, null); // do nothing if cancel
        builder.create();
        builder.show();
    }

    // callback to main to redisplay screen;
    public void onAddStockRequest() {
        Log.i(DEBUG_TAG, "in onAddStockRequest()");
        stockListFragmentListener.onAddStockToDatabaseRequest();
    }

    public void updateStockListView() {
        Log.i(DEBUG_TAG, "in updateStockListView()");
        new GetAllStocksAsyncTask().execute((Object[]) null);
    }

    // *****************************************************
    // perform database query outside the GUI thread
    // *****************************************************
    private class GetAllStocksAsyncTask extends AsyncTask<Object, Object, Cursor> {

        DatabaseConnector databaseConnector = new DatabaseConnector(getActivity());

        // open DB and return a cursor to all contacts
        @Override
        protected Cursor doInBackground(Object... params) {
            Log.i(DEBUG_TAG, "in GetAllStocksAsyncTask/doInBackground()");
            databaseConnector.openForRead();
            return databaseConnector.getAllStocks();
        }

        // set cursor returned to adapter and close db
        @Override
        protected void onPostExecute(Cursor results) {
            Log.i(DEBUG_TAG, "in GetAllStocksAsyncTask/onPostExecute()");
            // super.onPostExecute(results);
            // results.moveToFirst();
            stockCursorAdapter.changeCursor(results);
            databaseConnector.close();
        }
    }


}
