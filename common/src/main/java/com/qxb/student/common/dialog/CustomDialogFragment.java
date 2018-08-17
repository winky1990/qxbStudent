package com.qxb.student.common.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qxb.student.common.R;
import com.qxb.student.common.dialog.model.DialogContent;
import com.qxb.student.common.dialog.model.DialogFooter;
import com.qxb.student.common.dialog.model.DialogHeader;
import com.qxb.student.common.dialog.model.DialogParams;
import com.qxb.student.common.utils.SysUtils;

/**
 * @author winky
 * @date 2018/8/15
 */
public class CustomDialogFragment extends DialogFragment {

    private DialogViewModel viewModel;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        viewModel = ViewModelProviders.of(getActivity()).get(DialogViewModel.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTheme(R.style.AlertDialog);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(inflateView());
        return builder.create();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getDialog() == null) {
            return;
        }
        Dialog dialog = getDialog();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                DialogParams params = viewModel.getParams();
                if (params != null && params.getContent().getDialogMode().equals(DialogMode.INPUT)) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (inputMethodManager != null) {
                        inputMethodManager.hideSoftInputFromWindow(
                                getActivity().getWindow().getDecorView().getWindowToken(), 0);
                        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                    }
                }
                viewModel.setParams(null);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() == null) {
            return;
        }
        Dialog dialog = getDialog();
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        if (viewModel.getParams() != null) {
            DialogParams params = viewModel.getParams();
            DialogMode dialogMode = params.getContent().getDialogMode();
            if (DialogMode.INPUT.equals(dialogMode)) {
                dialog.setCanceledOnTouchOutside(false);
            } else if (DialogMode.LOADING.equals(dialogMode)) {
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(true);
                if (params.getWidth() == 0) {
                    params.setWidth(SysUtils.getInstance().getDisplayMetrics().widthPixels / 2);
                }
            }
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            window.setLayout(
                    params.getWidth() == 0 ? ViewGroup.LayoutParams.WRAP_CONTENT : params.getWidth(),
                    params.getHeight() == 0 ? ViewGroup.LayoutParams.WRAP_CONTENT : params.getHeight());
        }
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        if (isAdded()) {
            transaction.remove(this);
        }
        transaction.add(this, tag);
        transaction.commitAllowingStateLoss();
    }

    private View inflateView() {
        DialogParams params = viewModel.getParams();
        LinearLayout rootView = new LinearLayout(getContext());
        rootView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        rootView.setOrientation(LinearLayout.VERTICAL);
        if (params.getBackgroundColor() != 0) {
            rootView.setBackgroundColor(params.getBackgroundColor());
        }
        if (params.getHeader() != null) {
            inflateHeader(rootView, params.getHeader());
        }
        if (params.getContent() != null) {
            inflateContent(rootView, params.getContent());
        }
        inflateFooter(rootView, params.getPositive(), params.getNegative());
        return rootView;
    }

    private void inflateFooter(LinearLayout rootView, DialogFooter positive, DialogFooter negative) {
        View footerView = View.inflate(getContext(), R.layout.dialog_footer, null);
        if (negative != null || positive != null) {
            footerView.setVisibility(View.VISIBLE);
            setFooter((TextView) footerView.findViewById(R.id.text1), negative);
            setFooter((TextView) footerView.findViewById(R.id.text2), positive);
            rootView.addView(footerView);
        } else {
            footerView.setVisibility(View.GONE);
        }
    }

    private void setFooter(TextView textView, final DialogFooter dialogFooter) {
        if (dialogFooter != null) {
            textView.setVisibility(View.VISIBLE);
            setTextSize(textView, dialogFooter.getTextSize());
            setTextColor(textView, dialogFooter.getTextColor());
            textView.setText(dialogFooter.getText());
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                    dialogFooter.getClickListener().onClick(view);
                }
            });
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    private void inflateContent(LinearLayout rootView, @NonNull DialogContent content) {
        switch (content.getDialogMode()) {
            case MESSAGE: {
                TextView textView = View.inflate(getContext(), R.layout.dialog_content_message, null).findViewById(R.id.text1);
                setContentText(textView, content);
                rootView.addView(textView);
            }
            break;
            case LIST:
                break;
            case INPUT: {
                View view = View.inflate(getContext(), R.layout.dialog_content_input, null);
                EditText editText = view.findViewById(R.id.edit1);
                editText.setHint(content.getHintText());
                editText.setText(content.getText());
                if (content.getHintTextColor() != 0) {
                    editText.setHintTextColor(content.getHintTextColor());
                }
                if (content.getTextColor() != 0) {
                    editText.setTextColor(content.getTextColor());
                }
                if (content.getTextSize() > 0) {
                    editText.setTextSize(content.getTextSize());
                }
                editText.setHeight(content.getHeight());
                if (content.getGravity() != 0) {
                    editText.setGravity(content.getGravity());
                }
                if (!TextUtils.isEmpty(content.getText())) {
                    editText.setSelection(content.getText().length());
                }
                rootView.addView(view);
            }
            break;
            case PROGRESSBAR:
                break;
            case LOADING: {
                View view = View.inflate(getContext(), R.layout.dialog_content_loading, null);
                TextView textView = view.findViewById(R.id.text1);
                setContentText(textView, content);
                rootView.addView(view);
            }
            break;
            default:
                break;
        }
    }

    private void inflateHeader(LinearLayout rootView, @NonNull DialogHeader header) {
        TextView textView = View.inflate(getContext(), R.layout.dialog_title, null).findViewById(R.id.text1);
        textView.setText(header.getTitle());
        setTextColor(textView, header.getTextColor());
        setTextSize(textView, header.getTextSize());
        rootView.addView(textView);
    }

    private void setContentText(TextView textView, DialogContent content) {
        setText(textView, content.getText());
        setTextColor(textView, content.getTextColor());
        setGravity(textView, content.getGravity());
        setTextSize(textView, content.getTextSize());
    }

    private void setTextColor(TextView textView, @ColorInt int textColor) {
        if (textColor != 0) {
            textView.setTextColor(textColor);
        }
    }

    private void setTextSize(TextView textView, int textSize) {
        if (textSize > 0) {
            textView.setTextSize(textSize);
        }
    }

    private void setText(TextView textView, CharSequence content) {
        if (!TextUtils.isEmpty(content)) {
            textView.setText(content);
        }
    }

    private void setGravity(TextView textView, int gravity) {
        if (gravity != 0) {
            textView.setGravity(gravity);
        }
    }
}
