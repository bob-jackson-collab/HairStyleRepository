package com.hair.hairstyle.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class Search_ListView extends ListView {
    public Search_ListView(Context context) {
        super(context);
    }

    public Search_ListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Search_ListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    // 通过复写其onMeasure方法，达到对ScrollView适配的效果
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
