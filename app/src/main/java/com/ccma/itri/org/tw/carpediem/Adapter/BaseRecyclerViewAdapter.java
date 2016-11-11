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
import com.ccma.itri.org.tw.carpediem.Dialog.BackpackDialog;
import com.ccma.itri.org.tw.carpediem.EventObject.BackpackItem;
import com.ccma.itri.org.tw.carpediem.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by A40503 on 2016/11/10.
 */

public class BaseRecyclerViewAdapter extends RecyclerView.Adapter<BaseRecyclerViewAdapter.myViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private String[] mTitles;
    private List<BackpackItem> backpackItemList;

    public static class myViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_main, ll_reward;
        ImageButton imageButton;
        TextView title, content, deadline, dayleft;
        ImageView type, logo;

        myViewHolder(View view) {
            super(view);
            title = (TextView)view.findViewById(R.id.txt_title_backppack2);
            dayleft = (TextView)view.findViewById(R.id.txt_reward_dayleft_backpack2);
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

    @Override
    public BaseRecyclerViewAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("BaseRecyclerViewAdapter","onCreateViewHolder");

        // create a new view
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_backpack, parent, false);
        View view = mLayoutInflater.inflate(R.layout.item_backpack2, parent, false);

        myViewHolder viewHolder = new myViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, final int position) {
        final BackpackItem item = CarpeDiemController.getInstance().Items.get(position);
        int nums = (int)(Math.random()*9);
        Picasso.with(mContext).load(getNumImage(nums)).into(holder.logo);
        holder.dayleft.setText(item.getDayLeft());
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



}
