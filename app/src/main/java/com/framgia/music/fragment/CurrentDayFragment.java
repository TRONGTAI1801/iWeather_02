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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import com.framgia.music.Constants;
import com.framgia.music.R;
import com.framgia.music.TemperatureChangedEvent;
import com.framgia.music.adapter.HoursWeatherAdapter;
import com.framgia.music.model.LocationWeather;
import com.framgia.music.model.Temperature;
import com.framgia.music.model.Weather;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by trong_tai on 23/01/2018.
 */

public class CurrentDayFragment extends Fragment {

    private LocationWeather mLocationWeather;
    private int mCurrentlyTemperature, mCurrentlyWindSpeed;

    private Spinner mSpinnerTemperature, mSpinnerWindSpeed;
    private TextView mTextViewCurrentlyTemperature, mTextViewLowTemperature,
            mTextViewHighTemperature, mTextViewWindSpeed, mTextViewHumidity, mTextViewUvIndex,
            mTextViewVisibility;
    private ImageView mImageViewIconWeather;
    private RecyclerView mRecyclerViewHoursWeatherF, mRecyclerViewHoursWeatherC;
    private HoursWeatherAdapter mHoursWeatherAdapterF, mHoursWeatherAdapterC;


    private static final String KMPH = "km/h";
    private static final String MPS = "m/s";
    private ArrayList<Weather> mHourlyWeatherArrayListC = new ArrayList<>();
    public CurrentDayFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mLocationWeather = getBundleFromActivity();
        if (mLocationWeather == null) {
            return;
        }
        for (int i = 0; i < mLocationWeather.getHourly().getWeathers().size(); i++) {
            Weather tmpWeather = mLocationWeather.getHourly().getWeathers().get(i);
            mHourlyWeatherArrayListC.add(new Weather(tmpWeather.getIcon(),
                    new Temperature(String.valueOf(Math.round((Float.parseFloat(
                            tmpWeather.getTemperature().getTemperature()) - 32) / 1.8)), "", ""),
                    tmpWeather.getWindSpeed(), tmpWeather.getHumidity(), tmpWeather.getVisibility(),
                    tmpWeather.getUvIndex(), tmpWeather.getTimeWeather()));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mCurrentlyTemperature = Math.round(Float.parseFloat(
                mLocationWeather.getCurrently().getWeather().getTemperature().getTemperature()));
        mCurrentlyWindSpeed = Math.round(Float.parseFloat(
                mLocationWeather.getCurrently().getWeather().getWindSpeed().getSpeed()));
        View view = inflater.inflate(R.layout.fragment_current_weather, container, false);
        initViews(view);
        return view;
    }

    public void initViews(View view) {
        initSpinnerTemperature(view);
        initSpinnerWindSpeed(view);
        initTextView(view);
        initRecyclerViewHourWeather(view);
        initIcon(view);
    }

    public LocationWeather getBundleFromActivity() {
        if (getArguments() == null) {
            return null;
        }
        return getArguments().getParcelable(Constants.LOCATION_WEATHER_FROM_ACTIVITY);
    }

