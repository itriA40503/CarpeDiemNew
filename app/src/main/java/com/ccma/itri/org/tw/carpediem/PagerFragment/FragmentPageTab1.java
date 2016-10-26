package com.ccma.itri.org.tw.carpediem.PagerFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ccma.itri.org.tw.carpediem.R;

/**
 * Created by A40503 on 2016/10/25.
 */

public class FragmentPageTab1 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_one, container, false);
        return view;
    }
}
