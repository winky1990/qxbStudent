package com.qxb.student.type;

import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;

import com.qxb.student.R;

/**
 * @author winky
 * @date 2018/8/30
 */
public enum HollanderType {
    /**
     * 霍兰德类型
     */
    REAL("R", R.string.hint_hollander_type_r, R.string.hint_hollander_r, R.color.assess_hollander_r),
    RESEARCH("I", R.string.hint_hollander_type_i, R.string.hint_hollander_i, R.color.assess_hollander_i),
    ART("A", R.string.hint_hollander_type_a, R.string.hint_hollander_a, R.color.assess_hollander_a),
    SOCIETY("S", R.string.hint_hollander_type_s, R.string.hint_hollander_s, R.color.assess_hollander_s),
    ENTERPRISE("E", R.string.hint_hollander_type_e, R.string.hint_hollander_e, R.color.assess_hollander_e),
    CONVENTION("C", R.string.hint_hollander_type_c, R.string.hint_hollander_c, R.color.assess_hollander_c);

    private String type;
    @StringRes
    private int titleRes;
    @StringRes
    private int descRes;
    @ColorRes
    private int colorRes;

    HollanderType(String type, int titleRes, int descRes, int colorRes) {
        this.type = type;
        this.titleRes = titleRes;
        this.descRes = descRes;
        this.colorRes = colorRes;
    }

    public String getType() {
        return type;
    }

    public int getTitleRes() {
        return titleRes;
    }

    public int getDescRes() {
        return descRes;
    }

    public int getColorRes() {
        return colorRes;
    }

    public static HollanderType findByType(String type) {
        for (HollanderType hollanderType : HollanderType.values()) {
            if (hollanderType.getType().equals(type)) {
                return hollanderType;
            }
        }
        return null;
    }
}
