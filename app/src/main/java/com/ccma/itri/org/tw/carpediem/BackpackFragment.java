package com.ccma.itri.org.tw.carpediem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ccma.itri.org.tw.carpediem.Adapter.BackpackItemAdapter;
import com.ccma.itri.org.tw.carpediem.Adapter.TimeEventAdapter;
import com.ccma.itri.org.tw.carpediem.EventObject.BackpackItem;


import java.util.List;

/**
 * Created by A40503 on 2016/11/2.
 */

public class BackpackFragment extends Fragment {
    private ListView listItem;
    private List<BackpackItem> BackpackItemList;
    private BackpackItemAdapter mBackpackIteAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("BackpackFragment","onCreateView");
        View view = inflater.inflate(R.layout.fragment_backpack, container, false);
        BackpackItemList = CarpeDiemController.getInstance().getBackPackItems();
        listItem = (ListView)view.findViewById(R.id.lv_backpack);
        Log.d("BackpackFragment","new BackpackItemAdapter");
        mBackpackIteAdapter = new BackpackItemAdapter(getActivity());
        Log.d("BackpackFragment","setAdapter");
        listItem.setAdapter(mBackpackIteAdapter);
        Log.d("BackpackFragment","timeEventList");
        mBackpackIteAdapter.setItems(BackpackItemList);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("BackpackFragment","onPause");
        mBackpackIteAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("BackpackFragment","onResume");
        mBackpackIteAdapter.notifyDataSetChanged();
    }
}
