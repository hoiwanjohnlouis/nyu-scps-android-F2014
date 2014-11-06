package com.example.hoiwanlouis.s09e52_hoidynamicbuttonlayout;

        import android.app.Activity;
        import android.content.Intent;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.Gravity;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.LinearLayout;
        import android.widget.TableLayout;
        import android.widget.TableRow;
        import android.widget.TextView;

        import java.util.Random;

        import static java.lang.Math.abs;


public class MainActivity extends Activity {

    private String DEBUG_TAG = this.getClass().getSimpleName();

    protected void onCreate(Bundle savedInstanceState) {
        Log.v(DEBUG_TAG, "in onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.v(DEBUG_TAG, "in onCreateOptionsMenu()");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.v(DEBUG_TAG, "in onOptionsItemSelected()");
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void init() {

        Random randId = new Random(7919);  // a very large prime number
        Log.v(DEBUG_TAG, "in init()");
        TableLayout myTable = (TableLayout) findViewById(R.id.table_main);

        TableLayout.LayoutParams rowLayoutParams =
                new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.WRAP_CONTENT,  // Width
                        TableLayout.LayoutParams.WRAP_CONTENT,  // Height
                        1                                       // Weight
                );

        TableLayout.LayoutParams buttonLayoutParams =
                new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.MATCH_PARENT,  // Width
                        TableLayout.LayoutParams.MATCH_PARENT,  // Height
                        1                                       // Weight
                );

        int buttonId = 0;
        int numberOfButtons = 0;
        for (int i = 0; i < 5; i++) {
            TableRow myRow = new TableRow(this);
            myRow.setLayoutParams(rowLayoutParams);
            myRow.setGravity(Gravity.CENTER);
            for (int j = 0; j < 5; j++) {
                numberOfButtons++;
                buttonId = abs(randId.nextInt());
                Button myButton = new Button(this);
                myButton.setId(buttonId);
                myButton.setText(MainActivity.randomChar().toUpperCase());
                myButton.setTextColor(Color.WHITE);
                myButton.setGravity(Gravity.CENTER);

//                btn0.setLayoutParams(buttonLayoutParams);
                Log.d(DEBUG_TAG, "Adding button=[" + numberOfButtons + "," + myButton.getText() + "], buttonId=" + (buttonId));
                myButton.setOnClickListener(
                        new View.OnClickListener() {
                            public void onClick(View v) {
                                Log.v(DEBUG_TAG,"in onClick()");
                                Button btn = (Button) v;
                                Log.d(DEBUG_TAG,"buttonId=[" + v.getId() + "] letter=["
                                        + btn.getText()
                                        + "] clicked");
                                if (btn.getCurrentTextColor() == Color.RED) {
                                    btn.setTextColor(Color.WHITE);
                                    // remove the letter from word array/hash
                                }
                                else {
                                    btn.setTextColor(Color.RED);
                                    // add the letter to word array/hash
                                }

                            }
                        }
                );
                myRow.addView(myButton);

                // TODO: set up an intent somewhere to process a SUBMIT to process word array/hash.
                // or add button to an arraylist/hash map for word comparison?
                // hash map should use Id as key & numberOfButtons as data to reuse Mike's verification code?

            }
            myTable.addView(myRow);
        }
        // stk.addView(tl0);

    }
    public static String randomChar() {
        // Randomly select letter from alphabet
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        Random r = new Random();
        int index = r.nextInt(26);
        return alphabet.substring(index, index + 1);
    }

}
