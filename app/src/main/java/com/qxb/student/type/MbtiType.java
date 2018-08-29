package com.qxb.student.type;

import com.qxb.student.R;

/**
 * @author winky
 * @date 2018/8/29
 */
public enum MbtiType {
    /**
     * E
     */
    E("E", R.drawable.assess_result_e, R.color.assess_result_e),
    F("F", R.drawable.assess_result_f, R.color.assess_result_f),
    I("I", R.drawable.assess_result_i, R.color.assess_result_i),
    J("J", R.drawable.assess_result_j, R.color.assess_result_j),
    N("N", R.drawable.assess_result_n, R.color.assess_result_n),
    P("P", R.drawable.assess_result_p, R.color.assess_result_p),
    S("S", R.drawable.assess_result_s, R.color.assess_result_s),
    T("T", R.drawable.assess_result_t, R.color.assess_result_t);

    private String type;
    private int drawableRes;
    private int colorRes;

    MbtiType(String type, int drawableRes, int colorRes) {
        this.type = type;
        this.drawableRes = drawableRes;
        this.colorRes = colorRes;
    }

    public String getType() {
        return type;
    }

    public int getDrawableRes() {
        return drawableRes;
    }

    public int getColorRes() {
        return colorRes;
    }

    public static MbtiType findByType(String type) {
        for (MbtiType mbtiType : MbtiType.values()) {
            if (mbtiType.getType().equals(type)) {
                return mbtiType;
            }
        }
        return null;
    }
}
