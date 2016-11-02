package com.ccma.itri.org.tw.carpediem.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.ccma.itri.org.tw.carpediem.CallApi.ApiController;
import com.ccma.itri.org.tw.carpediem.CarpeDiemController;
import com.ccma.itri.org.tw.carpediem.EventObject.TimeEvent;
import com.ccma.itri.org.tw.carpediem.R;
import com.ccma.itri.org.tw.carpediem.UserData.UserData;

import java.util.Random;

/**
 * Created by A40503 on 2016/9/21.
 */
public class TimeEventAdapter extends BaseArrayAdapter<TimeEvent>{
    public TimeEventAdapter(Context context){
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("TimeEventAdapter", "getView:"+String.valueOf(position));
        View view = convertView;
        if(view == null){
            LayoutInflater vi = LayoutInflater.from(getContext());
            view = vi.inflate(R.layout.item_time_event, parent, false);
        }

        TimeEventHolder holder = new TimeEventHolder(position, view);
        TimeEvent event = getItem(position);

//        int [] logos = new int[]{R.drawable.pika3, R.drawable.pika, R.drawable.logo1, R.drawable.logo2, R.drawable.logo3, R.drawable.logo4, R.drawable.logo5, R.drawable.logo6};
//        int log = logos[event.mLogo];
        int types = (int)(Math.random()*4);
        holder.imgLogo.setImageResource(LogoByNumber(event.mLogo));
        holder.imgType.setImageResource(getTypeImage(types));
        holder.title.setText(event.mEventName);
        holder.timeLeft.setText(event.getTimer().timeLeftString());
        holder.pTimeleft.setProgress(event.getTimer().progressLeft());
        if(!event.mContinuous){
            holder.layoutStart.setBackgroundColor(Color.parseColor("#7abeff"));
        }
//        holder.pTimeleft.getProgressDrawable().setColorFilter(event.mPrgressBarColor, PorterDuff.Mode.MULTIPLY);

        if(event.getStatus() == event.END){
            holder.layoutReward.setVisibility(View.VISIBLE);
            holder.layoutStart.setVisibility(View.GONE);
            //# PUT event complete
            CarpeDiemController.getInstance().completeEvent(event.mId, UserData.getInstance().getUserToken());
        }else if(event.getStatus() == event.RUNNING){
            holder.imgBtnStart.setImageResource(R.drawable.event_clock);
        }
        return view;
    }

    private int setLogoByName(String name){
        Log.d("setLogoByName",name);
//        int [] logos = new int[]{R.drawable.logo1, R.drawable.logo2, R.drawable.logo3, R.drawable.logo4, R.drawable.logo5, R.drawable.logo6};
        int Logo;
        switch (name){
            case "猥瑣皮卡丘娃娃":
                Logo = R.drawable.pika3;
                break;
            case "皮卡丘娃娃":
                Logo = R.drawable.pika;
                break;
            default:
//                int length = logos.length;
                Logo = R.drawable.pika;
                break;
        }
        return Logo;
    }

    private int LogoByNumber(int num){
        int Logo;
        switch(num){
            case 0:
                Logo = R.drawable.pika3;
                break;
            case 1:
                Logo = R.drawable.pika;
                break;
            case 2:
                Logo = R.drawable.logo1;
                break;
            case 3:
                Logo = R.drawable.logo2;
                break;
            case 4:
                Logo = R.drawable.logo3;
                break;
            case 5:
                Logo = R.drawable.logo4;
                break;
            case 6:
                Logo = R.drawable.logo5;
                break;
            case 7:
                Logo = R.drawable.logo6;
                break;
            case 8:
                Logo = R.drawable.logo7;
                break;
            default:
                Logo = R.drawable.pika;
                break;
        }
        return Logo;
    }

    private int getTypeImage(int num){
        switch (num){
            case 0:
                return R.drawable.type_icon_birthday;
            case 1:
                return R.drawable.type_icon_discount;
            case 2:
                return R.drawable.type_icon_gift;
            case 3:
                return R.drawable.type_icon_one_plus_one;
        }
        return R.drawable.type1;
    }

    public class TimeEventHolder{
        LinearLayout layoutStart, layoutReward;
        ProgressBar pTimeleft;
        TextView title, timeLeft;
        ImageView imgLogo, imgType;
        ImageButton imgBtnStart;
        public TimeEventHolder(final int position, View view){
            imgType = (ImageView)view.findViewById(R.id.img_type);
            imgLogo = (ImageView)view.findViewById(R.id.img_logo);
            layoutStart = (LinearLayout)view.findViewById(R.id.ll1);
            layoutReward = (LinearLayout)view.findViewById(R.id.ll2);
            title = (TextView)view.findViewById(R.id.txt_title);
            timeLeft = (TextView)view.findViewById(R.id.txt_timeleft);

            pTimeleft = (ProgressBar)view.findViewById(R.id.progressbar_timeleft);
            pTimeleft.setProgress(getItem(position).getTimer().progressLeft());

            imgBtnStart = (ImageButton)view.findViewById(R.id.imgbtn_start);
            imgBtnStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgBtnStart.setImageResource(R.drawable.event_clock);
                    getItem(position).startEvent();
                    //# PUT event Start
                    Log.d("UserDataGetToken",UserData.getInstance().getUserToken());
                    CarpeDiemController.getInstance().startEvent(getItem(position).mId, UserData.getInstance().getUserToken());
//                    getItem(position).getTimer().resume();
                }
            });
        }

        public void atRunning(){
//            timeLeft.setText(time);

        }
    }
}
