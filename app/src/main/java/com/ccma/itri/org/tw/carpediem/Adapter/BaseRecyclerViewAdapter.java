package com.ccma.itri.org.tw.carpediem.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
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
import com.ccma.itri.org.tw.carpediem.Dialog.BackpackDialog;
import com.ccma.itri.org.tw.carpediem.EventObject.BackpackItem;
import com.ccma.itri.org.tw.carpediem.R;
import com.ccma.itri.org.tw.carpediem.ShowCase.CustomShowCase;
import com.ccma.itri.org.tw.carpediem.Timer.CountDownTimerWithPause;
import com.ccma.itri.org.tw.carpediem.UserData.UserData;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

/**
 * Created by A40503 on 2016/11/10.
 */

public class BaseRecyclerViewAdapter extends RecyclerView.Adapter<BaseRecyclerViewAdapter.myViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private Activity mActcivity;
    private String[] mTitles;
    private List<BackpackItem> backpackItemList;
    private List<CountDownTimerWithPause> timers;

    public static class myViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_main, ll_reward;
        ImageButton imageButton;
        TextView title, deadline, dayleft, dayleft2;
        ImageView type, logo;
        CountDownTimerWithPause mTimer;

        myViewHolder(View view) {
            super(view);
            title = (TextView)view.findViewById(R.id.txt_title_backppack2);
            dayleft = (TextView)view.findViewById(R.id.txt_reward_dayleft_backpack2);
            dayleft2 = (TextView)view.findViewById(R.id.txt_reward_dayleft2_backpack2);
            deadline = (TextView)view.findViewById(R.id.txt_deadline_backpack2);
            type = (ImageView)view.findViewById(R.id.img_type_backppack2);
            logo = (ImageView)view.findViewById(R.id.img_logo_backppack2);

//            ll_main = (LinearLayout)view.findViewById(R.id.ll1_backppack);
//            ll_reward = (LinearLayout)view.findViewById(R.id.ll2_backppack);
//
//            title = (TextView)view.findViewById(R.id.txt_title_backppack);
//            content = (TextView)view.findViewById(R.id.txt_content_backppack);
//            timeleft = (TextView)view.findViewById(R.id.txt_reward_timeleft);
//            dayleft = (TextView)view.findViewById(R.id.txt_reward_dayleft);
//
//            type = (ImageView)view.findViewById(R.id.img_type_backppack);
//            logo = (ImageView)view.findViewById(R.id.img_logo_backppack);
//
//            imageButton = (ImageButton)view.findViewById(R.id.imgbtn_get_backppack);
        }

    }

    public BaseRecyclerViewAdapter(List<BackpackItem> backpackItems){
        Log.d("BaseRecyclerViewAdapter","new");
        backpackItemList = backpackItems;
    }


    public BaseRecyclerViewAdapter(Context context) {
//        mTitles = context.getResources().getStringArray(R.array.titles);
        Log.d("BaseRecyclerViewAdapter","new");
        backpackItemList = CarpeDiemController.getInstance().getBackPackItems();
        mContext = context;

//        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayoutInflater = LayoutInflater.from(context);
    }

    public BaseRecyclerViewAdapter(Activity activity, Context context) {
//        mTitles = context.getResources().getStringArray(R.array.titles);
        Log.d("BaseRecyclerViewAdapter","new");
        backpackItemList = CarpeDiemController.getInstance().getBackPackItems();
        mContext = context;
        mActcivity = activity;
//        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public BaseRecyclerViewAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("BaseRecyclerViewAdapter","onCreateViewHolder");

        // create a new view
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_backpack, parent, false);
        View view = mLayoutInflater.inflate(R.layout.item_backpack2, parent, false);

        myViewHolder viewHolder = new myViewHolder(view);
//        showCase(viewHolder);
//        timers = new ArrayList<CountDownTimerWithPause>();
//        for (BackpackItem item : backpackItemList){
//            CountDownTimerWithPause mTimer =new CountDownTimerWithPause(item.getleftTimes(), 1000) {
//                @Override
//                public void onTick(long millisUntilFinished) {
//                    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
//                    Date format = new Date(millisUntilFinished);
//                    viewHolder.dayleft.setText(sdf.format(format));
//                }
//
//                @Override
//                public void onFinish() {
//
//                }
//            }.create();
//
//            timers.add(mTimer);
//        }
        showCase(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final myViewHolder holder, final int position) {
        final BackpackItem item = CarpeDiemController.getInstance().Items.get(position);
        int nums = (int)(Math.random()*9);
        Picasso.with(mContext).load(getNumImage(nums)).into(holder.logo);
//        if(item.getUserItemList().getItem().getImage().getUrl()!=null){
//            Log.d("onBindViewHolder","URL img");
//            Picasso.with(mContext).load(item.getUserItemList().getItem().getImage().getUrl()).into(holder.logo);
//        }else {
//            Log.d("onBindViewHolder","NOT URL img");
//            Picasso.with(mContext).load(getNumImage(nums)).into(holder.logo);
//        }
        int leftday = item.getDayLeft();
        Log.d("onBindViewHolder",position+":"+leftday);
        if(leftday == 0 && leftday < 1){
//            if (!timers.get(position).isRunning()){
//                timers.get(position).resume();
//            }

            holder.dayleft.setText("TODAY LIMIT !");
            holder.dayleft.setTextSize(18);
            holder.dayleft2.setVisibility(View.GONE);
//            if(holder.mTimer == null){
//                holder.mTimer = new CountDownTimerWithPause(item.getleftTimes(),1000) {
//                    @Override
//                    public void onTick(long millisUntilFinished) {
//                        holder.dayleft.setText(dateFormat(millisUntilFinished));
//                    }
//
//                    @Override
//                    public void onFinish() {
//
//                    }
//                }.create();
//                holder.mTimer.resume();
//            }
            holder.dayleft.setTextColor(Color.RED);

//            holder.dayleft.setText(item.getTimer().timeLeft()+"");
        }else {
//            timers.get(position).pause();
            holder.dayleft.setText(String.valueOf(item.getDayLeft()));
            holder.dayleft2.setVisibility(View.VISIBLE);
            holder.dayleft.setTextSize(20);
            holder.dayleft.setTextColor(mContext.getResources().getColor(R.color.bluelight));
        }

        holder.deadline.setText(item.getExpiredAt());
//        holder.type.setImageResource(getTypeImage(types));
//        holder.logo.setImageResource(getNumImage(nums));
        holder.logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackpackDialog cDialog = new BackpackDialog(mContext);
                cDialog.settingReward(R.drawable.pika, R.drawable.type_icon_discount, item);
                cDialog.show();
                cDialog.setCanceledOnTouchOutside(true);
            }
        });
        holder.title.setText(item.mName);
