package com.ccma.itri.org.tw.carpediem.EventObject;

import android.app.Notification;
import android.util.Log;

import com.ccma.itri.org.tw.carpediem.CarpeDiemController;
import com.ccma.itri.org.tw.carpediem.MainActivity;
import com.ccma.itri.org.tw.carpediem.R;
import com.ccma.itri.org.tw.carpediem.Timer.CountDownTimerWithPause;

import br.com.goncalves.pugnotification.notification.PugNotification;

/**
 * Created by A40503 on 2016/9/21.
 */
public class TimeEvent {
    private static final String TAG = "TimeEvent";
    public static final int START=0, RUNNING=1, END=2;
    public int mStatus = START;
    public String mEventName, mReward;
    public String mId;
    public long mTotalTime;
    public int mLogo, mType;
    public int mPrgressBarColor;
    public boolean mContinuous;
    public CountDownTimerWithPause mTimer;



    public TimeEvent (String id, String eventName, long totalTime, boolean continuous){
        Log.d(TAG,"Create "+eventName);
        mId = id;
        mEventName = eventName;
        mTotalTime = totalTime;
        mLogo = setLogoByName(mEventName);
        mContinuous = continuous;
//        if(continuous){
//            mPrgressBarColor = R.color.bluegreen;
//        }else {
//            mPrgressBarColor = R.color.dark_red;
//        }
        createTimer();
    }

    private void createTimer(){
        mStatus = START;
        mTimer = new CountDownTimerWithPause(mTotalTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d("Status", String.valueOf(mStatus));
            }

            @Override
            public void onFinish() {
                setEND();
                Log.d("Status", String.valueOf(mStatus));
            }
        }.create();
    }

    private int setLogoByName(String name){
        int Logo;
        switch (name){
            case "皮卡丘絨毛玩具":
                Logo = 0;
                break;
            case "皮卡丘娃娃":
//                int [] pika = new int[]{R.drawable.pika};
//                Logo = pika[(int)(Math.random()*pika.length)];
                Logo = 1;
                break;
            default:
//                Logo = (int)(Math.random()*6)+2;
                Logo = 1;
                break;
        }
        Log.d("setLogoByName",name+":"+String.valueOf(Logo));
        return Logo;
    }

    public CountDownTimerWithPause getTimer(){
        return mTimer;
    }

    public void restartTimer(){
        createTimer();
    }

    public void startEvent(){
        mStatus = RUNNING;
    }

    public int getStatus(){
        return mStatus;
    }

    public void setEND(){
        if(mStatus != END){
            mStatus = END;
            PugNotification.with(CarpeDiemController.getInstance().getApplicationContext())
                    .load()
                    .ticker(mEventName)
                    .message("Event Finished !")
                    .smallIcon(R.drawable.carpediem_icon)
                    .largeIcon(mLogo)
                    .flags(Notification.DEFAULT_ALL)
                    .click(MainActivity.class)
                    .simple()
                    .build();
        }
    }
}
