package com.thehackerati.android.apps.myoldanimationproj1;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends Activity {

    protected AlphaAnimation alphaAnimation;
    protected ScaleAnimation scaleAnimation;
    protected ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    protected void fadeIn(View view)
    {
        imgView = (ImageView) findViewById(R.id.ImageViewForShape);
        alphaAnimation = new AlphaAnimation(0,1);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setFillBefore(true);
        alphaAnimation.setRepeatCount(100);
        alphaAnimation.setAnimationListener(animationListener);

        //magic!
        stopAnimation();
        imgView.startAnimation(alphaAnimation);
    }

    protected void fadeOut(View view) {
        imgView = (ImageView) findViewById(R.id.ImageViewForShape);
        alphaAnimation = new AlphaAnimation(1,0);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setRepeatCount(100);
        alphaAnimation.setAnimationListener(animationListener);

        //magic!
        stopAnimation();
        imgView.startAnimation(alphaAnimation);
    }

    protected void stopAnimation() {
        if (alphaAnimation != null) {
            alphaAnimation.cancel();
        }
    }

    protected Animation.AnimationListener animationListener
            = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            Toast.makeText(MainActivity.this.getApplicationContext(),
                    "Animation started.",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            Toast.makeText(MainActivity.this.getApplicationContext(),
                    "Animation ended.",
                    Toast.LENGTH_SHORT).show();
            scale();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    };

    protected void scale() {
        scaleAnimation = new ScaleAnimation(1f,2f,1f,2f,0.5f,0.5f);
        scaleAnimation.setDuration(200);
        imgView.startAnimation(scaleAnimation);
    }

    @Override
    protected void onResume() {
        super.onResume();


        final Button fadeOutButton = (Button) findViewById(R.id.fadeOutButton);
        fadeOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fadeOut(view);
            }
        });

        final Button fadeInButton = (Button) findViewById(R.id.fadeInButton);
        fadeInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fadeIn(view);
            }
        });

        final Button stopButton = (Button) findViewById(R.id.stopButton);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopAnimation();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
}
