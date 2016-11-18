package com.ccma.itri.org.tw.carpediem.PagerFragment;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.ccma.itri.org.tw.carpediem.CarpeDiemController;
import com.ccma.itri.org.tw.carpediem.Dialog.CustomDialog;
import com.ccma.itri.org.tw.carpediem.R;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;

/**
 * Created by A40503 on 2016/10/25.
 */

public class FragmentPageTab1 extends Fragment {
    private ImageView imageView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_one, container, false);
        imageView = (ImageView)view.findViewById(R.id.img_tab1);
        imageView.setClickable(true);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionSheetDialog();
            }
        });
        return view;
    }
    private void ActionSheetDialog() {
        final String[] stringItems = {"1", "2", "3", "4"};
        final ActionSheetDialog dialog = new ActionSheetDialog(getActivity(), stringItems, null);
        dialog.title("This is\r\nNot iphone")//
                .titleTextSize_SP(14.5f)//
                .show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
//                CarpeDiemController.getInstance().showToast(stringItems[position]);
                CustomDialog cDialog = new CustomDialog(getActivity());

                switch (position){
                    case 0:
                        cDialog.setImage(R.drawable.pika3);
                        break;
                    case 1:
                        cDialog.setImage(R.drawable.pika5);
                        break;
                    case 2:
                        cDialog.setImage(R.drawable.pika6);
                        break;
                    case 3:
                        cDialog.setImage(R.drawable.pika7);
                        break;
                }
                cDialog.show();
                cDialog.setCanceledOnTouchOutside(true);
                dialog.dismiss();
            }
        });
    }
}
