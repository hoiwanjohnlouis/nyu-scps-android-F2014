package com.example.hoiwanlouis.s08e26_myipintentservice;

import android.app.ActionBar;
import android.app.Service;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowId;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

/**
 * Created by hoiwanlouis on 10/25/14.
 */
public class OverlayService extends Service {

    private View overlayView;

    @Override
    public void onCreate() {
        super.onCreate();

        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        LayoutInflater
                inflater =
                (Inflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        overlayView = inflater.inflate(R.layout.overlay, new FrameLayout(this));

        Button button = (Button) overlayView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OverlayService.this.stopSelf();
            }
        });

        windowManager.addView(overlayView, createLayoutParams());

    }

    private WindowManager.LayoutParams createLayoutParams() {
        WindowManager.LayoutParams
                layoutParams =
                new WindowManager.LayoutParams(
                        WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.TYPE_PHONE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                        PixelFormat.TRANSLUCENT
                );

        layoutParams.gravity = Gravity.CENTER;
        return layoutParams;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).removeView(overlayView);
        Toast.makeText(this, "Service onDestroy()", Toast.LENGTH_SHORT).show();
        return;
    }
}
