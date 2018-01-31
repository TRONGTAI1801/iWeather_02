package com.framgia.music.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by trong_tai on 30/01/2018.
 */

public class TimeWeather implements Parcelable{
    private String mTimeWeather;

    public TimeWeather(String timeWeather) {
        this.mTimeWeather = timeWeather;
    }

    protected TimeWeather(Parcel in) {
        mTimeWeather = in.readString();
    }

    public String getTimeWeather() {
        return mTimeWeather;
    }

    public static final Creator<TimeWeather> CREATOR = new Creator<TimeWeather>() {
        @Override
        public TimeWeather createFromParcel(Parcel in) {
            return new TimeWeather(in);
        }

        @Override
        public TimeWeather[] newArray(int size) {
            return new TimeWeather[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTimeWeather);
    }
}
