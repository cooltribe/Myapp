package com.cyz.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by n006498 on 2015/10/20.
 */
public class CustomTitleView extends View {
    private  String titleText;
    private int titleTextColor;
    private int titleTextSize;
    private Rect mBound;
    private Paint mPaint;

    public CustomTitleView(Context context) {
        this(context,null);
    }

    public CustomTitleView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomTitleView,defStyleAttr,0);
        int n = a.getIndexCount();
        System.out.print("111111111:"+n);
        Log.i("111111111111111111", "" + n);
//        for (int i = 0; i < n ; i++) {
//            int attr = a.getIndex(i);
//            Log.i("aaaaaaa", "a:"+attr+",b:" + n+",c:" + i);
//            switch (attr){
//                case R.styleable.CustomTitleView_titletext:
//                    titleText = a.getString(attr);
//                    break;
//                case R.styleable.CustomTitleView_titletextcolor:
//                    titleTextColor = a.getColor(attr, Color.BLACK);
//
//                    int titleTextColor1 = a.getColor(R.styleable.CustomTitleView_titletextcolor,Color.BLACK);
//                   Log.i("bbbbbbb","color:" + titleTextColor + ",color1:" + titleTextColor1);
//                    break;
//                case R.styleable.CustomTitleView_titletextsize:
//                    titleTextSize = a.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
//                    break;
//            }
//        }
        titleText = a.getString(R.styleable.CustomTitleView_titletext);
        titleTextColor = a.getColor(R.styleable.CustomTitleView_titletextcolor,Color.BLACK);
        titleTextSize = a.getDimensionPixelSize(R.styleable.CustomTitleView_titletextsize, (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,16,getResources().getDisplayMetrics()));
        a.recycle();

        mPaint = new Paint();
        mPaint.setColor(titleTextColor);
        mPaint.setTextSize(titleTextSize);
        mBound = new Rect();
        mPaint.getTextBounds(titleText,0,titleText.length(),mBound);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                titleText = randomText();
                postInvalidate();
            }
        });
    }

    private String randomText(){
        Random random = new Random();
        Set<Integer> set = new HashSet<Integer>();
        while (set.size() < 4){
            int randomInt = random.nextInt(10);
            set.add(randomInt);
        }
        StringBuffer sb = new StringBuffer();
        for (Integer i : set) {
            sb.append("" + i);
        }
        return  sb.toString();

    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int  widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width = 0;
        int height = 0;
        if (widthMode == MeasureSpec.EXACTLY){
            width = widthSize;
        } else {
//            mPaint.setTextSize(titleTextSize);
//            mPaint.getTextBounds(titleText,0,titleText.length(),mBound);
            float textWidth = mBound.width();
            int desired = (int)(getPaddingLeft() + textWidth + getPaddingRight());
            width = desired;

        }
        if (heightMode == MeasureSpec.EXACTLY){
           height = heightSize;
        } else {
//            mPaint.setTextSize(titleTextSize);
//            mPaint.getTextBounds(titleText,0,titleText.length(),mBound);
            float textHeight = mBound.height();
            int desired = (int)(getPaddingTop() + textHeight + getPaddingBottom());
            height = desired;
        }
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(Color.YELLOW);
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint);
        mPaint.setColor(titleTextColor);
        canvas.drawText(titleText,getWidth() / 2 - mBound.width() / 2,getHeight() / 2 + mBound.height() / 2,mPaint);
    }
}
