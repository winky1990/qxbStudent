package com.qxb.student.common.module;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.qxb.student.common.http.task.DataHandle;
import com.qxb.student.common.http.task.HttpTask;
import com.qxb.student.common.module.api.EvaluationApi;
import com.qxb.student.common.module.bean.BaseEvaluationResult;
import com.qxb.student.common.utils.UserCache;

/**
 * @author winky
 * @date 2018/8/29
 */
public class AssessRepository extends BaseRepository {

    public void getResultNew(MutableLiveData<BaseEvaluationResult> liveData, String result) {
        new HttpTask<BaseEvaluationResult>()
                .call(httpUtils.create(EvaluationApi.class).getResultNew(
                        UserCache.getInstance().getUserId(), UserCache.getInstance().getUserName(), result))
                .netLive(liveData)
                .handle(new DataHandle<BaseEvaluationResult>() {
                    @Override
                    public void handle(@NonNull BaseEvaluationResult data) {

                    }
                }).start();
    }
}
