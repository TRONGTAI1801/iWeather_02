package com.framgia.music.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by trong_tai on 25/01/2018.
 */

public class Currently implements Parcelable {
    private Weather mWeather;

    public Currently(Weather weather) {
        mWeather = weather;
    }

    protected Currently(Parcel in) {
        mWeather = in.readParcelable(Weather.class.getClassLoader());
    }

    public Weather getWeather() {
        return mWeather;
    }

    public static final Creator<Currently> CREATOR = new Creator<Currently>() {
        @Override
        public Currently createFromParcel(Parcel in) {
            return new Currently(in);
        }

        @Override
        public Currently[] newArray(int size) {
            return new Currently[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(mWeather, i);
    }
}
