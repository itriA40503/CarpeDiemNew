package com.ccma.itri.org.tw.carpediem.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ccma.itri.org.tw.carpediem.EventObject.TimeEvent;
import com.ccma.itri.org.tw.carpediem.R;

/**
 * Created by A40503 on 2016/9/21.
 */
public class TimeEventAdapter extends BaseArrayAdapter<TimeEvent>{
    public TimeEventAdapter(Context context){
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("TimeEventAdapter", "getView");
        View view = convertView;
        if(view == null){
            LayoutInflater vi = LayoutInflater.from(getContext());
            view = vi.inflate(R.layout.item_time_event, parent, false);
        }

        TimeEventHolder holder = new TimeEventHolder(position, view);
        TimeEvent event = getItem(position);
        holder.title.setText(event.mEventName);
        holder.timeLeft.setText(event.getTimer().timeLeftString());
        holder.pTimeleft.setProgress(event.getTimer().progressLeft());
        if(event.getStatus() == event.END){
            holder.layoutReward.setVisibility(View.VISIBLE);
            holder.layoutStart.setVisibility(View.GONE);
        }else if(event.getStatus() == event.RUNNING){
            holder.imgBtnStart.setImageResource(R.drawable.clock);
        }
        return view;
    }

    public class TimeEventHolder{
        LinearLayout layoutStart, layoutReward;
        ProgressBar pTimeleft;
        TextView title, timeLeft;
        ImageButton imgBtnStart;
        public TimeEventHolder(final int position, View view){
            layoutStart = (LinearLayout)view.findViewById(R.id.ll1);
            layoutReward = (LinearLayout)view.findViewById(R.id.ll2);
            pTimeleft = (ProgressBar)view.findViewById(R.id.progressbar_timeleft);
            title = (TextView)view.findViewById(R.id.txt_title);
            timeLeft = (TextView)view.findViewById(R.id.txt_timeleft);
            pTimeleft.setProgress(getItem(position).getTimer().progressLeft());
            imgBtnStart = (ImageButton)view.findViewById(R.id.imgbtn_start);
            imgBtnStart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgBtnStart.setImageResource(R.drawable.clock);
                    getItem(position).startEvent();
//                    getItem(position).getTimer().resume();
                }
            });
        }

        public void atRunning(){
//            timeLeft.setText(time);

        }
    }
}
