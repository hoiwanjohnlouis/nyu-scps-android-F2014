package com.hwtechservicesllc.stocks.fragments;

import android.app.Activity;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.hwtechservicesllc.stocks.R;
import com.hwtechservicesllc.stocks.database.Database;
import com.hwtechservicesllc.stocks.database.DatabaseConnector;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SymbolsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SymbolsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
//public class SymbolsFragment extends ListFragment {
public class SymbolsFragment extends Fragment {

    // the fragment bundle key 1: ARG_ITEM_STRING, aka ARG_PARAM1
    private static final String ARG_ROW_ID = FragmentConstants.ROW_ID;

    // the fragment bundle data 1: VALUE DATA, aka mParam1
    private long mRowId;

    //
    // callback interface/method
    //
    private OnFragmentInteractionListener mListener;

    /****************************************************************
     *
     * Start of non-template variables
     *
     ****************************************************************/

    //
    // logging purposes
    //
    private final String DEBUG_TAG = this.getClass().getSimpleName();

    // the ListActivity's ListView
    private ListView listView;
    // adapter for ListView
    private CursorAdapter cursorAdapter;

//    private ImageButton saveButton;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment UpdateFragment.
     */
    public static SymbolsFragment newInstance() {
        Log.i("SymbolsFragment", "in newInstance()");
        SymbolsFragment fragment = new SymbolsFragment();
        return fragment;
    } // end factory method newInstance

    //
    // Standard function provided by fragment template
    //
    public SymbolsFragment() {
        Log.i(DEBUG_TAG, "in Constructor");
        // Required empty public constructor
    }

    //
    // Standard function provided by fragment template
    //
    @Override
    public void onAttach(Activity activity) {
        Log.i(DEBUG_TAG, "in onAttach()");
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    } // end method onAttach()

    //
    // Standard function provided by fragment template
    //
    public void onButtonPressed(Bundle fragmentInterfaceBundle) {
        Log.i(DEBUG_TAG, "in onButtonPressed(Bundle inventoryFragmentInterfaceBundle)");
        if (mListener != null) {
            mListener.onSymbolsFragmentSymbolSelected(fragmentInterfaceBundle);
        }
    } // end method onButtonPressed

    //
    // Standard function provided by fragment template
    //
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(DEBUG_TAG, "in onCreate()");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRowId = getArguments().getLong(ARG_ROW_ID);
        }
    } // end method onCreate

    //
    // Standard function provided by fragment template
    //  with changes for application
    //
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(DEBUG_TAG, "in onCreateView()");
        super.onCreateView(inflater, container, savedInstanceState);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_symbols, container, false);

        // get ListView reference and configure it
        listView = (ListView) view.findViewById(R.id.symbolsList);
        listView.setOnItemClickListener(onItemClickListener);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // map each item/symbol to a TextView in the ListView layout
        cursorAdapter =
                new SimpleCursorAdapter(
                        getActivity(),
                        R.layout.app_item,
                        null,
                        Database.Query.fromDBColumns,
                        Database.Query.toRIds,
                        0);

        // set adapter that supplies data
        listView.setAdapter(cursorAdapter);

        return view;
    } // end method onCreateView

    //
    // Standard function provided by fragment template
    //
    @Override
    public void onDetach() {
        Log.i(DEBUG_TAG, "in onDetach()");
        super.onDetach();
        // clean up callback for interface implementation
        mListener = null;
    } // end method onDetach

    //
    // Standard function provided by fragment template
    //
    // called after View is created
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.i(DEBUG_TAG, "in onCreateViewCreated()");
        super.onViewCreated(view, savedInstanceState);

        // set text to display when there is no data
//        setEmptyText(getResources().getString(R.string.fragment_no_items));

    } // end method onViewCreated

    //
    // when fragment resumes, use  GetContactsTask to load contacts
    //
    @Override
    public void onResume() {
        Log.i(DEBUG_TAG, "in onResume()");
        super.onResume();
        new GetSymbolsAsyncTask().execute((Object[]) null);
    } // end method onResume()

    //
    // when fragment stops, null out cursor references to remove memory leaks
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
    // Standard function provided by fragment template
    //
    public interface OnFragmentInteractionListener {
        // called when user selects an item/symbol
        public void onSymbolsFragmentSymbolSelected(Bundle fragmentInterfaceBundle);
    }


    /****************************************************************
     *
     * Start of non-template functions
     *
     ****************************************************************/

    //
    // respond to user touching an item/symbol in the ListView
    //
    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.i(DEBUG_TAG, "in onItemClickListener()/onItemClick()");

            //
            // rowID is returned in a bundle for consistency
            //
            Bundle fragmentInterfaceBundle = new Bundle();
            fragmentInterfaceBundle.putLong(ARG_ROW_ID, id);
            // inform callback on what has happened
            onButtonPressed(fragmentInterfaceBundle);
        }
    };

    //
    // update data set
    //
    public void updateListView() {
        Log.i(DEBUG_TAG, "in updateListView()");
        // get a new list since it's been changed
        new GetSymbolsAsyncTask().execute((Object[]) null);
    } // end method updateListView()

    //
    // *****************************************************
    // perform database query outside the GUI thread
    // *****************************************************
    private class GetSymbolsAsyncTask extends AsyncTask<Object, Object, Cursor> {

        DatabaseConnector databaseConnector = new DatabaseConnector(getActivity());

        //
        // open DB and return a cursor for all contacts
        //
        @Override
        protected Cursor doInBackground(Object... params) {
            Log.i(DEBUG_TAG, "in GetSymbolsAsyncTask()/doInBackground()");
            databaseConnector.open();
            return databaseConnector.getAllItems();
        } // end method doInBackground()

        //
        // use cursor returned from the doInBackground method
        //
        @Override
        protected void onPostExecute(Cursor results) {
            Log.i(DEBUG_TAG, "in GetSymbolsAsyncTask()/onPostExecute()");
            cursorAdapter.changeCursor(results);
            databaseConnector.close();
        } // end method onPostExecute()

    } // end inner class GetContactsTask()

}
