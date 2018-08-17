package com.qxb.student.common.view.recycler.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.LayoutRes;
import android.util.SparseArray;

import com.qxb.student.common.view.recycler.listener.MultiItemEntity;


/**
 * 多种布局适配
 * Created by winky on 2018/4/9.
 */

public abstract class MultiItemAdapter<T extends MultiItemEntity> extends QuickAdapter<T> {

    private SparseArray<Integer> layouts;

    public MultiItemAdapter() {
        super(null);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return layouts.get(viewType);
    }

    @SuppressLint("UseSparseArrays")
    public void addItemType(int type, @LayoutRes int layoutResId) {
        if (layouts == null) {
            layouts = new SparseArray<>();
        }
        layouts.put(type, layoutResId);
    }

    @Override
    public int getItemViewType(int position) {
        T item = getItem(position);
        if (item != null) {
            return item.getItemType();
        }
        return 0;
    }
}
