package com.qxb.student.common.dialog.model;

/**
 * @author winky
 * @date 2018/8/16
 */
public class DialogHeader {

    private String title;
    private int textSize;
    private int textColor;

    public DialogHeader(String title, int textSize, int textColor) {
        this.title = title;
        this.textSize = textSize;
        this.textColor = textColor;
    }

    public String getTitle() {
        return title;
    }

    public int getTextSize() {
        return textSize;
    }

    public int getTextColor() {
        return textColor;
    }

}
