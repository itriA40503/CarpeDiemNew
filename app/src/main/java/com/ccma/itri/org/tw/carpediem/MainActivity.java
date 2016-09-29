package com.ccma.itri.org.tw.carpediem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.ccma.itri.org.tw.carpediem.EventObject.TimeEvent;
import com.ccma.itri.org.tw.carpediem.UserData.UserData;

import java.util.ArrayList;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private TextView text;
    protected UserData mUserDataManager;
    android.app.FragmentManager fragmentMgr;
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
        pushNewFragment(new CardFragment());
        //# pushNewFragment After Get EventList
//        observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<CarpeDiemController>() {
//                    @Override
//                    public void onCompleted() {
//                        pushNewFragment(new CardFragment());
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onNext(CarpeDiemController carpeDiemController) {
////                        carpeDiemController.RxGetEventList(getUserDataManager().getUserToken());
//                    }
//
////                    @Override
////                    public void onNext(String s) {
////                        //# Get Event List
////                        CarpeDiemController.getInstance().RxGetEventList(getUserDataManager().getUserToken());
////                        Log.d("observable",s);
////
////                    }
//                });
//        Log.d("mainGet",UserData.getInstance().getUserUUID());
    }
    protected UserData getUserDataManager() {
        if(mUserDataManager == null) {
            mUserDataManager = UserData.getInstance();
        }
        return mUserDataManager;
    }

    private void pushNewFragment(android.app.Fragment fragment){
        Log.d("pushNewFragment","fragment");
        fragmentMgr = getFragmentManager();
        android.app.FragmentTransaction fragmentTrans = fragmentMgr.beginTransaction();
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
