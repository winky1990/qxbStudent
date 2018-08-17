package com.qxb.student.common.dialog.model;

import android.support.annotation.ColorInt;

import com.qxb.student.common.dialog.DialogMode;

/**
 * 弹窗内容
 *
 * @author winky
 * @date 2018/8/16
 */
public class DialogContent {

    public static DialogContent message() {
        return create(DialogMode.MESSAGE);
    }

    public static DialogContent list() {
        return create(DialogMode.LIST);
    }

    public static DialogContent input() {
        return create(DialogMode.INPUT);
    }

    public static DialogContent progress() {
        return create(DialogMode.PROGRESSBAR);
    }

    public static DialogContent loading() {
        return create(DialogMode.LOADING);
    }

    public static DialogContent create(DialogMode dialogMode) {
        return new DialogContent(dialogMode);
    }

    /**
     * 弹窗内容类型
     */
    private DialogMode dialogMode;
    /**
     * 文本
     */
    private CharSequence text;
    /**
     * 提示语
     */
    private CharSequence hintText;
    /**
     * 提示字体颜色
     */
    private int hintTextColor;
    /**
     * 内容字体颜色
     */
    private int textColor;
    /**
     * 内容字体大小
     */
    private int textSize;
    /**
     * 内容控件内部偏向
     */
    private int gravity;
    /**
     * 内容控件高度
     */
    private int height;

    private DialogContent(DialogMode dialogMode) {
        this.dialogMode = dialogMode;
    }

    public DialogContent setText(CharSequence text) {
        this.text = text;
        return this;
    }

    public DialogContent setHintText(CharSequence hintText) {
        this.hintText = hintText;
        return this;
    }

    public DialogContent setHintTextColor(@ColorInt int hintTextColor) {
        this.hintTextColor = hintTextColor;
        return this;
    }

    public DialogContent setTextColor(@ColorInt int textColor) {
        this.textColor = textColor;
        return this;
    }

    public DialogContent setTextSize(int textSize) {
        this.textSize = textSize;
        return this;
    }

    public DialogContent setGravity(int gravity) {
        this.gravity = gravity;
        return this;
    }

    public DialogContent setHeight(int height) {
        this.height = height;
        return this;
    }

    public DialogMode getDialogMode() {
        return dialogMode;
    }

    public void setDialogMode(DialogMode dialogMode) {
        this.dialogMode = dialogMode;
    }

    public CharSequence getText() {
        return text;
    }

    public CharSequence getHintText() {
        return hintText;
    }

    public int getHintTextColor() {
        return hintTextColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public int getTextSize() {
        return textSize;
    }

    public int getGravity() {
        return gravity;
    }

    public int getHeight() {
        return height;
    }
}
