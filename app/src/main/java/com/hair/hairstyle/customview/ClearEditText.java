package com.hair.hairstyle.customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.hair.hairstyle.R;

/**
 * Created by yunshan on 17/9/7.
 */

public class ClearEditText extends AppCompatEditText {

    private Drawable searchDrawable, clearDrawable;

    public ClearEditText(Context context) {
        super(context);
        init();
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        searchDrawable = getResources().getDrawable(R.mipmap.search);
        clearDrawable = getResources().getDrawable(R.mipmap.clear);

        setCompoundDrawablesWithIntrinsicBounds(searchDrawable, null, null, null);
    }


    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        setClearIconVisible(hasFocus() && text.length() > 0);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);

        setClearIconVisible(focused && length() > 0);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setClearIconVisible(boolean isVisible) {
        setCompoundDrawablesWithIntrinsicBounds(searchDrawable, null, isVisible ? clearDrawable : null, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (event.getX() <= getWidth() - getPaddingRight() &&
                        event.getX() >= getWidth() - getPaddingRight() - clearDrawable.getBounds().width()) {
                    setText("");
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
