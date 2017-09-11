package com.hair.hairstyle.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by yunshan on 17/9/4.
 */

public class HairTypePagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;
    private String[] mHairTypes;

    public HairTypePagerAdapter(FragmentManager fm, List<Fragment> mFragments, String[] mHairTypes) {
        this(fm);
        this.mFragments = mFragments;
        this.mHairTypes = mHairTypes;
    }

    public HairTypePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position) == null ? null : mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments == null ? 0 : mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mHairTypes[position] == null ? null : mHairTypes[position];
    }
}
