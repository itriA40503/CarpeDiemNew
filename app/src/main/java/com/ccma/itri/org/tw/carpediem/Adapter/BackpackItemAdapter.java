package com.ccma.itri.org.tw.carpediem.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ccma.itri.org.tw.carpediem.Dialog.BackpackDialog;
import com.ccma.itri.org.tw.carpediem.Dialog.RewardDialog;
import com.ccma.itri.org.tw.carpediem.EventObject.BackpackItem;

import com.ccma.itri.org.tw.carpediem.R;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by A40503 on 2016/11/2.
 */

public class BackpackItemAdapter extends BaseArrayAdapter<BackpackItem> {
    private Context mContext;
    public BackpackItemAdapter(Context context){
        super(context);
        mContext = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.d("BackpackItemAdapter", "getView:"+String.valueOf(position));
//        View view = convertView;
//        if(view == null){
//            LayoutInflater vi = LayoutInflater.from(getContext());
//            view = vi.inflate(R.layout.item_backpack, parent, false);
//        }
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        if (convertView == null) {
            view = inflater.inflate(R.layout.item_backpack, null);
        } else {
            view = convertView;
        }

        final BackpackItemHolder holder = new BackpackItemHolder(position, view);
        final BackpackItem item = getItem(position);

        int types = (int)(Math.random()*4);
        holder.type.setImageResource(getTypeImage(types));
        holder.logo.setImageResource(R.drawable.pika);
        holder.title.setText(item.mName);
        holder.content.setText(item.getCreatedAt()+"-"+item.getExpiredAt());

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BackpackItem",String.valueOf(position));
                BackpackDialog cDialog = new BackpackDialog(getContext());
                cDialog.settingReward(R.drawable.carpediem_logo, R.drawable.type_icon_discount, item);
                cDialog.show();
                cDialog.setCanceledOnTouchOutside(true);
//                holder.setVisi();
            }
        });

        return view;
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

    public class BackpackItemHolder {
        LinearLayout ll_main, ll_reward;
        ImageButton imageButton;
        TextView title, content, timeleft, dayleft;
        ImageView type, logo;

        public BackpackItemHolder(final int position, View view){
            ll_main = (LinearLayout)view.findViewById(R.id.ll1_backppack);
            ll_reward = (LinearLayout)view.findViewById(R.id.ll2_backppack);

            title = (TextView)view.findViewById(R.id.txt_title_backppack);
            content = (TextView)view.findViewById(R.id.txt_content_backppack);
            timeleft = (TextView)view.findViewById(R.id.txt_reward_timeleft);
            dayleft = (TextView)view.findViewById(R.id.txt_reward_dayleft);

            type = (ImageView)view.findViewById(R.id.img_type_backppack);
            logo = (ImageView)view.findViewById(R.id.img_logo_backppack);

            imageButton = (ImageButton)view.findViewById(R.id.imgbtn_get_backppack);

//            imageButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Log.d("BackpackItem",String.valueOf(position));
//                    ll_reward.setVisibility(View.VISIBLE);
//                    ll_main.setVisibility(View.GONE);
//                }
//            });

        }

        public void setVisi(){
            ll_reward.setVisibility(View.VISIBLE);
            ll_main.setVisibility(View.GONE);
        }


    }
}
