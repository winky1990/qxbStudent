package com.qxb.student.common.dialog.model;

/**
 * @author winky
 * @date 2018/8/16
 */
public class DialogParams {

    private int contentGravity;
    private boolean canceledOnTouchOutside;
    private boolean cancelable;
    private float alpha;
    private int backgroundColor;
    private int width;
    private int height;

    private DialogHeader header;
    private DialogContent content;
    private DialogFooter negative;
    private DialogFooter positive;

    public DialogHeader getHeader() {
        return header;
    }

    public void setHeader(DialogHeader header) {
        this.header = header;
    }

    public DialogContent getContent() {
        return content;
    }

    public void setContent(DialogContent content) {
        this.content = content;
    }

    public DialogFooter getNegative() {
        return negative;
    }

    public void setNegative(DialogFooter negative) {
        this.negative = negative;
    }

    public DialogFooter getPositive() {
        return positive;
    }

    public void setPositive(DialogFooter positive) {
        this.positive = positive;
    }

    public int getContentGravity() {
        return contentGravity;
    }

    public void setContentGravity(int contentGravity) {
        this.contentGravity = contentGravity;
    }

    public boolean isCanceledOnTouchOutside() {
        return canceledOnTouchOutside;
    }

    public void setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
        this.canceledOnTouchOutside = canceledOnTouchOutside;
    }

    public boolean isCancelable() {
        return cancelable;
    }

    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
