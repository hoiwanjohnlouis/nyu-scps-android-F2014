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
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.hoiwanlouis.mystockportfolio.R;
import com.hoiwanlouis.mystockportfolio.database.DatabaseConnector;
import com.hoiwanlouis.mystockportfolio.fields.Gui2Database;


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
        // called when user deletes a Stock
        void onSLFLAddStockRequest();
        // called when user selects a Stock for detail display
        void onSLFLStockDetailRequest(Bundle arguments);
        // called when user selects a Stock for detail display
        void onSLFLDeleteStockComplete(Bundle arguments);
    }


    //
    private final String DEBUG_TAG = this.getClass().getSimpleName();
    //
    private StockListFragmentListener mListener;
    //
    private ListView mListView;
    // adapter for ListView
    private CursorAdapter mCursorAdapter;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    //
    //
    //
    public StockListFragment() {
        Log.i(DEBUG_TAG, "in StockListFragment(), required empty public constructor");
    } // end constructor StockListFragment()

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddStockFragment.
     */
    //
    // TODO: Rename and change types and number of parameters
    //
    public static StockListFragment newInstance() {
        StockListFragment fragment = new StockListFragment();
        //Bundle args = new Bundle();
        //fragment.setArguments(args);
        return fragment;
    }

    //
    // set StockListFragmentListener when Fragment is attached
    //
    @Override
    public void onAttach(Activity context) {
        Log.i(DEBUG_TAG, "in onAttach()");
        super.onAttach(context);
        // init callback to interface implementation
        mListener = (StockListFragmentListener) context;
    } // end method onAttach

    //
    //
    //
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(DEBUG_TAG, "in onCreate()");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    // configures the QuizFragment when its View is created
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v(DEBUG_TAG, "in onCreateView()");
        View view = super.onCreateView(inflater, container, savedInstanceState);
//        View view = inflater.inflate(R.layout.fragment_stock_list, container, false);
        return view; // returns the fragment's view for display
    }

    //
    // called after StockListFragment is created
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
        mListView = getListView();
//        mListView = (ListView) view.findViewById(R.id.list);
        mListView.setOnItemClickListener(onItemClickListener);
        mListView.setOnItemLongClickListener(onItemLongClickListener);
        mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mListView.addFooterView(view.findViewById(R.id.copyright_layout));

        // map each item/symbol to a TextView in the ListView layout
/*
        mCursorAdapter =
                new StockListCursorAdapter(
                        getActivity(),
                        null,
                        0);
         */
        mCursorAdapter =
                new SimpleCursorAdapter(
                        getActivity(),
                        R.layout.app_item,
                        null,
                        Gui2Database.fromDBColumns,
                        Gui2Database.toRIds,
                        0);

        // set adapter that supplies data
        mListView.setAdapter(mCursorAdapter);
//        setListAdapter(mCursorAdapter);
    } // end method onViewCreated()

    //
    //
    //
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(DEBUG_TAG, "in onActivityCreated()");
        super.onActivityCreated(savedInstanceState);
    }

    //
    //
    //
    @Override
    public void onStart() {
        Log.i(DEBUG_TAG, "in onStart()");
        super.onStart();
    } // end method onStart()

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
    //
    //
    @Override
    public void onPause() {
        Log.i(DEBUG_TAG, "in onPause()");
        super.onPause();
    }

    //
    // when fragment resumes, clean up
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
    //
    //
    @Override
    public void onDestroyView() {
        Log.i(DEBUG_TAG, "in onDestroyView()");
        super.onDestroyView();
    }

    //
    //
    //
    @Override
    public void onDestroy() {
        Log.i(DEBUG_TAG, "in onDestroy()");
        super.onDestroy();
    }

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

    // display this fragment's menu items
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.i(DEBUG_TAG, "in onCreateOptionsMenu()");
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_stock_list_menu, menu);
    } // end method onCreateOptionsMenu()

    // handle choice from options menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(DEBUG_TAG, "in onOptionsItemSelected()");

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id)
        {
            case R.id.action_add:;
                // let the callback take care of this
                onAddStockButtonPressed();
                return true;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    } // end method onOptionsItemSelected()



    //
    // respond to user touching an item/symbol in the ListView
    //
    private AdapterView.OnItemClickListener onItemClickListener =
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.i(DEBUG_TAG, "in onItemClickListener()");
                    // let the callback take care of this
                    onStockSelectedButtonPressed(id);
                }
            };

    // long click will allow edit symbol
    private AdapterView.OnItemLongClickListener onItemLongClickListener =
            new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.i(DEBUG_TAG, "in onItemLongClickListener()");
                    // let the callback take care of this
                    onDeleteStockButtonPressed(parent, view, position, id);
                    return false;
                }
            };

    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////
    //
    // callback to main to redisplay screen;
    //
    public void onAddStockButtonPressed() {
        Log.i(DEBUG_TAG, "in onAddStockButtonPressed()");
        if (mListener != null) {
            // let the callback take care of this
            mListener.onSLFLAddStockRequest();
        }
    }

    //
    // callback to main to redisplay screen;
    //
    public void onStockSelectedButtonPressed(long id) {
        Log.i(DEBUG_TAG, "in onDeleteStockButtonPressed()");
        if (mListener != null) {
            final Bundle arguments = new Bundle();
            arguments.putLong(Gui2Database.BUNDLE_KEY, id);
            // let the callback take care of this
            mListener.onSLFLStockDetailRequest(arguments);
        }
    }

    public void onDeleteStockButtonPressed(AdapterView<?> parent, View view, int position, long id) {
        Log.i(DEBUG_TAG, "in onDeleteStockButtonPressed()");
        if (mListener != null) {
            mListener.onSLFLAddStockRequest();
        }
        final DatabaseConnector databaseConnector = new DatabaseConnector(getActivity());
        // need tickerId and tickerSymbol for logging
        final long tickerId = id;
        final TextView nameView = (TextView) view.findViewById(R.id.TextView_symbol);
        final String tickerSymbol = nameView.getText().toString();
        // Use an Alert dialog to confirm delete operation
        new AlertDialog.Builder(getActivity())
                .setMessage("Delete Stock Record for Symbol [" + tickerSymbol + "]?")
                .setPositiveButton("Delete",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                // Delete the Symbol
                                databaseConnector.deleteOneStock(tickerId, tickerSymbol);
                                // a symbol was deleted, refresh the data in our symbolList
                                if (mListener != null) {
                                    final Bundle arguments = new Bundle();
                                    arguments.putLong(Gui2Database.BUNDLE_KEY, tickerId);
                                    // let the callback take care of this
                                    mListener.onSLFLDeleteStockComplete(arguments);
                                }
                            }
                        })
                .setNegativeButton("Cancel",null)  // do nothing if cancel
                .show();

    }

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
            super.onPostExecute(results);
            // move to the first item
            results.moveToFirst();

            mCursorAdapter.changeCursor(results);
//            setListAdapter(mCursorAdapter);
            databaseConnector.close();
        } // end method onPostExecute()

    } // end inner class GetContactsTask()

}
