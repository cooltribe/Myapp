package com.cyz.app.customview;

import android.content.Context;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.animation.Interpolator;
import com.android.common.components.log.Logger;
import com.cyz.app.utils.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * Created by n006498 on 2015/10/21.
 */
public class AutoScrollViewPager extends  MyViewPager {
    public static final String TAG = "AutoScrollViewPager";

    /**
     * 自动向左滚动
     */
    public static final int LEFT = 0;

    /**
     *  自动向右滚动
     */
    public static final int RIGHT = 1;

    /**
     * 滚动方向，默认使用{@link #RIGHT}
     */
    private int mDirection = RIGHT;

    /**
     * 是否循环滚动，默认为true
     */
    private boolean mIsCycle = true;
    /**
     * 循环滚动到尽头时，是否显示滚动动画
     */
    private boolean mIsBorderAnimation = true;
    public static final int SCROLL_WHAT = 0;

    /**
     * 默认滚动间隔
     */
    public static final int DEFAULT_INTERVAL = 1500;
    /**
     * 滚动间隔，默认使用{@link #DEFAULT_INTERVAL}
     */
    private long mInterval = DEFAULT_INTERVAL;
    /**
     * 滚动动画时间系数 默认为1
     */
    private  double mAutoScrollFator = 1.0;

    /**
     * 滚动动画事件系数 默认为1
     */
    private  double mSwipeScrollFator = 1.0;
    private MyHandler mHandler;
    private CustomDurationScroller mScroller;
    public AutoScrollViewPager(Context context) {
        super(context);
    }

    public AutoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init(){
        mHandler = new MyHandler();
        setViewPagerScroller();

    }
    private class MyHandler extends android.os.Handler{
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            if (msg.what == SCROLL_WHAT){
                mScroller.setScrollDurationFactor(mAutoScrollFator);
                scrollOnce();
                mScroller.setScrollDurationFactor(mSwipeScrollFator);
                sendScrollMessage(mInterval + mScroller.getDuration());
            }
        }
    }

    private void sendScrollMessage(long delayTimeInMills){
        mHandler.removeMessages(SCROLL_WHAT);
        mHandler.sendEmptyMessageDelayed(SCROLL_WHAT,delayTimeInMills);
    }
    /**
     * 滚动一次
     */
    private void scrollOnce(){
        PagerAdapter adapter = getAdapter();
        int currentItem = getCurrentItem();
        int totalCount;
        if (adapter == null || (totalCount = adapter.getCount()) <= 1){
            return;
        }
        int nextItem = mDirection == LEFT ? --currentItem : ++currentItem;
        if (nextItem < 0){
            if (mIsCycle) {
               setCurrentItem(nextItem -1,mIsBorderAnimation);
            }
        } else if (nextItem == totalCount){
            if (mIsCycle){
                setCurrentItem(0,mIsBorderAnimation);
            }
        } else {
            setCurrentItem(nextItem,true);
        }
    }

    /**
     * 获取自定义的Interpolator
     * @return 自定义的Interpolator
     */
    protected Interpolator getCustomInterpolator(){
        return null;
    }

    /**
     * 设置scroller
     */
    private void setViewPagerScroller(){
        try {
            Field scrollField = ViewPager.class.getDeclaredField("mScroller");
            ReflectionUtils.setAccessible(scrollField,true);
            Field interpolatorField = ViewPager.class.getDeclaredField("sInterpolator");
            ReflectionUtils.setAccessible(interpolatorField,true);
            Interpolator interpolator = getCustomInterpolator();
            if (interpolator == null) {
                interpolator = (Interpolator) interpolatorField.get(null);
            }
            mScroller = new CustomDurationScroller(getContext(),interpolator);
            scrollField.set(this,mScroller);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.error(TAG,TAG,e);
        }
    }
}
