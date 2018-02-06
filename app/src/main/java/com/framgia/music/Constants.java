package com.framgia.music;

import com.framgia.music.model.Weather;

/**
 * Created by trong_tai on 03/02/2018.
 */

public final class Constants {
    public static final String DATABASE_NAME = "Histories";
    public static final String TABLE_NAME = "Location_Histories";
    public static final String CREATE_TABLE = "CREATE TABLE";
    public static final String REAL = "REAL";
    public static final String LATITUDE = "LATITUDE";
    public static final String LONGITUDE = "LONGITUDE";
    public static final String TIME = "TIME";
    public static final String DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS";
    public static final String SELECT_FROM = "SELECT * FROM ";
    public static final String DELETE_FROM = "DELETE FROM ";
    public static final String CONDITION_UPDATE = "=?";
    public static final String AND = "and";
    public static final int DB_VERSION = 1;
    public static final long CONSTANT_DATE_CHANGE = 1000L;
    public static final String LOCATION_WEATHER_FROM_ACTIVITY = "locationWeatherFromActivity";
    public static final String CLEAR_DAY = "clear-day";
    public static final String CLEAR_NIGHT = "clear-night";
    public static final String RAIN = "rain";
    public static final String SNOW = "snow";
    public static final String SLEET = "sleet";
    public static final String FOG = "fog";
    public static final String CLOUDY = "cloudy";
    public static final String WIND = "wind";
    public static final String PART_CLOUDY_DAY = "partly-cloudy-day";
    public static final String PART_CLOUDY_NIGHT = "partly-cloudy-night";
    public static final String F = "°F";
    public static final String C = "°C";
    public static final int ZERO = 0;
    public static final int ONE = 1;
    public static final int TWO = 2;

    private Constants() {
    }

    public static int getIconWeather(Weather weather) {
        switch(weather.getIcon()) {
            case Constants.CLEAR_DAY:
                return R.drawable.clear_day;
            case Constants.CLEAR_NIGHT:
                return R.drawable.clear_night;
            case Constants.RAIN:
                return R.drawable.rain;
            case Constants.SNOW:
                return R.drawable.snow;
            case Constants.SLEET:
                return R.drawable.sleet;
            case Constants.WIND:
                return R.drawable.wind;
            case Constants.FOG:
                return R.drawable.fog;
            case Constants.CLOUDY:
                return R.drawable.cloudy;
            case Constants.PART_CLOUDY_DAY:
                return R.drawable.part_cloudy_day;
            case Constants.PART_CLOUDY_NIGHT:
                return R.drawable.part_cloudy_night;
        }
        return 0;
    }

}
