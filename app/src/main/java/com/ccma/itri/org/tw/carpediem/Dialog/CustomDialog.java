package com.ccma.itri.org.tw.carpediem.Dialog;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import com.ccma.itri.org.tw.carpediem.R;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.flyco.animation.Attention.Swing;
import com.flyco.animation.FadeEnter.FadeEnter;
import com.flyco.animation.Jelly;
import com.flyco.animation.NewsPaperEnter;
import com.flyco.dialog.utils.CornerUtils;
import com.flyco.dialog.widget.base.BaseDialog;

/**
 * Created by A40503 on 2016/10/28.
 */

public class CustomDialog extends BaseDialog<CustomDialog> {
    private int mImage = R.drawable.pika2;
    private ImageView imageView;
    public CustomDialog(Context context) {
        super(context);
    }
    public void setImage(int image){
        mImage = image;
    }
        @Override
    public View onCreateView() {
        widthScale(0.85f);
        showAnim(new NewsPaperEnter());
        View inflate = View.inflate(mContext, R.layout.dialog_test, null);

        inflate.setBackgroundDrawable(CornerUtils.cornerDrawable(Color.parseColor("#ffffff"), dp2px(5)));

        imageView = (ImageView)inflate.findViewById(R.id.img_dialog);
        imageView.setImageResource(mImage);

//        YoYo.with(Techniques.Flash)
//            .duration(1500)
//            .playOn(imageView);



        return inflate;
    }

    @Override
    public void setUiBeforShow() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
