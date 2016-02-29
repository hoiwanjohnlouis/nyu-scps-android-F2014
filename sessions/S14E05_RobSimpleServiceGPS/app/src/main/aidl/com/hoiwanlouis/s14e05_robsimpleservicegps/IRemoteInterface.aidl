// IRemoteInterface.aidl
package com.hoiwanlouis.s14e05_robsimpleservicegps;

// Declare any non-default types here with import statements
import com.hoiwanlouis.s14e05_robsimpleservicegps.GPXPoint;

interface IRemoteInterface {
    Location getLastLocation();
    GPXPoint getGPXPoint();
}
