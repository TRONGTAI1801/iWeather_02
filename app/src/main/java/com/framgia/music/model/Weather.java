package com.framgia.music.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by trong_tai on 25/01/2018.
 */

public class Weather implements Parcelable {

    private String mIcon;
    private TimeWeather mTimeWeather;
    private Temperature mTemperature;
    private WindSpeed mWindSpeed;
    private Humidity mHumidity;
    private Visibility mVisibility;
    private UvIndex mUvIndex;

    public Weather(String icon, Temperature temperature, WindSpeed windSpeed,
            Humidity humidity, Visibility visibility,
            UvIndex uvIndex, TimeWeather timeWeather) {
        mTemperature = temperature;
        mWindSpeed = windSpeed;
        mHumidity = humidity;
        mVisibility = visibility;
        mUvIndex = uvIndex;
        mIcon = icon;
        mTimeWeather = timeWeather;
    }

    protected Weather(Parcel in) {
        mIcon = in.readString();
        mTemperature = in.readParcelable(Temperature.class.getClassLoader());
        mHumidity = in.readParcelable(Humidity.class.getClassLoader());
        mVisibility = in.readParcelable(Visibility.class.getClassLoader());
        mUvIndex = in.readParcelable(UvIndex.class.getClassLoader());
        mTimeWeather = in.readParcelable(TimeWeather.class.getClassLoader());
        mWindSpeed = in.readParcelable(WindSpeed.class.getClassLoader());
    }

    public Temperature getTemperature() {
        return mTemperature;
    }

    public void setTemperature(Temperature temperature) {
        mTemperature = temperature;
    }

    public WindSpeed getWindSpeed() {
        return mWindSpeed;
    }

    public void setWindSpeed(WindSpeed windSpeed) {
        mWindSpeed = windSpeed;
    }

    public Humidity getHumidity() {
        return mHumidity;
    }

    public void setHumidity(Humidity humidity) {
        mHumidity = humidity;
    }

    public Visibility getVisibility() {
        return mVisibility;
    }

    public void setVisibility(Visibility visibility) {
        mVisibility = visibility;
    }

    public UvIndex getUVIndex() {
        return mUvIndex;
    }

    public void setUVIndex(UvIndex uvIndex) {
        mUvIndex = uvIndex;
    }

    public TimeWeather getTimeWeather() {
        return mTimeWeather;
    }

    public void setTimeWeather(TimeWeather timeWeather) {
        mTimeWeather = timeWeather;
    }

    public UvIndex getUvIndex() {
        return mUvIndex;
    }

    public void setUvIndex(UvIndex uvIndex) {
        mUvIndex = uvIndex;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public static final Creator<Weather> CREATOR = new Creator<Weather>() {
        @Override
        public Weather createFromParcel(Parcel in) {
            return new Weather(in);
        }

        @Override
        public Weather[] newArray(int size) {
            return new Weather[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mIcon);
        parcel.writeParcelable(mTemperature, i);
        parcel.writeParcelable(mHumidity, i);
        parcel.writeParcelable(mVisibility, i);
        parcel.writeParcelable(mUvIndex, i);
        parcel.writeParcelable(mTimeWeather, i);
        parcel.writeParcelable(mWindSpeed, i);
    }
}
