package com.example.hoiwanlouis.s09e51_myandroidservice;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.Date;

/**
 * Created by hoiwanlouis on 11/1/14.
 */
public final class GPXPoint implements Parcelable {

    private static final String DEBUG_TAG = "GPXPoint";

    public int latitude;
    public int longitude;
    public Date timestamp;
    public double elevation;

    public static final Parcelable.Creator<GPXPoint> CREATOR = new Parcelable.Creator<GPXPoint>() {

        public GPXPoint createFromParcel(Parcel src) {
            return new GPXPoint(src);
        }

        public GPXPoint[] newArray(int size) {
            return new GPXPoint[size];
        }
    };

    public GPXPoint() {}

    private GPXPoint(Parcel src) {
        readFromParcel(src);
    }

    public void writeToParcel(Parcel dest, int flags) {
        Log.v(DEBUG_TAG, "writeToParcel() called");
        dest.writeInt(latitude);
        dest.writeInt(longitude);
        dest.writeDouble(elevation);
        dest.writeLong(timestamp.getTime());
    }

    public void readFromParcel(Parcel src) {
        Log.v(DEBUG_TAG, "readFromParcel() called");
        latitude = src.readInt();
        longitude = src.readInt();
        elevation = src.readDouble();
        timestamp = new Date(src.readLong());
    }

    public int describeContents() {
        Log.v(DEBUG_TAG, "describeContents() called");
        return 0;
    }
}
