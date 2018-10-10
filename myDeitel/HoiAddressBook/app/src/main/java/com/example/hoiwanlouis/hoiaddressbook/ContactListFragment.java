package com.example.hoiwanlouis.hoiaddressbook;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ContactListFragment extends ListFragment {

    private final String DEBUG_TAG = this.getClass().getSimpleName();

    // call back methods implemented by MainActivity
    public interface ContactListFragmentListener {
        // called when user selects a contact
        public void onContactDetailRequest(long rowID);

        // called when user adds a contact
        public void onAddContactRequest();
    } // end interface onContactDetailRequest()

    private ContactListFragmentListener listener;

    private CursorAdapter contactAdapter;


    //
    public ContactListFragment() {
        Log.i(DEBUG_TAG, "in ContactListFragment()");
        // Required empty public constructor
    } // end constructor ContactListFragment()

    // set ContactListFragmentListener when Fragment is attached
    @Override
    public void onAttach(Context context) {
        Log.i(DEBUG_TAG, "in onAttach()");
        super.onAttach(context);
        listener = (ContactListFragmentListener) context;
    } // end method onAttach()

    @Override
    @SuppressWarnings("deprecation")
    public void onAttach(Activity activity) {
        Log.i(DEBUG_TAG, "in onAttach()");
        super.onAttach(activity);
        listener = (ContactListFragmentListener) activity;
    } // end method onAttach()

    //
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(DEBUG_TAG, "in onCreate()");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    //
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(DEBUG_TAG, "in onCreateView()");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    // called after View is created
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Log.i(DEBUG_TAG, "in onViewCreated()");
        super.onViewCreated(view, savedInstanceState);

        setRetainInstance(true);
        setHasOptionsMenu(true);

        setEmptyText(getResources().getString(R.string.no_contacts));

        // get ListView reference and configure it (the ListView)
        ListView contactListView = getListView();
        contactListView.setOnItemClickListener(viewContactListener);
        contactListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // map each contact's name to a TextView in the ListView layout
        String[] from = new String[] {"name"};
        int[] to = new int[] {android.R.id.text1};
        contactAdapter =
                new SimpleCursorAdapter(
                        getActivity(),
                        android.R.layout.simple_list_item_1,
                        null,
                        from, to,
                        0);
        setListAdapter(contactAdapter);
    } // end method onViewCreated()

    // for logging purposes, method can be remove once debugging is complete
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(DEBUG_TAG, "in onActivityCreated()");
        super.onActivityCreated(savedInstanceState);
    }

    // for logging purposes, method can be remove once debugging is complete
    @Override
    public void onStart() {
        Log.i(DEBUG_TAG, "in onStart()");
        super.onStart();
    } // end method onStart()

    // when fragment resumes, use  GetContactsTask to load contacts
    @Override
    public void onResume() {
        Log.i(DEBUG_TAG, "in onResume()");
        super.onResume();
        new GetContactsTask().execute((Object[]) null);
    } // end method onResume()

    // for logging purposes, method can be remove once debugging is complete
    @Override
    public void onPause() {
        Log.i(DEBUG_TAG, "in onPause()");
        super.onPause();
    }

    // when fragment resumes, use  GetContactsTask to load contacts
    @Override
    public void onStop() {
        Log.i(DEBUG_TAG, "in onStop()");

        // get current database cursor
        Cursor cursor = contactAdapter.getCursor();

        // adapter now has no cursor (basically housekeeping and cleanup
        contactAdapter.changeCursor(null);

        // release the cursor if needed
        if (cursor != null) {
            cursor.close();
        }

        super.onStop();
    } // end method onStop()

    // for logging purposes, method can be remove once debugging is complete
    @Override
    public void onDestroyView() {
        Log.i(DEBUG_TAG, "in onDestroyView()");
        super.onDestroyView();
    }

    // for logging purposes, method can be remove once debugging is complete
    @Override
    public void onDestroy() {
        Log.i(DEBUG_TAG, "in onDestroy()");
        super.onDestroy();
    }

    // remove ContactListFragmentListener when Fragment is detached
    @Override
    public void onDetach() {
        Log.i(DEBUG_TAG, "in onDetach()");
        super.onDetach();
        listener = null;
    } // end method onDetach

    // display this fragment's menu items
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.i(DEBUG_TAG, "in onCreateOptionsMenu()");
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_contact_list_menu, menu);
    } // end method onCreateOptionsMenu()

    // handle choices from options menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(DEBUG_TAG, "in onOptionsItemSelected()");

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id)
        {
            case R.id.action_add:
                listener.onAddContactRequest();
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    } // end method onOptionsItemSelected()


    // respond to user touching a contact's name in the ListView
    AdapterView.OnItemClickListener viewContactListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.i(DEBUG_TAG, "in viewContactListener()/onItemClick()");
            listener.onContactDetailRequest(id);
        }
    };

    /////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////
    // update data set
    public void updateContactList() {
        Log.i(DEBUG_TAG, "in updateContactList()");
        new GetContactsTask().execute((Object[]) null);
    } // end method updateContactList()

    // *****************************************************
    // perform database query outside the GUI thread
    // *****************************************************
    private class GetContactsTask extends AsyncTask<Object, Object, Cursor> {
        DatabaseConnector databaseConnector = new DatabaseConnector(getActivity());

        // open DB and return a cursor for all contacts
        @Override
        protected Cursor doInBackground(Object... params) {
            Log.i(DEBUG_TAG, "in GetContactsTask()/doInBackground()");
            databaseConnector.open();
            return databaseConnector.getAllContacts();
        } // end method doInBackground()

        // use cursor returned from the doInBackground method
        @Override
        protected void onPostExecute(Cursor results) {
            Log.i(DEBUG_TAG, "in GetContactsTask()/onPostExecute()");
            contactAdapter.changeCursor(results);
            databaseConnector.close();
        } // end method onPostExecute()
    } // end inner class GetContactsTask()

} // end class ContactListFragment()
