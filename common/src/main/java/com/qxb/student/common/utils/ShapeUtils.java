package com.qxb.student.common.utils;

import android.animation.AnimatorInflater;
import android.content.res.ColorStateList;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.view.View;

import com.qxb.student.common.R;

/**
 * @author winky
 * @date 2018/9/5
 */
public class ShapeUtils {

    private static final Singleton<ShapeUtils> SINGLETON = new Singleton<ShapeUtils>() {
        @Override
        protected ShapeUtils create() {
            return new ShapeUtils();
        }
    };

    public static ShapeUtils getInstance() {
        return SINGLETON.get();
    }

    @ColorInt
    private int pressColor = -1;
    @ColorInt
    private int normalColor = -1;

    private int radius = 0;

    public ShapeUtils setPressColor(@ColorRes int pressColor) {
        this.pressColor = SysUtils.getInstance().convertColor(pressColor);
        return this;
    }

    public ShapeUtils setNormalColor(@ColorRes int normalColor) {
        this.normalColor = SysUtils.getInstance().convertColor(normalColor);
        return this;
    }

    public ShapeUtils setRadius(int radius) {
        this.radius = radius;
        return this;
    }

    public void intoView(View view) {
        GradientDrawable drawable = new GradientDrawable();
        if (normalColor != -1) {
            drawable.setColor(normalColor);
        }
        drawable.setCornerRadius(radius);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            if (pressColor != -1) {
                view.setBackground(new RippleDrawable(ColorStateList.valueOf(pressColor), drawable, null));
            }
            view.setStateListAnimator(AnimatorInflater.loadStateListAnimator(view.getContext(), R.animator.btn_press));
        } else {
            view.setBackground(drawable);
        }
    }
}