//        holder.content.setText(item.getCreatedAt()+"-"+item.getExpiredAt());

//        holder.imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Log.d("BackpackItem",String.valueOf(position));
//                BackpackDialog cDialog = new BackpackDialog(mContext);
//                cDialog.settingReward(R.drawable.carpediem_logo, R.drawable.type_icon_discount, item);
//                cDialog.show();
//                cDialog.setCanceledOnTouchOutside(true);
////                holder.setVisi();
//            }
//        });
    }

    public void remove(int position) {
        notifyItemRemoved(position);
    }

    public void add(String text, int position) {
        notifyItemInserted(position);
    }

    private int getNumImage(int num){
        switch (num){
            case 0:
                return R.drawable.eevee1_dream;
            case 1:
                return R.drawable.eevee2_vaporeon_dream;
            case 2:
                return R.drawable.eevee3_jolteon_dream;
            case 3:
                return R.drawable.eevee4_flareon_dream;
            case 4:
                return R.drawable.eevee5_espeon_dream;
            case 5:
                return R.drawable.eevee6_umbreon_dream;
            case 6:
                return R.drawable.eevee7_leafeon_dream;
            case 7:
                return R.drawable.eevee8_glaceon_dream;
            case 8:
                return R.drawable.eevee9_sylveon_dream;
        }
        return R.drawable.pika;
    }

    @Override
    public int getItemCount() {
        return backpackItemList.size();
    }

    private String dateFormat(long date){

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        Date format = new Date(date);
        Log.d("getleftTimes",format+"   "+date+"    "+sdf.format(format));
        return sdf.format(format);
    }

    private void showCase(final myViewHolder viewHolder) {
        if(!UserData.getInstance().checkShowCaseItem()){
//        if(!CarpeDiemController.getInstance().showCaseItems) {
//            CarpeDiemController.getInstance().showCaseItems = true;
            final ShowcaseView ShowCase = new ShowcaseView.Builder(mActcivity)
                    .withHoloShowcase()
                    .setStyle(R.style.CustomShowcaseTheme2)
                    .setTarget(new ViewTarget(viewHolder.title))
//                    .hideOnTouchOutside()
                    .setContentTitle("Bonus")
                    .setContentText("This is bonus of store.")
                    .build();
            ShowCase.setButtonText("Next");
            ShowCase.overrideButtonClick(new View.OnClickListener() {
                int count = 1;

                @Override
                public void onClick(View v) {
                    count++;
                    switch (count) {
                        case 1:
//                            ShowCase.setTarget(new ViewTarget(viewHolder.deadline));
                            ShowCase.setShowcase(new ViewTarget(viewHolder.deadline), true);
                            ShowCase.setContentTitle("Deadline");
                            ShowCase.setContentText("This is deadline.");
                            ShowCase.setButtonText("Next");
                            break;

                        case 2:
//                            ShowCase.setTarget(new ViewTarget(viewHolder.logo));
                            ShowCase.setShowcase(new ViewTarget(viewHolder.logo), true);
                            ShowCase.setContentTitle(("Get bonus!"));
                            ShowCase.setContentText("Click image can get bonus !!");
                            ShowCase.setButtonText("Finish");
//                            ShowCase.hide();
                            break;
                        case 3:
                            ShowCase.hide();
                    }
                }
            });
        }
//        Log.d("showCaseItems", CarpeDiemController.getInstance().showCaseItems + "");
//        if (!CarpeDiemController.getInstance().showCaseItems) {
//            CarpeDiemController.getInstance().showCaseItems = true;
//
////             sequence example
//            ShowcaseConfig config = new ShowcaseConfig();
//            config.setDelay(500); // half second between each showcase view
//
//            MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(mActcivity, "TRY");
//            sequence.setConfig(config);
//
//            sequence.addSequenceItem(viewHolder.title,
//                    "This is bonus of store.", "GOT IT");
//            sequence.addSequenceItem(viewHolder.deadline,
//                    "This is deadline.", "GOT IT");
//            sequence.addSequenceItem(viewHolder.logo,
//                    "Tab image can get bonus !!", "GOT IT");
//            sequence.start();
//
////            sequence.start();
//        }
    }
}
