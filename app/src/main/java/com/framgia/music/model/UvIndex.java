package com.framgia.music.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by trong_tai on 25/01/2018.
 */

public class UvIndex implements Parcelable {

    private String mUvIndex;

    public UvIndex(String uvIndex) {
        mUvIndex = uvIndex;
    }

    protected UvIndex(Parcel in) {
        mUvIndex = in.readString();
    }

    public String getUvIndex() {
        return mUvIndex;
    }

    public static final Creator<UvIndex> CREATOR = new Creator<UvIndex>() {
        @Override
        public UvIndex createFromParcel(Parcel in) {
            return new UvIndex(in);
        }

        @Override
        public UvIndex[] newArray(int size) {
            return new UvIndex[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mUvIndex);
    }
}
