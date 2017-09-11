package com.hair.hairstyle.adapter;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hair.hairstyle.R;
import com.hair.hairstyle.base.BaseDataBindingAdapter;
import com.hair.hairstyle.databinding.ItemCardBinding;

/**
 * Created by yunshan on 17/9/8.
 */

public class TestAdapter extends BaseDataBindingAdapter<String, BaseDataBindingAdapter.BaseViewHolder<ItemCardBinding>> {
    @Override
    public BaseViewHolder<ItemCardBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemCardBinding itemCardBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_card, parent, false);
        return new BaseViewHolder<>(itemCardBinding);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder<ItemCardBinding> holder, int position) {
        super.onBindViewHolder(holder, position);
        if ("1".equals(datas.get(position))) {
            holder.viewDataBinding.cardView.setCardBackgroundColor(Color.BLUE);
        } else if ("2".equals(datas.get(position))) {
            holder.viewDataBinding.cardView.setCardBackgroundColor(Color.RED);
        } else if ("3".equals(datas.get(position))) {
            holder.viewDataBinding.cardView.setCardBackgroundColor(Color.GREEN);
        } else if ("4".equals(datas.get(position))) {
            holder.viewDataBinding.cardView.setCardBackgroundColor(Color.YELLOW);
        }
    }
}
