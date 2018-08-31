package com.qxb.student.ui.home.assess;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.qxb.student.R;
import com.qxb.student.common.Constant;
import com.qxb.student.common.basics.AbsToolbarFragment;
import com.qxb.student.common.utils.NavigationUtils;

/**
 * mbti测评
 *
 * @author winky
 * @date 2018/8/17
 */
public class MBTIStartFragment extends AbsToolbarFragment {

    @Override
    public int bindContentView() {
        return R.layout.fragment_mbti_start;
    }

    @Override
    public void initContent(View contentView, @Nullable Bundle savedInstanceState) {
        contentView.findViewById(R.id.text1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavigationUtils.getInstance().jump(getFragment(),
                        R.id.nav_assess_problem,
                        AssessProblemFragment.create(Constant.ASSETS_MBTI_ASSESS));
            }
        });
    }
}
