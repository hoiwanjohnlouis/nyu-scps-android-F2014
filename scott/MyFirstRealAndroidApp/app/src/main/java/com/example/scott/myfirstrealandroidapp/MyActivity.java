package com.example.scott.myfirstrealandroidapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class MyActivity extends Activity {
    private static final String TAG = MyActivity.class.getSimpleName();

    public final static String HOST = "com.example.scott.myfirstrealandroidapp.HOST";
    public final static String PORT = "com.example.scott.myfirstrealandroidapp.PORT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_search:
                onOptionsSearch();
                return true;
            case R.id.action_settings:
                onActionsSearch();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Settings app nav handler
    void onOptionsSearch() {
        Log.i(TAG, "onOptionsSearch");
    }

    void onActionsSearch() {
        Log.i(TAG, "onActionsSearch");
    }

    // Send button handler
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);

        EditText hostEditText = (EditText)findViewById(R.id.host_address);
        String hostText = hostEditText.getText().toString();
        intent.putExtra(HOST, hostText);

        EditText portEditText = (EditText)findViewById(R.id.port_address);
        String portText = portEditText.getText().toString();
        intent.putExtra(PORT, portText);

        startActivity(intent);
    }

}
