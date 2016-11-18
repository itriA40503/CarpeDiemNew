package com.ccma.itri.org.tw.carpediem.CallApi.ApiObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by A40503 on 2016/11/16.
 */

public class NewUserEventList {
    String id;
    String status;
    String cumulativeTime;
    String completedTimes;
    String createdAt;
    String updatedAt;
    Event event;

    public String getCreatedAt(){
//        long unixSeconds = Long.parseLong(createdAt);
        Double doubleNum = Double.parseDouble(createdAt);
        long unixSeconds = doubleNum.longValue();
        Date date = new Date(unixSeconds*1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // the format of your date
//        sdf.setTimeZone(TimeZone.getTimeZone("GMT-4")); // give a timezone reference for formating (see comment at the bottom
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public Event getEvent(){
        return event;
    }

    public String getId(){return id;}

    public String getStatus(){return status;}

    public String getCompletedTimes(){return completedTimes;}


    public class Event {
        String id;
        String name;
        String description;
        String beaconId;
        String timeRequire;
        String createdAt;
        Advertiser advertiser;
        Image image;
        Item item;

        public Item getItem(){return item;}

        public Advertiser getAdvertiser(){
            return advertiser;
        }

        public Image getImage(){
            return image;
        }

        public String getId(){return id;}

        public String getName(){return name;}

        public String getDescription(){return description;}

        public String getTimeRequire(){return timeRequire;}

        public String getBeaconId(){return beaconId;}

        public String getCreatedAt(){
            //        long unixSeconds = Long.parseLong(createdAt);
            Double doubleNum = Double.parseDouble(createdAt);
            long unixSeconds = doubleNum.longValue();
            Date date = new Date(unixSeconds*1000L); // *1000 is to convert seconds to milliseconds
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd"); // the format of your date
//        sdf.setTimeZone(TimeZone.getTimeZone("GMT-4")); // give a timezone reference for formating (see comment at the bottom
            String formattedDate = sdf.format(date);
            return formattedDate;
        }
    }


}
