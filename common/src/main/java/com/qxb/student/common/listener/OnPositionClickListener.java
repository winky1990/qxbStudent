package com.qxb.student.common.listener;

import android.view.View;

/**
 * 主要作用在列表点击事件.注意列表数量有变化时position不会改变
 *
 * @author winky
 * @date 2018/5/6
 */
public abstract class OnPositionClickListener implements View.OnClickListener {

    private int position;

    public OnPositionClickListener(int position) {
        this.position = position;
    }

    @Override
    public void onClick(View v) {
        v.setClickable(false);
        onPositionClick(v, position);
        v.postDelayed(new TRunnable<View>(v) {
            @Override
            public void run(View view) {
                view.setClickable(true);
            }
        }, 500);
    }

    /**
     * 点击下标事件
     *
     * @param view     点击视图
     * @param position 点击视图在列表下标
     */
    public abstract void onPositionClick(View view, int position);
}
