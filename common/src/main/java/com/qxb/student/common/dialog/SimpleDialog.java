package com.qxb.student.common.dialog;

import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.qxb.student.common.Config;
import com.qxb.student.common.dialog.model.DialogContent;
import com.qxb.student.common.dialog.model.DialogFooter;
import com.qxb.student.common.dialog.model.DialogHeader;
import com.qxb.student.common.dialog.model.DialogParams;
import com.qxb.student.common.utils.SysUtils;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author winky
 * @date 2018/8/16
 */
public class SimpleDialog {

    private static final AtomicReference<CustomDialogFragment> REFERENCE = new AtomicReference<>();
    private final String TAG = "SimpleDialog";

    public static SimpleDialog with(FragmentActivity activity) {
        return new SimpleDialog(activity);
    }

    private final FragmentActivity activity;
    private final DialogParams params;

    private SimpleDialog(FragmentActivity activity) {
        this.activity = activity;
        this.params = new DialogParams();
    }

    public SimpleDialog setAlpha(@FloatRange(from = 0.0, to = 1.0) float alpha) {
        params.setAlpha(alpha);
        return this;
    }

    public SimpleDialog setBackgroundColor(@ColorInt int backgroundColor) {
        params.setBackgroundColor(backgroundColor);
        return this;
    }

    public SimpleDialog setWidth(int width) {
        params.setWidth(width);
        return this;
    }

    public SimpleDialog setWidth(@FloatRange(from = 0.0, to = 1.0) float width) {
        params.setWidth((int) (width * SysUtils.getInstance().getDisplayMetrics().widthPixels));
        return this;
    }

    public SimpleDialog setHeight(int height) {
        params.setHeight(height);
        return this;
    }

    public SimpleDialog setHeight(@FloatRange(from = 0.0, to = 1.0) float height) {
        params.setHeight((int) (height * SysUtils.getInstance().getDisplayMetrics().heightPixels));
        return this;
    }

    public SimpleDialog setGravity(int gravity) {
        params.setContentGravity(gravity);
        return this;
    }

    public SimpleDialog touchOutside() {
        params.setCanceledOnTouchOutside(true);
        return this;
    }

    public SimpleDialog cancelable() {
        params.setCancelable(true);
        return this;
    }

    public SimpleDialog title(String title) {
        return this.title(title, 0);
    }

    public SimpleDialog title(String title, @ColorInt int textColor) {
        return this.title(title, textColor, 0);
    }

    public SimpleDialog title(String title, @ColorInt int textColor, int textSize) {
        params.setHeader(new DialogHeader(title, textSize, textColor));
        return this;
    }

    public SimpleDialog positive(String text, View.OnClickListener clickListener) {
        return this.positive(text, 0, 0, clickListener);
    }

    public SimpleDialog positive(String text, int textColor, View.OnClickListener clickListener) {
        return this.positive(text, textColor, 0, clickListener);
    }

    public SimpleDialog positive(String text, int textColor, int textSize, View.OnClickListener clickListener) {
        params.setPositive(new DialogFooter(text, textSize, textColor, clickListener));
        return this;
    }

    public SimpleDialog negative(String text) {
        return this.negative(text, 0, 0, null);
    }

    public SimpleDialog negative(String text, View.OnClickListener clickListener) {
        return this.negative(text, 0, 0, clickListener);
    }

    public SimpleDialog negative(String text, int textColor, View.OnClickListener clickListener) {
        return this.negative(text, textColor, 0, clickListener);
    }

    public SimpleDialog negative(String text, int textColor, int textSize, View.OnClickListener clickListener) {
        params.setNegative(new DialogFooter(text, textSize, textColor, clickListener));
        return this;
    }

    public SimpleDialog content(DialogContent dialogContent) {
        params.setContent(dialogContent);
        return this;
    }

    public SimpleDialog message(String message) {
        params.setContent(DialogContent.message().setText(message));
        return this;
    }

    public SimpleDialog loading() {
        params.setContent(DialogContent.loading());
        return this;
    }

    public void show() {
        DialogViewModel viewModel = ViewModelProviders.of(activity).get(DialogViewModel.class);
        viewModel.setParams(params);
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        CustomDialogFragment fragment = (CustomDialogFragment) fragmentManager.findFragmentByTag(TAG);
        if (fragment == null) {
            fragment = get();
        }
        fragment.show(fragmentManager, TAG);
    }

    public void cancle() {
        CustomDialogFragment fragment = get();
        if (fragment.getDialog() != null) {
            fragment.getDialog().cancel();
        }
    }

    private CustomDialogFragment get() {
        CustomDialogFragment fragment = REFERENCE.get();
        if (fragment == null) {
            synchronized (Config.LOCK) {
                if (REFERENCE.get() == null) {
                    fragment = new CustomDialogFragment();
                    REFERENCE.set(fragment);
                } else {
                    fragment = REFERENCE.get();
                }
            }
        }
        return fragment;
    }

}
