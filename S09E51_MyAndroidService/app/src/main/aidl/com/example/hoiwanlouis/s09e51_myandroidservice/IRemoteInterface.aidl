// IRemoteInterface.aidl
package com.example.hoiwanlouis.s09e51_myandroidservice;

// Declare any non-default types here with import statements
import  com.example.hoiwanlouis.s09e51_myandroidservice.GPXPoint;

interface IRemoteInterface {

    Location getLastLocation();
    GPXPoint getGPXPoint();

}
