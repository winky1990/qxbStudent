package com.qxb.student.common.dialog.model;

import android.view.View;

/**
 * @author winky
 * @date 2018/8/16
 */
public class DialogFooter {

    private String text;
    private int textSize;
    private int textColor;
    private View.OnClickListener clickListener;

    public DialogFooter(String text, int textSize, int textColor, View.OnClickListener clickListener) {
        this.text = text;
        this.textSize = textSize;
        this.textColor = textColor;
        this.clickListener = clickListener;
    }

    public String getText() {
        return text;
    }

    public int getTextSize() {
        return textSize;
    }

    public int getTextColor() {
        return textColor;
    }

    public View.OnClickListener getClickListener() {
        return clickListener;
    }
}
