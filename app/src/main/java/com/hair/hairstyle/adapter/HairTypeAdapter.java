package com.hair.hairstyle.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hair.hairstyle.R;
import com.hair.hairstyle.base.BaseDataBindingAdapter;
import com.hair.hairstyle.databinding.ItemHairTypeBinding;
import com.hair.hairstyle.net.result.HairTypeResult;


/**
 * Created by yunshan on 17/9/4.
 */

public class HairTypeAdapter extends BaseDataBindingAdapter<HairTypeResult.HairstyleListBean, BaseDataBindingAdapter.BaseViewHolder<ItemHairTypeBinding>> {

    @Override
    public BaseViewHolder<ItemHairTypeBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemHairTypeBinding itemHairTypeBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_hair_type, parent, false);
        BaseViewHolder baseViewHolder = new BaseViewHolder(itemHairTypeBinding);
        return baseViewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<ItemHairTypeBinding> holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.viewDataBinding.setHairTypeBean(datas.get(position));
        holder.viewDataBinding.executePendingBindings();
    }
}
