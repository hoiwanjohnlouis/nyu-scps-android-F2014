package com.example.hoiwanlouis.s08e26_myipintentservice;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;



public class MainActivity extends Activity {

    //    private BoundService boundService;
    private Button startOverlayServiceButton;

    private BoundService boundService;
    private Button startBoundServiceButton;
    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(MyActivity.this, "Service is disconnected", Toast.LENGTH_SHORT).show();
            startBoundServiceButton.setText("Bind to BoundService");
            boundService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Toast.makeText(MyActivity.this,"BoundService is connected", Toast.LENGTH_SHORT).show();
            startBoundServiceButton.setText("Disconnect from BoundService");

            LocalBinder localBinder = (LocalBinder) service;
            boundService = localBinder.getBoundServerInstance();
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        Button startIntentServiceButton = (Button) findViewById(R.id.button);
        startIntentServiceButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }
        );

        Button startOverlayServiceButton = (Button) findViewById(R.id.overlayServiceButton);
        startIntentServiceButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }
        );

        startBoundServiceButton = (Button) findViewById(R.id,boundServiceButton);
        startBoundServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyActivity.this, BoundService.class);
                if (boundService == null) {
                    boundService(intent, se)
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
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
