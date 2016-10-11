package com.ccma.itri.org.tw.carpediem.CallApi;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by A40503 on 2016/9/21.
 */
public interface CarpeDiemApi {
//    String ENDPOINT = "http://tarsanad.ddns.net/";
//
//    @GET("testing/{name}")
//    Call<CarpeDiemObject>getToken(@Path("jsonTest.php")String name);

    String ENDPOINT = "https://ccmacd.ddns.net/";

    @FormUrlEncoded
    @POST("user/create")
    Call<CarpeDiemObject>getToken(@Field("uuid") String uuid);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST("user/create")
    Observable<CarpeDiemObject> getTokenPOST(@Field("uuid") String uuid);

    @Headers({"Accept: application/json"})
    @GET("/user/signin")
    Observable<CarpeDiemObject>getSiginByUUID(@Query(value = "uuid" ,encoded = true) String uuid);

    @GET("/user/signin")
    Observable<CarpeDiemObject>getSigin(@Query(value = "username" ,encoded = true) String username, @Query(value = "password", encoded = true) String password);

    @Headers({"Accept: application/json"})
    @GET("/event")
    Observable<CarpeDiemListEventObject>getEventList(@Query(value = "access_token", encoded = true) String  token);
}
