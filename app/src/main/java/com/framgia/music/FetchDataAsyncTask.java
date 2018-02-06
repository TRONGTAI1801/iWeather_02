package com.framgia.music;

import android.os.AsyncTask;
import com.framgia.music.model.Currently;
import com.framgia.music.model.DailyAndHourly;
import com.framgia.music.model.Humidity;
import com.framgia.music.model.LocationWeather;
import com.framgia.music.model.Temperature;
import com.framgia.music.model.TimeWeather;
import com.framgia.music.model.UvIndex;
import com.framgia.music.model.Visibility;
import com.framgia.music.model.Weather;
import com.framgia.music.model.WindSpeed;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by trong_tai on 01/02/2018.
 */

public class FetchDataAsyncTask extends AsyncTask<String, LocationWeather,  LocationWeather> {

    private static final int REAL_TIMEOUT = 10000;
    private static final int CONNECT_TIMEOUT = 15000;
    private JSONObject mJsonObjectLocation = null;
    private LocationWeather mLocation = null;
    private OnFetchData mOnFetchData = null;
    private static final String TEMPERATUREHIGH = "temperatureMax";
    private static final String TEMPERATURELOW = "temperatureMin";
    private static final String TEMPERATURE = "temperature";
    private static final String HUMIDITY = "humidity";
    private static final String VISIBILITY = "visibility";
    private static final String WINSPEED = "windSpeed";
    private static final String UV_INDEX = "uvIndex";
    private static final String TIME = "time";
    private static final String DATA = "data";
    private static final String DAILY = "daily";
    private static final String HOURLY = "hourly";
    private static final String CURRENTLY = "currently";
    private static final String ICON = "icon";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String TIMEZONE = "timezone";
    private static final String GET = "GET";
    private static final String UTF = "UTF-8";

    public FetchDataAsyncTask(OnFetchData onFetchData) {
        mOnFetchData = onFetchData;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected LocationWeather doInBackground(String... strings) {
        return  parseJsonObjectToLocationObject(strings[0]);
    }

    @Override
    protected void onPostExecute(LocationWeather location) {
        super.onPostExecute(location);
        mOnFetchData.onFetchLocationWeatherSuccess(location);
    }

    @Override
    protected void onProgressUpdate(LocationWeather... values) {
        super.onProgressUpdate(values);
    }

    private String getJSONStringFromURL(String urlString) throws IOException, JSONException {
        HttpURLConnection urlConnection = null;
        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setRequestMethod(GET);
        urlConnection.setReadTimeout(REAL_TIMEOUT /* milliseconds */);
        urlConnection.setConnectTimeout(CONNECT_TIMEOUT /* milliseconds */);
        urlConnection.setDoOutput(true);

        urlConnection.connect();
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), UTF));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            line += "\n";
            sb.append(line);
        }
        br.close();

        String jsonString = sb.toString();

        urlConnection.disconnect();
        return jsonString;
    }

    private LocationWeather parseJsonObjectToLocationObject(String string) {
        try {
            String mJson = getJSONStringFromURL(string);
            mJsonObjectLocation = new JSONObject(mJson);
            JSONObject mJsonObjectCurrently = mJsonObjectLocation.getJSONObject(CURRENTLY);
            Currently mCurrently = new Currently(new Weather(mJsonObjectCurrently.getString(ICON),
                    new Temperature(mJsonObjectCurrently.getString(TEMPERATURE), "", ""),
                    new WindSpeed(mJsonObjectCurrently.getString(WINSPEED)),
                    new Humidity(mJsonObjectCurrently.getString(HUMIDITY)),
                    new Visibility(mJsonObjectCurrently.getString(VISIBILITY)),
                    new UvIndex(mJsonObjectCurrently.getString(UV_INDEX)),
                    new TimeWeather(mJsonObjectCurrently.getString(TIME))));
            JSONObject mJsonObjectDaily = mJsonObjectLocation.getJSONObject(DAILY);
            JSONArray mJsonArrayDaily = mJsonObjectDaily.getJSONArray(DATA);
            ArrayList<Weather> mWeatherDailyList = new ArrayList<>();
            for (int i = 0; i < mJsonArrayDaily.length(); i++) {
                Weather weatherDaily = new Weather(
                        mJsonArrayDaily.getJSONObject(i).getString(ICON),
                        new Temperature("",
                                mJsonArrayDaily.getJSONObject(i).getString(TEMPERATUREHIGH),
                                mJsonArrayDaily.getJSONObject(i).getString(TEMPERATURELOW)),
                        new WindSpeed(mJsonArrayDaily.getJSONObject(i).getString(WINSPEED)),
                        new Humidity(mJsonArrayDaily.getJSONObject(i).getString(HUMIDITY)),
                        new Visibility(""),
                        new UvIndex(mJsonArrayDaily.getJSONObject(i).getString(UV_INDEX)),
                        new TimeWeather(mJsonArrayDaily.getJSONObject(i).getString(TIME)));
                mWeatherDailyList.add(weatherDaily);
            }
            DailyAndHourly mDaily = new DailyAndHourly(mWeatherDailyList);
            JSONObject mJsonObjectHourly = mJsonObjectLocation.getJSONObject(HOURLY);
            JSONArray mJsonArrayHourly = mJsonObjectHourly.getJSONArray(DATA);
            ArrayList<Weather> mWeatherHourlyList = new ArrayList<>();

            for (int i = 0; i < mJsonArrayHourly.length(); i++) {
                Weather weatherHours = new Weather(
                        mJsonArrayHourly.getJSONObject(i).getString(ICON),
                        new Temperature(mJsonArrayHourly.getJSONObject(i).getString(TEMPERATURE),
                                "", ""),
                        new WindSpeed(mJsonArrayHourly.getJSONObject(i).getString(WINSPEED)),
                        new Humidity(mJsonArrayHourly.getJSONObject(i).getString(HUMIDITY)),
                        new Visibility(""),
                        new UvIndex(mJsonArrayHourly.getJSONObject(i).getString(UV_INDEX)),
                        new TimeWeather(mJsonArrayHourly.getJSONObject(i).getString(TIME)));
                mWeatherHourlyList.add(weatherHours);
            }
            DailyAndHourly mHourly = new DailyAndHourly(mWeatherHourlyList);
            mLocation = new LocationWeather(mJsonObjectLocation.getString(LATITUDE),
                    mJsonObjectLocation.getString(LONGITUDE),
                    mJsonObjectLocation.getString(TIMEZONE),
                    mCurrently, mDaily, mHourly);
        } catch (IOException e) {
            mOnFetchData.onFetchLocationWeatherError(e);
        } catch (JSONException e) {
            mOnFetchData.onFetchLocationWeatherError(e);
        }
        return mLocation;
    }
}
