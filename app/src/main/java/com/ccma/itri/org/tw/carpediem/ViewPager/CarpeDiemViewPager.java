package com.ccma.itri.org.tw.carpediem.ViewPager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by A40503 on 2016/10/25.
 */

public class CarpeDiemViewPager extends ViewPager {

    protected boolean _ifSwipeable = true;

    public CarpeDiemViewPager(Context context) {
        super(context);
    }

    public CarpeDiemViewPager(Context context, AttributeSet attrs){super(context, attrs);}

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent arg0) {
//        return (_ifSwipeable) ? super.onInterceptTouchEvent(arg0) : false;
//    }
}
