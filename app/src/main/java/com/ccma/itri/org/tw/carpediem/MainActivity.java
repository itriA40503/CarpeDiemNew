package com.ccma.itri.org.tw.carpediem;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.ccma.itri.org.tw.carpediem.UserData.UserData;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.Subscriber;

public class MainActivity extends AppCompatActivity {
    private TextView text;
    protected UserData mUserDataManager;
    private FragmentManager fragmentMgr;
    private List<Fragment> mFragmentStack;
    private FloatingActionMenu menuFAB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView)findViewById(R.id.textTest);
        text.setText(getUserDataManager().getUserToken());


//        Log.d("getUUI",getUserDataManager().getUserUUID());
//        CarpeDiemController.getInstance().Events = new ArrayList<TimeEvent>();
//        CarpeDiemController.getInstance().Events.add(new TimeEvent("FisrtEvent", 5000, false));
//        CarpeDiemController.getInstance().Events.add(new TimeEvent("SecondEvent", 10000, false));
//        CarpeDiemController.getInstance().Events.add(new TimeEvent("ContinuousEvent", 10000, true));
//        CarpeDiemController.getInstance().Events.add(new TimeEvent("ContinuousEvent2", 5000, true));
//        getUserDataManager().clearUserData();
        mFragmentStack = new ArrayList<Fragment>();
        pushNewFragment(new CardFragment());
        initFab();
    }

    private void initFab(){
//        menuFAB = (FloatingActionMenu)findViewById(R.id.menu_green);
//        menuFAB.setOnClickListener(new View.OnClickListener() {
//            @Override public void onClick(View v) {
//                CarpeDiemController.getInstance().showToast("FAB");
////                ContextCompat.getColor(MainActivity.this, R.color.light_grey);
//                menuFAB.setClosedOnTouchOutside(true);
//                menuFAB.hideMenuButton(false);
//                menuFAB.toggle(true);
//            }
//        });

//        final FloatingActionButton programFab1 = new FloatingActionButton(MainActivity.this);
//        programFab1.setButtonSize(FloatingActionButton.SIZE_MINI);
//
//        menuFAB.addMenuButton(programFab1);
//        programFab1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                programFab1.setLabelColors(ContextCompat.getColor(MainActivity.this, R.color.grey),
//                        ContextCompat.getColor(MainActivity.this, R.color.light_grey),
//                        ContextCompat.getColor(MainActivity.this, R.color.white_transparent));
//                programFab1.setLabelTextColor(ContextCompat.getColor(MainActivity.this, R.color.black));
//            }
//        });
    }
    protected UserData getUserDataManager() {
        if(mUserDataManager == null) {
            mUserDataManager = UserData.getInstance();
        }
        return mUserDataManager;
    }

    private void pushNewFragment(Fragment fragment){
        Log.d("pushNewFragment","fragment");
        fragmentMgr = getSupportFragmentManager();
        FragmentTransaction fragmentTrans = fragmentMgr.beginTransaction();
        if(mFragmentStack.size() > 0) {
            Fragment currentFragment = mFragmentStack.get(mFragmentStack.size() - 1);
            fragmentTrans.remove(currentFragment);
            if(mFragmentStack.size() > 1){
                mFragmentStack.remove(mFragmentStack.size() -1);
            }

        }
        fragmentTrans.replace(R.id.fragment, fragment);
//        fragmentTrans.add(R.id.frameLayout, fragment, "Second");
//        fragmentTrans.replace(R.id.frameLayout, fragment);
        fragmentTrans.commit();
    }

    Observable observable = Observable.create(new Observable.OnSubscribe<CarpeDiemController>() {
        @Override
        public void call(Subscriber<? super CarpeDiemController> subscriber) {
            CarpeDiemController carpeDiemController = CarpeDiemController.getInstance();
            subscriber.onNext(carpeDiemController);
            subscriber.onCompleted();
        }

//        @Override
//        public void call(Subscriber<? super String> subscriber) {
//            subscriber.onNext("Get Event List");
//            //# Get Event List
//            CarpeDiemController.getInstance().RxGetEventList(getUserDataManager().getUserToken());
//            subscriber.onNext("Get Event List END");
//            subscriber.onCompleted();
//        }
    });
}
