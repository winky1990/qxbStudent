package com.qxb.student.control;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.qxb.student.common.module.SchoolRepository;
import com.qxb.student.common.module.bean.SchoolCondition;

/**
 * @author winky
 * @date 2018/9/4
 */
public class SchoolLibraryRetrieveControl extends AndroidViewModel {

    private SchoolRepository schoolRepository = new SchoolRepository();
    private MutableLiveData<SchoolCondition> schoolConditionMutableLiveData = new MutableLiveData<>();

    public SchoolLibraryRetrieveControl(@NonNull Application application) {
        super(application);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        schoolRepository = null;
        schoolConditionMutableLiveData = null;
    }

    public MutableLiveData<SchoolCondition> getSchoolLibPage() {
        schoolRepository.getSchoolLibPage(schoolConditionMutableLiveData);
        return schoolConditionMutableLiveData;
    }
}
