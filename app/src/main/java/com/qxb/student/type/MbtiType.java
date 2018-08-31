package com.qxb.student.type;

import com.qxb.student.R;

/**
 * @author winky
 * @date 2018/8/29
 */
public enum MbtiType {
    /**
     * mbti类型
     */
    E("E", R.drawable.assess_mbti_e, R.color.assess_mbti_e),
    F("F", R.drawable.assess_mbti_f, R.color.assess_mbti_f),
    I("I", R.drawable.assess_mbti_i, R.color.assess_mbti_i),
    J("J", R.drawable.assess_mbti_j, R.color.assess_mbti_j),
    N("N", R.drawable.assess_mbti_n, R.color.assess_mbti_n),
    P("P", R.drawable.assess_mbti_p, R.color.assess_mbti_p),
    S("S", R.drawable.assess_mbti_s, R.color.assess_mbti_s),
    T("T", R.drawable.assess_mbti_t, R.color.assess_mbti_t);

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
