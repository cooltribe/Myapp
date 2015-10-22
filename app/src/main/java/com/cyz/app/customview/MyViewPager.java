package com.cyz.app.customview;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by n006498 on 2015/10/21.
 */
public class MyViewPager extends ViewPager {
    /**
     * 是否已获得最终size
     */
    private boolean hasGetSize = false;

    /**
     * 高度
     */
    private int height;
    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        hasGetSize = false;
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        hasGetSize = false;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
        hasGetSize = true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (hasGetSize && mHeight > height){
            setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
            return;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
