package com.ccma.itri.org.tw.carpediem.CallApi.ApiObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by A40503 on 2016/9/29.
 */
public class CarpeDiemEventObject {
//    String id;
    String eventId;
    public String name;
    public String description;
    String beaconId;
    String createdAt;
    String advertiserId;
    String timeRequire;
    public item item;

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

    public String getEventDesc(){
        return description;
    }

    public String getEventId(){
        return eventId;
    }

    public String getName(){return name;}

    public String getDescription(){return description;}

    public String getTimeRequire(){return timeRequire;}

    public class item{
        String itemId;
        String typeId;
        String name;
        String description;

        public String getItemContent(){
            return itemId+":"+typeId+":"+description;
        }

        public String getTypeId(){return  typeId;}

        public String getItemId(){
            return itemId;
        }

        public String getName(){return name;}

        public String getItemDesc(){
            return description;
        }

    }

}
