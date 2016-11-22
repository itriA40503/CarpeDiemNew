package com.ccma.itri.org.tw.carpediem;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ccma.itri.org.tw.carpediem.Adapter.BackpackItemAdapter;
import com.ccma.itri.org.tw.carpediem.Adapter.BaseRecyclerViewAdapter;
import com.ccma.itri.org.tw.carpediem.CallApi.ApiController;
import com.ccma.itri.org.tw.carpediem.CallApi.ApiObject.Advertiser;
import com.ccma.itri.org.tw.carpediem.CallApi.ApiObject.ArrayUserItemList;
import com.ccma.itri.org.tw.carpediem.CallApi.ApiObject.CarpeDiemEventObject;
import com.ccma.itri.org.tw.carpediem.CallApi.ApiObject.NewUserItemList;
import com.ccma.itri.org.tw.carpediem.CallApi.ApiObject.UserItemList;
import com.ccma.itri.org.tw.carpediem.EventObject.BackpackItem;
import com.ccma.itri.org.tw.carpediem.UserData.UserData;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private BaseRecyclerViewAdapter adapter;
    private View view, emptylayout;
    private ImageView imgBackpack;
    private TextView backpackNum;
    private Button btnLeft,btnLast, btnOwner;
    private CardView backpackInfo;
    private int tmpDemo = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("BackpackFragment","onCreateView");
        view = inflater.inflate(R.layout.fragment_rv_backpack, container, false);
//        CarpeDiemController.getInstance().settingDummy();
        //# Setting BackpackItemList and sorting
        CarpeDiemController.getInstance().SorttingItem("left"); //# sortting dayleft
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
//        adapter = new BaseRecyclerViewAdapter(getActivity());
        adapter = new BaseRecyclerViewAdapter(getActivity(), getActivity());
        mRecyclerView.setAdapter(new ScaleInAnimationAdapter(adapter));
//        alphaAdapter.setDuration(1000);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

