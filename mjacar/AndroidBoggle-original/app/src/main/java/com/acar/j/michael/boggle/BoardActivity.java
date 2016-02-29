package com.acar.j.michael.boggle;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class BoardActivity extends Activity implements BoardFragment.OnFragmentInteractionListener, HighScoreFragment.OnFragmentInteractionListener{

    // Key used to persistently store the value of high score
    public static final String highScore_key = "com.acar.j.michael.boggle.highscore";

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

    public boolean isValidWord(String checkWord) {
        // Checks if checkWord is in myDict
        return myDict.containsKey(checkWord);
    }

    public void setupBoard() {
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

        SharedPreferences saveHighScore = getSharedPreferences(highScore_key, 0);
        Integer highScore = saveHighScore.getInt(highScore_key, 0);

        TextView highScoreText = (TextView) findViewById(R.id.high_score);
        highScoreText.setText(highScore.toString());
    }

    public void clearWord() {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDict = BoggleDriver.loadDictionary(this);

        setContentView(R.layout.activity_board);
        setupBoard();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.board, menu);
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

    public void chooseLetter(View v) {
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
        // Submit word
        // Take appropriate action and clear sequence and word
        TextView output = (TextView) findViewById(R.id.output);
        TextView scoreText = (TextView) findViewById(R.id.score);
        TextView highScoreText = (TextView) findViewById(R.id.high_score);
        SharedPreferences saveHighScore = getSharedPreferences(highScore_key, 0);
        Integer highScore = saveHighScore.getInt(highScore_key, 0);

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

            if (score > highScore) {
                SharedPreferences.Editor highScoreEditor = saveHighScore.edit();
                highScoreEditor.putInt(highScore_key, score);
                highScoreEditor.commit();
                highScoreText.setText(score.toString());
            }

            wordsUsed.add(word.toString());
            sequence.clear();
            clearWord();
            return;
        }
    }
}
