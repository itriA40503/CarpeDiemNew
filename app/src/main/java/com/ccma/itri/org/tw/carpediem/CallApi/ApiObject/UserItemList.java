package com.ccma.itri.org.tw.carpediem.CallApi.ApiObject;

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
