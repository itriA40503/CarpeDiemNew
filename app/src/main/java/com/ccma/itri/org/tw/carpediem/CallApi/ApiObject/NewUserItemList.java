package com.ccma.itri.org.tw.carpediem.CallApi.ApiObject;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by A40503 on 2016/11/16.
 */

public class NewUserItemList {
    String id;
    String hashcode;
    String eventId;
    String createdAt;
    String expiredAt;
    Item item;

    public Item getItem(){return item;}

    public String getId(){return id;}

    public String getHashcode(){return hashcode;}

    public String getEventId(){return eventId;}

    public String getCreatedAt(){

//        long unixSeconds = Long.parseLong(createdAt);
        Double doubleNum = Double.parseDouble(createdAt);
        long unixSeconds = doubleNum.longValue();

        Date date = new Date(unixSeconds*1000L); // *1000 is to convert seconds to milliseconds

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss"); // the format of your date
//        sdf.setTimeZone(TimeZone.getTimeZone("GMT-4")); // give a timezone reference for formating (see comment at the bottom
        String formatCreateDate = sdf.format(date);

        return formatCreateDate;
    }

    public String getExpiredAt(){
//        long unixSeconds = Long.parseLong(createdAt);

        Double doubleNum = Double.parseDouble(expiredAt);
        long unixSeconds = doubleNum.longValue();

        Date date = new Date(unixSeconds*1000L); // *1000 is to convert seconds to milliseconds

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss"); // the format of your date
//        sdf.setTimeZone(TimeZone.getTimeZone("GMT-4")); // give a timezone reference for formating (see comment at the bottom
        String formattedDate = sdf.format(date);

        return formattedDate;
    }
}
