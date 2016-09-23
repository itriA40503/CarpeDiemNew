package com.ccma.itri.org.tw.carpediem.CallApi;

/**
 * Created by A40503 on 2016/9/22.
 */
//# Map the Json keys to the Object

public class CarpeDiemObject {
    String Satus;
    String token;
    String login;
    String name;
    String code;
    @Override
    public String toString() {
        return login;
    }

    public String getToken(){
        return  token;
    }

    public String getCode(){
        return  code;
    }
}
