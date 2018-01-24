package com.framgia.music.adapter;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.framgia.music.R;
import com.framgia.music.fragment.CurrentDayFragment;
import com.framgia.music.fragment.WeekFragment;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.framgia.music.adapter.ViewPagesAdapter.PagerTypeDef.PAGER_TODAY;
import static com.framgia.music.adapter.ViewPagesAdapter.PagerTypeDef.PAGER_WEEK;

/**
 * Created by trong_tai on 23/01/2018.
 */

public class ViewPagesAdapter extends FragmentStatePagerAdapter {

    private static final int sNUMPAGER = 2;
    private Context mContext;


    public ViewPagesAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(@PagerTypeDef int position) {
        switch (position){
            case PAGER_TODAY:
                return new CurrentDayFragment();
            case PAGER_WEEK:
                return new WeekFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return sNUMPAGER;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(@PagerTypeDef int position) {
        switch (position){
            case PAGER_TODAY:
                return mContext.getResources().getString(R.string.today);
            case PAGER_WEEK:
                return mContext.getResources().getString(R.string.week);
        }
        return null;
    }



    @Retention(RetentionPolicy.SOURCE)
    @IntDef ({PAGER_TODAY, PAGER_WEEK})
    public @interface PagerTypeDef {
        int PAGER_TODAY = 0;
        int PAGER_WEEK = 1;
    }

}
