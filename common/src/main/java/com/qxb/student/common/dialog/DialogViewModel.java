package com.qxb.student.common.dialog;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.qxb.student.common.dialog.model.DialogParams;

/**
 * @author winky
 * @date 2018/8/15
 */
public class DialogViewModel extends AndroidViewModel {

    public DialogViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        params = null;
    }

    private DialogParams params;

    public DialogParams getParams() {
        return params;
    }

    public void setParams(DialogParams params) {
        this.params = params;
    }
}
