package com.ccma.itri.org.tw.carpediem;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ccma.itri.org.tw.carpediem.Adapter.ViewPagerAdpter;
import com.ccma.itri.org.tw.carpediem.EventObject.TimeEvent;
import com.ccma.itri.org.tw.carpediem.PagerFragment.FragmentPageTab1;
import com.ccma.itri.org.tw.carpediem.PagerFragment.FragmentPageTab2;
import com.ccma.itri.org.tw.carpediem.ViewPager.CarpeDiemViewPager;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import me.majiajie.pagerbottomtabstrip.Controller;
import me.majiajie.pagerbottomtabstrip.PagerBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.TabItemBuilder;
import me.majiajie.pagerbottomtabstrip.TabLayoutMode;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectListener;

/**
 * Created by A40503 on 2016/10/25.
 */

public class NewMainActivity extends AppCompatActivity {
    private ViewPagerAdpter adpter;
    private List<Fragment> mFragments;
    private Controller controller;
    private Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_main);
        CarpeDiemController.getInstance().Events.add(new TimeEvent("1","FisrtEvent", 5000, false));
        CarpeDiemController.getInstance().Events.add(new TimeEvent("1","SecondEvent", 10000, false));
        CarpeDiemController.getInstance().Events.add(new TimeEvent("1","ContinuousEvent", 10000, true));
//        CarpeDiemViewPager carpeDiemViewPager = (CarpeDiemViewPager)findViewById(R.id.main_pager);
//        adpter = new ViewPagerAdpter(getSupportFragmentManager());
//        carpeDiemViewPager.setAdapter(adpter);
        mFragments = new ArrayList<>();
        mFragments.add(new CardFragment());
        mFragments.add(new FragmentPageTab2());
        mFragments.add(new FragmentPageTab1());
        mFragments.add(new FragmentPageTab2());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // transaction.setCustomAnimations(R.anim.push_up_in,R.anim.push_up_out);
        transaction.add(R.id.contentContainer,mFragments.get(0));
        transaction.commit();
        BottomTabTest();
        settingToolbar();

    }

    private void settingToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);

//        toolbar.setLogo(R.drawable.carpediem_logo);
        toolbar.setTitle("(σﾟ∀ﾟ)σ CarpeDiem ヽ( ° ▽°)ノ");

        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(onMenuItemClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClickListener = new Toolbar.OnMenuItemClickListener(){
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.scan:
                    CarpeDiemController.getInstance().showToast("SCAN");
                    break;
            }
            return false;
        }
    };

    private void BottomTabTest(){
        PagerBottomTabLayout pagerBottomTabLayout = (PagerBottomTabLayout) findViewById(R.id.bottomBar);

        //用TabItemBuilder构建一个导航按钮
        TabItemBuilder tabItemBuilder = new TabItemBuilder(this).create()
                .setDefaultIcon(R.drawable.menu_home_icon_pickup)
                .setText("Events")
                .setSelectedColor(getResources().getColor(R.color.bluelight))
                .setTag("A")
                .build();

        //构建导航栏,得到Controller进行后续控制
        controller = pagerBottomTabLayout.builder()
                .addTabItem(tabItemBuilder)
                .addTabItem(R.drawable.menu_backpack_icon_pickup, "Backpack",getResources().getColor(R.color.greenlight))
                .addTabItem(R.drawable.carpediem_logo, "News",getResources().getColor(R.color.orangelight))
                .addTabItem(R.drawable.pika, "More",getResources().getColor(R.color.darkgrey))
//                .setMode(TabLayoutMode.HIDE_TEXT)
//                .setMode(TabLayoutMode.CHANGE_BACKGROUND_COLOR)
                .setMode(TabLayoutMode.HIDE_TEXT| TabLayoutMode.CHANGE_BACKGROUND_COLOR)
                .build();

//        controller.setMessageNumber("A",2);
//        controller.setDisplayOval(0,true);

        controller.addTabItemClickListener(listener);
    }
    OnTabItemSelectListener listener = new OnTabItemSelectListener() {
        @Override
        public void onSelected(int index, Object tag){
            Log.i("asd","onSelected:"+index+"   TAG: "+tag.toString());
            switch (index){
                case 0:
                    toolbar.setTitle("               (`へ´≠)");
                    break;
                case 1:
                    toolbar.setTitle("(ﾒﾟДﾟ)ﾒ");
                    break;
                case 2:
                    toolbar.setTitle("(ﾒﾟДﾟ)ﾒ        (`へ´≠)");
                    break;
                case 3:
                    toolbar.setTitle("(◓Д◒)✄╰⋃╯     ٩(ŏ﹏ŏ、)۶");
                    break;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            //transaction.setCustomAnimations(R.anim.push_up_in,R.anim.push_up_out);
            transaction.replace(R.id.contentContainer,mFragments.get(index));
            transaction.commit();
        }

        @Override
        public void onRepeatClick(int index, Object tag) {
            Log.i("asd","onRepeatClick:"+index+"   TAG: "+tag.toString());
        }
    };

    private void bottomBarOrg(){
        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (tabId){
                    case R.id.tab_events:
                        transaction.replace(R.id.contentContainer,mFragments.get(0));
                        transaction.commit();
                        break;
                    case R.id.tab_bagpack:
                        transaction.replace(R.id.contentContainer,mFragments.get(1));
                        transaction.commit();
                        break;
                    case R.id.tab_news:
                        transaction.replace(R.id.contentContainer,mFragments.get(2));
                        transaction.commit();
                        break;
                    case R.id.tab_more:
                        transaction.replace(R.id.contentContainer,mFragments.get(3));
                        transaction.commit();
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
