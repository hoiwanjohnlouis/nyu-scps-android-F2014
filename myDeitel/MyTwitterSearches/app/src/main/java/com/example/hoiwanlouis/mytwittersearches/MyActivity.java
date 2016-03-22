package com.example.hoiwanlouis.mytwittersearches;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;


public class MyActivity extends ListActivity {

    // for logging purposes
    private final String TAG = this.getClass().getSimpleName();

    // name of SharedPreferences XML file to store the saved searches
    private static final String SEARCHES = "searches";

    private EditText queryEditText;             // EditText where the user enters a query
    private EditText tagEditText;               // EditText where the user tag a query
    private SharedPreferences savedSearches;    // a user's favorite searches
    private ArrayList<String> tags;             // table of tags for saved searches
    private ArrayAdapter<String> adapter;       // used to bind tags to a ListView


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate Starting...");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        // get references to EditText fields
        queryEditText = (EditText) findViewById(R.id.queryEditText);
        tagEditText = (EditText) findViewById(R.id.tagEditText);

        // get the SharedPreferences containing the user's saved searches
        savedSearches = getSharedPreferences(SEARCHES,MODE_PRIVATE);

        // extract the savedSearches key data
        // and sort them into tags ArrayList, ignoring the case
        tags = new ArrayList<String>(savedSearches.getAll().keySet());
        Collections.sort(tags,String.CASE_INSENSITIVE_ORDER);

        // create ArrayAdapter and use it to bind the 'tags' to the ListView
        adapter = new ArrayAdapter<String>(this,R.layout.list_item, tags);
        setListAdapter(adapter);

        // register a listener to save a new or edited search
        ImageButton saveButton = (ImageButton) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(saveButtonListener);

        // register a listener that searches Twitter when a user touches/selects a tag
        getListView().setOnItemClickListener(itemClickListener);

        // register a listener that allow user to edit of delete a search
        getListView().setOnItemLongClickListener(itemLongClickListener);

