package com.framgia.music.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

/**
 * Created by trong_tai on 25/01/2018.
 */

public class DailyAndHourly implements Parcelable {
    private ArrayList<Weather> mWeathers;

    public DailyAndHourly(ArrayList<Weather> weathers) {
        mWeathers = weathers;
    }

    protected DailyAndHourly(Parcel in) {
        mWeathers = in.createTypedArrayList(Weather.CREATOR);
    }

    public static final Creator<DailyAndHourly> CREATOR = new Creator<DailyAndHourly>() {
        @Override
        public DailyAndHourly createFromParcel(Parcel in) {
            return new DailyAndHourly(in);
        }

        @Override
        public DailyAndHourly[] newArray(int size) {
            return new DailyAndHourly[size];
        }
    };

    public ArrayList<Weather> getWeathers() {
        return mWeathers;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(mWeathers);
    }
}
