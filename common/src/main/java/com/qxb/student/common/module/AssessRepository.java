package com.qxb.student.common.module;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.qxb.student.common.Constant;
import com.qxb.student.common.http.task.ClientTask;
import com.qxb.student.common.http.task.DataHandle;
import com.qxb.student.common.http.task.HttpTask;
import com.qxb.student.common.module.api.EvaluationApi;
import com.qxb.student.common.module.bean.HollanderAssess;
import com.qxb.student.common.module.bean.MbtiResult;
import com.qxb.student.common.module.bean.tab.Configure;
import com.qxb.student.common.utils.JsonUtils;
import com.qxb.student.common.utils.UserCache;

/**
 * @author winky
 * @date 2018/8/29
 */
public class AssessRepository extends BaseRepository {

    public void getResultNew(MutableLiveData<MbtiResult> liveData, String result) {
        new HttpTask<MbtiResult>()
                .call(httpUtils.create(EvaluationApi.class).getResultNew(
                        UserCache.getInstance().getUserId(), UserCache.getInstance().getUserName(), result))
                .netLive(liveData)
                .localLive(new ClientTask<MbtiResult>() {
                    @Override
                    public MbtiResult reqInSQLite(int pageIndex) {
                        return JsonUtils.getInstance().toBean(roomUtils.configureDao().query(Constant.CONFIG_MBTI_RESULT).getValue(), MbtiResult.class);
                    }
                })
                .handle(new DataHandle<MbtiResult>() {
                    @Override
                    public void handle(@NonNull MbtiResult data) {
                        roomUtils.configureDao().insert(new Configure(Constant.CONFIG_MBTI_RESULT, JsonUtils.getInstance().toJson(data)));
                    }
                }).start();
    }

    public void getResultHolland(MutableLiveData<HollanderAssess> liveData, String result) {
        new HttpTask<HollanderAssess>()
                .call(httpUtils.create(EvaluationApi.class).getResultHolland(
                        UserCache.getInstance().getUserId(), UserCache.getInstance().getUserName(), result))
                .netLive(liveData)
                .localLive(new ClientTask<HollanderAssess>() {
                    @Override
                    public HollanderAssess reqInSQLite(int pageIndex) {
                        return JsonUtils.getInstance().toBean(roomUtils.configureDao().query(Constant.CONFIG_HOLLANDER_RESULT).getValue(), HollanderAssess.class);
                    }
                })
                .handle(new DataHandle<HollanderAssess>() {
                    @Override
                    public void handle(@NonNull HollanderAssess data) {
                        roomUtils.configureDao().insert(new Configure(Constant.CONFIG_HOLLANDER_RESULT, JsonUtils.getInstance().toJson(data)));
                    }
                }).start();
    }
}
