package com.ccma.itri.org.tw.carpediem.Scanner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.Slide;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import me.dm7.barcodescanner.core.IViewFinder;
import me.dm7.barcodescanner.core.ViewFinderView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import com.ccma.itri.org.tw.carpediem.CallApi.ApiController;
import com.ccma.itri.org.tw.carpediem.CallApi.ApiObject.CarpeDiemEventObject;
import com.ccma.itri.org.tw.carpediem.CallApi.ApiObject.EventLists;
import com.ccma.itri.org.tw.carpediem.CallApi.ApiObject.Item;
import com.ccma.itri.org.tw.carpediem.CallApi.ApiObject.NewUserEventList;
import com.ccma.itri.org.tw.carpediem.CallApi.ApiObject.UserEventList;
import com.ccma.itri.org.tw.carpediem.CarpeDiemController;
import com.ccma.itri.org.tw.carpediem.Dialog.CustomDialog;
import com.ccma.itri.org.tw.carpediem.Dialog.ScanDialog;
import com.ccma.itri.org.tw.carpediem.EventObject.RewardItem;
import com.ccma.itri.org.tw.carpediem.EventObject.TimeEvent;
import com.ccma.itri.org.tw.carpediem.R;
import com.ccma.itri.org.tw.carpediem.UserData.UserData;
import com.google.gson.Gson;
import com.google.zxing.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by A40503 on 2016/10/27.
 */

public class ScanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
//        setupWindowAnimations();
        setupToolbar();

        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        mScannerView = new ZXingScannerView(this) {
            @Override
            protected IViewFinder createViewFinderView(Context context) {
                return new CustomViewFinderView(context);
            }
        };
        contentFrame.addView(mScannerView);

    }

