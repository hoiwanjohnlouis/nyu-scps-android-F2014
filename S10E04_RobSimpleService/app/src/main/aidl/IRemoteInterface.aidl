package com.example.hoiwanlouis.s10e04_robsimpleservice;

//import com.advancedandroidbook.simpleservice.GPXPoint;
import com.example.hoiwanlouis.s10e04_robsimpleservice.GPXPoint;

interface IRemoteInterface {

    Location getLastLocation();
    GPXPoint getGPXPoint();
}
