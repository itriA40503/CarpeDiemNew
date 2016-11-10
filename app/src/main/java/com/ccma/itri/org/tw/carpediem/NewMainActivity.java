package com.ccma.itri.org.tw.carpediem;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ccma.itri.org.tw.carpediem.Adapter.ViewPagerAdpter;
import com.ccma.itri.org.tw.carpediem.EventObject.TimeEvent;
import com.ccma.itri.org.tw.carpediem.PagerFragment.FragmentPageTab1;
import com.ccma.itri.org.tw.carpediem.PagerFragment.FragmentPageTab2;

import com.ccma.itri.org.tw.carpediem.Scanner.ScanActivity;
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
//    private Controller controller;
    private Toolbar toolbar;
    private TextView toolbarTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_main);
//        CarpeDiemController.getInstance().Events.add(new TimeEvent("1","FisrtEvent", 5000, false));
//        CarpeDiemController.getInstance().Events.add(new TimeEvent("1","SecondEvent", 10000, false));
//        CarpeDiemController.getInstance().Events.add(new TimeEvent("1","ContinuousEvent", 10000, true));
//        CarpeDiemViewPager carpeDiemViewPager = (CarpeDiemViewPager)findViewById(R.id.main_pager);
//        adpter = new ViewPagerAdpter(getSupportFragmentManager());
//        carpeDiemViewPager.setAdapter(adpter);
        mFragments = new ArrayList<>();
        mFragments.add(new CardFragment());
        mFragments.add(new BackpackFragment());
        mFragments.add(new FragmentPageTab1());
        mFragments.add(new FragmentPageTab2());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // transaction.setCustomAnimations(R.anim.push_up_in,R.anim.push_up_out);
        transaction.add(R.id.contentContainer,mFragments.get(0));
        transaction.commit();
        BottomTabTest();
        settingToolbar();

    }
    private void showDialog(){

    }
    private void settingToolbar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTitle = (TextView)findViewById(R.id.toolbar_title);
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
                    showDialog();
                    Intent intent = new Intent(NewMainActivity.this, ScanActivity.class);
                    startActivity(intent);
                    break;
            }
            return false;
        }
    };

    private void BottomTabTest(){
        PagerBottomTabLayout pagerBottomTabLayout = (PagerBottomTabLayout) findViewById(R.id.bottomBar);

        //# create bottom tab - events
        TabItemBuilder tabEvents = new TabItemBuilder(this).create()
                .setDefaultIcon(R.drawable.menu_home_icon_pickup)
                .setText("Events")
                .setSelectedColor(getResources().getColor(R.color.bluelight))
                .setTag("Events")
                .build();
        //# create bottom tab - backpack
        TabItemBuilder tabBackpack = new TabItemBuilder(this).create()
                .setDefaultIcon(R.drawable.menu_backpack_icon_pickup)
                .setText("Backpack")
                .setSelectedColor(getResources().getColor(R.color.greenlight))
                .setTag("Backpack")
                .build();
        //# create bottom tab - news
        TabItemBuilder tabNews = new TabItemBuilder(this).create()
                .setDefaultIcon(R.drawable.menu_news_icon_pickup)
                .setText("News")
                .setSelectedColor(getResources().getColor(R.color.orangelight))
                .setTag("News")
                .build();
        //# create bottom tab - More
//        TabItemBuilder tabMore = new TabItemBuilder(this).create()
//                .setDefaultIcon(R.drawable.pika)
//                .setText("More")
//                .setSelectedColor(getResources().getColor(R.color.darkgrey))
//                .setTag("More")
//                .build();
        //# build bottom tabs and control
        CarpeDiemController.getInstance().controller = pagerBottomTabLayout.builder()
                .addTabItem(tabEvents)
                .addTabItem(tabBackpack)
                .addTabItem(tabNews)
//                .addTabItem(tabMore)
//                .setMode(TabLayoutMode.HIDE_TEXT)
//                .setMode(TabLayoutMode.CHANGE_BACKGROUND_COLOR)
                .setMode(TabLayoutMode.HIDE_TEXT| TabLayoutMode.CHANGE_BACKGROUND_COLOR)
                .build();

//        controller.setMessageNumber("News",99);
        CarpeDiemController.getInstance().controller.setMessageNumber("Events", CarpeDiemController.getInstance().getEventNum());
        CarpeDiemController.getInstance().controller.setMessageNumber("Backpack", CarpeDiemController.getInstance().getItemNum());
//        controller.setDisplayOval(0,true);

        CarpeDiemController.getInstance().controller.addTabItemClickListener(listener);
    }
    OnTabItemSelectListener listener = new OnTabItemSelectListener() {
        @Override
        public void onSelected(int index, Object tag){
            Log.i("Tab","onSelected:"+index+"   TAG: "+tag.toString());
            switch (index){
                case 0:
                    toolbar.setTitle("               (`へ´≠)");
                    CarpeDiemController.getInstance().controller.setMessageNumber("Events", 0);
                    toolbarTitle.setText(
                            "　　　＿＿＿_ ∧∧　　\n" +
                            "　～'　＿＿__(,,ﾟДﾟ)\n" +
                            "　　 ＵU 　 　Ｕ U　");
                    break;
                case 1:
                    toolbar.setTitle("(ﾒﾟДﾟ)ﾒ");
                    CarpeDiemController.getInstance().controller.setMessageNumber("Backpack", 0);
                    toolbarTitle.setText(
                            "　　　＿＿＿＿＿＿_ ∧∧　　\n" +
                            "　～'　＿＿＿＿＿__(,,ﾟДﾟ)\n" +
                            "　　 ＵU 　　　　 　Ｕ U　");
                    break;
                case 2:
                    toolbar.setTitle("(ﾒﾟДﾟ)ﾒ        (`へ´≠)");
                    CarpeDiemController.getInstance().controller.setMessageNumber("News",0);
                    toolbarTitle.setText(
                            "　　　＿＿＿＿＿＿＿＿＿＿＿_ ∧∧　　\n" +
                            "　～'　＿＿＿＿＿＿＿＿＿＿__(,,ﾟДﾟ)\n" +
                            "　　 ＵU 　　　　　　　　　 　Ｕ U　");
                    break;
                case 3:
                    toolbar.setTitle("(◓Д◒)✄╰⋃╯     ٩(ŏ﹏ŏ、)۶");
                    toolbarTitle.setText(
                            "　　　＿＿＿＿＿＿＿＿＿＿＿＿_ ∧∧　　\n" +
                            "　～'　＿＿＿＿＿＿＿＿＿＿＿__(,,ﾟДﾟ)\n" +
                            "　　 ＵU 　　　　　　　　　　 　Ｕ U　");
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
