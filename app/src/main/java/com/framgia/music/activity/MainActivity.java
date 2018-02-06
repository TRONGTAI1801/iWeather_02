package com.framgia.music.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.framgia.music.Constants;
import com.framgia.music.DBManager;
import com.framgia.music.FetchDataAsyncTask;
import com.framgia.music.OnFetchData;
import com.framgia.music.R;
import com.framgia.music.adapter.ViewPagesAdapter;
import com.framgia.music.model.HistoriesLocation;
import com.framgia.music.model.LocationWeather;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements
        OnFetchData, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public static Activity mActivity;

    private static final long UPDATE_INTERVAL = 5000;
    private static final long FASTEST_INTERVAL = 5000;
    private static final int REQUEST_LOCATION_PERMISSION = 100;
    private static final String CANOT_FETCH_DATA = "Can't fetch data!";
    private static final String API_DARKNIGHT_BASE = "https://api.darksky.net/forecast/cc7c192a17dbd02950549503fee54f81/";

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Location mLastLocation;

    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    private FragmentManager mFragmentManager = getSupportFragmentManager();
    private ViewPagesAdapter mViewPagerAdapter;

    public Bundle mBundle = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActivity = this;

        initViews();

        requestLocationPermissions();

        if (isPlayServicesAvailable()) {
            setUpLocationClientIfNeeded();
            buildLocationRequest();
        } else {
            Toast.makeText(this,
                    "Device does not support Google Play services",
                    Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
        super.onStop();
    }

    @Override
    public void onDestroy() {
        if (mGoogleApiClient != null
                && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
            mGoogleApiClient = null;
        }
        super.onDestroy();
    }

    @Override
    public void onFetchLocationWeatherSuccess(LocationWeather locationWeather) {
        HistoriesLocation historiesLocation = new HistoriesLocation(
                Float.parseFloat(locationWeather.getLatitude()),
                Float.parseFloat(locationWeather.getLongitude()),
                Long.parseLong(locationWeather.getCurrently().getWeather()
                        .getTimeWeather().getTimeWeather())
        );
        DBManager dbManager = new DBManager(getApplicationContext());
        dbManager.AddHistoriesLocation(historiesLocation);

        mBundle.putParcelable(Constants.LOCATION_WEATHER_FROM_ACTIVITY, locationWeather);
        mViewPagerAdapter = new ViewPagesAdapter(this, mFragmentManager, mBundle);
        mViewPager.setAdapter(mViewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setTabsFromPagerAdapter(mViewPagerAdapter);
    }

    @Override
    public void onFetchLocationWeatherError(Throwable throwable) {
        Toast.makeText(this, CANOT_FETCH_DATA, Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_exit:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // TODO something later
                } else {
                    requestLocationPermissions();
                }
                break;
            default:
                break;
        }
    }
    @Override
    public void onConnected(Bundle bundle) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (lastLocation == null) {
            return;
        }
        mLastLocation = lastLocation;
        FetchDataAsyncTask mFetchDataAsyncTask = new FetchDataAsyncTask(MainActivity.this);
        mFetchDataAsyncTask.execute(String.format(Locale.US, "%s%f,%f",
                API_DARKNIGHT_BASE, mLastLocation.getLatitude(), mLastLocation.getLongitude()));
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    private void initViews() {
        mViewPager = findViewById(R.id.view_pager);
        mTabLayout = findViewById(R.id.tab_layout);
    }

    private void requestLocationPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
    }

    private boolean isPlayServicesAvailable() {
        return GoogleApiAvailability.getInstance()
                .isGooglePlayServicesAvailable(this)
                == ConnectionResult.SUCCESS;
    }

    private void setUpLocationClientIfNeeded() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    private void buildLocationRequest() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
    }

//    private boolean checkHistoriesLocation(List<HistoriesLocation> historiesLocationList,
//            HistoriesLocation historiesLocation) {
//        if (historiesLocationList.size() == 0) {
//            return false;
//        }
//        for (int i = 0; i < historiesLocationList.size(); i++) {
//            if (historiesLocationList.get(i).getLatitide() == historiesLocation.getLatitide()
//                    && historiesLocationList.get(i).getLongitude() == historiesLocation.getLongitude()
//                    && historiesLocationList.get(i).getTime() == historiesLocation.getTime()) {
//                return true;
//            }
//        }
//        return false;
//    }

}
