package com.example.weather_app.ui.main;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.weather_app.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "SectionsPagerAdapter";
    protected String dark_string;
    protected String image_obj;
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};
    private final Context mContext;


    public SectionsPagerAdapter(Context context, FragmentManager fm, String obj, String img_data) {
        super(fm);
        dark_string = obj;
        image_obj = img_data;
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        Fragment fragment = null;
        Log.d(TAG, "getItem: "+position);
        switch(position){
            case 0:
                fragment = Today.newInstance(dark_string);
                break;
            case 1:
                fragment = Weekly.newInstance(dark_string);
                break;
            case 2:
                Log.d("imagebefore passing",image_obj);
                fragment = Photos.newInstance(image_obj);
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 3;
    }
}