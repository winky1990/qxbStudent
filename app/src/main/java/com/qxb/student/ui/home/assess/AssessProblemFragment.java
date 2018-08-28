package com.qxb.student.ui.home.assess;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.qxb.student.R;
import com.qxb.student.common.Config;
import com.qxb.student.common.Constant;
import com.qxb.student.common.basics.AbsExpandFragment;
import com.qxb.student.common.listener.MultiClickUtil;
import com.qxb.student.common.module.bean.AssessAnswer;
import com.qxb.student.common.module.bean.AssessQuestion;
import com.qxb.student.common.utils.AssetUtils;
import com.qxb.student.common.utils.JsonUtils;
import com.qxb.student.common.utils.NavigationUtils;
import com.qxb.student.common.utils.SysUtils;
import com.qxb.student.common.utils.ToastUtils;
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
    private int position;

    @Override
    public void init(@NonNull View view, @Nullable Bundle savedInstanceState) {
        String json;
        if (getArguments() != null) {
            json = AssetUtils.getInstance().readFile(getContext(), getArguments().getString(TAG));
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
                textView.setText(item.getAnswer());
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
                if (!MultiClickUtil.isFastClick()) {
                    ToastUtils.toast(R.string.hint_frequent_operation);
                    return;
                }
                setCurrentQuestion(position + 1);
            }
        });

        questionList = JsonUtils.getInstance().toList(json, AssessQuestion.class);
        holder.progressBar.setMax(questionList.size());
        setCurrentQuestion(FIRST_QUESTION);
    }

    private void setCurrentQuestion(int position) {
        if (position == this.position) {
            return;
        }
        this.position = position;
        synchronized (Config.LOCK) {
            AssessQuestion question = questionList.get(position);
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
    public void onDestroyView() {
        super.onDestroyView();
        holder = null;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!MultiClickUtil.isFastClick()) {
                ToastUtils.toast(R.string.hint_frequent_operation);
                return;
            }
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
        LinearLayout layout1;
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

