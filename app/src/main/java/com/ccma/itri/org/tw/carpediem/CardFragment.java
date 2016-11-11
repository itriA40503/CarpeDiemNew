package com.ccma.itri.org.tw.carpediem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.support.v4.app.Fragment;
import com.ccma.itri.org.tw.carpediem.Adapter.TimeEventAdapter;
import com.ccma.itri.org.tw.carpediem.CallApi.ApiController;
import com.ccma.itri.org.tw.carpediem.CallApi.ApiObject.CarpeDiemEventObject;
import com.ccma.itri.org.tw.carpediem.CallApi.ApiObject.EventLists;
import com.ccma.itri.org.tw.carpediem.CallApi.ApiObject.UserEventList;
import com.ccma.itri.org.tw.carpediem.EventObject.RewardItem;
import com.ccma.itri.org.tw.carpediem.EventObject.TimeEvent;
import com.ccma.itri.org.tw.carpediem.UserData.UserData;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by A40503 on 2016/9/21.
 */
public class CardFragment extends Fragment {

    private ListView listEvent;
    private List<TimeEvent> timeEventList;
    private TimeEventAdapter mTimeEventAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("CardFragment","onCreateView");
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refresh_main_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                getEventList();
            }
        });

        timeEventList = CarpeDiemController.getInstance().getTimeEvents();
        listEvent = (ListView)view.findViewById(R.id.lv_time_event);
        Log.d("CardFragment", "new TimeEventAdapter");
        mTimeEventAdapter = new TimeEventAdapter(getActivity());
        Log.d("CardFragment","setAdapter");
        listEvent.setAdapter(mTimeEventAdapter);
        Log.d("CardFragment","timeEventList");
        mTimeEventAdapter.setItems(timeEventList);



        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("CardFragment","onPause");
        mTimeEventAdapter.notifyDataSetChanged();
//        listEvent.setAdapter(mTimeEventAdapter);
//        mTimeEventAdapter.setItems(timeEventList);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("CardFragment","onResume");
        mTimeEventAdapter.notifyDataSetChanged();
//        listEvent.setAdapter(mTimeEventAdapter);
//        mTimeEventAdapter.setItems(timeEventList);
    }

    private void getEventList(){
        ApiController.getInstance().getNewEventListWithLoc(UserData.getInstance().getUserToken(),"121.0450396" , "24.774296") //# mos
//        ApiController.getInstance().getNewEventList(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<EventLists>() {
                    @Override
                    public void onCompleted() {
                        mTimeEventAdapter.clearItems();
                        mTimeEventAdapter.setItems(CarpeDiemController.getInstance().getTimeEvents());
//                        mTimeEventAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        CarpeDiemEventObject ObjectFromGson;
                        if (e instanceof HttpException) {
                            // We had non-2XX http error
                            Log.d("RxGetNewEventList","HttpException");

                            String errorBody = null;
                            try {
                                errorBody = ((HttpException) e).response().errorBody().string();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            Gson gson = new Gson();
                            ObjectFromGson = gson.fromJson(errorBody,CarpeDiemEventObject.class);
                        }
                        if (e instanceof IOException) {
                            Log.d("RxGetNewEventList","IOException");
                            Log.d("RxGetNewEventList",e.toString());
//                            Log.d("startEvent","OK");
                            // A network or conversion error happened
                        }

//                        Log.d("RxGetEventList","CODE : "+ObjectFromGson.getCode());
                        Log.d("RxGetEventList","onERROR ");
                        ObjectFromGson = null;
                    }

                    @Override
                    public void onNext(EventLists carpeDiemListEventObject) {
                        Log.d("RxGetEventList","SIZE : "+Integer.toString(carpeDiemListEventObject.userEventList.size()));
                        CarpeDiemController.getInstance().eventNum = carpeDiemListEventObject.userEventList.size();
//                        itemNum = carpeDiemListEventObject.eventList.size();
                        CarpeDiemController.getInstance().Events.clear();

                        Log.d("RxGetEventList","");

                        ArrayList<UserEventList> userEventLists = new ArrayList<UserEventList>(); //# for mapping

                        for(UserEventList event : carpeDiemListEventObject.userEventList){
//                            Log.d("RxGetEventList",event.getItemContents());
                            userEventLists.add(new UserEventList(event.getId(), event.getEventId()));
                        }

                        for(CarpeDiemEventObject event : carpeDiemListEventObject.eventList){
                            String eventName = event.getName();
                            CarpeDiemEventObject.item item = event.item;
                            String time = event.getCreatedAt();
                            String id = null;

                            RewardItem rewardItem;
                            rewardItem =new RewardItem(item.getItemId(), item.getTypeId(), item.getName(), item.getItemDesc());
                            Log.d("rewardItem",item.getItemId()+","+item.getTypeId()+","+item.getName()+","+item.getItemDesc());
                            for(UserEventList _event : userEventLists){
                                if(event.getEventId().equals(_event.getEventId())){
                                    id = _event.getId();
                                }
                            }
//                            Events.add(new TimeEvent(id, id+":"+eventName, time, event.getDescription() ,Long.parseLong(event.getTimeRequire())*250, false, rewardItem));
                            CarpeDiemController.getInstance().Events.add(new TimeEvent(id, eventName, time, event.getDescription() ,5*1000, false, rewardItem));
//                            Events.add(new TimeEvent(event.getId(), event.getId(), event.getCompletedTimes()*1000, true));
                        }
                    }
                });
    }
}
