package com.hair.hairstyle.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.hair.hairstyle.R;
import com.hair.hairstyle.base.BaseActivity;
import com.hair.hairstyle.base.BaseFragment;
import com.hair.hairstyle.base.BasePresenter;
import com.hair.hairstyle.databinding.ActivityMainBinding;
import com.hair.hairstyle.fragment.Fragment1;
import com.hair.hairstyle.fragment.Fragment2;
import com.hair.hairstyle.fragment.Fragment3;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yunshan on 17/8/8.
 */

public class MainActivity extends BaseActivity {

    private static final String STATE_FRAGMENT_NAME = "STATE_FRAGMENT_NAME";
    private BaseFragment mCurrentFragment;
    private FragmentManager mFragmentManager;
    private List<BaseFragment> mFragments;
    private int currentIndex = 0;
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mFragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) {
            restoreFragment(savedInstanceState);
        } else {
            initFragment();
        }


    }

    public void one(View view) {
        currentIndex = 0;
        showFragment();
    }

    public void two(View view) {
        currentIndex = 1;
        showFragment();
    }

    public void three(View view) {
        currentIndex = 2;
        showFragment();
    }

    private void initFragment() {
        mFragments = new ArrayList<>();
        Fragment1 fragment1 = new Fragment1();
        Fragment2 fragment2 = new Fragment2();
        Fragment3 fragment3 = new Fragment3();
        mFragments.add(fragment1);
        mFragments.add(fragment2);
        mFragments.add(fragment3);
        mCurrentFragment = fragment1;
        showFragment();
    }

    private void showFragment() {

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();

        if (!mFragments.get(currentIndex).isAdded()) {
            fragmentTransaction.hide(mCurrentFragment).add(R.id.frame_layout, mFragments.get(currentIndex), mFragments.get(currentIndex).getClass().getSimpleName());

        } else {
            fragmentTransaction.hide(mCurrentFragment).show(mFragments.get(currentIndex));
        }

        fragmentTransaction.commit();

        mCurrentFragment = mFragments.get(currentIndex);
    }

    private void restoreFragment(Bundle savedInstanceState) {

        currentIndex = savedInstanceState.getInt(STATE_FRAGMENT_NAME, 0);

        if (mFragments != null) {
            mFragments.removeAll(mFragments);
        } else {
            mFragments = new ArrayList<>();
        }

        mFragments.add(
                mFragmentManager.findFragmentByTag(Fragment1.class.getSimpleName()) != null ?
                        (Fragment1) mFragmentManager.findFragmentByTag(Fragment1.class.getSimpleName()) : new Fragment1());
        mFragments.add(
                mFragmentManager.findFragmentByTag(Fragment2.class.getSimpleName()) != null ?
                        (Fragment2) mFragmentManager.findFragmentByTag(Fragment2.class.getSimpleName()) : new Fragment2());
        mFragments.add(
                mFragmentManager.findFragmentByTag(Fragment3.class.getSimpleName()) != null ?
                        (Fragment3) mFragmentManager.findFragmentByTag(Fragment1.class.getSimpleName()) : new Fragment3());

        FragmentTransaction beginTransaction = mFragmentManager.beginTransaction();
        for (int i = 0; i < mFragments.size(); i++) {

            if (i == currentIndex) {
                beginTransaction.show(mFragments.get(currentIndex));
            } else {
                beginTransaction.hide(mFragments.get(i));
            }
        }

        beginTransaction.commit();
        mCurrentFragment = mFragments.get(currentIndex);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_FRAGMENT_NAME, currentIndex);
        super.onSaveInstanceState(outState);
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }
}
