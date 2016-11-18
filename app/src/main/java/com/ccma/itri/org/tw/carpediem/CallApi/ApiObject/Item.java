package com.ccma.itri.org.tw.carpediem.CallApi.ApiObject;

/**
 * Created by A40503 on 2016/11/16.
 */

public class Item {
    String id;
    String typeId;
    String name;
    String description;
    Image image;
    Advertiser advertiser;

    public Image getImage(){return image;}

    public Advertiser getAdvertiser(){return advertiser;}

    public String getId(){return id;}

    public String getName(){return name;}

    public String getTypeId(){return typeId;}

    public String getDescription(){return description;}

}
