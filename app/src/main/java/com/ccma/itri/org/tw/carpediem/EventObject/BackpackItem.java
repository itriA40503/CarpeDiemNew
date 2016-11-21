package com.ccma.itri.org.tw.carpediem.EventObject;

import android.util.Log;

import com.ccma.itri.org.tw.carpediem.CallApi.ApiObject.Advertiser;
import com.ccma.itri.org.tw.carpediem.CallApi.ApiObject.NewUserItemList;
import com.ccma.itri.org.tw.carpediem.CallApi.ApiObject.UserItemList;
import com.ccma.itri.org.tw.carpediem.Timer.CountDownTimerWithPause;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by A40503 on 2016/11/2.
 */

public class BackpackItem {
    private static final String TAG = "BackpackItem";
    public String mName;
    public String mDescription;
    private String mCreatedAt;
    private String mExpiredAt;
    private Advertiser mAdvertiser;
    private String mAdId;
    private String mAdName;
    private NewUserItemList mUserItemList;
    private CountDownTimerWithPause mTimer;

    public BackpackItem (String name, String description){
        mName = name;
        mDescription = description;
    }

    public BackpackItem (String name, String description, String createdAt, String expiredAt, String AdId, String AdName){
        mName = name;
        mDescription = description;
        mCreatedAt = createdAt;
        mExpiredAt = expiredAt;
        mAdId = AdId;
        mAdName = AdName;
    }

    public BackpackItem (String name, String description, String createdAt, String expiredAt, Advertiser advertiser){
        mName = name;
        mDescription = description;
        mCreatedAt = createdAt;
        mExpiredAt = expiredAt;
        mAdvertiser = advertiser;
    }

    public BackpackItem(NewUserItemList userItemList){
        mUserItemList = userItemList;
        mName = userItemList.getItem().getName();
        mDescription = userItemList.getItem().getDescription();
        mCreatedAt = userItemList.getCreatedAt();
        mExpiredAt = userItemList.getExpiredAt();
        mAdvertiser = userItemList.getItem().getAdvertiser();
//        if(getDayLeft()<1){
//            createTimer();
//        }

    }

    public BackpackItem (String name, String description, String createdAt, String expiredAt){
        mName = name;
        mDescription = description;
        mCreatedAt = createdAt;
        mExpiredAt = expiredAt;
    }

    public NewUserItemList getUserItemList(){
        return mUserItemList;
    }

    public Advertiser getAdvertiser(){return mAdvertiser;}

    public String getName(){return mName;}

    public String getDescription(){return mDescription;}

    public String getCreatedAt(){
        return dateFormat(mCreatedAt);
    }

    public String getExpiredAt(){
        return dateFormat(mExpiredAt);
    }

    public int getDayLeft(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        int diffInDays=0;

        try {
            Calendar cal = Calendar.getInstance();
            Date createDate = cal.getTime();
            Date expiredDate = sdf.parse(mExpiredAt);
//            Log.d("getDayLeft", createDate+"*"+expiredDate);
            diffInDays = (int)( (expiredDate.getTime() - createDate.getTime())
                    / (1000 * 60 * 60 * 24) );
//            Log.d("getDayleft", createDate+" | "+expiredDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return diffInDays;
    }

    public CountDownTimerWithPause getTimer(){
        return mTimer;
    }

    private void createTimer(){
        mTimer = new CountDownTimerWithPause(getleftTimes(), 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
//                Log.d("Status", String.valueOf(mStatus));
            }

            @Override
            public void onFinish() {
//                setEND();
//                Log.d("Status", String.valueOf(mStatus));
            }
        }.create();
    }

    public long getleftTimes(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        long diffInTimes=0;
        try {
            Calendar cal = Calendar.getInstance();
            Date createDate = cal.getTime();
            Date expiredDate = sdf.parse(mExpiredAt);
//            Log.d("getDayLeft", createDate+"*"+expiredDate);
//            Log.d("getleftTimes", expiredDate.getTime()+":"+createDate.getTime());
            diffInTimes = (long)( (expiredDate.getTime() - createDate.getTime())
                    / (1000));
//            Log.d("getDayleft", createDate+" | "+expiredDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return diffInTimes;
    }

    private String dateFormat(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        Date format = null;
        try {
            format = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return sdf.format(format);
    }

}
