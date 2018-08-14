package com.cj.couponlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

abstract public class BaseListViewAdapter<T> extends BaseAdapter {

    protected Context mContext = null;
    private ArrayList<T> mItemList = new ArrayList<>();

    BaseListViewAdapter(Context context) {
        mContext = context;
    }

    // reset and addItems
    public void setItems(List<? extends T> itemList) {
        mItemList.clear();
        addItems(itemList);
    }

    public void reset() {
        mItemList.clear();
        notifyDataSetChanged();
    }

    public void addItems(List<? extends T> itemList) {
        if (CollectionUtils.isNotEmpty(itemList)) {
            mItemList.addAll(itemList);
            notifyDataSetChanged();
        }
    }

    @NonNull
    public List<T> getItems() {
        return mItemList;
    }

    @Override
    public int getCount() {
        return mItemList.size();
    }

    @Override
    public T getItem(int position) {
        if ((position < getCount()) && (position >= 0)) {
            return mItemList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    abstract public View getView(int position, View convertView, ViewGroup parent);

}
