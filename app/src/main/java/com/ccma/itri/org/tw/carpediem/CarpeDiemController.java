package com.ccma.itri.org.tw.carpediem;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.provider.CalendarContract;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.ccma.itri.org.tw.carpediem.EventObject.TimeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by A40503 on 2016/9/20.
 */
public class CarpeDiemController extends Application {
    public static final String TAG = CarpeDiemController.class.getSimpleName();
    private static CarpeDiemController Instance;
    private List<TimeEvent> Events;

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
        Events = new ArrayList<TimeEvent>();
        Events.add(new TimeEvent("FisrtEvent", 5000, false));
        Events.add(new TimeEvent("SecondEvent", 10000, false));
        Events.add(new TimeEvent("ContinuousEvent", 10000, true));
        Events.add(new TimeEvent("ContinuousEvent2", 5000, true));
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

    private String getUUID(){
        TelephonyManager tManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String android_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        String tmDevice = "" + tManager.getDeviceId();
        String tmSerial = "" + tManager.getSimSerialNumber();
        UUID deviceUuid = new UUID(android_id.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();

        return uniqueId;
    }

}
