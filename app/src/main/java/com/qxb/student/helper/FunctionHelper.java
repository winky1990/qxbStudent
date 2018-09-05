package com.qxb.student.helper;

import android.content.Context;

import com.qxb.student.R;
import com.qxb.student.common.module.bean.FunctionItem;
import com.qxb.student.common.module.bean.attr.NavAttr;
import com.qxb.student.common.utils.NavigationUtils;
import com.qxb.student.common.utils.Singleton;
import com.qxb.student.common.utils.UserCache;
import com.qxb.student.type.FunctionType;

/**
 * @author winky
 * @date 2018/7/26
 */
public class FunctionHelper {

    private static final Singleton<FunctionHelper> SINGLETON = new Singleton<FunctionHelper>() {
        @Override
        protected FunctionHelper create() {
            return new FunctionHelper();
        }
    };

    public static FunctionHelper getInstance() {
        return SINGLETON.get();
    }

    public void jump(Context context, FunctionItem item) {
        if (context == null || item == null) {
            return;
        }
        if (!UserCache.getInstance().isLogin()) {
            NavigationUtils.getInstance().toNavigation(context,
                    new NavAttr.Builder()
                            .graphRes(R.navigation.nav_login)
                            .navId(R.id.nav_login)
                            .build());
            return;
        }
//        String level = "2";
//        if (level.equals(item.getLevel())) {
//            // TODO: 2018/8/28 判断补录
//            return;
//        }

        NavAttr navAttr = null;
        switch (item.getItem_type()) {
            case FunctionType.INTELLIGENT_SELECTION_SCHOOL:
                break;
            case FunctionType.CONSULT_EXPERT:
                break;
            case FunctionType.LISTEN_EXPLAIN:
                break;
            case FunctionType.ASSESS:
                navAttr = new NavAttr.Builder().graphRes(R.navigation.nav_assess).navId(R.id.nav_assess_home).build();
                break;
            case FunctionType.UNIVERSITY_LIB:
                navAttr = new NavAttr.Builder().graphRes(R.navigation.nav_school).navId(R.id.nav_school_library).build();
                break;
            case FunctionType.MAJOR_LIB:
                break;
            case FunctionType.ENROLLMENT_ANSWER:
                break;
            case FunctionType.INDEPENDENT_RECRUIT:
                break;
            case FunctionType.ART_RECRUIT:
                break;
            case FunctionType.ABROAD_STUDY:
                break;
            case FunctionType.UNIVERSITY_RANKING:
                break;
            case FunctionType.DISCIPLINE_RANKING:
                break;
            case FunctionType.FRANCE_STUDY:
                break;
            case FunctionType.SKILL_CULTIVATE:
                break;
            case FunctionType.FRACTION_PARAGRAPH:
                break;
            case FunctionType.SELECT_MAJOR:
                break;
            default:
                break;
        }
        if (navAttr != null) {
            NavigationUtils.getInstance().toNavigation(context, navAttr);
        }
    }
}
