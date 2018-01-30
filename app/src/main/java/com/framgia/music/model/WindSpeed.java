package com.framgia.music.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by trong_tai on 25/01/2018.
 */

public class WindSpeed implements Parcelable{

    private String mSpeed;

    public String getSpeed() {
        return mSpeed;
    }

    public WindSpeed(String speed) {
        mSpeed = speed;
    }

    protected WindSpeed(Parcel in) {
        mSpeed = in.readString();
    }

    public static final Creator<WindSpeed> CREATOR = new Creator<WindSpeed>() {
        @Override
        public WindSpeed createFromParcel(Parcel in) {
            return new WindSpeed(in);
        }

        @Override
        public WindSpeed[] newArray(int size) {
            return new WindSpeed[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mSpeed);
    }
}
