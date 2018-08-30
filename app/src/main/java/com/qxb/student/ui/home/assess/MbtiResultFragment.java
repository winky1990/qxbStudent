package com.qxb.student.ui.home.assess;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.qxb.student.R;
import com.qxb.student.common.basics.AbsToolbarFragment;
import com.qxb.student.common.dialog.SimpleDialog;
import com.qxb.student.common.listener.MultiClickUtil;
import com.qxb.student.common.module.AssessRepository;
import com.qxb.student.common.module.bean.BaseEvaluationResult;
import com.qxb.student.common.module.bean.abs.TwoPar;
import com.qxb.student.common.module.bean.attr.NavAttr;
import com.qxb.student.common.utils.MenuUtils;
import com.qxb.student.common.utils.NavigationUtils;
import com.qxb.student.common.utils.SpanUtils;
import com.qxb.student.common.view.abslist.adapter.AbsAdapter;
import com.qxb.student.type.MbtiType;

/**
 * @author winky
 * @date 2018/8/29
 */
public class MbtiResultFragment extends AbsToolbarFragment {

    private static final String TAG = "MbtiResultFragment";


    public static Bundle create(String result) {
        Bundle bundle = new Bundle();
        bundle.putString(TAG, result);
        return bundle;
    }

    @Override
    public int bindContentView() {
        return R.layout.fragment_mbti_result;
    }

    private AbsAdapter<TwoPar<String>> adapter;
    private TextView text1;
    private TextView text2;

    @Override
    public void initContent(View contentView, @Nullable Bundle savedInstanceState) {
        String result = getArguments() != null ? getArguments().getString(TAG) : null;
        if (TextUtils.isEmpty(result)) {
            onBackPressed();
            return;
        }
        setTitle(R.string.title_mbti);
        text1 = contentView.findViewById(R.id.text1);
        text2 = contentView.findViewById(R.id.text2);
        GridView gridView = contentView.findViewById(R.id.gridView);
        adapter = new AbsAdapter<TwoPar<String>>(getContext(), R.layout.item_mbti_result) {
            @Override
            protected void bindView(View view, int position, TwoPar<String> item) {
                TextView text1 = view.findViewById(R.id.text1);
                text1.setText(item.getKey());
                MbtiType mbtiType = MbtiType.findByType(item.getKey());
                if (mbtiType != null) {
                    text1.setBackgroundResource(mbtiType.getDrawableRes());
                }
                setText(view, R.id.text2, item.getValue());
            }
        };
        gridView.setAdapter(adapter);
        submitAssess(result);
    }

    private void submitAssess(String result) {
        SimpleDialog.with(getActivity()).loading().show();
        MutableLiveData<BaseEvaluationResult> liveData = new MutableLiveData<>();
        liveData.observe(this, new Observer<BaseEvaluationResult>() {
            @Override
            public void onChanged(@Nullable BaseEvaluationResult evaluationResult) {
                SimpleDialog.with(getActivity()).loading().cancle();
                refreshUI(evaluationResult);
            }
        });
        new AssessRepository().getResultNew(liveData, result);
    }

    private void refreshUI(BaseEvaluationResult evaluationResult) {
        if (evaluationResult == null) {
            return;
        }
        String result = evaluationResult.getResult();
        String[] tags = evaluationResult.getName().split(" ");
        SpanUtils spanUtils = SpanUtils.from(String.format(getString(R.string.hint_mbti_theme), result));
        char[] types = result.toCharArray();
        for (int i = 0; i < types.length; i++) {
            String type = Character.toString(types[i]);
            MbtiType mbtiType = MbtiType.findByType(type);
            if (mbtiType != null) {
                spanUtils.setColor(mbtiType.getColorRes(), 2 + i, 3 + i);
            }
            adapter.add(new TwoPar<>(type, tags[i]));
        }
        spanUtils.setSize(100, 2, 6);
        text1.setText(spanUtils.result());
        adapter.notifyDataSetChanged();
        text2.setText(evaluationResult.getDescription());

    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!MultiClickUtil.isFastClick()) {
                return;
            }
            switch (view.getId()) {
                case R.id.item1:
                    SimpleDialog.with(getActivity())
                            .message(R.string.hint_mbti_again)
                            .negative()
                            .positive(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    NavigationUtils.getInstance().toNavigation(getContext(),
                                            new NavAttr.Builder()
                                                    .graphRes(R.navigation.nav_assess)
                                                    .navId(R.id.nav_assess_mbti_start)
                                                    .build());
                                    getActivity().finish();
                                }
                            })
                            .show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuUtils.with(menu).addText(R.id.item1, R.string.hint_mbti_right_title, clickListener);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
