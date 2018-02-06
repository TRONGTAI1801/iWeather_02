package com.framgia.music.model;

/**
 * Created by trong_tai on 08/02/2018.
 */

public class HistoriesLocation {
    private double mLatitide;
    private double mLongitude;
    private long mTime;

    public HistoriesLocation() {
        mLatitide = 0;
        mLongitude = 0;
        mTime = 0;
    }

    public HistoriesLocation(double latitide, double longitude, long
            time) {
        mLatitide = latitide;
        mLongitude = longitude;
        mTime = time;
    }

    public double getLatitide() {
        return mLatitide;
    }

    public void setLatitide(double latitide) {
        mLatitide = latitide;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }
}
