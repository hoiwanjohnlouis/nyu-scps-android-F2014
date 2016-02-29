package com.example.scott.myfirstrealandroidapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class DisplayMessageActivity extends Activity {
    private static final String TAG = DisplayMessageActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent               = getIntent();
//        Integer port                = Integer.parseInt(intent.getStringExtra(MyActivity.PORT));
//        String host                 = intent.getStringExtra(MyActivity.HOST);

        new Thread(new ClientThread()).start();

    }

    class ClientThread implements Runnable {

        @Override
        public void run() {

            TimeClient timeClient =  null;
            try {
                timeClient = new TimeClient("AndroidClient", "192.168.1.133", 8600);
//            Log.i(this.timeClient);
                timeClient.runClient();
            }
            catch (NullPointerException ex3) {
                Log.e(TAG,"Null pointer son.\n"+ex3+"\n");
                ex3.printStackTrace(System.err);
            }
            catch (Exception ex) {
                Log.e(TAG, ex.toString());
            }
            finally {
                if (timeClient != null) {
                }
            }

        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.display_message, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
