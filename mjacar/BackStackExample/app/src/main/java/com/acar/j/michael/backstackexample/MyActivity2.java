package com.acar.j.michael.backstackexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Random;


public class MyActivity2 extends Activity {

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
        setContentView(R.layout.activity_my_activity2);

        TextView text = (TextView) findViewById(R.id.text2);

        if (savedInstanceState != null) {
            String newText = savedInstanceState.getString("text");
            text.setText(newText);
        }

        else {
            StringBuilder newText = new StringBuilder();
            newText.append(randomChar()).append(randomChar()).append(randomChar()).append(randomChar());
            text.setText(newText.toString());
        }
    }

    public void toAc3(View v) {
        Intent intent = new Intent(this, MyActivity3.class);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        TextView text = (TextView) findViewById(R.id.text2);
        outState.putString("text", (String) text.getText());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_activity2, menu);
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
