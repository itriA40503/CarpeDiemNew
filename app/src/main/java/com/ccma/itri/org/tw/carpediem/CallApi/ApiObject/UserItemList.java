package com.ccma.itri.org.tw.carpediem.CallApi.ApiObject;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by A40503 on 2016/10/24.
 */

public class UserItemList {
    String id;
    String itemId;
    String hashcode;
    String createdAt;
    String expiredAt;
    item item;
    public String getItemId(){
        return itemId;
    }

    public String getId(){
        return id;
    }

    public item getItem(){
        return item;
    }

    public String getCreatedAt(){
        Double doubleNum = Double.parseDouble(createdAt);
        long unixSeconds = doubleNum.longValue();
        Date date = new Date(unixSeconds*1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm"); // the format of your date
    //        sdf.setTimeZone(TimeZone.getTimeZone("GMT-4")); // give a timezone reference for formating (see comment at the bottom
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public String getExpiredAt(){
        Double doubleNum = Double.parseDouble(expiredAt);
        long unixSeconds = doubleNum.longValue();
        Date date = new Date(unixSeconds*1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm"); // the format of your date
//        sdf.setTimeZone(TimeZone.getTimeZone("GMT-4")); // give a timezone reference for formating (see comment at the bottom
        String formattedDate = sdf.format(date);
        return formattedDate;
    }
   public class item{
       String itemId;
       String typeId;
       String name;
       String description;

       public String getItemContent(){
           return itemId+":"+typeId+":"+name+":"+description;
       }

       public String getItemId(){
           return itemId;
       }

       public String getItemName(){
           return name;
       }

       public String getItemDesc(){
           return description;
       }


   }
}
