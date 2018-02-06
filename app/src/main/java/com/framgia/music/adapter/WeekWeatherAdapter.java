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
import java.util.Locale;

/**
 * Created by trong_tai on 26/01/2018.
 */

public class WeekWeatherAdapter extends RecyclerView.Adapter<WeekWeatherAdapter.MyViewHolder> {

    private static final String FORMAT_CHANGE_DATE = "EEEE";

    private LayoutInflater mLayoutInflater;
    private ArrayList<Weather> mHourlyWeatherArrayList;

    public WeekWeatherAdapter(ArrayList<Weather> hourlyWeatherArrayList) {
        mHourlyWeatherArrayList = hourlyWeatherArrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        View mView = mLayoutInflater.inflate(R.layout.item_row_weather_week, parent, false);
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

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView mTextViewHighTemperatureWeek,
                mTextViewDayOfWeek, mTextViewLowTemperatureWeek;
        private ImageView mImageViewIconWeatherWeek;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTextViewHighTemperatureWeek = itemView.findViewById(R.id.text_highTemperature_week);
            mTextViewLowTemperatureWeek = itemView.findViewById(R.id.text_lowTemperature_week);
            mTextViewDayOfWeek = itemView.findViewById(R.id.text_dayOfWeek);
            mImageViewIconWeatherWeek = itemView.findViewById(R.id.image_iconWeather_week);
        }

        public void bindHolder(Weather weather) {
            mTextViewHighTemperatureWeek.setText(String.format(Locale.US, "%d °",
                    Math.round(Float.parseFloat(
                            weather.getTemperature().getTemperatureHight()))));
            mTextViewLowTemperatureWeek.setText(String.format(Locale.US, "%d °",
                    Math.round(Float.parseFloat(
                            weather.getTemperature().getTemperatureLow()))));
            mTextViewDayOfWeek.setText(String.format(Locale.US, "%s",
                    chageTimestampToDatetime(Long.parseLong(
                            weather.getTimeWeather().getTimeWeather()))));
            mImageViewIconWeatherWeek.setImageResource(Constants.getIconWeather(weather));

        }

        public String chageTimestampToDatetime(long timestamp) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(FORMAT_CHANGE_DATE, Locale.US);
            return simpleDateFormat.format(new Date(timestamp * Constants.CONSTANT_DATE_CHANGE));
        }
    }
}
