package com.example.hoiwanlouis.s09e03_mikebackstack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Random;


public class MyActivity4 extends Activity {

    private static String randomChar() {
        // Randomly select letter from alphabet
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        Random r = new Random();
        int index = r.nextInt(26);
        return alphabet.substring(index, index + 1);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_activity4);

        StringBuilder newText = new StringBuilder();
        newText.append(randomChar()).append(randomChar()).append(randomChar()).append(randomChar()).append(randomChar()).append(randomChar());
        TextView text = (TextView) findViewById(R.id.text4);
        text.setText(newText.toString());
    }

    public void toAc1(View v) {
        Intent intent = new Intent(this, MyActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        TextView text = (TextView) findViewById(R.id.text4);
        outState.putString("text", (String) text.getText());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_activity4, menu);
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
