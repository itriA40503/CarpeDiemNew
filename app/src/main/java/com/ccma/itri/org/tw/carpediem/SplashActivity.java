package com.ccma.itri.org.tw.carpediem;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.ccma.itri.org.tw.carpediem.CallApi.CarpeDiemApi;
import com.ccma.itri.org.tw.carpediem.CallApi.CarpeDiemObject;
import com.ccma.itri.org.tw.carpediem.UserData.UserData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SplashActivity extends AppCompatActivity implements Callback<CarpeDiemObject> {
    public static final int REQUEST_PERMISSION_PHONE_STATE = 0;
    private static final String data = "DATA";
    private String uuid, token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //# Check Permission
        showPhoneStatePermission();
//        UserData.getInstance().clearUserData();
        openMainPage();
    }

    private void showPhoneStatePermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_PHONE_STATE)) {
                showExplanation("Permission Needed", "Choose 'Allow' to start CarpeDiem !", Manifest.permission.READ_PHONE_STATE, REQUEST_PERMISSION_PHONE_STATE);
            } else {
                requestPermission(Manifest.permission.READ_PHONE_STATE, REQUEST_PERMISSION_PHONE_STATE);
            }
        } else {
//            Toast.makeText(this, "Permission (already) Granted!", Toast.LENGTH_SHORT).show();
//            GetTokenFrom(getUUID());
//            SaveUserInPref(getUUID(), token);
        }
    }
    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            String permissions[],
            int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_PHONE_STATE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();
//                    GetTokenFrom(getUUID());
//                    SaveUserInPref(getUUID(), token);
                } else {
                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
        }
    }

    private void showExplanation(String title, String message, final String permission, final int permissionRequestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestPermission(permission, permissionRequestCode);
                    }
                });
        builder.create().show();
    }

    private void requestPermission(String permissionName, int permissionRequestCode) {
        ActivityCompat.requestPermissions(this,
                new String[]{permissionName}, permissionRequestCode);
    }

    private void openMainPage(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        },800);
    }

    @Override
    public void onResponse(Call<CarpeDiemObject> call, Response<CarpeDiemObject> response) {
        int code = response.code();
        Log.d("Response Code", String.valueOf(code));

        if (code == 200) {
            CarpeDiemObject user = response.body();
            Log.d("Got the Token:", user.getToken());
            token = user.getToken();
//            Toast.makeText(this, "Got the Token:" + user.getToken(), Toast.LENGTH_LONG).show();
        }else if(code == 401){
            CarpeDiemObject user = response.body();
            try {
                String errorBody = response.errorBody().string();
//                Log.d("Msg", errorBody);
                Gson gson = new Gson();
                CarpeDiemObject ObjectFromGson = gson.fromJson(errorBody,CarpeDiemObject.class);
                Log.d("Got the Token:",ObjectFromGson.getToken());
                token = ObjectFromGson.getToken();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            Toast.makeText(this, "Got the User:" + user.getCode(), Toast.LENGTH_LONG).show();
        } else {
            Log.e("Response Msg",response.message());
            Toast.makeText(this, "Did not work: " + String.valueOf(code), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onFailure(Call<CarpeDiemObject> call, Throwable t) {
        Log.d("Throwable",call.request().url().toString());
    }

    public void GetTokenFromUUID(String uuid){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CarpeDiemApi.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        CarpeDiemApi carpeDiemAPI = retrofit.create(CarpeDiemApi.class);

        Call<CarpeDiemObject> call = carpeDiemAPI.getToken(uuid);
        call.enqueue(this);

    }

    public void SaveUserInPref(String uuid, String token){
        if(UserData.getInstance().checkUUID()){
            Log.d("checkUUID", "TRUE");
        }else {
            Log.d("checkUUID", "FALSE");
            UserData.getInstance().saveUser(uuid, token);
        }
    }

}
