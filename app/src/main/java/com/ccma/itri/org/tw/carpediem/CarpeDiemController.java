package com.ccma.itri.org.tw.carpediem;

import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.ccma.itri.org.tw.carpediem.CallApi.ApiController;
import com.ccma.itri.org.tw.carpediem.CallApi.ApiObject.ArrayUserItemList;
import com.ccma.itri.org.tw.carpediem.CallApi.ApiObject.EventLists;
import com.ccma.itri.org.tw.carpediem.CallApi.ApiObject.UserEventList;
import com.ccma.itri.org.tw.carpediem.CallApi.ApiObject.UserItemList;
import com.ccma.itri.org.tw.carpediem.CallApi.ApiObject.CarpeDiemEventObject;
import com.ccma.itri.org.tw.carpediem.CallApi.ApiObject.CarpeDiemListEventObject;
import com.ccma.itri.org.tw.carpediem.CallApi.ApiObject.CarpeDiemObject;
import com.ccma.itri.org.tw.carpediem.EventObject.BackpackItem;
import com.ccma.itri.org.tw.carpediem.EventObject.TimeEvent;
import com.ccma.itri.org.tw.carpediem.UserData.UserData;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
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
    public static String UUID,TOKEN;
    public List<TimeEvent> Events;
    public List<BackpackItem> Items;

    @Override
    public void onCreate() {
        super.onCreate();
        Instance = this;

        //# Setting BrocastReceier
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        BroadcastReceiver mReceiver = new ScreenReceiver();
        registerReceiver(mReceiver, filter);

        ApiController.getInstance();
        //# Setting TimeEvents
        Events = new ArrayList<TimeEvent>();
        Items = new ArrayList<BackpackItem>();
//        Events.add(new TimeEvent("FisrtEvent", 5000, false));
//        Events.add(new TimeEvent("SecondEvent", 10000, false));
//        Events.add(new TimeEvent("ContinuousEvent", 10000, true));
//        Events.add(new TimeEvent("ContinuousEvent2", 5000, true));
    }

    public synchronized static CarpeDiemController getInstance(){
        return Instance;
    }

    public List<TimeEvent> getTimeEvents(){
        return Events;
    }

    public List<BackpackItem> getBackPackItems(){return Items;}

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
        UUID = uniqueId;
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

    private void SaveUserTokenInPref(String token){
        Log.d("SaveUserInPref","");
        UserData.getInstance().saveUserToken(token);
    }

    public void RxGetTokenCreate(final String uuid,final Activity activity){
        ApiController.getInstance().getTokenPOST(uuid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CarpeDiemObject>() {
                    @Override
                    public void onCompleted() {
                        Log.d("RxGetTokenCreate","onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("RxGetTokenCreate","onError");
//                        if (e instanceof HttpException) {
//                            // We had non-2XX http error
//                        }
//                        if (e instanceof IOException) {
//                            // A network or conversion error happened
//                        }

                        try {
//                            Log.d("getTokenPOST onError",((HttpException) e).response().headers().toString());
//                            Log.d("getTokenPOST onError",((HttpException) e).response().errorBody().string());
//                            Log.d("getTokenPOST onError",((HttpException) e).response().raw().toString());
//
                            //# Get errorBody to gson
                            String errorBody = ((HttpException) e).response().errorBody().string();
                            Gson gson = new Gson();
                            CarpeDiemObject ObjectFromGson = gson.fromJson(errorBody,CarpeDiemObject.class);
                            if(ObjectFromGson.getCode() == 1){
                                TOKEN = ObjectFromGson.getToken();
                                Log.d(TAG, "401 : " + TOKEN);
                                if(TOKEN != null){
                                    SaveUserInPref(uuid, TOKEN);
                                    RxGetToken(uuid, activity);
//                                    RxGetToken()
                                }else {
                                    Log.d("RxGetTokenCreate","TOKEN is NULL");
                                }
                            }else if(ObjectFromGson.getCode() == 0){
                                Log.d(TAG, "Parameter error");
                            }else if(ObjectFromGson.getCode() == 2){
                                Log.d(TAG, "Other error");
                            }
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(CarpeDiemObject carpeDiemObject) {
                        Log.d("getTokenPOST","onNext");
                        RxGetToken(uuid, activity);
                    }
                });
    }

    public void RxGetToken(final String uuid,final Activity activity){
        showToast("Singin GetToken");
        ApiController.getInstance().SiginByUUID(uuid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CarpeDiemObject>() {
                    final static String TAG = "SINGIN GETTOKEN";
//                    private String TOKEN;
                    @Override
                    public void onCompleted() {

                        Log.d("GetToken","onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        try {
//                            Log.d("RxGetToken onError",((HttpException) e).response().headers().toString());
//                            Log.d("RxGetToken onError",((HttpException) e).response().errorBody().string());
//                            Log.d("RxGetToken onError",((HttpException) e).response().raw().toString());
//                            Log.d("RxGetToken","onError");
                            //# Get errorBody to gson
                            String errorBody = ((HttpException) e).response().errorBody().string();
                            Log.d(TAG,errorBody);
                            Gson gson = new Gson();
                            CarpeDiemObject ObjectFromGson = gson.fromJson(errorBody,CarpeDiemObject.class);
                            if(ObjectFromGson.getCode() == 1){
                                Log.d(TAG,"The User doesn't exists");
                                RxGetTokenCreate(uuid, activity);
//                                TOKEN = ObjectFromGson.getToken();
//                                Log.d(TAG, "401 : " + TOKEN);
//                                if(TOKEN != null){
//                                    SaveUserInPref(uuid, TOKEN);
//                                    RxGetEventList(TOKEN, activity);
//                                }else {
//                                    Log.d("GetToken","TOKEN is NULL");
//                                }
                            }else if(ObjectFromGson.getCode() == 0){
                                Log.d(TAG, "Parameter error");
                            }else if(ObjectFromGson.getCode() == 2){
                                Log.d(TAG, "Other error");
                            }
                            ObjectFromGson = null;
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        Log.d("RxGetToken","onERROR ");
                    }
                    @Override
                    public void onNext(CarpeDiemObject carpeDiemObject) {
                        Log.d(TAG,"onNext");
                        TOKEN = carpeDiemObject.getToken();
                        if(TOKEN != null){
                            SaveUserInPref(uuid, TOKEN);
                            RxGetItemList(TOKEN);
                            RxGetNewEventList(TOKEN, activity);
                        }else {
                            Log.d("GetToken","TOKEN is NULL");
                        }
                        Log.d(TAG, TOKEN);
                    }
                });
    }

//    Observable<CarpeDiemObject> singinGetToken(String uuid){
//        return ApiController.getInstance().SiginByUUID(uuid)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
//    }

    public void RxGetEventList(String token, final Activity activity) {
        showToast("Get EventList");
        ApiController.getInstance().getEventList(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CarpeDiemListEventObject>() {
                    @Override
                    public void onCompleted() {
                        OpenMainPage(activity);
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
                        Log.d("RxGetEventList","onERROR ");
                        ObjectFromGson = null;
                    }

                    @Override
                    public void onNext(CarpeDiemListEventObject carpeDiemListEventObject) {
                        Log.d("RxGetEventList","SIZE : "+Integer.toString(carpeDiemListEventObject.eventList.size()));
                        Log.d("RxGetEventList","");
                        for(CarpeDiemEventObject event : carpeDiemListEventObject.eventList){
//                            Log.d("RxGetEventList",event.getItemContents());
                            CarpeDiemEventObject.item item = event.item;
                            Events.add(new TimeEvent(event.getEventId(), event.getEventId(), 1200, true));
                        }
//                        Events.add(new TimeEvent("Eeny",5000,true));
//                        Events.add(new TimeEvent("meeny",6000,true));
//                        Events.add(new TimeEvent("miny",5000,false));
//                        Events.add(new TimeEvent("moe",5000,false));
                    }
                });
    }

    public void RxGetNewEventList(String token, final Activity activity) {
        showToast("Get EventList");
        ApiController.getInstance().getNewEventList(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<EventLists>() {
                    @Override
                    public void onCompleted() {
                        OpenMainPage(activity);
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
                        Log.d("RxGetEventList","onERROR ");
                        ObjectFromGson = null;
                    }

                    @Override
                    public void onNext(EventLists carpeDiemListEventObject) {
                        Log.d("RxGetEventList","SIZE : "+Integer.toString(carpeDiemListEventObject.userEventList.size()));
                        Log.d("RxGetEventList","");
                        for(UserEventList event : carpeDiemListEventObject.userEventList){
//                            Log.d("RxGetEventList",event.getItemContents());
//                            CarpeDiemEventObject.item item = event.item;
                            Events.add(new TimeEvent(event.getId(), event.getId(), Long.parseLong(event.getStatus())*1000, true));
//                            Events.add(new TimeEvent(event.getId(), event.getId(), event.getCompletedTimes()*1000, true));
                        }
//                        Events.add(new TimeEvent("Eeny",5000,true));
//                        Events.add(new TimeEvent("meeny",6000,true));
//                        Events.add(new TimeEvent("miny",5000,false));
//                        Events.add(new TimeEvent("moe",5000,false));
                    }
                });
    }

    public void RxGetItemList(String token){
        ApiController.getInstance().mAPI.getItemList(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayUserItemList>() {
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
                        Log.d("RxGetItemList","CODE : "+ObjectFromGson.getCode());
                        Log.d("RxGetItemList","onERROR ");
                        ObjectFromGson = null;
                    }

                    @Override
                    public void onNext(ArrayUserItemList userItemLists) {
                        Log.d("RxGetItemList","SIZE : "+Integer.toString(userItemLists.userItemList.size()));
                        Log.d("RxGetItemList","");
                        for(UserItemList item : userItemLists.userItemList){
//                            Log.d("RxGetEventList",event.getItemContents());
//                            CarpeDiemEventObject.item item = event.item;
                            Items.add(new BackpackItem(item.getItem().getItemName(),item.getItem().getItemDesc()));
//                            Events.add(new TimeEvent(item.getId(), item.getItem().getItemName(), 5000, true));
                        }
                    }
                });
    }

    public void OpenMainPage(final Activity activity){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(activity.getApplication(), NewMainActivity.class);

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                activity.finish();
                Log.d("OpenMainPage","finish");
            }
        },800);
    }

    public void showToast(String text){
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    public void startEvent(String eventId, String token){
        ApiController.getInstance().startEvent(eventId, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CarpeDiemObject>(){
                    @Override
                    public void onCompleted() {
                        Log.d("startEvent","onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            // We had non-2XX http error
                            Log.d("startEvent","HttpException");

                        }
                        if (e instanceof IOException) {
                            Log.d("startEvent","IOException");
                            Log.d("startEvent",e.toString());
                            Log.d("startEvent","OK");
                            // A network or conversion error happened
                        }

                    }

                    @Override
                    public void onNext(CarpeDiemObject carpeDiemObject) {
                        Log.d("startEvent","onNext");
                    }
                });
    }

    public void completeEvent(String eventId, String token){
        ApiController.getInstance().completeEvent(eventId,token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CarpeDiemObject>(){
                    @Override
                    public void onCompleted() {
                        Log.d("completeEvent","onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            // We had non-2XX http error
                            Log.d("completeEvent","HttpException");

                        }
                        if (e instanceof IOException) {
                            Log.d("completeEvent","IOException");
                            Log.d("completeEvent",e.toString());
//                            Log.d("startEvent","OK");
                            // A network or conversion error happened
                        }

                    }

                    @Override
                    public void onNext(CarpeDiemObject carpeDiemObject) {
                        Log.d("completeEvent","onNext");
                    }
                });
    }
}
