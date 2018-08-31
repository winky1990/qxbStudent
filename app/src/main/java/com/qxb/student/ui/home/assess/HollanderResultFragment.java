package com.qxb.student.ui.home.assess;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.qxb.student.R;
import com.qxb.student.common.basics.AbsToolbarFragment;
import com.qxb.student.common.dialog.SimpleDialog;
import com.qxb.student.common.listener.MultiClickUtil;
import com.qxb.student.common.module.AssessRepository;
import com.qxb.student.common.module.bean.HollanderAssess;
import com.qxb.student.common.module.bean.HollanderResult;
import com.qxb.student.common.module.bean.attr.NavAttr;
import com.qxb.student.common.utils.MenuUtils;
import com.qxb.student.common.utils.NavigationUtils;
import com.qxb.student.common.utils.SpanUtils;
import com.qxb.student.common.view.abslist.adapter.AbsAdapter;
import com.qxb.student.common.view.custom.RadarChartView;
import com.qxb.student.helper.IntegralHelper;
import com.qxb.student.type.HollanderType;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author winky
 * @date 2018/8/30
 */
public class HollanderResultFragment extends AbsToolbarFragment {

    private static final String TAG = "HollanderResultFragment";


    public static Bundle create(String result) {
        Bundle bundle = new Bundle();
        bundle.putString(TAG, result);
        return bundle;
    }

    @Override
    public int bindContentView() {
        return R.layout.fragment_hollander_result;
    }

    private TextView text1;
    private TextView text2;
    private ListView listView;
    private RadarChartView chartView;

    @Override
    public void initContent(View contentView, @Nullable Bundle savedInstanceState) {
        // TODO: 2018/8/31 根据档案中 has_holland 判断是否已经测试过
        String result = getArguments() != null ? getArguments().getString(TAG) : null;
        if (TextUtils.isEmpty(result)) {
            onBackPressed();
            return;
        }
        setTitle(R.string.title_hollander);
        text1 = contentView.findViewById(R.id.text1);
        text2 = contentView.findViewById(R.id.text2);
        chartView = contentView.findViewById(R.id.chartView);
        listView = contentView.findViewById(R.id.listView);
        listView.setDividerHeight(0);
        listView.setPadding(60, 0, 60, 0);
        AbsAdapter<HollanderType> adapter = new AbsAdapter<HollanderType>(getContext(), R.layout.item_hollander_result, Arrays.asList(HollanderType.values())) {
            @Override
            protected void bindView(View view, int position, HollanderType item) {
                TextView text1 = view.findViewById(R.id.text1);
                text1.setText(getString(item.getTitleRes()));
                text1.setBackgroundResource(item.getColorRes());
                setText(view, R.id.text2, getString(item.getDescRes()));
            }
        };
        listView.setAdapter(adapter);
        submitAssess(result);
    }

    private void submitAssess(String result) {
        SimpleDialog.with(getActivity()).loading().show();
        MutableLiveData<HollanderAssess> liveData = new MutableLiveData<>();
        liveData.observe(this, new Observer<HollanderAssess>() {
            @Override
            public void onChanged(@Nullable HollanderAssess evaluationResult) {
                SimpleDialog.with(getActivity()).loading().cancle();
                refreshUI(evaluationResult);
            }
        });
        new AssessRepository().getResultHolland(liveData, result);
    }

    private void refreshUI(HollanderAssess evaluationResult) {
        if (evaluationResult == null) {
            return;
        }
        HollanderResult result = evaluationResult.getResult();
        if (evaluationResult.getAddIntegral() > 0) {
            SimpleDialog.with(getActivity()).integral(evaluationResult.getAddIntegral()).positive().show();
            IntegralHelper.update();
        }
        JSONObject jsonObject = JSONObject.parseObject(result.getHolland_score());
        String[] titles = new String[HollanderType.values().length];
        int[] values = new int[HollanderType.values().length];
        for (int i = 0; i < HollanderType.values().length; i++) {
            HollanderType type = HollanderType.values()[i];
            titles[i] = getString(type.getTitleRes());
            values[i] = jsonObject.getIntValue(type.getType());
        }
        chartView.setTitles(titles);
        chartView.setValue(values);
        chartView.setDot(true);
        chartView.refreshView();

        SpanUtils spanUtils = SpanUtils.from(String.format(getString(R.string.hint_assess_theme), result.getResult()));
        char[] types = result.getResult().toCharArray();
        for (int i = 0; i < types.length; i++) {
            String type = Character.toString(types[i]);
            HollanderType hollanderType = HollanderType.findByType(type);
            if (hollanderType != null) {
                spanUtils.setColor(hollanderType.getColorRes(), 3 + i, 4 + i);
            }
        }
        spanUtils.setSize(100, 3, 3 + types.length);
        text1.setText(spanUtils.build());
        text2.setText(evaluationResult.getDescription());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        MenuUtils.with(menu).addText(R.id.item1, R.string.hint_mbti_right_title, clickListener);
        super.onCreateOptionsMenu(menu, inflater);
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
                            .message(R.string.hint_assess_again)
                            .negative()
                            .positive(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    NavigationUtils.getInstance().toNavigation(getActivity(),
                                            new NavAttr.Builder()
                                                    .graphRes(R.navigation.nav_assess)
                                                    .navId(R.id.nav_assess_hollander_start)
                                                    .build());
                                    Objects.requireNonNull(getActivity()).finish();
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
    public void onDestroy() {
        super.onDestroy();
        text1 = null;
        text2 = null;
        listView = null;
    }
}
