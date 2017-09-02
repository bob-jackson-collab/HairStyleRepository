package com.hair.hairstyle.base;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * Created by yunshan on 17/8/31.
 */

public abstract class BaseDataBindingAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected List<T> datas;

    private OnItemClickListener mOnItemClickListener = null;


    public void setData(List<T> datas){
        this.datas = datas;
        notifyDataSetChanged();
    }


    public interface OnItemClickListener {

        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (datas == null || datas.size() <= 0)
            return 0;
        return datas.size();
    }

    public static class BaseViewHolder<VD extends ViewDataBinding> extends RecyclerView.ViewHolder {

        public  VD viewDataBinding;

        public BaseViewHolder(VD vd) {
            super(vd.getRoot());
            viewDataBinding = vd;
        }
    }

}
