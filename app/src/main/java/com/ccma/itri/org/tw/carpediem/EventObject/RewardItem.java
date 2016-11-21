package com.ccma.itri.org.tw.carpediem.EventObject;

import com.ccma.itri.org.tw.carpediem.CallApi.ApiObject.Advertiser;

/**
 * Created by A40503 on 2016/11/9.
 */

public class RewardItem {
    String mItemId;
    String mTypeId;
    String mName;
    String mDescription;
    String mAdvertiserId;
    Advertiser mAdvertiser;

    public RewardItem (String itemId, String typeId, String name, String descrpition, String advertiserId){
        mItemId = itemId;
        mTypeId = typeId;
        mName = name;
        mDescription = descrpition;
//        mAdvertiser = advertiser;
        mAdvertiserId = advertiserId;
    }

    public RewardItem (String itemId, String typeId, String name, String descrpition, Advertiser advertiser){
        mItemId = itemId;
        mTypeId = typeId;
        mName = name;
        mDescription = descrpition;
        mAdvertiser = advertiser;
//        mAdvertiserId = AdvertiserId;
    }

    public Advertiser getAdvertiser(){return mAdvertiser;}

    public String getName(){return mName;}

    public String getDescription(){return mDescription;}
}
