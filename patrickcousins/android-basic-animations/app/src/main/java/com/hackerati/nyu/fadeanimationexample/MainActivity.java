/*
    Copyright 2014 Patrick Cousins

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package com.hackerati.nyu.fadeanimationexample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends Activity {


    ImageView imgView;

    AlphaAnimation alphaAnimation;
    ScaleAnimation scaleAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    protected void onResume() {
        super.onResume();

        Button btn = (Button) findViewById(R.id.button);
        imgView = (ImageView) findViewById(R.id.img_view);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeIn();
            }
        });
        Button fadeOutBtn = (Button) findViewById(R.id.button3);

        fadeOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeOut();
            }
        });

        Button slideButton = (Button) findViewById(R.id.slideBtn);

        slideButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fadeAndSlideOut();
            }
        });

        Button stopBtn = (Button) findViewById(R.id.button2);

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopAnimation();
            }
        });


        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(
                        MainActivity.this.getApplicationContext(),
                        "Still here!",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });

    }


    private void fadeIn() {
        imgView.startAnimation(getFadeInAnim());
    }

    private void fadeOut() {
        imgView.startAnimation(getFadeOutAnim());
    }

    private Animation getFadeOutAnim() {
        AlphaAnimation anim = new AlphaAnimation(1,0);
        anim.setDuration(500);
        anim.setStartOffset(1000);
        anim.setFillAfter(true);
        return anim;
    }

    private Animation getFadeInAnim() {
        AlphaAnimation anim = new AlphaAnimation(0,1);
        anim.setDuration(500);
        anim.setStartOffset(2500);
        anim.setFillAfter(true);
        return anim;
    }


    private void scale() {
        // from X, to X, from Y, to Y, Pivot X, Pivot Y
        scaleAnimation = new ScaleAnimation(1f, 2f, 1f, 2f, 0.5f, 0.5f);
        scaleAnimation.setDuration(200);
        imgView.startAnimation(scaleAnimation);
    }

    private void stopAnimation() {

        alphaAnimation.cancel();

        // this also works...
        //imgView.clearAnimation();
    }


    private void fadeAndSlideOut() {

        AnimationSet set = new AnimationSet(false);
        set.addAnimation(getFadeOutAnim());
        set.addAnimation(getSlideOutAnim());

        imgView.startAnimation(set);


    }


    private Animation getSlideOutAnim() {
        Animation out = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT,  0.0f,
                Animation.RELATIVE_TO_PARENT,  +1.0f,
                Animation.RELATIVE_TO_PARENT,  0.0f,
                Animation.RELATIVE_TO_PARENT,  0.0f
        );
        out.setDuration(3000);
        out.setInterpolator(new AnticipateOvershootInterpolator(0.9f));
        return out;
    }


    Animation.AnimationListener animationListener
            = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            Toast.makeText(
                    MainActivity.this.getApplicationContext(),
                    "Anim Start!",
                    Toast.LENGTH_SHORT)
                        .show();
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            Toast.makeText(
                    MainActivity.this.getApplicationContext(),
                    "Anim End!",
                    Toast.LENGTH_SHORT)
                    .show();

            scale();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {
        }
    };

}
