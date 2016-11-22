package com.ccma.itri.org.tw.carpediem.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ccma.itri.org.tw.carpediem.CarpeDiemController;
import com.ccma.itri.org.tw.carpediem.Dialog.RewardDialog;
import com.ccma.itri.org.tw.carpediem.EventObject.TimeEvent;
import com.ccma.itri.org.tw.carpediem.R;
import com.ccma.itri.org.tw.carpediem.Timer.CountDownTimerWithPause;
import com.ccma.itri.org.tw.carpediem.UserData.UserData;
import com.dinuscxj.progressbar.CircleProgressBar;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.squareup.picasso.Picasso;

import java.util.List;

import co.mobiwise.library.ProgressLayout;
import rx.Observable;
import rx.Subscriber;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

/**
 * Created by A40503 on 2016/11/14.
 */

public class TimeEventRViewAdapter extends RecyclerView.Adapter<TimeEventRViewAdapter.myTimeEventViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private Activity mActcivity;
    private List<TimeEvent> timeEvents;

    public TimeEventRViewAdapter(Context context) {
//        mTitles = context.getResources().getStringArray(R.array.titles);
        Log.d("TimeEventRViewAdapter","new");
        timeEvents = CarpeDiemController.getInstance().getTimeEvents();
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public TimeEventRViewAdapter(Activity activity, Context context) {
//        mTitles = context.getResources().getStringArray(R.array.titles);
        Log.d("TimeEventRViewAdapter","new");
        timeEvents = CarpeDiemController.getInstance().getTimeEvents();
        mContext = context;
        mActcivity = activity;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public static class myTimeEventViewHolder extends RecyclerView.ViewHolder{
        LinearLayout layoutStart,layoutContent, layoutReward, layoutwhole;
        RelativeLayout layout;
        //        ProgressBar pTimeleft;
        TextView title, timeLeft, date, despcrip;
        ImageView imgLogo, imgType;
        ImageButton imgBtnStart, imgbtnReward;
        ProgressLayout progressLayout;
        CircleProgressBar circleProgressBar;
        private boolean descripClick = false;
        myTimeEventViewHolder(View view) {
            super(view);

            progressLayout = (ProgressLayout)view.findViewById(R.id.progressLayout);
            layoutwhole = (LinearLayout)view.findViewById(R.id.ll_content_event);
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
        showCase(viewHolder);
//        CarpeDiemController.getInstance().mainShowSeq.start();
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

        String desc = "";
        //# for DEMO ###
        switch (position){
            case 0:
                desc = "道館家的子女只會使用家族擅長的神奇寶貝，他們也應作為訓練師得到全面培養，何況也有想做別的工作的小孩。\n" +
                        "但是由於小地方的道館營運並不佳，就算想去做別的事也是心有餘力而不足。萬一首領連敗四場，又沒有後繼者的話，那長年經營的道館就不得不轉讓給別人。為了避免這一情況發生，就必須培養很多後代，這也使他們的生活更為艱苦。\n" +
                        "有些地方的道館只是虛名，一邊經營道館、一邊從事其他職業的首領也不在少數。還有一些人在三次敗北後，在第四戰時會故意買通挑戰者以保持其身分，雖然是明顯不正當的行為，可是看他們慘不忍睹的經營情況，又不忍心告發。";
                break;
            case 1:
                desc = "我家是間水系道館，一個月會來兩三位挑戰者。首領的爸爸，有時候贏，有時候輸。贏的時候，吃壽喜燒，輸的時候，吃鯉魚王鱗片熬的汁煮出來的粥。\n" +
                        "我不討厭鯉魚王，但我不喜歡鯉魚王的粥。輸的時候爸爸，會把鯉魚王的鰭放進酒裡喝，然後像兇惡神奇寶貝暴鯉龍一樣發狂。\n" +
                        "第二天，輸的消息會傳到學校，「你的道館就是鯉魚王…沒有用的鯉魚王」被同學欺負。其它的日子，回家後，爸爸會，不是欺負我，是嚴格訓練我。\n" +
                        "我從三歲開始，一直在做，成為首領的訓練。在水裡，可以三分鐘，不用呼吸。我，成為首領後，每月都想吃，兩三回壽喜燒。";
                break;
            case 2:
                desc = "神奇寶貝約於200萬年前誕生\n" +
                        "這是發生在某個夜晚的事，黑暗中突然出現了細微的光芒…。「碰…」發出輕微破裂的聲響，然後，一個生物誕生了…\n" +
                        "那一夜它們就以現在我們在圖鑑上見到的模樣，降臨到這世上。之後，這個生物被稱為“神奇寶貝”。\n" +
                        "它們並不是一個種族，是神奇寶貝又不是神奇寶貝。偏離進化樹無法說明的生物，統稱為“神奇寶貝”，在我國，它們又被稱為攜帶獸。";
                break;
            default:
                desc = event.getDescripition();
                break;
        }
        holder.despcrip.setText(desc);

        int progressLeft = event.getTimer().progressLeft();
        Log.d("progressLeft",progressLeft+"");
        if(progressLeft == 0 ){
            holder.progressLayout.setVisibility(View.GONE);
            holder.layoutwhole.setBackgroundColor(mContext.getResources().getColor(R.color.orangelight2_transparent));
        }else if(progressLeft != 100 && progressLeft > 0){
            holder.progressLayout.setVisibility(View.VISIBLE);
            holder.progressLayout.setCurrentProgress(100 - progressLeft);
            int width = holder.layoutwhole.getWidth();
            int height = holder.layoutwhole.getHeight();

            if (width == 0){
                width = 1050;
                height = 252;
            }
            Log.d("progressLeft",width+","+height+"|"+progressLeft);
            holder.progressLayout.setLayoutParams(new RelativeLayout.LayoutParams(width,height));
        }
        holder.circleProgressBar.setProgress(100 - progressLeft);

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

    private void showCase(final myTimeEventViewHolder viewHolder){
//        Log.d("showCase", CarpeDiemController.getInstance().showCase+"");
//        if(!CarpeDiemController.getInstance().showCase){
//            CarpeDiemController.getInstance().showCase = true;
        if(!UserData.getInstance().checkShowCaseEvent()){
            final ShowcaseView ShowCase =  new ShowcaseView.Builder(mActcivity)
                    .withMaterialShowcase()
                    .setStyle(R.style.CustomShowcaseTheme2)
                    .setTarget(new ViewTarget(viewHolder.title))
//                    .hideOnTouchOutside()
                    .setContentTitle("Event")
                    .setContentText("This is Event, that you can got some bonus!")
                    .build();
            ShowCase.setButtonText("Next");
            ShowCase.overrideButtonClick(new View.OnClickListener() {
                int count = 0;
                @Override
                public void onClick(View v) {
                    count++;
                    switch (count) {
                        case 1:
//                            ShowCase.setTarget(new ViewTarget(viewHolder.timeLeft));
                            ShowCase.setShowcase(new ViewTarget(viewHolder.timeLeft), true);
                            ShowCase.setContentTitle("TimeLeft");
                            ShowCase.setContentText("This is times of you need put down phone.");
                            ShowCase.setButtonText("next");
                            break;

                        case 2:
//                            ShowCase.setTarget(new ViewTarget(viewHolder.imgBtnStart));
                            ShowCase.setShowcase(new ViewTarget(viewHolder.imgBtnStart), true);
                            ShowCase.setContentTitle(("Challenge"));
                            ShowCase.setContentText("Click to Challenge!!");
                            ShowCase.setButtonText("finish");
//                            ShowCase.hide();
                            break;
                        case 3:
                            ShowCase.hide();
                            break;
                    }
                }
            });


//            new ShowcaseView.Builder(mActcivity)
//                    .withMaterialShowcase()
//                    .setStyle(R.style.CustomShowcaseTheme2)
//                    .setTarget(new ViewTarget(viewHolder.timeLeft))
//                    .hideOnTouchOutside()
//                    .setContentTitle("TimeLeft")
//                    .setContentText("This is times of you need put down phone.")
//                    .build();
//
//            new ShowcaseView.Builder(mActcivity)
//                    .withMaterialShowcase()
//                    .setStyle(R.style.CustomShowcaseTheme2)
//                    .setTarget(new ViewTarget(viewHolder.imgBtnStart))
//                    .hideOnTouchOutside()
//                    .setContentTitle("Challenge")
//                    .setContentText("Click to Challenge!!")
//                    .build();


//             sequence example
//            ShowcaseConfig config = new ShowcaseConfig();
//            config.setDelay(500); // half second between each showcase view
//
//            MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(mActcivity, "TRY");
//            sequence.setConfig(config);
//
//            sequence.addSequenceItem(viewHolder.title,
//                    "This is Event, that you can got some bonus!", "GOT IT");
//            sequence.addSequenceItem(viewHolder.timeLeft,
//                    "This is times of you need put down phone.", "GOT IT");
//            sequence.addSequenceItem(viewHolder.imgBtnStart,
//                    "Click to Challenge!!", "GOT IT");
//            sequence.start();

//            CarpeDiemController.getInstance().mainShowSeq.addSequenceItem(viewHolder.imgBtnStart,
//                    "This is imgbtn one", "GOT IT");
//
//            CarpeDiemController.getInstance().mainShowSeq.addSequenceItem(viewHolder.title,
//                    "This is txt two", "GOT IT");


//            sequence.start();
        }

    }

}
