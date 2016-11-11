package com.ccma.itri.org.tw.carpediem.EventObject;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by A40503 on 2016/11/2.
 */

public class BackpackItem {
    private static final String TAG = "BackpackItem";
    public String mName;
    public String mDescription;
    private String mCreatedAt;
    private String mExpiredAt;

    public BackpackItem (String name, String description){
        mName = name;
        mDescription = description;
    }

    public BackpackItem (String name, String description, String createdAt, String expiredAt){
        mName = name;
        mDescription = description;
        mCreatedAt = createdAt;
        mExpiredAt = expiredAt;

    }

    public String getName(){return mName;}

    public String getDescription(){return mDescription;}

    public String getCreatedAt(){
        return mCreatedAt;
    }

    public String getExpiredAt(){
        return mExpiredAt;
    }

    public String getDayLeft(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        int diffInDays=0;
        try {
            Calendar cal = Calendar.getInstance();
            Date createDate = cal.getTime();
            Date expiredDate = sdf.parse(mExpiredAt);
//            Log.d("getDayLeft", createDate+"*"+expiredDate);
            diffInDays = (int)( (expiredDate.getTime() - createDate.getTime())
                    / (1000 * 60 * 60 * 24) );
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(diffInDays);
    }

}
