package com.example.hoiwanlouis.s10e06_robuseservice.simpleservice;

import com.example.hoiwanlouis.s10e06_robuseservice.simpleservice.GPXPoint;

interface IRemoteInterface {
    Location getLastLocation();
    GPXPoint getGPXPoint();
}
