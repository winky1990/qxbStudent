package com.qxb.student.common.view.shape;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.support.annotation.ColorInt;

/**
 * @author winky
 * @date 2018/8/15
 */
public class SimpleShapeDrawable extends ShapeDrawable {

    public SimpleShapeDrawable(@ColorInt int backgroundColor, int leftTop, int rightTop, int rightBottom, int leftBottom) {
        this(backgroundColor, new int[]{leftTop, rightTop, rightBottom, leftBottom});
    }

    public SimpleShapeDrawable(@ColorInt int backgroundColor, int[] angle) {
        super();

        getPaint().setColor(backgroundColor);

        final float[] outerRadii = new float[8];
        int length = 4;
        if (angle == null || angle.length != length) {
            angle = new int[]{0, 0, 0, 0};
        }
        int leftTop = 0;
        int rightTop = 1;
        int rightBottom = 2;
        int leftBottom = 3;
        if (angle[leftTop] > 0) {
            outerRadii[0] = angle[leftTop];
            outerRadii[1] = angle[leftTop];
        } else if (angle[rightTop] > 0) {
            outerRadii[2] = angle[rightTop];
            outerRadii[3] = angle[rightTop];
        } else if (angle[rightBottom] > 0) {
            outerRadii[4] = angle[rightBottom];
            outerRadii[5] = angle[rightBottom];
        } else if (angle[leftBottom] > 0) {
            outerRadii[6] = angle[leftBottom];
            outerRadii[7] = angle[leftBottom];
        }

        setShape(new RoundRectShape(outerRadii, null, null));
    }


}
