package com.framgia.music.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by trong_tai on 25/01/2018.
 */

public class Humidity implements Parcelable {

    private String mHumidity;

    public Humidity(String humidity) {
        mHumidity = humidity;
    }

    protected Humidity(Parcel in) {
        mHumidity = in.readString();
    }

    public String getHumidity() {
        return mHumidity;
    }

    public static final Creator<Humidity> CREATOR = new Creator<Humidity>() {
        @Override
        public Humidity createFromParcel(Parcel in) {
            return new Humidity(in);
        }

        @Override
        public Humidity[] newArray(int size) {
            return new Humidity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mHumidity);
    }
}
