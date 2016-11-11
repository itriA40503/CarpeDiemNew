package com.ccma.itri.org.tw.carpediem.Dialog;

import android.content.Context;
import android.graphics.Color;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ccma.itri.org.tw.carpediem.EventObject.RewardItem;
import com.ccma.itri.org.tw.carpediem.R;
import com.flyco.animation.Attention.Swing;
import com.flyco.animation.FadeEnter.FadeEnter;
import com.flyco.animation.NewsPaperEnter;
import com.flyco.dialog.utils.CornerUtils;
import com.flyco.dialog.widget.base.BaseDialog;

/**
 * Created by A40503 on 2016/11/9.
 */

public class RewardDialog extends BaseDialog<RewardDialog> {
    private ImageView imgLogo, imgType;
    private TextView txtTitle, txtContent;
    private View layout;
    private View inflate;
    private int logo, type;
    private RewardItem rewardItem;
    public RewardDialog(Context context) {
        super(context);
    }
//
//    public RewardDialog(Context context, int imgLogo, int imgType, RewardItem reward) {
//        this(context);
//        logo = imgLogo;
//        type = imgType;
//        rewardItem = reward;
//    }

    @Override
    public View onCreateView() {
        Log.d("RewardDialog", "onCreateView");
        widthScale(0.75f);
        heightScale(0.75f);
        showAnim(new FadeEnter());
        View inflate = View.inflate(mContext, R.layout.dialog_reward, null);

        inflate.setBackgroundDrawable(
                CornerUtils.cornerDrawable(Color.parseColor("#ffffff"), dp2px(5)));

        layout = (View)inflate.findViewById(R.id.ll_dialog_reward);

        if(rewardItem!=null){
            imgLogo = (ImageView)inflate.findViewById(R.id.img_reward_logo);
            imgType = (ImageView)inflate.findViewById(R.id.img_reward_type);

            imgLogo.setImageResource(logo);
            imgType.setImageResource(type);

            txtTitle = (TextView)inflate.findViewById(R.id.txt_reward_title);
            txtContent = (TextView)inflate.findViewById(R.id.txt_reward_content);

            txtTitle.setText(rewardItem.getName());
            txtContent.setText(rewardItem.getDescription());
        }
//        imageView = (ImageView)inflate.findViewById(R.id.img_dialog);
//        imageView.setImageResource(mImage);

        return inflate;
    }

    public void settingReward(int imgLogo, int imgType, RewardItem reward){
        Log.d("RewardDialog", "settingReward");
        logo = imgLogo;
        type = imgType;
        rewardItem = reward;
    }

    @Override
    public void setUiBeforShow() {
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

//    public void set
}
