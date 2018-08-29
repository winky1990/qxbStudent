package com.qxb.student.common.module.bean.abs;

/**
 * @author winky
 * @date 2018/8/29
 */
public class TwoPar<T> {

    private String key;
    private T value;

    public TwoPar(String key, T value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public T getValue() {
        return value;
    }
}
