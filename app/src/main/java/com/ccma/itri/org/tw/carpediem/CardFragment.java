package com.ccma.itri.org.tw.carpediem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ccma.itri.org.tw.carpediem.Adapter.TimeEventAdapter;
import com.ccma.itri.org.tw.carpediem.EventObject.TimeEvent;

import java.util.List;

/**
 * Created by A40503 on 2016/9/21.
 */
public class CardFragment extends android.app.Fragment {

    private ListView listEvent;
    private List<TimeEvent> timeEventList;
    private TimeEventAdapter mTimeEventAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("CardFragment","onCreateView");
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        listEvent = (ListView)view.findViewById(R.id.lv_time_event);
        timeEventList = CarpeDiemController.getInstance().getTimeEvents();
        mTimeEventAdapter = new TimeEventAdapter(getActivity());
        listEvent.setAdapter(mTimeEventAdapter);
        mTimeEventAdapter.setItems(timeEventList);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("CardFragment","onPause");
//        listEvent.setAdapter(mTimeEventAdapter);
//        mTimeEventAdapter.setItems(timeEventList);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("CardFragment","onResume");
        listEvent.setAdapter(mTimeEventAdapter);
        mTimeEventAdapter.setItems(timeEventList);
    }
}
