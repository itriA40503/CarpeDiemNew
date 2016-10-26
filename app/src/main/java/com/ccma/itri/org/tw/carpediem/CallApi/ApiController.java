package com.ccma.itri.org.tw.carpediem.CallApi;

import com.ccma.itri.org.tw.carpediem.CallApi.ApiObject.CarpeDiemListEventObject;
import com.ccma.itri.org.tw.carpediem.CallApi.ApiObject.CarpeDiemObject;
import com.ccma.itri.org.tw.carpediem.CallApi.ApiObject.EventLists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by A40503 on 2016/9/26.
 */
public class ApiController {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
            .create();

    public final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(CarpeDiemApi.ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .client(SSLTrustClient.getClient())
            .build();

    public static ApiController mInstance;
    public static ApiController getInstance(){
        if (mInstance == null){
            mInstance = new ApiController();
        }
        return mInstance;
    }

    public CarpeDiemApi mAPI;

    public ApiController(){
        mAPI = retrofit.create(CarpeDiemApi.class);
    }

    public Observable<CarpeDiemObject>getTokenPOST(String uuid){
        return  mAPI.getTokenPOST(uuid);
    }

    public Observable<CarpeDiemObject>SiginByUUID(String uuid){
//        return mAPI.getTokenOb(uuid);
        return mAPI.getSiginByUUID(uuid);
    }

    public Observable<CarpeDiemObject>sigin(String uuid){
        return mAPI.getSiginByUUID(uuid);
    }

    public Observable<CarpeDiemListEventObject>getEventList(String token){
        return mAPI.getEventList(token);
    }

    public Observable<EventLists>getNewEventList(String token){
        return mAPI.getNewEventList(token);
    }

    public Observable<CarpeDiemObject>startEvent(String eventId, String token){
        return mAPI.startEvent(eventId, token);
    }

    public Observable<CarpeDiemObject>completeEvent(String eventId, String token){
        return mAPI.completeEvent(eventId, token);
    }

}
