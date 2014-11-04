package com.example.hoiwanlouis.s06e51_hoiboggle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class BoardActivity extends Activity implements BoardFragment.OnFragmentInteractionListener, HighScoreFragment.OnFragmentInteractionListener{

    private String DEBUG_TAG;

    // Keys for reading grid data from SharedPreferences
    private String BOGGLE_GRID_SHARED_PREFERENCES;
    private String DEFAULT_GRID_SIZE;
    private SharedPreferences saveGridSize;
    private String boggleGridSize;

    // Key used to persistently store the value of high score
    private String BOGGLE_HIGH_SCORE_SHARED_PREFERENCES;
    private SharedPreferences saveHighScore;
    private Integer boggleHighScore;

    // The letters that make up the board
    public ArrayList<String> board = new ArrayList<String>();
    // The dictionary being checked against
    public HashMap<String, String> myDict = new HashMap<String, String>();
    // The sequence of indices chosen from board
    public ArrayList<Integer> sequence = new ArrayList<Integer>();
    // The actual word composed from sequence
    public StringBuilder word = new StringBuilder();
    // The words that have already been used in this game
    public ArrayList<String> wordsUsed = new ArrayList<String>();
    // Your score
    public Integer score = 0;

    public void onFragmentInteraction(Uri uri) {}

    private String internalFileName;
    private FileOutputStream internalOutputStream;
    private File internalFile;

    private String externalFileName;
    private FileOutputStream externalOutputStream;
    private File externalFile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        DEBUG_TAG = getString(R.string.title_activity_board);
        Log.v(DEBUG_TAG,"onCreate()");

        // 20141025 initialized variables from resource file strings.xml
        initializeResources();

        // 20141025 initialized variables from resource file strings.xml
        connectSharedPreferences();

        // 20141025 open the internal file, create if needed
        internalFile = openInternalFile();

        // 20141025 open the external file, create if needed
        externalFile = openExternalFile();

        // 20141025 open the sqlite db, create if needed
        openSQLiteDB();

        // 20141104 get the boggle data shared preferences
        boggleGridSize = getBoggleGridSize();
        boggleHighScore = getBoggleHighScore();

        //
        myDict = BoggleDriver.loadDictionary(this);
        setupBoard();

        return;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.v(DEBUG_TAG,"onCreateOptionsMenu()");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.v(DEBUG_TAG, "in onOptionsItemSelected()");
        boolean isItemSelected = false;

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            isItemSelected = true;
        }
        else {
            isItemSelected = super.onOptionsItemSelected(item);
        }

        return isItemSelected;
    }

    public void setupBoard() {
        Log.v(DEBUG_TAG,"setupBoard()");

        // Assign random letters to all slots
        // Initialize high score text with the high score
        Button letter = (Button) findViewById(R.id.letter1);
        String randomChar = BoggleDriver.randomChar();
        board.add(randomChar);
        letter.setText(randomChar.toUpperCase());

        letter = (Button) findViewById(R.id.letter2);
        randomChar = BoggleDriver.randomChar();
        board.add(randomChar);
        letter.setText(randomChar.toUpperCase());

        letter = (Button) findViewById(R.id.letter3);
        randomChar = BoggleDriver.randomChar();
        board.add(randomChar);
        letter.setText(randomChar.toUpperCase());

        letter = (Button) findViewById(R.id.letter4);
        randomChar = BoggleDriver.randomChar();
        board.add(randomChar);
        letter.setText(randomChar.toUpperCase());

        letter = (Button) findViewById(R.id.letter5);
        randomChar = BoggleDriver.randomChar();
        board.add(randomChar);
        letter.setText(randomChar.toUpperCase());

        letter = (Button) findViewById(R.id.letter6);
        randomChar = BoggleDriver.randomChar();
        board.add(randomChar);
        letter.setText(randomChar.toUpperCase());

        letter = (Button) findViewById(R.id.letter7);
        randomChar = BoggleDriver.randomChar();
        board.add(randomChar);
        letter.setText(randomChar.toUpperCase());

        letter = (Button) findViewById(R.id.letter8);
        randomChar = BoggleDriver.randomChar();
        board.add(randomChar);
        letter.setText(randomChar.toUpperCase());

        letter = (Button) findViewById(R.id.letter9);
        randomChar = BoggleDriver.randomChar();
        board.add(randomChar);
        letter.setText(randomChar.toUpperCase());

        letter = (Button) findViewById(R.id.letter10);
        randomChar = BoggleDriver.randomChar();
        board.add(randomChar);
        letter.setText(randomChar.toUpperCase());

        letter = (Button) findViewById(R.id.letter11);
        randomChar = BoggleDriver.randomChar();
        board.add(randomChar);
        letter.setText(randomChar.toUpperCase());

        letter = (Button) findViewById(R.id.letter12);
        randomChar = BoggleDriver.randomChar();
        board.add(randomChar);
        letter.setText(randomChar.toUpperCase());

        letter = (Button) findViewById(R.id.letter13);
        randomChar = BoggleDriver.randomChar();
        board.add(randomChar);
        letter.setText(randomChar.toUpperCase());

        letter = (Button) findViewById(R.id.letter14);
        randomChar = BoggleDriver.randomChar();
        board.add(randomChar);
        letter.setText(randomChar.toUpperCase());

        letter = (Button) findViewById(R.id.letter15);
        randomChar = BoggleDriver.randomChar();
        board.add(randomChar);
        letter.setText(randomChar.toUpperCase());

        letter = (Button) findViewById(R.id.letter16);
        randomChar = BoggleDriver.randomChar();
        board.add(randomChar);
        letter.setText(randomChar.toUpperCase());

        letter = (Button) findViewById(R.id.letter17);
        randomChar = BoggleDriver.randomChar();
        board.add(randomChar);
        letter.setText(randomChar.toUpperCase());

        letter = (Button) findViewById(R.id.letter18);
        randomChar = BoggleDriver.randomChar();
        board.add(randomChar);
        letter.setText(randomChar.toUpperCase());

        letter = (Button) findViewById(R.id.letter19);
        randomChar = BoggleDriver.randomChar();
        board.add(randomChar);
        letter.setText(randomChar.toUpperCase());

        letter = (Button) findViewById(R.id.letter20);
        randomChar = BoggleDriver.randomChar();
        board.add(randomChar);
        letter.setText(randomChar.toUpperCase());

        letter = (Button) findViewById(R.id.letter21);
        randomChar = BoggleDriver.randomChar();
        board.add(randomChar);
        letter.setText(randomChar.toUpperCase());

        letter = (Button) findViewById(R.id.letter22);
        randomChar = BoggleDriver.randomChar();
        board.add(randomChar);
        letter.setText(randomChar.toUpperCase());

        letter = (Button) findViewById(R.id.letter23);
        randomChar = BoggleDriver.randomChar();
        board.add(randomChar);
        letter.setText(randomChar.toUpperCase());

        letter = (Button) findViewById(R.id.letter24);
        randomChar = BoggleDriver.randomChar();
        board.add(randomChar);
        letter.setText(randomChar.toUpperCase());

        letter = (Button) findViewById(R.id.letter25);
        randomChar = BoggleDriver.randomChar();
        board.add(randomChar);
        letter.setText(randomChar.toUpperCase());

        TextView highScoreText = (TextView) findViewById(R.id.high_score);
        highScoreText.setText(boggleHighScore.toString());
    }

    public boolean isValidWord(String checkWord) {
        Log.v(DEBUG_TAG,"isValidWord()");
        // Checks if checkWord is in myDict
        return myDict.containsKey(checkWord);
    }

    public void clearWord() {
        Log.v(DEBUG_TAG,"clearWord()");
        // Reset board after word has been submitted
        // Change all colors back and clear word
        Button letter = (Button) findViewById(R.id.letter1);
        letter.getBackground().setColorFilter(0xff888888, PorterDuff.Mode.MULTIPLY);
        letter = (Button) findViewById(R.id.letter2);
        letter.getBackground().setColorFilter(0xff888888, PorterDuff.Mode.MULTIPLY);
        letter = (Button) findViewById(R.id.letter3);
        letter.getBackground().setColorFilter(0xff888888, PorterDuff.Mode.MULTIPLY);
        letter = (Button) findViewById(R.id.letter4);
        letter.getBackground().setColorFilter(0xff888888, PorterDuff.Mode.MULTIPLY);
        letter = (Button) findViewById(R.id.letter5);
        letter.getBackground().setColorFilter(0xff888888, PorterDuff.Mode.MULTIPLY);
        letter = (Button) findViewById(R.id.letter6);
        letter.getBackground().setColorFilter(0xff888888, PorterDuff.Mode.MULTIPLY);
        letter = (Button) findViewById(R.id.letter7);
        letter.getBackground().setColorFilter(0xff888888, PorterDuff.Mode.MULTIPLY);
        letter = (Button) findViewById(R.id.letter8);
        letter.getBackground().setColorFilter(0xff888888, PorterDuff.Mode.MULTIPLY);
        letter = (Button) findViewById(R.id.letter9);
        letter.getBackground().setColorFilter(0xff888888, PorterDuff.Mode.MULTIPLY);
        letter = (Button) findViewById(R.id.letter10);
        letter.getBackground().setColorFilter(0xff888888, PorterDuff.Mode.MULTIPLY);
        letter = (Button) findViewById(R.id.letter11);
        letter.getBackground().setColorFilter(0xff888888, PorterDuff.Mode.MULTIPLY);
        letter = (Button) findViewById(R.id.letter12);
        letter.getBackground().setColorFilter(0xff888888, PorterDuff.Mode.MULTIPLY);
        letter = (Button) findViewById(R.id.letter13);
        letter.getBackground().setColorFilter(0xff888888, PorterDuff.Mode.MULTIPLY);
        letter = (Button) findViewById(R.id.letter14);
        letter.getBackground().setColorFilter(0xff888888, PorterDuff.Mode.MULTIPLY);
        letter = (Button) findViewById(R.id.letter15);
        letter.getBackground().setColorFilter(0xff888888, PorterDuff.Mode.MULTIPLY);
        letter = (Button) findViewById(R.id.letter16);
        letter.getBackground().setColorFilter(0xff888888, PorterDuff.Mode.MULTIPLY);
        letter = (Button) findViewById(R.id.letter17);
        letter.getBackground().setColorFilter(0xff888888, PorterDuff.Mode.MULTIPLY);
        letter = (Button) findViewById(R.id.letter18);
        letter.getBackground().setColorFilter(0xff888888, PorterDuff.Mode.MULTIPLY);
        letter = (Button) findViewById(R.id.letter19);
        letter.getBackground().setColorFilter(0xff888888, PorterDuff.Mode.MULTIPLY);
        letter = (Button) findViewById(R.id.letter20);
        letter.getBackground().setColorFilter(0xff888888, PorterDuff.Mode.MULTIPLY);
        letter = (Button) findViewById(R.id.letter21);
        letter.getBackground().setColorFilter(0xff888888, PorterDuff.Mode.MULTIPLY);
        letter = (Button) findViewById(R.id.letter22);
        letter.getBackground().setColorFilter(0xff888888, PorterDuff.Mode.MULTIPLY);
        letter = (Button) findViewById(R.id.letter23);
        letter.getBackground().setColorFilter(0xff888888, PorterDuff.Mode.MULTIPLY);
        letter = (Button) findViewById(R.id.letter24);
        letter.getBackground().setColorFilter(0xff888888, PorterDuff.Mode.MULTIPLY);
        letter = (Button) findViewById(R.id.letter25);
        letter.getBackground().setColorFilter(0xff888888, PorterDuff.Mode.MULTIPLY);

        word.setLength(0);
    }

    public void chooseLetter(View v) {
        Log.v(DEBUG_TAG,"chooseLetter()");
        // Change color of button to indicate selection
        // Add index to sequence to later check for valid sequence (this check could be done on every letter click)
        // Add letter to word to later check for valid word
        int id = v.getId();
        Button button = (Button) v.findViewById(id);
        button.getBackground().setColorFilter(0xFF00FF00, PorterDuff.Mode.MULTIPLY);

        if (id == R.id.letter1) {
            sequence.add(0);
            word.append(board.get(0));
        }

        else if (id == R.id.letter2) {
            sequence.add(1);
            word.append(board.get(1));
        }

        else if (id == R.id.letter3) {
            sequence.add(2);
            word.append(board.get(2));
        }

        else if (id == R.id.letter4) {
            sequence.add(3);
            word.append(board.get(3));
        }

        else if (id == R.id.letter5) {
            sequence.add(4);
            word.append(board.get(4));
        }

        else if (id == R.id.letter6) {
            sequence.add(5);
            word.append(board.get(5));
        }

        else if (id == R.id.letter7) {
            sequence.add(6);
            word.append(board.get(6));
        }

        else if (id == R.id.letter8) {
            sequence.add(7);
            word.append(board.get(7));
        }

        else if (id == R.id.letter9) {
            sequence.add(8);
            word.append(board.get(8));
        }

        else if (id == R.id.letter10) {
            sequence.add(9);
            word.append(board.get(9));
        }

        else if (id == R.id.letter11) {
            sequence.add(10);
            word.append(board.get(10));
        }

        else if (id == R.id.letter12) {
            sequence.add(11);
            word.append(board.get(11));
        }

        else if (id == R.id.letter13) {
            sequence.add(12);
            word.append(board.get(12));
        }

        else if (id == R.id.letter14) {
            sequence.add(13);
            word.append(board.get(13));
        }

        else if (id == R.id.letter15) {
            sequence.add(14);
            word.append(board.get(14));
        }

        else if (id == R.id.letter16) {
            sequence.add(15);
            word.append(board.get(15));
        }

        else if (id == R.id.letter17) {
            sequence.add(16);
            word.append(board.get(16));
        }

        else if (id == R.id.letter18) {
            sequence.add(17);
            word.append(board.get(17));
        }

        else if (id == R.id.letter19) {
            sequence.add(18);
            word.append(board.get(18));
        }

        else if (id == R.id.letter20) {
            sequence.add(19);
            word.append(board.get(19));
        }

        else if (id == R.id.letter21) {
            sequence.add(20);
            word.append(board.get(20));
        }

        else if (id == R.id.letter22) {
            sequence.add(21);
            word.append(board.get(21));
        }

        else if (id == R.id.letter23) {
            sequence.add(22);
            word.append(board.get(22));
        }

        else if (id == R.id.letter24) {
            sequence.add(23);
            word.append(board.get(23));
        }

        else if (id == R.id.letter25) {
            sequence.add(24);
            word.append(board.get(24));
        }
    }

    public void checkSequence(View v) {
        Log.v(DEBUG_TAG,"checkSequence()");
        // Submit word
        // Take appropriate action and clear sequence and word
        TextView output = (TextView) findViewById(R.id.output);
        TextView scoreText = (TextView) findViewById(R.id.score);
        TextView highScoreText = (TextView) findViewById(R.id.high_score);

        if (!BoggleDriver.isValidSequence(sequence)) {
            // Output "Not a valid sequence"
            output.setText("Not a valid sequence");
            sequence.clear();
            clearWord();
            return;
        }

        else if (!isValidWord(word.toString())) {
            // Output "Not a valid word"
            output.setText("Not a valid word");
            sequence.clear();
            clearWord();
            return;
        }

        else if (wordsUsed.contains(word.toString())) {
            // Output "Already used this"
            output.setText("Already used this");
            sequence.clear();
            clearWord();
            return;
        }

        else if (isValidWord(word.toString()) && !wordsUsed.contains(word.toString())) {
            // Update score = score + 1
            output.setText("Good word!");
            score++;
            scoreText.setText(score.toString());

            // check if this is a new high score?
            if (score > getBoggleHighScore()) {
                setBoggleHighScore(score);
                highScoreText.setText(score.toString());

                // 20141025 Add the code to add a sqlite db record for new high score

            }

            // 20141025 Add the code to update an internal file to add the word

            // 20141025 Add the code to update an external file to add the word

            wordsUsed.add(word.toString());
            sequence.clear();
            clearWord();

            return;
        }
    }

    // load data from resource file strings.xml
    private void initializeResources() {
        Log.v(DEBUG_TAG,"initializeResources()");

        BOGGLE_GRID_SHARED_PREFERENCES = getString(R.string.boggle_grid_shared_preferences);
        DEFAULT_GRID_SIZE = getString(R.string.default_number_of_boggle_dice);
        Log.i(DEBUG_TAG,"BOGGLE_GRID_SHARED_PREFERENCES:" + BOGGLE_GRID_SHARED_PREFERENCES);
        Log.i(DEBUG_TAG,"DEFAULT_GRID_SIZE:" + DEFAULT_GRID_SIZE);

        BOGGLE_HIGH_SCORE_SHARED_PREFERENCES = getString(R.string.boggle_high_score_shared_preferences);
        Log.i(DEBUG_TAG,"BOGGLE_HIGH_SCORE_SHARED_PREFERENCES:" + BOGGLE_HIGH_SCORE_SHARED_PREFERENCES);

        internalFileName = getString(R.string.internalFileName);
        externalFileName = getString(R.string.externalFileName);
        Log.i(DEBUG_TAG,"internalFileName:" + internalFileName);
        Log.i(DEBUG_TAG,"externalFileName:" + externalFileName);

        return;
    }

    private File openInternalFile() {
        Log.v(DEBUG_TAG,"openInternalFile()");
        File intFile = null;

        return intFile;
    }

    private File openExternalFile() {
        Log.v(DEBUG_TAG,"openExternalFile()");
        File extFile = null;

        return extFile;
    }

    private void openSQLiteDB() {
        Log.v(DEBUG_TAG,"openSQLiteDB()");

        return;
    }

    //
    private void connectSharedPreferences() {
        Log.v(DEBUG_TAG,"connectSharedPreferences()");
        // handles to shared preferences
        saveGridSize = getSharedPreferences(BOGGLE_GRID_SHARED_PREFERENCES, 0);
        saveHighScore = getSharedPreferences(BOGGLE_HIGH_SCORE_SHARED_PREFERENCES, 0);

        // for debugging purposes
        SharedPreferences.Editor newEdit = saveHighScore.edit();
        newEdit.clear();
        newEdit.commit();

    }

    // retrieve the grid size from shared preferences
    private String getBoggleGridSize() {
        Log.v(DEBUG_TAG,"getBoggleGridSize()");
        boggleGridSize = saveGridSize.getString(BOGGLE_GRID_SHARED_PREFERENCES, DEFAULT_GRID_SIZE);
        Log.i(DEBUG_TAG,"Using grid size of:" + boggleGridSize);
        return boggleGridSize;
    }

    // retrieve the high score from shared preferences
    private Integer getBoggleHighScore() {
        Log.v(DEBUG_TAG,"getBoggleHIghScore()");
        boggleHighScore = saveHighScore.getInt(BOGGLE_HIGH_SCORE_SHARED_PREFERENCES, 0);
        Log.i(DEBUG_TAG,"Current high score is:" + boggleHighScore.toString());
        return boggleHighScore;
    }

    // we have a new high score
    public void setBoggleHighScore(Integer newHighScore) {
        Log.v(DEBUG_TAG,"getBoggleHIghScore()");
        SharedPreferences.Editor highScoreEditor = saveHighScore.edit();
        highScoreEditor.putInt(BOGGLE_HIGH_SCORE_SHARED_PREFERENCES, newHighScore);
        highScoreEditor.commit();
        Log.i(DEBUG_TAG,"New high score is:" + newHighScore.toString());
    }


}
