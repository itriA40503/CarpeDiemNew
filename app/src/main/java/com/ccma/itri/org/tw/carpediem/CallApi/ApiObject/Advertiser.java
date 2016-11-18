package com.ccma.itri.org.tw.carpediem.CallApi.ApiObject;

/**
 * Created by A40503 on 2016/11/16.
 */

public class Advertiser {
    String id;
    String name;

    public Advertiser (String _id, String _name){
        id = _id;
        name = _name;
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }
}
