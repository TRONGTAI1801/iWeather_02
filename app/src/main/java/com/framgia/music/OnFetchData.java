package com.framgia.music;

import com.framgia.music.model.LocationWeather;

/**
 * Created by trong_tai on 01/02/2018.
 */

public interface OnFetchData {
    void onFetchLocationWeatherSuccess(LocationWeather location);
    void onFetchDataError(Throwable throwable);
}

