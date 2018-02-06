package com.framgia.music.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.framgia.music.Constants;
import com.framgia.music.R;
import com.framgia.music.model.Weather;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by trong_tai on 26/01/2018.
 */

public class HoursWeatherAdapter extends RecyclerView.Adapter<HoursWeatherAdapter.MyViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<Weather> mHourlyWeatherArrayList = new ArrayList<>();

    public HoursWeatherAdapter(ArrayList<Weather> hourlyWeatherArrayList) {
        mHourlyWeatherArrayList = hourlyWeatherArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        View mView = mLayoutInflater.inflate(R.layout.item_row_weather_hours, parent, false);
        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.bindHolder(mHourlyWeatherArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return mHourlyWeatherArrayList != null ? mHourlyWeatherArrayList.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextViewTemperatureHours, mTextViewHour;
        private ImageView mImageViewIconWeatherHours;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTextViewTemperatureHours = itemView.findViewById(R.id.text_temperature_hours);
            mTextViewHour = itemView.findViewById(R.id.text_hour);
            mImageViewIconWeatherHours = itemView.findViewById(R.id.image_iconWeather_hours);
        }

        public void bindHolder(Weather weather) {
            mTextViewTemperatureHours.setText(String.format(Locale.US, "%d°",
                    Math.round(Float.parseFloat(
                            weather.getTemperature().getTemperature()))));
            mTextViewHour.setText(String.format(Locale.US, "%s",
                    chageTimestampToDatetime(Long.parseLong(
                            weather.getTimeWeather().getTimeWeather()))));
            mImageViewIconWeatherHours.setImageResource(Constants.getIconWeather(weather));
        }

        public String chageTimestampToDatetime(long timestamp) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh a", Locale.US);
            return simpleDateFormat.format(new Date(timestamp * Constants.CONSTANT_DATE_CHANGE));
        }
    }
}
