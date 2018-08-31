package com.qxb.student.ui.home.assess;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qxb.student.R;
import com.qxb.student.common.Config;
import com.qxb.student.common.Constant;
import com.qxb.student.common.basics.AbsExpandFragment;
import com.qxb.student.common.dialog.SimpleDialog;
import com.qxb.student.common.module.bean.AssessAnswer;
import com.qxb.student.common.module.bean.AssessQuestion;
import com.qxb.student.common.utils.AssetUtils;
import com.qxb.student.common.utils.JsonUtils;
import com.qxb.student.common.utils.NavigationUtils;
import com.qxb.student.common.utils.SysUtils;
import com.qxb.student.common.view.abslist.adapter.AbsAdapter;

import java.util.List;

/**
 * 测评题目
 *
 * @author winky
 * @date 2018/8/20
 */
public class AssessProblemFragment extends AbsExpandFragment {

    private static final String TAG = "AssessProblemFragment";

    @Override
    public int bindLayout() {
        return R.layout.fragment_assess_problem;
    }

    public static Bundle create(String fileName) {
        Bundle bundle = new Bundle();
        bundle.putString(TAG, fileName);
        return bundle;
    }

    private final int FIRST_QUESTION = 1;
    private List<AssessQuestion> questionList;
    private Holder holder;
    private AbsAdapter<AssessAnswer> adapter;
    private String fileName;
    private int position;

    @Override
    public void init(@NonNull View view, @Nullable Bundle savedInstanceState) {
        String json;
        if (getArguments() != null) {
            json = AssetUtils.getInstance().readFile(getContext(), fileName = getArguments().getString(TAG));
        } else {
            onBackPressed();
            return;
        }
        holder = new Holder(view);
        holder.layout1.setPadding(0, SysUtils.getInstance().getStatusHeight(), 0, 0);
        holder.listView.setAdapter(adapter = new AbsAdapter<AssessAnswer>(getContext(), R.layout.item_assess_problem) {
            @Override
            protected void bindView(View view, int position, AssessAnswer item) {
                TextView textView = view.findViewById(R.id.text1);
                textView.setText(item.getAnswer().trim());
                if (item.isCheck()) {
                    textView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                } else {
                    textView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorDesc));
                }
            }
        });
        holder.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                if (!MultiClickUtil.isFastClick()) {
//                    ToastUtils.toast(R.string.hint_frequent_operation);
//                    return;
//                }
                List<AssessAnswer> answers = questionList.get(position - 1).getAnswers();
                for (int j = 0; j < answers.size(); j++) {
                    answers.get(j).setCheck(j == i);
                }
                if (position == questionList.size()) {
                    toResult();
                } else {
                    setCurrentQuestion(position + 1);
                }
            }
        });

        questionList = JsonUtils.getInstance().toList(json, AssessQuestion.class);
        holder.progressBar.setMax(questionList.size());
        setCurrentQuestion(FIRST_QUESTION);
    }

    private void toResult() {
        SimpleDialog.with(getActivity()).loading();
        StringBuilder buffer = new StringBuilder();
        for (AssessQuestion question : questionList) {
            for (AssessAnswer answer : question.getAnswers()) {
                if (answer.isCheck()) {
                    buffer.append(answer.getScore()).append(",");
                    break;
                }
            }
        }
        buffer.deleteCharAt(buffer.length() - 1);
        SimpleDialog.with(getActivity()).cancle();
        if (Constant.ASSETS_MBTI_ASSESS.equals(fileName)) {
            NavigationUtils.getInstance().jump(getFragment(), R.id.nav_assess_mbti_result, MbtiResultFragment.create(buffer.toString()));
        } else {
            NavigationUtils.getInstance().jump(getFragment(), R.id.nav_assess_hollander_result, HollanderResultFragment.create(buffer.toString()));
        }
    }

    private void setCurrentQuestion(int position) {
        if (position == this.position) {
            return;
        }
        this.position = position;
        synchronized (Config.LOCK) {
            AssessQuestion question = questionList.get(position - 1);
            holder.progressBar.setProgress(position);
            holder.text1.setText(String.format(getString(R.string.previous_problem_nav), position, questionList.size()));
            holder.text3.setText(question.getQuestion());
            adapter.clear();
            adapter.addCollection(question.getAnswers());
            adapter.notifyDataSetChanged();
            holder.text6.setVisibility(position == FIRST_QUESTION ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        holder = null;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.text2:
                    NavigationUtils.getInstance().goBack(getFragment());
                    break;
                case R.id.text6:
                    setCurrentQuestion(position - 1);
                    break;
                default:
                    break;
            }
        }
    };

    class Holder {
        RelativeLayout layout1;
        ProgressBar progressBar;
        TextView text1;
        TextView text2;
        TextView text3;
        ListView listView;
        TextView text6;

        Holder(View view) {
            layout1 = view.findViewById(R.id.layout1);
            progressBar = view.findViewById(R.id.progressBar1);
            text1 = view.findViewById(R.id.text1);
            text2 = view.findViewById(R.id.text2);
            text3 = view.findViewById(R.id.text3);
            listView = view.findViewById(R.id.listView);
            text6 = view.findViewById(R.id.text6);
            text2.setOnClickListener(clickListener);
            text6.setOnClickListener(clickListener);
        }
    }
}