        Log.i(TAG, "onCreate Ends");
        return;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(TAG, "onCreateOptionsMenu Starting...");

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);

        Log.i(TAG, "onCreateOptionsMenu Ends");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, "onOptionsItemSelected Starting...");

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        boolean foundMatch;
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            foundMatch = true;
        }
        else {
            foundMatch = super.onOptionsItemSelected(item);
        }

        Log.i(TAG, "onOptionsItemSelected Ends");
        return foundMatch;
    }

    // Anonymous class saveButtonListener saves a tag-query pair into SharedPreferences
    public OnClickListener saveButtonListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            Log.i(TAG, "saveButtonListener Starting...");

            // create tag if when queryEditText and tagEditText are filled in
            if ((queryEditText.getText().length() > 0) &&
                    (tagEditText.getText().length() > 0)) {

                // add to savesSearches
                addTaggedSearch(
                    queryEditText.getText().toString(),
                    tagEditText.getText().toString()
                );
                queryEditText.setText(""); // clear queryEditText for next user input
                tagEditText.setText(""); // clear tagEditText for next user input

                // turn off display of soft-keyboard
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(tagEditText.getWindowToken(), 0);
            }
            else {
                // display message  asking user to provide a query and a tag

                // create a new AlertDialog Builder
                AlertDialog.Builder builder = new AlertDialog.Builder(MyActivity.this);

                // set dialog's message to display
                builder.setMessage(R.string.missingMessage);

                // provide an OK button that simply dismisses the dialog
                builder.setPositiveButton(R.string.OK, null);

                // create AlertDialog from the AlertDialog.Builder
                AlertDialog errorDialog = builder.create();
                errorDialog.show(); // display the modal dialog
            }

            Log.i(TAG, "saveButtonListener Ends");
            return;
        } // end method onClick
    }; // end OnClickListener anonymous inner class

    // 1. add new search to the save file(SharedPreferences)
    // 2. refresh all the buttons
    private void addTaggedSearch(String query, String tag) {
        Log.i(TAG, "addTaggedSearch Starting...");

        // 1. get an editor object to allow updating
        // 2. add the tag,query value pair
        // 3. commit the addition
        SharedPreferences.Editor preferencesEditor = savedSearches.edit();
        preferencesEditor.putString(tag, query);
        preferencesEditor.apply();

        // if the tag exists, do nothing
        if (tags.contains(tag)) {
            ;
        }
        // else the tag is new, add to savedSearches and sort
        else {
            tags.add(tag);                  // add new tag
            Collections.sort(tags,String.CASE_INSENSITIVE_ORDER);
            adapter.notifyDataSetChanged(); // rebind updated list
        }

        Log.i(TAG, "addTaggedSearch Ends");
        return;
    }

    // itemClickListener launches a web browser to display search results
    OnItemClickListener itemClickListener = new OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.i(TAG, "onItemClick Starting...");

            // get the query string and create a URL representing the search
            String tag = ((TextView) view).getText().toString();
            String urlString = getString(R.string.searchURL) +
                    Uri.encode(savedSearches.getString(tag, ""), "UTF-8");

            // create an Intent to launch a web browser
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlString));

            startActivity(webIntent); // launches web browser to view results

            Log.i(TAG, "onItemClick Ends");
            return;
        }

    }; // end itemClickListener declaration


    // itemLongClickListener displays a dialog allowing the user to delete
    // or edit a saved search
    OnItemLongClickListener itemLongClickListener = new OnItemLongClickListener() {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            Log.i(TAG, "itemLongClickListener Starting...");

            // get the tag that the user long touched
            final String tag = ((TextView) view).getText().toString();

            // create a new AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(MyActivity.this);

            // set the AlertDialog's title
            builder.setTitle(getString(R.string.shareEditDeleteTitle, tag));

            // set list of items to display in dialog
            builder.setItems(
                    R.array.dialog_items,
                    new DialogInterface.OnClickListener() {
                            // responds to user touch by sharing, editing or
                            // deleting a saved search
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                switch (which) {
                                    case 0: // share
                                        shareSearch(tag);
                                        break;
                                    case 1: // edit
                                        // set EditTexts to match chosen tag and query
                                        tagEditText.setText(tag);
                                        queryEditText.setText(savedSearches.getString(tag, ""));
                                        break;
                                    case 2: // delete
                                        deleteSearch(tag);
                                        break;
                                } // end switch
                            } // end OnClick
                        } // end DialogInterface.OnClickListener
            ); // end call to builder.setItems


            // set the AlertDialog's negative Button
            builder.setNegativeButton(
                    getString(R.string.cancel),
                    new DialogInterface.OnClickListener() {
                            // called when the "Cancel" Button is clicked
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel(); // dismiss the AlertDialog
                            }
                        }
            ); // end call to setNegativeButton

            // display the AlertDialog
            builder.create().show();

            Log.i(TAG, "itemLongClickListener Ends");
            return true;
        } // end method onItemLongClick
    }; // end OnItemLongClickListener declaration

    // allows user to choose an app for sharing a saved search's URL
    private void shareSearch(String tag)
    {
        Log.i(TAG, "shareSearch Starting...");

        // create the URL representing the search
        String urlString = getString(R.string.searchURL) +
                Uri.encode(savedSearches.getString(tag, ""), "UTF-8");

        // create Intent to share urlString
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT,
                getString(R.string.shareSubject));
        shareIntent.putExtra(Intent.EXTRA_TEXT,
                getString(R.string.shareMessage, urlString));
        shareIntent.setType("text/plain");

        // display apps that can share text
        startActivity(Intent.createChooser(shareIntent,
                getString(R.string.shareSearch)));

        Log.i(TAG, "shareSearch Ends");
        return;
    }

    // deletes a search after the user confirms the delete operation
    private void deleteSearch(final String tag)
    {
        Log.i(TAG, "deleteSearch Starting...");

        // create a new AlertDialog
        AlertDialog.Builder confirmBuilder = new AlertDialog.Builder(this);

        // set the AlertDialog's message
        confirmBuilder.setMessage(getString(R.string.confirmMessage, tag));

        // set the AlertDialog's negative Button
        confirmBuilder.setNegativeButton(
                getString(R.string.cancel),
                new DialogInterface.OnClickListener()
                {
                    // called when "Cancel" Button is clicked
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.cancel(); // dismiss dialog
                    }
                }
        ); // end call to setNegativeButton

        // set the AlertDialog's positive Button
        confirmBuilder.setPositiveButton(
                getString(R.string.delete),
                new DialogInterface.OnClickListener()
                {
                    // called when "Cancel" Button is clicked
                    public void onClick(DialogInterface dialog, int id)
                    {
                        tags.remove(tag); // remove tag from tags

                        // get SharedPreferences.Editor to remove saved search
                        SharedPreferences.Editor preferencesEditor = savedSearches.edit();
                        preferencesEditor.remove(tag); // remove search
                        preferencesEditor.apply(); // saves the changes

                        // rebind tags ArrayList to ListView to show updated list
                        adapter.notifyDataSetChanged();
                    }
                } // end OnClickListener
        ); // end call to setPositiveButton

        confirmBuilder.create().show(); // display AlertDialog

        Log.i(TAG, "deleteSearch Ends");
        return;
    } // end method deleteSearch


}
