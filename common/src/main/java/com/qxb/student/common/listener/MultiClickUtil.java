package com.qxb.student.common.listener;


import com.qxb.student.common.R;
import com.qxb.student.common.utils.ToastUtils;

/**
 * author: zjk9527
 * created on: 2018/5/21 15:44
 * description:
 */

public class MultiClickUtil {
    // 两次点击按钮之间的点击间隔不能少于1000毫秒
    private static final int MIN_CLICK_DELAY_TIME = 900;
    private static long lastClickTime;

    public static boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        } else {
            ToastUtils.toast(R.string.hint_frequent_operation);
        }
        lastClickTime = curClickTime;
        return flag;
    }
    /*btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isFastClick()) {
                    // 进行点击事件后的逻辑操作
                }
            }
        });*/
}
