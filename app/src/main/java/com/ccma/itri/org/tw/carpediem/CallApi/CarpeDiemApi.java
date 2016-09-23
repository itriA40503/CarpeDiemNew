package com.ccma.itri.org.tw.carpediem.CallApi;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by A40503 on 2016/9/21.
 */
public interface CarpeDiemApi {
//    String ENDPOINT = "http://tarsanad.ddns.net/";
//
//    @GET("testing/{name}")
//    Call<CarpeDiemObject>getToken(@Path("jsonTest.php")String name);

    String ENDPOINT = "http://ccmacd.ddns.net";

    @FormUrlEncoded
    @POST("/user/create")
    Call<CarpeDiemObject>getToken(@Field("uuid") String uuid);

    @GET("/user/sigin")
    Call<CarpeDiemObject>signInFromUUID(@Field("uuid") String uuid);

    @GET("/user/sigin")
    Call<CarpeDiemObject>signIn(@Field("username") String username, @Field("password")String password);
}
