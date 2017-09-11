package com.hair.hairstyle.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.hair.hairstyle.R;
import com.hair.hairstyle.adapter.TestAdapter;
import com.hair.hairstyle.base.BaseFragment;
import com.hair.hairstyle.databinding.FragmentPersonBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yunshan on 17/8/8.
 */

public class PersonalFragment extends BaseFragment {

    public static final String TAG = PersonalFragment.class.getSimpleName();
    private FragmentPersonBinding mBinding;
    private List<String> mData;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_person;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        PagerSnapHelper pageSnapHelper = new PagerSnapHelper();
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        TestAdapter mAdapter = new TestAdapter();
        mBinding.recyclerView.setLayoutManager(manager);
        mBinding.recyclerView.setAdapter(mAdapter);
        pageSnapHelper.attachToRecyclerView(mBinding.recyclerView);
        mAdapter.setData(mData);
    }

    @Override
    protected View getFragmentView(LayoutInflater inflater, @Nullable ViewGroup container, int layoutId) {
        mBinding = DataBindingUtil.inflate(inflater, layoutId, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mBinding.etSearch.setFocusable(true);
        mBinding.etSearch.setFocusableInTouchMode(true);
        mBinding.etSearch.requestFocus();

        InputMethodManager manager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.showSoftInput(mBinding.etSearch, 0);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mBinding.etSearch.setFocusable(true);
            mBinding.etSearch.setFocusableInTouchMode(true);
            mBinding.etSearch.requestFocus();

            InputMethodManager manager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

            manager.showSoftInput(mBinding.etSearch, 0);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onAttach");
        mData = new ArrayList<>();
        mData.add("1");
        mData.add("2");
        mData.add("3");
        mData.add("4");
    }


}
