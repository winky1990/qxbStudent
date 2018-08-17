package com.qxb.student.common.utils;

import com.qxb.student.common.Config;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 抽象单例
 *
 * @author winky
 * @param <T> 单例类
 */
public abstract class Singleton<T> {

    private final AtomicReference<T> atomicReference = new AtomicReference<>();

    public T get() {
        T model = atomicReference.get();
        if (model == null) {
            synchronized (Config.LOCK) {
                if (atomicReference.get() == null) {
                    model = create();
                    atomicReference.set(model);
                } else {
                    model = atomicReference.get();
                }
            }
        }
        return model;
    }

    /**
     * 创建单例对象
     * @return 单例
     */
    protected abstract T create();
}
