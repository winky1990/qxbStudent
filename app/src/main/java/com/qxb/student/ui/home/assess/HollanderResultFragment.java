package com.qxb.student.ui.home.assess;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.qxb.student.R;
import com.qxb.student.common.basics.AbsToolbarFragment;

/**
 * @author winky
 * @date 2018/8/30
 */
public class HollanderResultFragment extends AbsToolbarFragment {
    @Override
    public int bindContentView() {
        return R.layout.fragment_hollander_result;
    }

    @Override
    public void initContent(View contentView, @Nullable Bundle savedInstanceState) {
        setTitle(R.string.title_hollander);

    }
}
