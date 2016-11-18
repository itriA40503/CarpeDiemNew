package com.ccma.itri.org.tw.carpediem.Dialog;

import android.content.Context;
import android.graphics.Color;
import android.provider.ContactsContract;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ccma.itri.org.tw.carpediem.R;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.flyco.animation.FadeEnter.FadeEnter;
import com.flyco.dialog.widget.base.BaseDialog;

/**
 * Created by A40503 on 2016/11/15.
 */

public class ScanDialog extends BaseDialog<ScanDialog> {

    private CardView cardView;
    private TextView txt1, txt2;
    private ImageView img, img2;
//    private Context mContext;
    public ScanDialog(Context context) {
        super(context);
//        mContext = context;
    }

    @Override
    public View onCreateView() {
//        widthScale(0.85f);
        showAnim(new FadeEnter());
        View inflate = View.inflate(mContext, R.layout.dialog_scan, null);

        cardView = (CardView)inflate.findViewById(R.id.card_scan);

        txt1 = (TextView)inflate.findViewById(R.id.txt_scan1);
        txt2 = (TextView)inflate.findViewById(R.id.txt_scan2);

        img = (ImageView)inflate.findViewById(R.id.img_scan);
        img2 = (ImageView)inflate.findViewById(R.id.img_scan2) ;

        YoYo.with(Techniques.RollIn)
                .duration(1800)
                .playOn(txt1);
        YoYo.with(Techniques.ZoomInUp)
                .duration(2500)
                .playOn(txt2);
        YoYo.with(Techniques.SlideInLeft)
                .duration(1500)
                .playOn(img);
        YoYo.with(Techniques.SlideInRight)
                .duration(1500)
                .playOn(img2);

        return inflate;
    }

    @Override
    public void setUiBeforShow() {
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
