package com.qxb.student.helper;

import com.qxb.student.common.module.UserRepository;

/**
 * @author winky
 * @date 2018/8/31
 */
public class IntegralHelper {

    /**
     * 更新积分
     */
    public static void update() {
        new UserRepository().updateIntegral();
    }
}
