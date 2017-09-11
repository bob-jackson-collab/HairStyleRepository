package com.hair.hairstyle.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hair.hairstyle.R;
import com.hair.hairstyle.adapter.HairTypePagerAdapter;
import com.hair.hairstyle.base.BaseFragment;
import com.hair.hairstyle.databinding.FragmentOrderBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yunshan on 17/8/8.
 */

public class OrderFragment extends BaseFragment implements TabLayout.OnTabSelectedListener {

    public static final String TAG = OrderFragment.class.getSimpleName();

    private FragmentOrderBinding mBinding;
    private String[] mHairType;
    private List<Fragment> mFragments;
    private HairTypePagerAdapter mAdapter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_order;
    }

    @Override
    protected void initViewsAndEvents(View view) {

        mBinding.tabLayout.setTabMode(TabLayout.MODE_FIXED);
//        mBinding.tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        mBinding.tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorWhite));
        mBinding.tabLayout.setSelectedTabIndicatorHeight(5);
        mBinding.tabLayout.setTabTextColors(getResources().getColor(R.color.colorSelected),getResources().getColor(R.color.colorWhite));

        mAdapter = new HairTypePagerAdapter(getChildFragmentManager(), mFragments, mHairType);
        mBinding.viewPager.setAdapter(mAdapter);
        mBinding.tabLayout.setupWithViewPager(mBinding.viewPager);
    }

    @Override
    protected View getFragmentView(LayoutInflater inflater, @Nullable ViewGroup container, int layoutId) {
        mBinding = DataBindingUtil.inflate(inflater, layoutId, container, false);
        return mBinding.getRoot();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e(TAG, "onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate");
        mHairType = new String[]{"全部", "女士", "男士", "儿童"};
        mFragments = new ArrayList<>();
        AllHairTypeFragment allHairTypeFragment = new AllHairTypeFragment();
        FemaleHairTypeFragment femaleHairTypeFragment = new FemaleHairTypeFragment();
        MaleHairTypeFragment maleHairTypeFragment = new MaleHairTypeFragment();
        ChildrenHairTypeFragment childrenHairTypeFragment = new ChildrenHairTypeFragment();
        mFragments.add(allHairTypeFragment);
        mFragments.add(femaleHairTypeFragment);
        mFragments.add(maleHairTypeFragment);
        mFragments.add(childrenHairTypeFragment);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, "onActivityCreated");
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mBinding.viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
