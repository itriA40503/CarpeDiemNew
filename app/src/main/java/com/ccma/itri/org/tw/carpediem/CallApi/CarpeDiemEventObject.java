package com.ccma.itri.org.tw.carpediem.CallApi;

/**
 * Created by A40503 on 2016/9/29.
 */
public class CarpeDiemEventObject {
    String Satus,code;
    String event_list_id;
    String event_name;
    String event_description;
    String beacon_id;
    String time_created;
    String item_id;
    String advertiser_id;
    public item item;

    public String getCode(){
        return  code;
    }

    public String getItemContents(){
        return  item_id+":"+item.item_type_id+":"+item.item_name+":"+item.item_description;
    }

    public class item{
        String item_id;
        String item_type_id;
        String item_name;
        String item_description;

        public String getItemContent(){
            return item_id+":"+item_type_id+":"+item_name+":"+item_description;
        }

    }

}
