package com.hoiwanlouis.s10e52_hoiboundserviceexample_2;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Random;

public class LocalService extends Service {
    private final String DEBUG_TAG = this.getClass().getSimpleName();
    // Binder given to requesting client.
    private final IBinder mBinder = new LocalBinder();
    // Random number generator
    private final Random mGenerator = new Random();

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        LocalService getService() {
            Log.d(DEBUG_TAG, "in getService()");
            // Return this instance of LocalService so clients can call public methods
            return LocalService.this;
        }
    }
    public LocalService() {
        Log.d(DEBUG_TAG, "in LocalService()");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(DEBUG_TAG, "in onBind()");
        // TODO: Return the communication channel to the service.
        // throw new UnsupportedOperationException("Not yet implemented");
        return mBinder;
    }

    /** method for clients */
    public int getRandomNumber() {
        Log.d(DEBUG_TAG, "in getRandomNumber()");
        return mGenerator.nextInt(100);
    }
}
