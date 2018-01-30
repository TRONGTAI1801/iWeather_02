package com.framgia.music.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by trong_tai on 25/01/2018.
 */

public class Temperature implements Parcelable {

    private String mTemperature;
    private String mTemperatureHight;
    private String mTemperatureLow;

    public Temperature(String temperature, String temperatureHight, String temperatureLow) {

        mTemperature = temperature;
        mTemperatureHight = temperatureHight;
        mTemperatureLow = temperatureLow;
    }

    protected Temperature(Parcel in) {
        mTemperature = in.readString();
        mTemperatureHight = in.readString();
        mTemperatureLow = in.readString();
    }

    public String getTemperature() {
        return mTemperature;
    }

    public String getTemperatureHight() {
        return mTemperatureHight;
    }

    public String getTemperatureLow() {
        return mTemperatureLow;
    }

    public static final Creator<Temperature> CREATOR = new Creator<Temperature>() {
        @Override
        public Temperature createFromParcel(Parcel in) {
            return new Temperature(in);
        }

        @Override
        public Temperature[] newArray(int size) {
            return new Temperature[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTemperature);
        parcel.writeString(mTemperatureHight);
        parcel.writeString(mTemperatureLow);
    }
}
