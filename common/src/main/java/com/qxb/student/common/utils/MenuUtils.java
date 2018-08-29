package com.qxb.student.common.utils;

import android.view.Menu;
import android.view.MenuInflater;

import com.qxb.student.common.R;

/**
 * @author winky
 * @date 2018/8/29
 */
public class MenuUtils {

    public static MenuUtils with(Menu menu, MenuInflater inflater) {
        return new MenuUtils(menu, inflater);
    }

    private Menu menu;
    private MenuInflater inflater;

    MenuUtils(Menu menu, MenuInflater inflater) {
        this.menu = menu;
        this.inflater = inflater;
    }

    public MenuUtils single() {
        inflater.inflate(R.menu.single, menu);
        return this;
    }

    public MenuUtils pair() {
        inflater.inflate(R.menu.pair, menu);
        return this;
    }

    public void setSingle(CharSequence sequence) {
        single();
        menu.findItem(R.id.item1).setTitle(sequence);
    }

    public void setPair(CharSequence sequence1, CharSequence sequence2) {
        pair();
        menu.findItem(R.id.item1).setTitle(sequence1);
        menu.findItem(R.id.item2).setTitle(sequence2);
    }
}
