package com.hair.hairstyle.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hair.hairstyle.R;
import com.hair.hairstyle.base.BaseDataBindingAdapter;
import com.hair.hairstyle.databinding.ItemHairStoreBinding;
import com.hair.hairstyle.net.result.HairStoreResult;

/**
 * Created by yunshan on 17/8/31.
 */

public class HairStoreAdapter extends BaseDataBindingAdapter<HairStoreResult.StoreListBean, BaseDataBindingAdapter.BaseViewHolder<ItemHairStoreBinding>> {


    @Override
    public BaseViewHolder<ItemHairStoreBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemHairStoreBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_hair_store, parent, false);
        BaseViewHolder holder = new BaseViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<ItemHairStoreBinding> holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.viewDataBinding.setBean(datas.get(position));
        holder.viewDataBinding.executePendingBindings();
    }
}
