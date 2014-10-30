package com.example.hoiwanlouis.s08e22_myexternalstorage;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


public class MyActivity extends Activity {

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (
                (Environment.MEDIA_MOUNTED.equals(state)) ||
                (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state))
           ) {
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);


//        File myFile = new File("hoiBufferIO.txt");
//        PrintWriter pwriter = new PrintWriter(new BufferedWriter(new FileWriter(myFile)));
//        System.out.println(myFile.getAbsoluteFile());
//        pwriter.println("Here's the value of pi:");  // no need for "\n", bufferedWrite auto includes
//        pwriter.println(3.14159);
//        pwriter.println("We are done!");
//        pwriter.close();
//
//        BufferedReader preader = null;
//        try {
//            preader = new BufferedReader(new FileReader(myFile));
//            System.out.println(myFile.getAbsoluteFile());
//            System.out.println(preader.readLine());
//            String piString = preader.readLine();
//            double piValue = Double.parseDouble(piString);
//            System.out.println(piValue);
//            System.out.println(preader.readLine());
//        } catch (NumberFormatException ex1) {
//            System.out.println(ex1);
//        } catch (NullPointerException ex2) {
//            System.out.println(ex2);
//        } catch (FileNotFoundException ex3) {
//            System.out.println(ex3);
//        } finally {
//            if (preader != null) {
//                preader.close();
//            }
//        }
// change code to use bufferedio, streams are deprecated

        File dir = null;
        dir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS),
                "my_own_directory"
        );
        if (!isExternalStorageWritable() || !dir.mkdirs()) {
            Log.e("tag", "Directory not created");
        }
        File myExternalFile = new File(dir, "myExternalData.txt");
        System.out.println(myExternalFile.getAbsoluteFile());

        PrintWriter pwriter = null;
        try {
            pwriter = new PrintWriter(new BufferedWriter(new FileWriter(myExternalFile)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pwriter.println("I'm trapped in an external file!");
        pwriter.close();

        // work variable
        StringBuilder sbText = new StringBuilder();

        // read the internal file and append to sbText
        BufferedReader brMyExternalFile = null;
        try {
            brMyExternalFile = new BufferedReader(new FileReader(myExternalFile));
            String myLine;
            sbText.append("myExternalFile:");
            while ((myLine = brMyExternalFile.readLine()) != null) {
                sbText.append(myLine);
                sbText.append('\n');
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (brMyExternalFile != null) {
                try {
                    brMyExternalFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        TextView tv = (TextView)findViewById(R.id.text_view);
        tv.setText(sbText);

//        file.delete();

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
