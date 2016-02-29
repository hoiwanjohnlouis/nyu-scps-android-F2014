// IRemoteInterface.aidl
package com.thehackerati.android.apps.mysimpleservicegps;

import com.thehackerati.android.apps.mysimpleservicegps.GPXPoint;

interface IRemoteInterface {

    Location getLastLocation();
    GPXPoint getGPXPoint();
}
