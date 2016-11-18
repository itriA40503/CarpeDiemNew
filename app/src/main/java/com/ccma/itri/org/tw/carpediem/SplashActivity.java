package com.ccma.itri.org.tw.carpediem;

import android.Manifest;

import android.content.Context;
import android.content.DialogInterface;

import android.content.Intent;
import android.content.pm.PackageManager;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ccma.itri.org.tw.carpediem.UserData.UserData;


public class SplashActivity extends AppCompatActivity {
    public static final int REQUEST_PERMISSION_PHONE_STATE = 0;
    private static final String data = "DATA";
    private String uuid, token;
    private boolean getService = false;     //是否已開啟定位服務
    private String bestProvider = LocationManager.GPS_PROVIDER;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //#取得系統定位服務
        LocationManager status = (LocationManager) (this.getSystemService(Context.LOCATION_SERVICE));
        if(status.isProviderEnabled(LocationManager.GPS_PROVIDER)|| status.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
        {
            //#如果GPS或網路定位開啟，呼叫locationServiceInitial()更新位置
            CarpeDiemController.getInstance().locationServiceInitial();
        } else {
            Toast.makeText(this, "請開啟定位服務", Toast.LENGTH_LONG).show();
            getService = true; //#確認開啟定位服務
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)); //#開啟設定頁面
        }
        UserData.getInstance().clearUserData();
        //# get UUID
        uuid = CarpeDiemController.getInstance().getUUID();
//        CarpeDiemController.getInstance().RxGetTokenCreate(uuid,this);
//        if(UserData.getInstance().getUserToken().equals("No Value")){
//            CarpeDiemController.getInstance().RxGetTokenCreate(uuid);
//        }
        //# TRY DUMMY
//        CarpeDiemController.getInstance().settingDummy();
//        CarpeDiemController.getInstance().OpenMainPage(SplashActivity.this);

        //# openMainPage After Check Permission
         showPhoneStatePermission();

//        openMainPage();

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
            //# get Token than OpenMainPage
            CarpeDiemController.getInstance().RxGetToken(uuid, SplashActivity.this);
        }
    }
    @Override
    public void onRequestPermissionsResult( int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_PHONE_STATE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();
                    //# get Token than OpenMainPage
                    CarpeDiemController.getInstance().RxGetToken(uuid, SplashActivity.this);
                } else {
                    Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                    showExplanation("Permission Needed", "Choose 'Allow' to start CarpeDiem !", Manifest.permission.READ_PHONE_STATE, REQUEST_PERMISSION_PHONE_STATE);
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

//    private void openMainPage(){
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//                startActivity(intent);
//                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
////                finish();
//            }
//        },800);
//    }
}
