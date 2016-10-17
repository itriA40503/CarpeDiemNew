package com.ccma.itri.org.tw.carpediem.CallApi;

/**
 * Created by A40503 on 2016/9/29.
 */
public class CarpeDiemEventObject {
    String Satus,code;
    String id;
    String eventId;
    String name;
    String description;
    String beaconId;
    String createdAt;
    String advertiserId;

    public item item;

    public String getCode(){
        return  code;
    }

    public String getEventDesc(){
        return description;
    }

    public String getEventId(){
        return eventId;
    }

    public String getItemContents(){
        return  item.id+":"+item.name+":"+item.description;
    }

    public class item{
        String id;
        String typeId;
        String name;
        String description;

        public String getItemContent(){
            return id+":"+typeId+":"+name+":"+description;
        }

        public String getItemId(){
            return id;
        }

        public String getItemName(){
            return name;
        }

        public String getItemDesc(){
            return description;
        }

    }

}
