package com.qxb.student.common.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.qxb.student.common.R;

/**
 * @author winky
 * @date 2018/8/29
 */
public class MenuUtils {

    public static MenuUtils with(Menu menu) {
        return new MenuUtils(menu);
    }

    private final Menu menu;
    private final Context context;

    MenuUtils(Menu menu) {
        this.menu = menu;
        this.context = ContextUtils.getInstance().getContext();
    }

    public MenuUtils addText(@IdRes int id, @StringRes int strRes, @NonNull View.OnClickListener clickListener) {
        return this.addText(id, context.getString(strRes), clickListener);
    }

    public MenuUtils addText(@IdRes int id, CharSequence title, @NonNull View.OnClickListener clickListener) {
        return this.addText(id, title, R.color.colorAccent, clickListener);
    }

    public MenuUtils addText(@IdRes int id, CharSequence title, @ColorRes int color, @NonNull View.OnClickListener clickListener) {
        MenuItem item = menu.add(0, id, 0, "");
        TextView textView = View.inflate(context, R.layout.menu_text, null).findViewById(R.id.text1);
        textView.setId(id);
        textView.setText(title);
        textView.setTextColor(SysUtils.getInstance().convertColor(color));
        textView.setOnClickListener(clickListener);
        item.setActionView(textView);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return this;
    }

    public MenuUtils addIcon(@IdRes int id, @DrawableRes int drawableRes, @NonNull View.OnClickListener clickListener) {
        MenuItem item = menu.add(0, id, 0, "");
        ImageButton button = View.inflate(context, R.layout.menu_image, null).findViewById(R.id.image1);
        button.setId(id);
        button.setImageResource(drawableRes);
        button.setOnClickListener(clickListener);
        item.setActionView(button);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return this;
    }
}
