package com.ccma.itri.org.tw.carpediem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ListView;

import com.ccma.itri.org.tw.carpediem.Adapter.BackpackItemAdapter;
import com.ccma.itri.org.tw.carpediem.Adapter.BaseRecyclerViewAdapter;
import com.ccma.itri.org.tw.carpediem.CallApi.ApiController;
import com.ccma.itri.org.tw.carpediem.CallApi.ApiObject.ArrayUserItemList;
import com.ccma.itri.org.tw.carpediem.CallApi.ApiObject.CarpeDiemEventObject;
import com.ccma.itri.org.tw.carpediem.CallApi.ApiObject.UserItemList;
import com.ccma.itri.org.tw.carpediem.EventObject.BackpackItem;
import com.ccma.itri.org.tw.carpediem.UserData.UserData;
import com.google.gson.Gson;


import java.io.IOException;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.ScaleInAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by A40503 on 2016/11/2.
 */

public class BackpackFragment extends Fragment {
    private ListView listItem;
    private List<BackpackItem> BackpackItemList;
    private BackpackItemAdapter mBackpackIteAdapter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private View view, emptylayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("BackpackFragment","onCreateView");
        view = inflater.inflate(R.layout.fragment_rv_backpack, container, false);
//        CarpeDiemController.getInstance().settingDummy();
        //# Setting BackpackItemList and sorting
        CarpeDiemController.getInstance().SorttingItem(); //# sortting dayleft
        BackpackItemList = CarpeDiemController.getInstance().getBackPackItems();

        checkEmpty();

        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getItemList();
            }
        });

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_backpack);

        Log.d("BackpackFragment","new adapter");
        BaseRecyclerViewAdapter adapter = new BaseRecyclerViewAdapter(getActivity());
        mRecyclerView.setAdapter(new ScaleInAnimationAdapter(adapter));
//        alphaAdapter.setDuration(1000);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

//        layoutManager.setSmoothScrollbarEnabled(true);
        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

//        BackpackItemList = CarpeDiemController.getInstance().getBackPackItems();
//        listItem = (ListView)view.findViewById(R.id.lv_backpack);
////        listItem.setHeaderDividersEnabled(false);
////        listItem.setFooterDividersEnabled(false);
//        Log.d("BackpackFragment","new BackpackItemAdapter");
//        mBackpackIteAdapter = new BackpackItemAdapter(getActivity());
//        Log.d("BackpackFragment","setAdapter");
//        listItem.setAdapter(mBackpackIteAdapter);
//        Log.d("BackpackFragment","timeEventList");
//        mBackpackIteAdapter.setItems(BackpackItemList);

        return view;
    }

    private void getItemList(){
        ApiController.getInstance().mAPI.getItemList(UserData.getInstance().getUserToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayUserItemList>() {
                    @Override
                    public void onCompleted() {
                        BackpackItemList = CarpeDiemController.getInstance().SorttingItem(); //# sortting dayleft
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    @Override
                    public void onError(Throwable e) {
                        String errorBody = null;
                        Log.d("RxGetItemList",e.toString());
                        try {
                            errorBody = ((HttpException) e).response().errorBody().string();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        Gson gson = new Gson();
                        CarpeDiemEventObject ObjectFromGson = gson.fromJson(errorBody,CarpeDiemEventObject.class);
//                        Log.d("RxGetItemList","CODE : "+ObjectFromGson.getCode());
                        Log.d("RxGetItemList","onERROR ");
                        ObjectFromGson = null;
                    }
                    @Override
                    public void onNext(ArrayUserItemList userItemLists) {
                        Log.d("RxGetItemList","SIZE : "+Integer.toString(userItemLists.userItemList.size()));
                        CarpeDiemController.getInstance().itemNum = userItemLists.userItemList.size();
                        Log.d("RxGetItemList","");
                        CarpeDiemController.getInstance().Items.clear();
                        for(UserItemList item : userItemLists.userItemList){
                            String createdAt = item.getCreatedAt();
                            String expiredAt = item.getExpiredAt();
                            CarpeDiemController.getInstance().Items.add(new BackpackItem(item.getId(),item.getItem().getItemDesc(), createdAt, expiredAt));
                        }
                        CarpeDiemController.getInstance().settingDummy();
                        checkEmpty();
                    }
                });
    }

    private void checkEmpty(){
        emptylayout = (View)view.findViewById(R.id.empty_backpack);
        if(BackpackItemList.size()==0){
            emptylayout.setVisibility(View.VISIBLE);
        }else{
            emptylayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("BackpackFragment","onPause");
//        mBackpackIteAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("BackpackFragment","onResume");
//        mBackpackIteAdapter.notifyDataSetChanged();
    }
}
