package com.ccma.itri.org.tw.carpediem.CallApi.ApiObject;

import com.ccma.itri.org.tw.carpediem.CallApi.ApiGeneralResult;

/**
 * Created by A40503 on 2016/9/22.
 */
//# Map the Json keys to the Object

public class CarpeDiemObject extends ApiGeneralResult {
    String Satus;
    String token;
    String login;
    String name;
//    int code;
    @Override
    public String toString() {
        return login;
    }

    public String getToken(){
        return  token;
    }

//    public int getCode(){
//        return  code;
//    }
}