//    private void setupWindowAnimations() {
//        Fade fade = new Fade();
//        fade.setDuration(1000);
//        getWindow().setEnterTransition(fade);
//
//        Slide slide = new Slide();
//        slide.setDuration(1000);
//        getWindow().setReturnTransition(slide);
//    }

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    public void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.scan_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final ActionBar ab = getSupportActionBar();
        if(ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void handleResult(final Result rawResult) {
        Toast.makeText(this, "Contents = " + rawResult.getText() +
                ", Format = " + rawResult.getBarcodeFormat().toString(), Toast.LENGTH_SHORT).show();

        // Note:
        // * Wait 2 seconds to resume the preview.
        // * On older devices continuously stopping and resuming camera preview can result in freezing the app.
        // * I don't know why this is the case but I don't have the time to figure out.
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                mScannerView.resumeCameraPreview(ScanActivity.this);
                getEventList("1");
//                CarpeDiemController.getInstance().settingDummy();
                ScanDialog cDialog = new ScanDialog(mScannerView.getContext());
                cDialog.show();
                cDialog.setCanceledOnTouchOutside(true);
                mScannerView.resumeCameraPreview(ScanActivity.this);
            }
        }, 1000);
    }

    private static class CustomViewFinderView extends ViewFinderView {
        public static final String TRADE_MARK_TEXT = "CarpeDiem";
        public static final int TRADE_MARK_TEXT_SIZE_SP = 40;
        public final Paint PAINT = new Paint();

        public CustomViewFinderView(Context context) {
            super(context);
            init();
        }

        public CustomViewFinderView(Context context, AttributeSet attrs) {
            super(context, attrs);
            init();
        }

        private void init() {
            PAINT.setColor(Color.WHITE);
            PAINT.setAntiAlias(true);
            float textPixelSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                    TRADE_MARK_TEXT_SIZE_SP, getResources().getDisplayMetrics());
            PAINT.setTextSize(textPixelSize);
            setSquareViewFinder(true);
        }

        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            drawTradeMark(canvas);
        }

        private void drawTradeMark(Canvas canvas) {
            Rect framingRect = getFramingRect();
            float tradeMarkTop;
            float tradeMarkLeft;
            if (framingRect != null) {
                tradeMarkTop = framingRect.bottom + PAINT.getTextSize() + 10;
                tradeMarkLeft = framingRect.left;
            } else {
                tradeMarkTop = 10;
                tradeMarkLeft = canvas.getHeight() - PAINT.getTextSize() - 10;
            }
            canvas.drawText(TRADE_MARK_TEXT, tradeMarkLeft, tradeMarkTop, PAINT);
        }
    }

    private void getEventList(String beaconId){
//        ApiController.getInstance().getNewEventListWithLoc(UserData.getInstance().getUserToken(),"121.0450396" , "24.774296") //# mos
//        ApiController.getInstance().getNewEventList(token)
        ApiController.getInstance().getNewEventListWithBeaconId(UserData.getInstance().getUserToken(), beaconId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<EventLists>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        CarpeDiemEventObject ObjectFromGson;
                        if (e instanceof HttpException) {
                            // We had non-2XX http error
                            Log.d("EventListWithBeaconId","HttpException");

                            String errorBody = null;
                            try {
                                errorBody = ((HttpException) e).response().errorBody().string();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            Gson gson = new Gson();
                            ObjectFromGson = gson.fromJson(errorBody,CarpeDiemEventObject.class);
                        }
                        if (e instanceof IOException) {
                            Log.d("EventListWithBeaconId","IOException");
                            Log.d("EventListWithBeaconId",e.toString());
//                            Log.d("startEvent","OK");
                            // A network or conversion error happened
                        }

//                        Log.d("RxGetEventList","CODE : "+ObjectFromGson.getCode());
                        Log.d("EventListWithBeaconId","onERROR ");
                        ObjectFromGson = null;
                    }

                    @Override
                    public void onNext(EventLists carpeDiemListEventObject) {
                        Log.d("RxGetEventList","SIZE : "+Integer.toString(carpeDiemListEventObject.userEventList.size()));
                        CarpeDiemController.getInstance().eventNum = carpeDiemListEventObject.userEventList.size();
//                        itemNum = carpeDiemListEventObject.eventList.size();
                        CarpeDiemController.getInstance().Events.clear();

                        Log.d("RxGetEventList","");
                        for (NewUserEventList eventList : carpeDiemListEventObject.userEventList){
                            NewUserEventList.Event event = eventList.getEvent();
                            Item item = event.getItem();
                            RewardItem rewardItem;
                            rewardItem =new RewardItem(item.getId(), item.getTypeId(), item.getName(), item.getDescription(), item.getAdvertiser());
//                            CarpeDiemController.getInstance().Events.add(new TimeEvent(eventList.getId(), event.getName(), event.getCreatedAt(), event.getDescription() ,5*1000, false, rewardItem));
                            CarpeDiemController.getInstance().Events.add(new TimeEvent(eventList , false, rewardItem));
                        }

//                        ArrayList<UserEventList> userEventLists = new ArrayList<UserEventList>(); //# for mapping
//
//                        for(UserEventList event : carpeDiemListEventObject.userEventList){
////                            Log.d("RxGetEventList",event.getItemContents());
//                            userEventLists.add(new UserEventList(event.getId(), event.getEventId()));
//                        }
//
//                        for(CarpeDiemEventObject event : carpeDiemListEventObject.eventList){
//                            String eventName = event.getName();
//                            CarpeDiemEventObject.item item = event.getItem();
//                            String time = event.getCreatedAt();
//                            String id = null;
//
//                            RewardItem rewardItem;
//                            rewardItem =new RewardItem(item.getItemId(), item.getTypeId(), item.getName(), item.getItemDesc(), item.getAdvertiserId());
//                            Log.d("rewardItem",item.getItemId()+","+item.getTypeId()+","+item.getName()+","+item.getItemDesc());
//                            for(UserEventList _event : userEventLists){
//                                if(event.getEventId().equals(_event.getEventId())){
//                                    id = _event.getId();
//                                }
//                            }
////                            CarpeDiemController.getInstance().storeName = event.getAdvertiserId();
////                            Events.add(new TimeEvent(id, id+":"+eventName, time, event.getDescription() ,Long.parseLong(event.getTimeRequire())*250, false, rewardItem));
//                            CarpeDiemController.getInstance().Events.add(new TimeEvent(id, eventName, time, event.getDescription() ,5*1000, false, rewardItem));
////                            Events.add(new TimeEvent(event.getId(), event.getId(), event.getCompletedTimes()*1000, true));
//                        }
                    }
                });
    }
}
