package com.ccma.itri.org.tw.carpediem.CallApi.ApiObject;

/**
 * Created by A40503 on 2016/10/17.
 */

public class UserEventList {
    String id;
    String eventId;
    String status;
    String cumulativeTime; //#  1 = idle, 2 = start, 3 = complete, 4 = end
    public int completedTimes;
    String createdAt;

    public String getId(){
        return id;
    }

    public String getEventId(){
        return eventId;
    }

    public String getStatus(){
        return status;
    }

    public long getCompletedTimes(){ return Long.valueOf(completedTimes);}
}
