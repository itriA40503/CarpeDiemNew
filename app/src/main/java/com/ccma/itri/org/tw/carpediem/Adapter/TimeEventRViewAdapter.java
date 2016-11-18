package com.ccma.itri.org.tw.carpediem.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ccma.itri.org.tw.carpediem.CarpeDiemController;
import com.ccma.itri.org.tw.carpediem.Dialog.RewardDialog;
import com.ccma.itri.org.tw.carpediem.EventObject.TimeEvent;
import com.ccma.itri.org.tw.carpediem.R;
import com.ccma.itri.org.tw.carpediem.UserData.UserData;
import com.dinuscxj.progressbar.CircleProgressBar;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by A40503 on 2016/11/14.
 */

public class TimeEventRViewAdapter extends RecyclerView.Adapter<TimeEventRViewAdapter.myTimeEventViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private List<TimeEvent> timeEvents;

    public TimeEventRViewAdapter(Context context) {
//        mTitles = context.getResources().getStringArray(R.array.titles);
        Log.d("TimeEventRViewAdapter","new");
        timeEvents = CarpeDiemController.getInstance().getTimeEvents();
        mContext = context;

        mLayoutInflater = LayoutInflater.from(context);
    }

    public static class myTimeEventViewHolder extends RecyclerView.ViewHolder{
        LinearLayout layoutStart,layoutContent, layoutReward;
        //        ProgressBar pTimeleft;
        TextView title, timeLeft, date, despcrip;
        ImageView imgLogo, imgType;
        ImageButton imgBtnStart, imgbtnReward;

        CircleProgressBar circleProgressBar;
        private boolean descripClick = false;
        myTimeEventViewHolder(View view) {
            super(view);

            imgType = (ImageView)view.findViewById(R.id.img_type);
            imgLogo = (ImageView)view.findViewById(R.id.img_logo);
            layoutStart = (LinearLayout)view.findViewById(R.id.ll1);
            layoutReward = (LinearLayout)view.findViewById(R.id.ll2);
            layoutContent = (LinearLayout)view.findViewById(R.id.ll_event);
            title = (TextView)view.findViewById(R.id.txt_title);
            timeLeft = (TextView)view.findViewById(R.id.txt_timeleft);
            date = (TextView)view.findViewById(R.id.txt_eventdate);
            despcrip = (TextView)view.findViewById(R.id.txt_descrip);
            despcrip.setSingleLine();

            layoutContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!descripClick){
                        despcrip.setSingleLine(false);
                        descripClick = true;
                    }else {
                        despcrip.setSingleLine();
                        descripClick = false;
                    }

                }
            });

            circleProgressBar = (CircleProgressBar)view.findViewById(R.id.progressbar_circle);

            imgBtnStart = (ImageButton)view.findViewById(R.id.imgbtn_start);
            imgbtnReward = (ImageButton)view.findViewById(R.id.imgbtn_reward);

        }

        public void atRunning(){
//            timeLeft.setText(time);
            imgBtnStart.setVisibility(View.GONE);
            circleProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public myTimeEventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("TimeEventRViewAdapter","onCreateViewHolder");
        View view = mLayoutInflater.inflate(R.layout.item_time_event, parent, false);

        myTimeEventViewHolder viewHolder = new myTimeEventViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final myTimeEventViewHolder holder, int position) {
        final TimeEvent event = timeEvents.get(position);
        final int types = getTypeImage((int)(Math.random()*4));
        final int logo = LogoByNumber(event.mLogo);
        //# set icons
        Picasso.with(mContext).load(logo).into(holder.imgLogo);
        Picasso.with(mContext).load(types).into(holder.imgType);
//        holder.imgLogo.setImageResource(logo);
//        holder.imgType.setImageResource(types);
        //# set textview
        holder.title.setText(event.mEventName);
        holder.timeLeft.setText(event.getTimer().timeLeftString());
        holder.date.setText(event.getEventDate());
        holder.despcrip.setText(event.getDescripition());

        holder.circleProgressBar.setProgress(100- timeEvents.get(position).getTimer().progressLeft());

        holder.imgBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                holder.imgBtnStart.setImageResource(R.drawable.event_clock);
                Picasso.with(mContext).load(R.drawable.event_clock).into(holder.imgBtnStart);
                holder.imgBtnStart.setVisibility(View.GONE);
                holder.circleProgressBar.setVisibility(View.VISIBLE);
                event.startEvent();
                //# PUT event Start
                CarpeDiemController.getInstance().startEvent(event.mId, UserData.getInstance().getUserToken());
//                    getItem(position).getTimer().resume();
            }
        });
        holder.imgbtnReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RewardDialog cDialog = new RewardDialog(mContext);
                cDialog.settingReward(logo, types, event.getRewardItem());
                cDialog.show();
                cDialog.setCanceledOnTouchOutside(true);
            }
        });
        if(event.getStatus() == event.END){
            holder.layoutReward.setVisibility(View.VISIBLE);
            holder.layoutStart.setVisibility(View.GONE);
            //# PUT event complete
            CarpeDiemController.getInstance().completeEvent(event.mId, UserData.getInstance().getUserToken());
        }else if(event.getStatus() == event.RUNNING){
            holder.atRunning();
//            holder.imgBtnStart.setImageResource(R.drawable.event_clock);
        }
    }

    @Override
    public int getItemCount() {
        return timeEvents.size();
    }

    public void remove(int position) {
        notifyItemRemoved(position);
    }

    public void add(String text, int position) {
        notifyItemInserted(position);
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


}
