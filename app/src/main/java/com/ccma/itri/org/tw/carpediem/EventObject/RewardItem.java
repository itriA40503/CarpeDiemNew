package com.ccma.itri.org.tw.carpediem.EventObject;

/**
 * Created by A40503 on 2016/11/9.
 */

public class RewardItem {
    String mItemId;
    String mTypeId;
    String mName;
    String mDescription;
    public RewardItem (String itemId, String typeId, String name, String descrpition){
        mItemId = itemId;
        mTypeId = typeId;
        mName = name;
        mDescription = descrpition;
    }

    public String getName(){return mName;}

    public String getDescription(){return mDescription;}
}
