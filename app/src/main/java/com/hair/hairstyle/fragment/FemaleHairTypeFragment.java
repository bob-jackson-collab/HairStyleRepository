package com.hair.hairstyle.fragment;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hair.hairstyle.R;
import com.hair.hairstyle.base.BaseFragment;
import com.hair.hairstyle.databinding.FragmentFemalHairTypeBinding;

/**
 * Created by yunshan on 17/9/4.
 */

public class FemaleHairTypeFragment extends BaseFragment{

    private FragmentFemalHairTypeBinding mBinding;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_femal_hair_type;
    }

    @Override
    protected void initViewsAndEvents(View view) {

    }

    @Override
    protected View getFragmentView(LayoutInflater inflater, @Nullable ViewGroup container, int layoutId) {
        mBinding = DataBindingUtil.inflate(inflater,layoutId,container,false);
        return mBinding.getRoot();
    }
}
