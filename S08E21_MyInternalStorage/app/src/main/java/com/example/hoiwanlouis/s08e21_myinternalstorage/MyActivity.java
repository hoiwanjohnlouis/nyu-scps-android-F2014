package com.example.hoiwanlouis.s08e21_myinternalstorage;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class MyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        // create the Internal Storage File
        String fileName = "myInternalStorage.txt";
        Context context = this;

        // create a regular file
        File myFile;
        myFile = new File(context.getFilesDir(), fileName);
        System.out.println(myFile.getAbsoluteFile());
        // open a regular file
        PrintWriter pwMyFile = null;
        try {
            pwMyFile = new PrintWriter(new BufferedWriter(new FileWriter(myFile)));
        }   catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(pwMyFile);

        // or create a tmp file?
        File tmpFile = null;
        try {
            tmpFile = File.createTempFile(fileName, null, context.getCacheDir());
        }   catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(tmpFile.getAbsoluteFile());
        // open the tmp file?
        PrintWriter pwTmpFile = null;
        try {
            pwTmpFile = new PrintWriter(new BufferedWriter(new FileWriter(tmpFile)));
        }   catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(pwTmpFile);

        String text = "I'm trapped in an internal storage file!";

        // write to internal file
        pwMyFile.println("my:" + text);
        pwMyFile.close();

        // write to tmp file
        pwTmpFile.println("tmp:" + text);
        pwTmpFile.close();

        StringBuilder sbText = new StringBuilder();

        // read the internal file and append to sbText
        BufferedReader brMyFile = null;
        try {
            brMyFile = new BufferedReader(new FileReader(myFile));
            String myLine;
            while ((myLine = brMyFile.readLine()) != null) {
                sbText.append(myLine);
                sbText.append('\n');
            }
        }   catch (IOException e) {
            e.printStackTrace();
        }   finally {
            if (brMyFile != null) {
                try {
                    brMyFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // read the internal file and append to sbText
        BufferedReader brTmpFile = null;
        try {
            brTmpFile = new BufferedReader(new FileReader(tmpFile));
            String tmpLine;
            while ((tmpLine = brTmpFile.readLine()) != null) {
                sbText.append(tmpLine);
                sbText.append('\n');
            }
        }   catch (IOException e) {
            e.printStackTrace();
        }   finally {
            if (brTmpFile != null) {
                try {
                    brTmpFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // show our data on mobile device
        TextView tv = (TextView)findViewById(R.id.text_view);
        tv.setText(sbText);

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
