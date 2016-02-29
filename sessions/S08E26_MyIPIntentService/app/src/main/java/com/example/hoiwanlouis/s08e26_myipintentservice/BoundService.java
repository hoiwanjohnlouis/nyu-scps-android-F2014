package com.example.hoiwanlouis.s08e26_myipintentservice;

import android.app.Service;
import android.os.Binder;
import android.os.IBinder;



/**
 * Created by hoiwanlouis on 10/25/14.
 */
public class BoundService extends Service {
    private IBinder localBinder = new LocalBinder();


}
