package com.ccma.itri.org.tw.carpediem.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.ccma.itri.org.tw.carpediem.CardFragment;
import com.ccma.itri.org.tw.carpediem.PagerFragment.FragmentPageTab1;

/**
 * Created by A40503 on 2016/10/25.
 */

public class ViewPagerAdpter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabtitles[] = new String[] { "Tab1", "Tab2", "Tab3" };
    Context context;

    public ViewPagerAdpter(FragmentManager fm){super(fm);}

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Fragment tab1 = new FragmentPageTab1();
                return tab1;

            case 1:
                Fragment tab2 = new FragmentPageTab1();
                return tab2;
            case 2:
                Fragment tab3 = new CardFragment();
                return tab3;

        }
        return null;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabtitles[position];
    }
}
