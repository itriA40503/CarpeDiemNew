package com.ccma.itri.org.tw.carpediem;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.CalendarContract;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.ccma.itri.org.tw.carpediem.CallApi.ApiController;
import com.ccma.itri.org.tw.carpediem.CallApi.CarpeDiemEventObject;
import com.ccma.itri.org.tw.carpediem.CallApi.CarpeDiemListEventObject;
import com.ccma.itri.org.tw.carpediem.CallApi.CarpeDiemObject;
import com.ccma.itri.org.tw.carpediem.EventObject.TimeEvent;
import com.ccma.itri.org.tw.carpediem.UserData.UserData;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by A40503 on 2016/9/20.
 */
public class CarpeDiemController extends Application {
    public static final String TAG = CarpeDiemController.class.getSimpleName();
    private static CarpeDiemController Instance;
    public List<TimeEvent> Events;

    @Override
    public void onCreate() {
        super.onCreate();
        Instance = this;

        //# Setting BrocastReceier
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        BroadcastReceiver mReceiver = new ScreenReceiver();
        registerReceiver(mReceiver, filter);

        //# Setting TimeEvents

    }

    public synchronized static CarpeDiemController getInstance(){
        return Instance;
    }

    public List<TimeEvent> getTimeEvents(){
        return Events;
    }

    public void timeEventResume(){
        for(TimeEvent event : Events){
            if(event.getStatus() == TimeEvent.RUNNING){
                event.getTimer().resume();
            }
        }
    }

    public void timeEventPause(){
        for(TimeEvent event : Events){
            if(event.getStatus() == TimeEvent.RUNNING){
                if(event.mContinuous){
                    event.getTimer().cancel();
                    event.restartTimer();
                }else{
                    event.getTimer().pause();
                }
            }
        }
    }

    public class ScreenReceiver extends BroadcastReceiver{
        public boolean wasScreenOn = true;
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                // do whatever you need to do here
                wasScreenOn = false;
                timeEventResume();
            } else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                // and do whatever you need to do here
                wasScreenOn = true;
                timeEventPause();
            }
        }
    }

    public String getUUID(){
        TelephonyManager tManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        String tmDevice = "" + tManager.getDeviceId();
        String tmSerial = "" + tManager.getSimSerialNumber();
        UUID deviceUuid = new UUID(android_id.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();

        return uniqueId;
    }

    private void SaveUserInPref(String uuid, String token){
        Log.d("SaveUserInPref","");
        if(UserData.getInstance().checkUUID()){
            Log.d("checkUUID", "TRUE");
        }else {
            Log.d("checkUUID", "FALSE");
            UserData.getInstance().saveUser(uuid, token);
        }
    }

    public void RxGetToken(final String uuid){
        ApiController.getInstance().getToken(uuid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CarpeDiemObject>() {
                    final static String TAG = "GETTOKEN";
                    private String TOKEN;
                    @Override
                    public void onCompleted() {

                        Log.d("GetToken","onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        try {
//                            Log.d("onError",((HttpException) e).response().headers().toString());
//                            Log.d("onError",((HttpException) e).response().errorBody().string());
//                            Log.d("onError",((HttpException) e).response().raw().toString());

                            //# Get errorBody to gson
                            String errorBody = ((HttpException) e).response().errorBody().string();
                            Gson gson = new Gson();
                            CarpeDiemObject ObjectFromGson = gson.fromJson(errorBody,CarpeDiemObject.class);
                            if(ObjectFromGson.getCode().equals(1)){
                                TOKEN = ObjectFromGson.getToken();
                                Log.d(TAG, "401 : " + TOKEN);
                                if(TOKEN != null){
                                    SaveUserInPref(uuid, TOKEN);
                                }else {
                                    Log.d("GetToken","TOKEN is NULL");
                                }
                            }else if(ObjectFromGson.getCode().equals("0")){
                                Log.d(TAG, "Parameter error");
                            }else if(ObjectFromGson.getCode().equals("2")){
                                Log.d(TAG, "Other error");
                            }
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    @Override
                    public void onNext(CarpeDiemObject carpeDiemObject) {
                        TOKEN = carpeDiemObject.getToken();
                        if(TOKEN != null){
                            SaveUserInPref(uuid, TOKEN);

                        }else {
                            Log.d("GetToken","TOKEN is NULL");
                        }
                        Log.d(TAG, TOKEN);
                    }
                });
    }

    public void RxGetEventList(String token) {
        ApiController.getInstance().getEventList(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CarpeDiemListEventObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        String errorBody = null;
                        try {
                            errorBody = ((HttpException) e).response().errorBody().string();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        Gson gson = new Gson();
                        CarpeDiemEventObject ObjectFromGson = gson.fromJson(errorBody,CarpeDiemEventObject.class);
                        Log.d("RxGetEventList","CODE : "+ObjectFromGson.getCode());
                    }

                    @Override
                    public void onNext(CarpeDiemListEventObject carpeDiemListEventObject) {
                        Log.d("RxGetEventList","SIZE : "+Integer.toString(carpeDiemListEventObject.eventList.size()));
                        Log.d("RxGetEventList","");
                        for(CarpeDiemEventObject event : carpeDiemListEventObject.eventList){
                            Log.d("RxGetEventList",event.getItemContents());
                        }

                    }
                });
    }
}
