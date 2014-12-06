package com.hoiwanlouis.mybusinesscard;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


//public class MainActivity extends Activity {
public class MainActivity extends ActionBarActivity {

    private final String DEBUG_TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(DEBUG_TAG, "in onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        setupButton();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(DEBUG_TAG, "in onCreateOptionsMenu");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(DEBUG_TAG, "in onOptionsItemSelected");
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setupButton() {
        Log.d(DEBUG_TAG, "setupButton");

        // setup the action button
        Button submitButton = (Button) findViewById(R.id.submit_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder sb = null;
                sb = sb.append("Hello ");
                sb = sb.append(findViewById(R.id.user_name).toString());
                sb = sb.append("!  Thank you for joining our mailing list.  ");
                sb = sb.append("We will contact you using the email address:");
                sb = sb.append(findViewById(R.id.email_address).toString());
                sb = sb.append(".");
                Toast.makeText(getApplicationContext(), sb, Toast.LENGTH_SHORT).show();
                Log.d(DEBUG_TAG, sb.toString());

//                Intent intent = new Intent(MainActivity.this, MyPrefActivity.class);
//                startActivity(intent);

            }
        });

    }

}
