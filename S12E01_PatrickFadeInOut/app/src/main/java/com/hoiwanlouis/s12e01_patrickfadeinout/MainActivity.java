package com.hoiwanlouis.s12e01_patrickfadeinout;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

    Button fadeInButton;
    Button fadeOutButton;
    Button stopButton;

    AlphaAnimation alphaAnimation;
    ScaleAnimation scaleAnimation;

    ImageView imgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onResume() {
        super.onResume();

        imgView = (ImageView) findViewById(R.id.imageView);

        fadeInButton = (Button) findViewById(R.id.fadeInButton);
        fadeInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeIn();
            }
        });

        fadeOutButton = (Button) findViewById(R.id.fadeOutButton);
        fadeOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeOut();
            }
        });

        stopButton = (Button) findViewById(R.id.stopButton);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

    //
    private void FadeAndSlideOut() {
        AnimationSet set = new AnimationSet(false);
        set.addAnimation(getFadeOutAnim());
        set.addAnimation(getSlideOutAnim());
        imgView.startAnimation(getFadeOutAnim());


    }

    //
    public void fadeIn() {
    //    alphaAnimation = new AlphaAnimation(0, 1);
    //    alphaAnimation.setDuration(1000);
    //    alphaAnimation.setFillAfter(true);
    //    alphaAnimation.setRepeatCount(7);
    //    alphaAnimation.setAnimationListener(animationListener);
        imgView.startAnimation(getFadeInAnim());
    }

    //
    public void fadeOut() {
    //    alphaAnimation = new AlphaAnimation(1, 0);
    //    alphaAnimation.setDuration(1000);
    //    alphaAnimation.setFillAfter(true);
    //    alphaAnimation.setRepeatCount(3);
    //    alphaAnimation.setAnimationListener(animationListener);
    //    imgView.startAnimation(alphaAnimation);
        imgView.startAnimation(getFadeOutAnim());
    }

    //
    private Animation getFadeInAnim() {
        AlphaAnimation anim = new AlphaAnimation(0,1);
        anim.setDuration(1000);
        anim.setFillAfter(true);
        anim.setRepeatCount(5);
        anim.setStartOffset(2500);
        anim.setAnimationListener(animationListener);
        return anim;
    }

    //
    private Animation getFadeOutAnim() {
        AlphaAnimation anim = new AlphaAnimation(1, 0);
        anim.setDuration(1000);
        anim.setFillAfter(true);
        anim.setRepeatCount(10);
        anim.setStartOffset(3500);
        anim.setAnimationListener(animationListener);
        return anim;
    }

    private Animation getSlideOutAnim() {
        AlphaAnimation anim = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT + 00.00f,
                Animation.RELATIVE_TO_PARENT + 00.50f,
                Animation.RELATIVE_TO_PARENT + 01.00f,
                Animation.RELATIVE_TO_PARENT + 00.50f
                );
        return anim;
    }

    //
    private void scale() {
        scaleAnimation = new ScaleAnimation(1f, 2f, 1f, 2f, 0.5f, 0.5f);
        scaleAnimation.setDuration(200);
        imgView.startAnimation(scaleAnimation);
    }

    //
    public void stopAnimation() {
        if (alphaAnimation != null) {
            alphaAnimation.cancel();
            // this also works, just change the if condition
            //  imgView.clearAnimation();
        }
    }

    Animation.AnimationListener animationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            Toast.makeText(MainActivity.this.getApplicationContext(),
                    "Animation Start!",
                    Toast.LENGTH_SHORT)
                    .show();
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            Toast.makeText(MainActivity.this.getApplicationContext(),
                    "Animation End!",
                    Toast.LENGTH_SHORT)
                    .show();
            // change the scale of image
            scale();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
            Toast.makeText(MainActivity.this.getApplicationContext(),
                    "Animation Repeat!",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    };


}

