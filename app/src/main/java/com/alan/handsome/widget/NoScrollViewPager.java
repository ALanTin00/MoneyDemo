package com.alan.handsome.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/***
 * 功能描述:可动态的控制 是否滑动
 * 作者:qiujialiu
 * 时间:2016/12/16
 * 版本:
 ***/
public class NoScrollViewPager extends ViewPager {

    private boolean isCanScroll = true;//true 为滑动

    private boolean CanScroll = true;

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCanScroll(boolean isCanScroll){
        this.isCanScroll = isCanScroll;
    }

//    public void setNoCanScroll(boolean CanScroll){
//        this.CanScroll=CanScroll;
//    }

    @Override
    public void scrollTo(int x, int y) {
        if(CanScroll){
            super.scrollTo(x, y);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        return isCanScroll && super.onTouchEvent(arg0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return isCanScroll && super.onInterceptTouchEvent(arg0);
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
    }

}
