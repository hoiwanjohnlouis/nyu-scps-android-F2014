package com.example.hoiwanlouis.s09e52_hoidynamicbuttonlayout;

        import android.app.Activity;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.Gravity;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TableLayout;
        import android.widget.TableRow;

        import java.util.ArrayList;
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;
        import java.util.Random;


public class MainActivity extends Activity {

    private String DEBUG_TAG = this.getClass().getSimpleName();
    private Random randId = new Random(7919);  // a very large prime number

    private List<Button> letterList = new ArrayList<Button>();
    private Map<Integer,Integer> id2hash = new HashMap<Integer, Integer>();
    private Map<Integer,Integer> hash2Id = new HashMap<Integer, Integer>();

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

        Log.v(DEBUG_TAG, "in init()");
        TableLayout myTable = (TableLayout) findViewById(R.id.boggle_board_table_main);

        TableLayout.LayoutParams rowLayoutParams =
                new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.WRAP_CONTENT,  // Width
                        TableLayout.LayoutParams.WRAP_CONTENT,  // Height
                        1                                       // Weight
                );

        int buttonId = 0;
        int numberOfButtons = 0;
        Button tmpBtn;
        for (int i = 0; i < 5; i++) {
            TableRow myRow = new TableRow(this);
            myRow.setLayoutParams(rowLayoutParams);
            myRow.setGravity(Gravity.CENTER);
            for (int j = 0; j < 5; j++) {
                buttonId = randId.nextInt();
                Button myButton = new Button(this);
                myButton.setId(buttonId);
                myButton.setText(MainActivity.randomChar().toUpperCase());
                myButton.setTextColor(Color.WHITE);
                myButton.setGravity(Gravity.CENTER);

// need SDK21, but using 17
//                Drawable tempDrawable = getDrawable(R.drawable.button);
//                myButton.setBackground(tempDrawable);

                Log.d(DEBUG_TAG, "Adding button=[" + numberOfButtons + "," + myButton.getText() + "], Id=" + (buttonId));
                myButton.setOnClickListener(
                        new View.OnClickListener() {
                            public void onClick(View v) {
                                Log.v(DEBUG_TAG,"in onClick()");
                                chooseLetter(v);
                            }
                        }
                );
                myRow.addView(myButton);

                // TODO: set up an intent somewhere to process a SUBMIT to process word array/hash.
                // or add button to an arraylist/hash map for word comparison?
                // hash map should use Id as key & numberOfButtons as data to reuse Mike's verification code?
                letterList.add(myButton);
                id2hash.put(numberOfButtons,buttonId);
                Log.d(DEBUG_TAG, "id2hash.get("+numberOfButtons+")="+id2hash.get(numberOfButtons));
                hash2Id.put(buttonId,numberOfButtons);
                int xx = hash2Id.get(buttonId);
                Log.d(DEBUG_TAG, "hash2Id.get("+buttonId+")="+hash2Id.get(buttonId));
                tmpBtn = letterList.get(xx);
                Log.d(DEBUG_TAG, "tmpBtn.getText()="+tmpBtn.getText());

                // get the next button slot
                numberOfButtons++;
            }
            myTable.addView(myRow);
        }
        // stk.addView(tl0);

    }
    public static String randomChar() {
        // Randomly select letter from alphabet
        String alphabet = "abcdefghijklmnopqrstuvwxyz";  // should be a resource string.
        Random r = new Random();
        int index = r.nextInt(alphabet.length());
        return alphabet.substring(index, index + 1);
    }

    public void chooseLetter(View v) {
        Log.v(DEBUG_TAG,"in chooseLetter()");
        Button btn = (Button) v;
        Log.d(DEBUG_TAG,
                "letter=["
                + btn.getText()
                + "]"
                + ", Id=["
                + v.getId()
                + "]"
                + " clicked");
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
