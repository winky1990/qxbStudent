package com.qxb.student.common.utils;

import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;

/**
 * @author winky
 * @date 2018/8/29
 */
public class SpanUtils {

    public static SpanUtils from(CharSequence sequence) {
        return new SpanUtils(sequence);
    }

    public static SpanUtils from(@StringRes int stringRes) {
        return from(ContextUtils.getInstance().getContext().getString(stringRes));
    }

    private SpannableStringBuilder builder;

    SpanUtils(CharSequence sequence) {
        this.builder = new SpannableStringBuilder(sequence);
    }

    public SpanUtils setColor(@ColorRes int color, int start, int end) {
        builder.setSpan(new ForegroundColorSpan(SysUtils.getInstance().convertColor(color)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public SpanUtils setColor(@ColorRes int color) {
        return setColor(color, 0, builder.length());
    }

    public SpanUtils setSize(int size, int start, int end) {
        builder.setSpan(new AbsoluteSizeSpan(size), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return this;
    }

    public SpannableStringBuilder build() {
        return builder;
    }
}
