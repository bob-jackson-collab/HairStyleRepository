package com.hair.hairstyle.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jdsjlzx.ItemDecoration.GridItemDecoration;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.hair.hairstyle.R;
import com.hair.hairstyle.adapter.HairTypeAdapter;
import com.hair.hairstyle.base.BaseEntity;
import com.hair.hairstyle.base.BaseFragment;
import com.hair.hairstyle.databinding.FragmentAllHairTypeBinding;
import com.hair.hairstyle.net.ServiceGenerator;
import com.hair.hairstyle.net.apiservice.IndexHairService;
import com.hair.hairstyle.net.param.HairTypeParam;
import com.hair.hairstyle.net.result.HairTypeResult;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yunshan on 17/9/4.
 */

public class AllHairTypeFragment extends BaseFragment implements OnLoadMoreListener, OnRefreshListener {

    private FragmentAllHairTypeBinding mBinding;
    private HairTypeAdapter mAdapter;
    private LRecyclerViewAdapter mRecyclerViewAdapter;
    private List<HairTypeResult.HairstyleListBean> mHairStyleList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHairStyleList = new ArrayList<>();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_all_hair_type;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        mBinding.lRecyclerView.setRefreshProgressStyle(ProgressStyle.BallClipRotate);
        mBinding.lRecyclerView.setOnRefreshListener(this);
        mBinding.lRecyclerView.setOnLoadMoreListener(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        mBinding.lRecyclerView.setLayoutManager(gridLayoutManager);
        //设置底部加载文字提示
        mBinding.lRecyclerView.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");
        mBinding.lRecyclerView.setFooterViewColor(R.color.colorIndicator, R.color.colorIndicator, R.color.colorWhite);
        mAdapter = new HairTypeAdapter();
        mRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
        mBinding.lRecyclerView.setAdapter(mRecyclerViewAdapter);
        //根据需要选择使用GridItemDecoration还是SpacesItemDecoration
        GridItemDecoration divider = new GridItemDecoration.Builder(mContext)
                .setHorizontal(3f)
                .setVertical(3f)
                .setColorResource(R.color.colorWhite)
                .build();
        mBinding.lRecyclerView.addItemDecoration(divider);

        mBinding.lRecyclerView.setHasFixedSize(true);
    }

    @Override
    protected View getFragmentView(LayoutInflater inflater, @Nullable ViewGroup container, int layoutId) {
        mBinding = DataBindingUtil.inflate(inflater, layoutId, container, false);
        return mBinding.getRoot();
    }

    @Override
    protected void onLazyLoad() {
        super.onLazyLoad();
        mBinding.lRecyclerView.forceToRefresh();
    }

    private void pullData() {
        ServiceGenerator.createService(IndexHairService.class, "http://192.168.63.11:8890/")
                .getHairTypeList(new HairTypeParam())
                .flatMap(new Function<BaseEntity<HairTypeResult>, ObservableSource<HairTypeResult>>() {
                    @Override
                    public ObservableSource<HairTypeResult> apply(@NonNull BaseEntity<HairTypeResult> hairTypeResultBaseEntity) throws Exception {
                        return Observable.just(hairTypeResultBaseEntity.getData());
                    }
                })
                .flatMap(new Function<HairTypeResult, ObservableSource<List<HairTypeResult.HairstyleListBean>>>() {

                    @Override
                    public ObservableSource<List<HairTypeResult.HairstyleListBean>> apply(@NonNull HairTypeResult hairTypeResult) throws Exception {
                        return Observable.just(hairTypeResult.getHairstyle_list());
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(hairstyleListBeen -> {
                    mBinding.lRecyclerView.refreshComplete(10);
                    mHairStyleList.clear();
                    if (hairstyleListBeen != null) {
                        mHairStyleList.addAll(hairstyleListBeen);
                        mAdapter.setData(mHairStyleList);
                        mRecyclerViewAdapter.notifyDataSetChanged();
                    }
                }, throwable -> {
                    mBinding.lRecyclerView.refreshComplete(10);
                });
    }

    @Override
    public void onLoadMore() {
        mBinding.lRecyclerView.setNoMore(true);
    }

    @Override
    public void onRefresh() {
        pullData();
    }
}
