package com.ccma.itri.org.tw.carpediem.UserData;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.ccma.itri.org.tw.carpediem.CarpeDiemController;

/**
 * Created by A40503 on 2016/9/20.
 */

class User {
    public String _uuid;
    public String _token;
//    public String tmpkey;

    public User(){
    }

    public User(String uuid, String token){
        this._uuid = uuid;
        this._token = token;
    }

    public void clear () {
        _uuid = null;
        _token = null;
    }
}

public class UserData {
    public static final String USERPREF = "USER_PREF";
    private static UserData instance;
    private Context context;
    private SharedPreferences userPref;
    private SharedPreferences.Editor userEditor;
    private User _user;

    public UserData(){
        context = CarpeDiemController.getInstance().getApplicationContext();
        getPrefs();
        restoreUserData();
    }

    public static UserData getInstance(){
        if(instance == null){
            Log.d("UserData","newInstance");
            instance = new UserData();
        }else {
            Log.d("UserData","oldInstance");
        }
        return instance;
    }

    private void getPrefs(){
        if(userPref == null){
            Log.d("UserData","userPref:NULL");
            userPref = context.getSharedPreferences(USERPREF, Context.MODE_PRIVATE);
            userEditor = userPref.edit();
        }
    }

    public void _clear(){
        _user.clear();
    }

    public boolean restoreUserData(){
        Log.d("UserData","restore");
        _user = new User(
                userPref.getString("uuid", null),
                userPref.getString("token", null)
                );

        if(_user._uuid != null){
            Log.d("restore-getUUID",_user._uuid);
            return true;
        }else{
            Log.d("restore-getUUID","NO UUID");
        }

        return false;
    }

    public boolean checkUUID(){
//        Log.d("user.uuid",_user._uuid.toString());
        if(_user._uuid == null){
            return false;
        }
        return true;
    }

    public String getUserUUID(){
//        Log.d("user.uuid",_user._uuid.toString());
        if(_user._uuid == null){

            return  "No value";
        }
        return  _user._uuid;
    }

    public String getUserToken(){
        if(_user._token == null){
            return  "No Value";
        }
        return _user._token;
    }

    public void saveUser(String uuid, String token){
        Log.d("saveUser",uuid+"::"+token);
        _user = new User(uuid, token);
        userEditor.putString("uuid", uuid);
        userEditor.putString("token", token);
        userEditor.commit();
    }
    public void clearUserData(){
        Log.d("UserData","CLEAR !!!");
        _clear();
        userEditor.clear().commit();
    }

}
