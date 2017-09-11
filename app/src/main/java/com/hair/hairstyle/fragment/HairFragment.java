package com.hair.hairstyle.fragment;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.hair.hairstyle.R;
import com.hair.hairstyle.adapter.HairStoreAdapter;
import com.hair.hairstyle.base.BaseFragment;
import com.hair.hairstyle.customview.EasyPopup;
import com.hair.hairstyle.databinding.FragmentHairBinding;
import com.hair.hairstyle.databinding.ItemImageBinding;
import com.hair.hairstyle.net.ServiceGenerator;
import com.hair.hairstyle.net.apiservice.IndexHairService;
import com.hair.hairstyle.net.param.HairIndexParam;
import com.hair.hairstyle.net.result.HairStoreResult;
import com.hair.hairstyle.rx.ResultFun;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZViewHolder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yunshan on 17/8/31.
 */

public class HairFragment extends BaseFragment implements OnRefreshListener, OnLoadMoreListener {

    private static final String TAG = "HairFragment";

    public static final int PAGE_NUM = 3;
    private FragmentHairBinding mBinding;
    private View mHeaderView;
    private MZBannerView mBannerView;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private HairStoreAdapter mAdapter;
    private List<String> mBanners;
    private List<HairStoreResult.StoreListBean> mItems;
    private List<HairStoreResult.CityBean> mListCites;

    private EasyPopup mPopup;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBanners = new ArrayList<>();
        mItems = new ArrayList<>();
        mListCites = new ArrayList<>();
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_hair;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        settingHeaderView();
        mBinding.tvCity.setOnClickListener(v -> mPopup.showAsDropDown(v, -5, 0));
        mAdapter = new HairStoreAdapter();
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
        mLRecyclerViewAdapter.addHeaderView(mHeaderView);
        mBinding.recyclerView.setAdapter(mLRecyclerViewAdapter);
        DividerDecoration divider = new DividerDecoration.Builder(mContext)
                .setHeight(10.0f)
                .setColor(Color.WHITE)
                .build();
        //设置LRecyclerView分割线
        mBinding.recyclerView.addItemDecoration(divider);
        mBinding.recyclerView.setPullRefreshEnabled(true);
        mBinding.recyclerView.setLoadMoreEnabled(true);
        mBinding.recyclerView.setOnRefreshListener(this);
        mBinding.recyclerView.setOnLoadMoreListener(this);
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        //设置底部加载文字提示
        mBinding.recyclerView.setFooterViewHint("拼命加载中", "已经全部为你呈现了", "网络不给力啊，点击再试一次吧");

        mPopup = new EasyPopup(mContext).setContentView(R.layout.popup_city, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setFocusAndOutsideEnable(true).createPopup();

    }

    @Override
    protected View getFragmentView(LayoutInflater inflater, @Nullable ViewGroup container, int layoutId) {
        mBinding = DataBindingUtil.inflate(inflater, layoutId, container, false);
        return mBinding.getRoot();
    }

    private void settingHeaderView() {
        mHeaderView = LayoutInflater.from(mContext).inflate(R.layout.index_header, null);
        mBannerView = (MZBannerView) mHeaderView.findViewById(R.id.banner);
    }

    @Override
    protected void onLazyLoad() {
        super.onLazyLoad();
        mBinding.recyclerView.refresh();
    }

    private void getHairIndexData() {
        ServiceGenerator.createService(IndexHairService.class, "http://192.168.63.11:8890/")
                .getStoreList(new HairIndexParam())
                .flatMap(new ResultFun<HairStoreResult>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(hairStoreResult -> {
                    mBinding.recyclerView.refreshComplete(PAGE_NUM);

                    if (hairStoreResult != null) {
                        mBinding.tvCity.setText(hairStoreResult.getSelect_city());

                        mBanners.clear();
                        mBanners.addAll(hairStoreResult.getBanner_urls());

                        mItems.clear();
                        mItems.addAll(hairStoreResult.getStore_list());

                        mListCites.clear();
                        mListCites.addAll(hairStoreResult.getCity());

                        dataAttachView();
                    }
                }, throwable -> {
                    Log.e(TAG, "getHairIndexData: " + throwable.getMessage());
                    mBinding.recyclerView.refreshComplete(PAGE_NUM);
                });
    }

    private void dataAttachView() {

        View city_view = mPopup.getContentView();
        LinearLayout cityLinear = (LinearLayout) city_view.findViewById(R.id.popup_linear);
        cityLinear.removeAllViews();

        for (HairStoreResult.CityBean mListCite : mListCites) {
            TextView textView = new TextView(mContext);
            textView.setText(mListCite.getF_name());
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(15);
            textView.setTextColor(Color.BLUE);
            textView.setOnClickListener(v -> {
                        mBinding.tvCity.setText(mListCite.getF_name());
                        mPopup.dismiss();
                    }
            );
            textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            textView.setPadding(10, 10, 10, 10);
            cityLinear.addView(textView);
        }

        mBannerView.setPages(mBanners, () -> new BannerViewHolder());
        mBannerView.start();
        mAdapter.setData(mItems);
        mLRecyclerViewAdapter.notifyDataSetChanged();

    }

    @Override
    public void onPause() {
        super.onPause();
        mBannerView.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onRefresh() {
        getHairIndexData();
    }

    @Override
    public void onLoadMore() {
        mBinding.recyclerView.setNoMore(false);
    }


    public static class BannerViewHolder implements MZViewHolder<String> {

        private ItemImageBinding itemImageBinding;

        @Override
        public View createView(Context context) {
            itemImageBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_image, null, false);
            return itemImageBinding.getRoot();
        }

        @Override
        public void onBind(Context context, int i, String s) {
            Glide.with(context).load(s).into(itemImageBinding.bannerImage);
        }
    }

}
