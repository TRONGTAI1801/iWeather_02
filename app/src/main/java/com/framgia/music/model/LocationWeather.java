package com.framgia.music.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by trong_tai on 25/01/2018.
 */

public class LocationWeather implements Parcelable {
    private String mLongitude;
    private String mLatitude;
    private String mTimezone;
    private Currently mCurrently;
    private DailyAndHourly mHourly;
    private DailyAndHourly mDaily;

    public LocationWeather(String latitude, String longitude, String timezone,
            Currently currently, DailyAndHourly daily, DailyAndHourly hourly) {
        mLongitude = longitude;
        mLatitude = latitude;
        mTimezone = timezone;
        mCurrently = currently;
        mHourly = hourly;
        mDaily = daily;
    }


    public LocationWeather(LocationWeather locationWeather) {
        mLongitude = locationWeather.mLongitude;
        mLatitude = locationWeather.mLatitude;
        mTimezone = locationWeather.mTimezone;
        mCurrently = locationWeather.mCurrently;
        mHourly = locationWeather.mHourly;
        mDaily = locationWeather.mDaily;
    }

    public String getLongitude() {
        return mLongitude;
    }

    public void setLongitude(String longitude) {
        mLongitude = longitude;
    }

    public String getLatitude() {
        return mLatitude;
    }

    public void setLatitude(String latitude) {
        mLatitude = latitude;
    }

    public String getTimezone() {
        return mTimezone;
    }

    public void setTimezone(String country) {
        mTimezone = country;
    }

    public Currently getCurrently() {
        return mCurrently;
    }

    public void setCurrently(Currently currently) {
        mCurrently = currently;
    }

    public DailyAndHourly getHourly() {
        return mHourly;
    }

    public void setHourly(DailyAndHourly hourly) {
        mHourly = hourly;
    }

    public DailyAndHourly getDaily() {
        return mDaily;
    }

    public void setDaily(DailyAndHourly daily) {
        mDaily = daily;
    }

    protected LocationWeather(Parcel in) {
        mLongitude = in.readString();
        mLatitude = in.readString();
        mTimezone = in.readString();
        mCurrently = in.readParcelable(Currently.class.getClassLoader());
        mHourly = in.readParcelable(DailyAndHourly.class.getClassLoader());
        mDaily = in.readParcelable(DailyAndHourly.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mLongitude);
        parcel.writeString(mLatitude);
        parcel.writeString(mTimezone);
        parcel.writeParcelable(mCurrently, i);
        parcel.writeParcelable(mHourly, i);
        parcel.writeParcelable(mDaily, i);
    }

    public static final Creator<LocationWeather> CREATOR = new Creator<LocationWeather>() {
        @Override
        public LocationWeather createFromParcel(Parcel in) {
            return new LocationWeather(in);
        }

        @Override
        public LocationWeather[] newArray(int size) {
            return new LocationWeather[size];
        }
    };
}
