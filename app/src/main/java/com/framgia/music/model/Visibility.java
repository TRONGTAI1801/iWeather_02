package com.framgia.music.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by trong_tai on 25/01/2018.
 */

public class Visibility implements Parcelable {

    private String mVisibility;

    public Visibility(String visibility) {
        this.mVisibility = visibility;
    }

    public String getVisibility() {
        return mVisibility;
    }

    protected Visibility(Parcel in) {
        mVisibility = in.readString();
    }

    public static final Creator<Visibility> CREATOR = new Creator<Visibility>() {
        @Override
        public Visibility createFromParcel(Parcel in) {
            return new Visibility(in);
        }

        @Override
        public Visibility[] newArray(int size) {
            return new Visibility[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mVisibility);
    }
}
