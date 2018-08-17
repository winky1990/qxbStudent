package com.qxb.student.common.module.bean.tab;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * 缓存配置
 *
 * @author winky
 * @date 2018/7/19
 */
@Entity(tableName = "Configure")
public class Configure {

    @PrimaryKey(autoGenerate = true)
    private int _id;
    /**
     * 键
     */
    private String key;
    /**
     * 值
     */
    private String value;

    public Configure() {
    }

    @Ignore
    public Configure(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
