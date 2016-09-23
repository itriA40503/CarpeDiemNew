package com.ccma.itri.org.tw.carpediem.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by A40503 on 2016/9/21.
 */
public abstract class BaseArrayAdapter<T> extends BaseAdapter {

    protected ArrayList<T> mArrayList;
    protected int mResource;
    protected Context mContext;

    public BaseArrayAdapter(Context context) {
        mArrayList = new ArrayList<T>();
        mContext = context;
    }

    public BaseArrayAdapter(Context context, T[] objects) {
        mArrayList = new ArrayList<T>();
        mContext = context;
        setItems(objects);

    }

    public BaseArrayAdapter(Context context, Collection<T> objects) {
        mArrayList = new ArrayList<T>();
        mContext = context;
        setItems(objects);
    }

    public BaseArrayAdapter(Context context, int resource) {
        mArrayList = new ArrayList<T>();
        mContext = context;
        mResource = resource;
    }

    public BaseArrayAdapter(Context context, int resource, T[] objects) {
        mArrayList = new ArrayList<T>();
        mContext = context;
        mResource = resource;
        setItems(objects);

    }

    public BaseArrayAdapter(Context context, int resource, Collection<T> objects) {
        mArrayList = new ArrayList<T>();
        mContext = context;
        mResource = resource;
        setItems(objects);
    }

    public int getResource() {
        return mResource;
    }

    public Context getContext() {
        return mContext;
    }

    public void setItems ( T[] objects ) {
        mArrayList.clear();
        mArrayList.addAll(Arrays.asList(objects));
        notifyDataSetChanged();
    }

    public void setItems ( Collection<T> objects ){
        mArrayList.clear();
        mArrayList.addAll(objects);
        notifyDataSetChanged();
    }

    public void setItems (ArrayList<T> objects) {
        mArrayList = objects;
        notifyDataSetChanged();
    }


    public void removeItem (int pos){
        mArrayList.remove(pos);
        notifyDataSetChanged();
    }

    public void addItem (T object){
        mArrayList.add(object);
        notifyDataSetChanged();
    }

    public void clearItems () {
        mArrayList.clear();
        notifyDataSetChanged();
    }

    public ArrayList<T> getItems() {
        return mArrayList;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public T getItem(int position) {
        return mArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        return null;
    }

}
