package com.ccma.itri.org.tw.carpediem.Scanner;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ccma.itri.org.tw.carpediem.CallApi.ApiController;
import com.ccma.itri.org.tw.carpediem.CallApi.ApiObject.CarpeDiemEventObject;
import com.ccma.itri.org.tw.carpediem.CallApi.ApiObject.EventLists;
import com.ccma.itri.org.tw.carpediem.CallApi.ApiObject.Item;
import com.ccma.itri.org.tw.carpediem.CallApi.ApiObject.NewUserEventList;
import com.ccma.itri.org.tw.carpediem.CallApi.ApiObject.UserEventList;
import com.ccma.itri.org.tw.carpediem.CarpeDiemController;
import com.ccma.itri.org.tw.carpediem.Dialog.ScanDialog;
import com.ccma.itri.org.tw.carpediem.EventObject.RewardItem;
import com.ccma.itri.org.tw.carpediem.EventObject.TimeEvent;
import com.ccma.itri.org.tw.carpediem.R;
import com.ccma.itri.org.tw.carpediem.UserData.UserData;
import com.google.gson.Gson;
import com.google.zxing.Result;

import java.io.IOException;
import java.util.ArrayList;

import me.dm7.barcodescanner.core.IViewFinder;
import me.dm7.barcodescanner.core.ViewFinderView;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by A40503 on 2016/11/16.
 */

public class ScanFragment extends Fragment implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_scan, container, false);

        ViewGroup contentFrame = (ViewGroup) view.findViewById(R.id.content_frame);
        mScannerView = new ZXingScannerView(getContext()) {
            @Override
            protected IViewFinder createViewFinderView(Context context) {
                return new ScanFragment.CustomViewFinderView(context);
            }
        };
        contentFrame.addView(mScannerView);

        return view;
    }

    @Override
    public void handleResult(Result rawResult) {
        CarpeDiemController.getInstance().showToast("Contents = " + rawResult.getText() +
                ", Format = " + rawResult.getBarcodeFormat().toString());

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
                mScannerView.resumeCameraPreview(ScanFragment.this);
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

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
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
                            rewardItem =new RewardItem(item.getId(), item.getTypeId(), item.getName(), item.getDescription(), item.getAdvertiser().getId());
                            CarpeDiemController.getInstance().Events.add(new TimeEvent(event.getId(), event.getName(), event.getCreatedAt(), event.getDescription() ,5*1000, false, rewardItem));
                        }
                    }
                });
    }
}
