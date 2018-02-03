package com.framgia.music.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.framgia.music.FetchDataAsyncTask;
import com.framgia.music.OnFetchData;
import com.framgia.music.R;
import com.framgia.music.adapter.ViewPagesAdapter;
import com.framgia.music.model.LocationWeather;

public class MainActivity extends AppCompatActivity implements OnFetchData {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        FetchDataAsyncTask mFetchDataAsyncTask = new FetchDataAsyncTask(MainActivity.this);
        mFetchDataAsyncTask.execute("https://api.darksky.net/forecast/cc7c192a17dbd02950549503fee54f81/37.4220,-122.0840"); // TODO update location dynamic later
    }

    @Override
    public void onFetchLocationWeatherSuccess(LocationWeather location) {
        // TODO edit later
    }

    @Override
    public void onFetchDataError(Throwable throwable) {
        // TODO edit later
    }

    private void initViews() {
        mViewPager = findViewById(R.id.view_pager);
        mTabLayout = findViewById(R.id.tab_layout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FragmentManager mFragmentManager = getSupportFragmentManager();
        ViewPagesAdapter mViewPagerAdapter = new ViewPagesAdapter(this, mFragmentManager);
        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setTabsFromPagerAdapter(mViewPagerAdapter);
    }
}
