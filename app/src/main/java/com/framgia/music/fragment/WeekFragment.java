package com.framgia.music.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.framgia.music.Constants;
import com.framgia.music.R;
import com.framgia.music.TemperatureChangedEvent;
import com.framgia.music.adapter.WeekWeatherAdapter;
import com.framgia.music.model.LocationWeather;
import com.framgia.music.model.Temperature;
import com.framgia.music.model.Weather;
import java.util.ArrayList;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by trong_tai on 23/01/2018.
 */

public class WeekFragment extends Fragment {

    private LocationWeather mLocationWeather;
    private RecyclerView mRecyclerViewWeekWeatherF, mRecyclerViewWeekWeatherC;
    private ArrayList<Weather> mDailyWeatherArrayListC = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        EventBus.getDefault().register(this);
        mLocationWeather = getBundleFromActivity();
        if (mLocationWeather == null) {
            return;
        }
        for (int i = 0; i < mLocationWeather.getDaily().getWeathers().size(); i++) {
            Weather tmpWeather = mLocationWeather.getDaily().getWeathers().get(i);
            mDailyWeatherArrayListC.add(new Weather(tmpWeather.getIcon(),
                    new Temperature("", String.valueOf(Math.round((Float.parseFloat(
                            tmpWeather.getTemperature().getTemperatureHight()) - 32) / 1.8)),
                            String.valueOf(Math.round((Float.parseFloat(
                                    tmpWeather.getTemperature().getTemperatureLow()) - 32) / 1.8))),
                    tmpWeather.getWindSpeed(), tmpWeather.getHumidity(), tmpWeather.getVisibility(),
                    tmpWeather.getUvIndex(), tmpWeather.getTimeWeather()));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_week_weather, container, false);
        initRecycyclerView(view);
        return view;
    }

    public LocationWeather getBundleFromActivity() {
        if (getArguments() == null) {
            return null;
        }
        return getArguments().getParcelable(Constants.LOCATION_WEATHER_FROM_ACTIVITY);
    }

    private void initRecycyclerView(View view) {
        mRecyclerViewWeekWeatherF = view.findViewById(R.id.recycler_WeatherWeek_F);
        mRecyclerViewWeekWeatherC = view.findViewById(R.id.recycler_WeatherWeek_C);

        mRecyclerViewWeekWeatherF.setHasFixedSize(true);
        mRecyclerViewWeekWeatherC.setHasFixedSize(true);

        LinearLayoutManager mLinearLayoutManagerF = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager mLinearLayoutManagerC = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);

        mRecyclerViewWeekWeatherF.setLayoutManager(mLinearLayoutManagerF);
        mRecyclerViewWeekWeatherC.setLayoutManager(mLinearLayoutManagerC);

        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
                        getActivity(), mLinearLayoutManagerF.getOrientation());

        mRecyclerViewWeekWeatherF.addItemDecoration(mDividerItemDecoration);
        mRecyclerViewWeekWeatherC.addItemDecoration(mDividerItemDecoration);

        WeekWeatherAdapter mWeekWeatherAdapterF = new WeekWeatherAdapter(
                        mLocationWeather.getDaily().getWeathers());
        WeekWeatherAdapter mWeekWeatherAdapterC = new WeekWeatherAdapter(mDailyWeatherArrayListC);

        mRecyclerViewWeekWeatherF.setAdapter(mWeekWeatherAdapterF);
        mRecyclerViewWeekWeatherC.setAdapter(mWeekWeatherAdapterC);
    }

    @Subscribe
    public void onEvent(TemperatureChangedEvent event) {
        if (event.getNewText() == Constants.C) {
            mRecyclerViewWeekWeatherC.setVisibility(View.VISIBLE);
            mRecyclerViewWeekWeatherF.setVisibility(View.GONE);
        } else {
            mRecyclerViewWeekWeatherF.setVisibility(View.VISIBLE);
            mRecyclerViewWeekWeatherC.setVisibility(View.GONE);
        }
    }
}
