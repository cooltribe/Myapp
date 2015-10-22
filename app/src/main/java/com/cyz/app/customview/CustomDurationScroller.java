package com.cyz.app.customview;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by n006498 on 2015/10/21.
 */
public class CustomDurationScroller extends Scroller {
    private double mScrollFactor = 1;
    public CustomDurationScroller(Context context) {
        super(context);
    }


    /**
     *
     * @param context 上下文
     * @param interpolator 插值器
     */
    public CustomDurationScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    /**
     * 【设置滚动事件系数】
     * @param scrollFactor 时间系数
     */
    public void setScrollDurationFactor(double scrollFactor){
        mScrollFactor = scrollFactor;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, (int)(duration * mScrollFactor));
    }
}