//        layoutManager.setSmoothScrollbarEnabled(true);
        mRecyclerView.setLayoutManager(layoutManager);
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        initComponents();
        backpackInfo();
        layoutSort();

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

    private void initComponents(){
        btnLeft = (Button)view.findViewById(R.id.btn_left);
        btnLast = (Button)view.findViewById(R.id.btn_last);
        btnOwner = (Button)view.findViewById(R.id.btn_owner);
        btnLeft.setBackground(getResources().getDrawable(R.drawable.buttonshape2));
        btnLast.setBackgroundColor(getResources().getColor(R.color.transparent));
        btnOwner.setBackgroundColor(getResources().getColor(R.color.transparent));
        btnLast.setTextColor(getResources().getColor(R.color.light_grey));
        btnOwner.setTextColor(getResources().getColor(R.color.light_grey));

        imgBackpack = (ImageView)view.findViewById(R.id.img_backpack_fill);

        backpackNum = (TextView)view.findViewById(R.id.txt_backpack_num);
    }

    private void layoutSort(){
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLeft.setBackground(getResources().getDrawable(R.drawable.buttonshape2));

                btnLast.setBackgroundColor(getResources().getColor(R.color.transparent));
                btnOwner.setBackgroundColor(getResources().getColor(R.color.transparent));
                btnLast.setTextColor(getResources().getColor(R.color.light_grey));
                btnOwner.setTextColor(getResources().getColor(R.color.light_grey));

                BackpackItemList = CarpeDiemController.getInstance().SorttingItem("left");
                adapter.notifyDataSetChanged();
            }
        });
        btnLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLast.setBackground(getResources().getDrawable(R.drawable.buttonshape2));

                btnLeft.setBackgroundColor(getResources().getColor(R.color.transparent));
                btnOwner.setBackgroundColor(getResources().getColor(R.color.transparent));
                btnLeft.setTextColor(getResources().getColor(R.color.light_grey));
                btnOwner.setTextColor(getResources().getColor(R.color.light_grey));

                BackpackItemList = CarpeDiemController.getInstance().SorttingItem("last");
                adapter.notifyDataSetChanged();
            }
        });

        btnOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnOwner.setBackground(getResources().getDrawable(R.drawable.buttonshape2));

                btnLeft.setBackgroundColor(getResources().getColor(R.color.transparent));
                btnLast.setBackgroundColor(getResources().getColor(R.color.transparent));
                btnLeft.setTextColor(getResources().getColor(R.color.light_grey));
                btnLast.setTextColor(getResources().getColor(R.color.light_grey));

                BackpackItemList = CarpeDiemController.getInstance().SorttingItem("owner");
                adapter.notifyDataSetChanged();

            }
        });

        imgBackpack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRecyclerView.scrollToPosition(0);
            }
        });
    }

    private void  backpackInfo(){
        int itemNum = CarpeDiemController.getInstance().getBackPackItems().size();
//        int itemNum = BackpackItemList.size();

        Log.d("backpackInfo",itemNum+"");
        if(itemNum == 0)Picasso.with(getContext()).load(R.drawable.backpack_5_0).into(imgBackpack);
        if(itemNum > 1)Picasso.with(getContext()).load(R.drawable.backpack_5_1).into(imgBackpack);
        if(itemNum > 11)Picasso.with(getContext()).load(R.drawable.backpack_5_2).into(imgBackpack);
        if(itemNum > 17)Picasso.with(getContext()).load(R.drawable.backpack_5_3).into(imgBackpack);
        if(itemNum > 23)Picasso.with(getContext()).load(R.drawable.backpack_5_4).into(imgBackpack);
        if(itemNum > 29)Picasso.with(getContext()).load(R.drawable.backpack_5_5).into(imgBackpack);


        backpackNum.setText(String.valueOf(itemNum));
    }

    private void getItemList(){
        ApiController.getInstance().mAPI.getItemList(UserData.getInstance().getUserToken())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ArrayUserItemList>() {
                    @Override
                    public void onCompleted() {
                        BackpackItemList = CarpeDiemController.getInstance().SorttingItem("left"); //# sortting dayleft
                        swipeRefreshLayout.setRefreshing(false);

                        //# for DEMO backpack
                        if(tmpDemo < BackpackItemList.size()){
                            tmpDemo = tmpDemo +5;
                        }else{
                            tmpDemo = BackpackItemList.size();
                        }

                        for(int i = BackpackItemList.size(); i > tmpDemo; i--){
                            Log.d("remove",""+i);
                            CarpeDiemController.getInstance().Items.remove(i-1);
//                            adapter.remove(i);
                        }
                        BackpackItemList = CarpeDiemController.getInstance().SorttingItem("left");
                        //########

                        backpackInfo();
                        adapter.notifyDataSetChanged();
                        checkEmpty();
                        initComponents();
                    }
                    @Override
                    public void onError(Throwable e) {
                        String errorBody = null;
                        Log.d("RxGetItemList",e.toString());
                        if (e instanceof HttpException){
                            try {
                                errorBody = ((HttpException) e).response().errorBody().string();
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            Gson gson = new Gson();
                            CarpeDiemEventObject ObjectFromGson = gson.fromJson(errorBody,CarpeDiemEventObject.class);
//                        Log.d("RxGetItemList","CODE : "+ObjectFromGson.getCode());
                            Log.d("RxGetItemList","onERROR HttpException");
                            ObjectFromGson = null;
                        }
                        if (e instanceof IOException){
                            Log.d("RxGetItemList","onERROR IOException");
                            Log.d("RxGetItemList",e.toString());
                        }

                    }
                    @Override
                    public void onNext(ArrayUserItemList userItemLists) {
                        Log.d("RxGetItemList","SIZE : "+Integer.toString(userItemLists.userItemList.size()));
                        CarpeDiemController.getInstance().itemNum = userItemLists.userItemList.size();
//                        Log.d("RxGetItemList","");
                        CarpeDiemController.getInstance().Items.clear();
                        for(NewUserItemList item : userItemLists.userItemList){
                            String createdAt = item.getCreatedAt();
                            String expiredAt = item.getExpiredAt();
//                            Advertiser advertiser = new Advertiser(item.getItem().getAdvertiser().getId(), item.getItem().getAdvertiser().getName());
//                            CarpeDiemController.getInstance().Items.add(new BackpackItem(item.getId(),item.getItem().getDescription(), createdAt, expiredAt, item.getItem().getAdvertiser()));
                            CarpeDiemController.getInstance().Items.add(new BackpackItem(item));
                        }
//                        CarpeDiemController.getInstance().settingDummy();

                    }
                });
    }

    private void checkEmpty(){
        emptylayout = (View)view.findViewById(R.id.empty_backpack);
        backpackInfo = (CardView)view.findViewById(R.id.card_backpack_info);
        if(BackpackItemList.size()==0){
            emptylayout.setVisibility(View.VISIBLE);
            backpackInfo.setVisibility(View.GONE);
        }else{
            emptylayout.setVisibility(View.GONE);
            backpackInfo.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("BackpackFragment","onPause");
        backpackInfo();
//        mBackpackIteAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("BackpackFragment","onResume");
        backpackInfo();
//        mBackpackIteAdapter.notifyDataSetChanged();
    }

}
