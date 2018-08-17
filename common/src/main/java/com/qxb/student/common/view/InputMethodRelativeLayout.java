package com.qxb.student.common.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Display;
import android.widget.RelativeLayout;

/**
 * created on: 2018/7/19 10:50
 * description:
 *
 * @author zjk9527
 */
public class InputMethodRelativeLayout extends RelativeLayout {
    private int width;
    protected OnSizeChangedListenner onSizeChangedListenner;
    private int height;
    private int NUM = 4;
    //屏幕高度
    private int screenHeight;

    public InputMethodRelativeLayout(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
        Display localDisplay = ((Activity) paramContext).getWindowManager()
                .getDefaultDisplay();
        this.screenHeight = localDisplay.getHeight();
    }

    public InputMethodRelativeLayout(Context paramContext,
                                     AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        this.width = widthMeasureSpec;
        this.height = heightMeasureSpec;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        //监听不为空、宽度不变、当前高度与历史高度不为0
        if ((this.onSizeChangedListenner != null) && (w == oldw) && (oldw != 0) && (oldh != 0)) {
            boolean sizeChanged = false;
            if ((h >= oldh) || (Math.abs(h - oldh) <= 1 * this.screenHeight / NUM)) {
                if ((h <= oldh) || (Math.abs(h - oldh) <= 1 * this.screenHeight / NUM)) {
                    return;
                }
                sizeChanged = false;
            } else {
                sizeChanged = true;
            }
            this.onSizeChangedListenner.onSizeChange(sizeChanged, oldh, h);
            measure(this.width - w + getWidth(), this.height - h + getHeight());
        }
    }

    /**
     * 设置监听事件
     *
     * @param paramonSizeChangedListenner
     */
    public void setOnSizeChangedListenner(
            InputMethodRelativeLayout.OnSizeChangedListenner paramonSizeChangedListenner) {
        this.onSizeChangedListenner = paramonSizeChangedListenner;
    }

    /**
     * 大小改变的内部接口
     */
    public abstract interface OnSizeChangedListenner {
        void onSizeChange(boolean paramBoolean, int w, int h);
    }
}