    public void initSpinnerTemperature(View view) {
        mSpinnerTemperature = view.findViewById(R.id.spinner_Temperature);
        final ArrayList<String> mListTemperature = new ArrayList<>();
        mListTemperature.add(Constants.F);
        mListTemperature.add(Constants.C);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.support_simple_spinner_dropdown_item, mListTemperature);
        mSpinnerTemperature.setAdapter(arrayAdapter);
        mSpinnerTemperature.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                EventBus bus = EventBus.getDefault();
                if (mSpinnerTemperature.getSelectedItem().toString().equals(Constants.F)
                        && mLocationWeather != null) {
                    mTextViewCurrentlyTemperature.setText(
                            String.format(Locale.US, "%d", mCurrentlyTemperature));
                    mTextViewHighTemperature.setText(String.format(Locale.US, "%d", Math.round(
                            Float.parseFloat(mLocationWeather.getDaily().getWeathers()
                                    .get(0).getTemperature().getTemperatureHight()))));
                    mTextViewLowTemperature.setText(String.format(Locale.US, "%d", Math.round(
                            Float.parseFloat(mLocationWeather.getDaily().getWeathers()
                                    .get(0).getTemperature().getTemperatureLow()))));
                    mRecyclerViewHoursWeatherC.setVisibility(View.GONE);
                    mRecyclerViewHoursWeatherF.setVisibility(View.VISIBLE);
                    bus.post(new TemperatureChangedEvent(Constants.F));
                } else {
                    mTextViewCurrentlyTemperature.setText(String.format(Locale.US, "%d",
                            Math.round((mCurrentlyTemperature - 32) / 1.8)));
                    mTextViewHighTemperature.setText(String.format(Locale.US, "%d", Math.round(
                            (Float.parseFloat(mLocationWeather.getDaily().getWeathers()
                                    .get(0).getTemperature().getTemperatureHight()) - 32) / 1.8)));
                    mTextViewLowTemperature.setText(String.format(Locale.US, "%d", Math.round(
                            (Float.parseFloat(mLocationWeather.getDaily().getWeathers()
                                    .get(0).getTemperature().getTemperatureLow()) - 32) / 1.8)));
                    mRecyclerViewHoursWeatherF.setVisibility(View.GONE);
                    mRecyclerViewHoursWeatherC.setVisibility(View.VISIBLE);
                    bus.post(new TemperatureChangedEvent(Constants.C));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void initSpinnerWindSpeed(View view) {
        mSpinnerWindSpeed = view.findViewById(R.id.spinner_windSpeed);
        List<String> mListTemperature = new ArrayList<>();
        mListTemperature.add(KMPH);
        mListTemperature.add(MPS);

        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item,
                        mListTemperature);
        mSpinnerWindSpeed.setAdapter(arrayAdapter);
        mSpinnerWindSpeed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (mSpinnerWindSpeed.getSelectedItem().toString().equals(MPS)
                        && mLocationWeather != null) {
                    mTextViewWindSpeed.setText(
                            String.format(Locale.US, " %d", mCurrentlyWindSpeed));
                } else {
                    mTextViewWindSpeed.setText(
                            String.format(Locale.US, " %d", Math.round(mCurrentlyWindSpeed * 3.6)));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void initTextView(View view) {
        mTextViewCurrentlyTemperature = view.findViewById(R.id.text_CurrentTemperature);
        mTextViewLowTemperature = view.findViewById(R.id.text_LowTemperature);
        mTextViewHighTemperature = view.findViewById(R.id.text_HighTemperature);
        mTextViewHumidity = view.findViewById(R.id.text_humidity_current);
        mTextViewUvIndex = view.findViewById(R.id.text_UvIndex_current);
        mTextViewVisibility = view.findViewById(R.id.text_visibility_current);
        mTextViewWindSpeed = view.findViewById(R.id.text_windSpeed_currently);

        if (mLocationWeather != null) {
            mTextViewHumidity.setText(String.format(Locale.US, "%s %s",
                    mLocationWeather.getCurrently().getWeather()
                            .getHumidity().getHumidity(), "%"));
            mTextViewUvIndex.setText(String.format(Locale.US, "%s",
                    mLocationWeather.getCurrently().getWeather().getUvIndex().getUvIndex()));
            mTextViewVisibility.setText(String.format(Locale.US, "%s  km",
                    mLocationWeather.getCurrently().getWeather().getVisibility()
                            .getVisibility()));
        }
    }

    public void initIcon(View view) {
        mImageViewIconWeather = view.findViewById(R.id.image_iconWeather_currently);
        mImageViewIconWeather.setImageResource(Constants.getIconWeather(
                mLocationWeather.getCurrently().getWeather()));
    }

    private void initRecyclerViewHourWeather(View view) {
        mRecyclerViewHoursWeatherF = view.findViewById(R.id.recycler_WeatherHours_F);
        mRecyclerViewHoursWeatherC = view.findViewById(R.id.recycler_WeatherHours_C);

        mRecyclerViewHoursWeatherF.setHasFixedSize(true);
        mRecyclerViewHoursWeatherC.setHasFixedSize(true);

        LinearLayoutManager mLinearLayoutManagerF =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager mLinearLayoutManagerC =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        DividerItemDecoration mDividerItemDecoration =
                new DividerItemDecoration(getActivity(), mLinearLayoutManagerF.getOrientation());

        mRecyclerViewHoursWeatherF.setLayoutManager(mLinearLayoutManagerF);
        mRecyclerViewHoursWeatherC.setLayoutManager(mLinearLayoutManagerC);

        mRecyclerViewHoursWeatherF.addItemDecoration(mDividerItemDecoration);
        mRecyclerViewHoursWeatherC.addItemDecoration(mDividerItemDecoration);

        mHoursWeatherAdapterF = new HoursWeatherAdapter(
                mLocationWeather.getHourly().getWeathers());
        mHoursWeatherAdapterC = new HoursWeatherAdapter(
                mHourlyWeatherArrayListC);

        mRecyclerViewHoursWeatherF.setAdapter(mHoursWeatherAdapterF);
        mRecyclerViewHoursWeatherC.setAdapter(mHoursWeatherAdapterC);
    }
}
