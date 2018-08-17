package com.qxb.student.ui.UserCorrelation;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qxb.student.R;
import com.qxb.student.common.basics.AbsExpandFragment;
import com.qxb.student.common.dialog.SimpleDialog;
import com.qxb.student.common.dialog.model.DialogContent;
import com.qxb.student.common.listener.MultiClickUtil;
import com.qxb.student.common.module.bean.attr.NavAttr;
import com.qxb.student.common.utils.NavigationUtils;
import com.qxb.student.common.view.text.ClearEditText;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author Administrator
 */
public class OnePolishingFragment extends AbsExpandFragment {

    private LinearLayout linearLayout;
    private TextView tv_midschool;
    private ClearEditText makeup_name;

    @Override
    public int bindLayout() {
        return R.layout.fragment_one_polishing;
    }

    @Override
    public void init(@NonNull View view, @Nullable Bundle savedInstanceState) {
        linearLayout = findViewById(R.id.linearLayout);
        tv_midschool = findViewById(R.id.tv_midschool);
        makeup_name = findViewById(R.id.makeup_name);
        findViewById(R.id.btn_result).setOnClickListener(mOnClickListener);
        setKeyboardStateListener();
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (MultiClickUtil.isFastClick()) {
                switch (view.getId()) {
                    case R.id.btn_result:
                        showConfirmDialog();

                        break;
                    default:
                        break;
                }
            }
        }
    };

    private void showConfirmDialog() {
        SimpleDialog.with(getActivity())
                .content(DialogContent.message().setText(getString(R.string.zhuce_judge)).setGravity(Gravity.CENTER))
                .negative(getString(R.string.cancel))
                .positive(getString(R.string.ok), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NavigationUtils.getInstance().toNavigation(getActivity(), new NavAttr.Builder().graphRes(R.navigation.nav_two_polishing).build());
                    }
                }).show();
    }

    @Override
    public void onKeyBoardShow() {
        super.onKeyBoardShow();
        linearLayout.setVisibility(View.GONE);
    }

    @Override
    public void onKeyBoardHide() {
        super.onKeyBoardHide();
        linearLayout.setVisibility(View.VISIBLE);
    }
}
