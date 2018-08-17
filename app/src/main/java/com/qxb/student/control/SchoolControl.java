package com.qxb.student.control;

import android.app.AlertDialog;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;

import com.qxb.student.R;
import com.qxb.student.common.adapter.FragmentAdapter;
import com.qxb.student.common.adapter.SchoolTagAdapter;
import com.qxb.student.common.dialog.SimpleDialog;
import com.qxb.student.common.dialog.model.DialogContent;
import com.qxb.student.common.listener.MultiClickUtil;
import com.qxb.student.common.module.CollectionRepository;
import com.qxb.student.common.module.SchoolRepository;
import com.qxb.student.common.module.bean.ApiModel;
import com.qxb.student.common.module.bean.MajorBat;
import com.qxb.student.common.module.bean.RongyUser;
import com.qxb.student.common.module.bean.SchoolDetail;
import com.qxb.student.common.module.bean.SchoolNews;
import com.qxb.student.common.module.bean.SchoolVideo;
import com.qxb.student.common.module.bean.ScoreBat;
import com.qxb.student.common.utils.ToastUtils;
import com.qxb.student.common.view.Toolbar;
import com.qxb.student.databinding.FragmentSchoolBinding;
import com.qxb.student.helper.HintHelper;
import com.qxb.student.ui.home.school.SchoolConductFragment;
import com.qxb.student.ui.home.school.SchoolDetailFragment;
import com.qxb.student.ui.home.school.SchoolIntroFragment;
import com.qxb.student.ui.home.school.SchoolRecruitMajorFragment;
import com.qxb.student.ui.widget.SchoolConsultFragment;

import java.util.Arrays;
import java.util.List;

/**
 * 学校
 *
 * @author winky
 * @date 2018/7/20
 */
public class SchoolControl extends AndroidViewModel {

    public SchoolControl(@NonNull Application application) {
        super(application);
    }

    private SchoolDetailFragment fragment;
    private FragmentSchoolBinding binding;

    private SchoolRepository schoolRepository = new SchoolRepository();
    private CollectionRepository collectionRepository = new CollectionRepository();

    private MutableLiveData<SchoolDetail> schoolLiveData = new MutableLiveData<>();
    private MutableLiveData<List<SchoolVideo>> schoolVideoLiveData = new MutableLiveData<>();
    private MutableLiveData<List<SchoolNews>> schoolNewsLiveData = new MutableLiveData<>();
    private MutableLiveData<List<MajorBat>> majorLiveData = new MutableLiveData<>();
    private MutableLiveData<List<ScoreBat>> scoreLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> registrationLiveData = new MutableLiveData<>();
    private MutableLiveData<ApiModel<List<RongyUser>>> rongUserLiveData = new MutableLiveData<>();

    @Override
    protected void onCleared() {
        super.onCleared();
        if (binding != null) {
            binding.unbind();
            binding = null;
        }
        schoolRepository = null;
        collectionRepository = null;
        schoolLiveData = null;
        schoolVideoLiveData = null;
        schoolNewsLiveData = null;
        majorLiveData = null;
        scoreLiveData = null;
        registrationLiveData = null;
        rongUserLiveData = null;
        fragment = null;
    }

    public void init(SchoolDetailFragment schoolDetailFragment, View view, String schoolId, int bat) {
        this.fragment = schoolDetailFragment;
        this.binding = DataBindingUtil.bind(view);
        final Toolbar toolbar = view.findViewById(R.id.toolbar);
        fragment.setSupportActionBar(toolbar);
        toolbar.setAppBarLayout(binding.appBarLayout);
        binding.viewPager.addOnPageChangeListener(pageChangeListener);
        binding.radioGroup.setOnCheckedChangeListener(checkedChangeListener);
        binding.radioGroup.check(binding.radioGroup.getChildAt(0).getId());
        schoolRepository.getSchoolById(schoolId, schoolLiveData);
        schoolLiveData.observe(fragment, new Observer<SchoolDetail>() {
            @Override
            public void onChanged(@Nullable SchoolDetail schoolDetail) {
                binding.setSchool(schoolDetail);
                binding.includeHeader.setSchool(schoolDetail);
                if (schoolDetail != null) {
                    binding.includeHeader.ivPlay.setVisibility(TextUtils.isEmpty(schoolDetail.getVideo_url()) ? View.GONE : View.VISIBLE);
                    toolbar.setTitle(schoolDetail.getSchool_name());
                    if (!TextUtils.isEmpty(schoolDetail.getTag())) {
                        String[] tags = schoolDetail.getTag().replaceAll("，", ",").replaceAll("；", ",").replaceAll(";", ",").split(",");
                        binding.includeHeader.flowLayout.setAdapter(new SchoolTagAdapter(Arrays.asList(tags)));
                    }
                }
            }
        });
        schoolVideoLiveData.observe(fragment, new Observer<List<SchoolVideo>>() {
            @Override
            public void onChanged(@Nullable List<SchoolVideo> schoolVideos) {
                int size = schoolVideos != null ? schoolVideos.size() : 0;
                binding.includeVideo.setVideo1(size > 0 ? schoolVideos.get(0) : null);
                binding.includeVideo.setVideo2(size > 1 ? schoolVideos.get(1) : null);
                binding.includeVideo.setVideoSize(size);
            }
        });
        schoolRepository.getSchoolVideoList(schoolId, "3", "1", schoolVideoLiveData);
        binding.viewPager.setAdapter(new FragmentAdapter(fragment.getChildFragmentManager(), Arrays.asList(
                SchoolRecruitMajorFragment.getInstance(schoolId, bat).setTitle(fragment.getString(R.string.school_major)),
                new SchoolIntroFragment().setTitle(fragment.getString(R.string.school_intro)),
                SchoolConductFragment.getInstance(schoolId).setTitle(fragment.getString(R.string.school_conduct))
        )));
        binding.viewPager.setCurrentItem(0);
        binding.includeHeader.ivPlay.setOnClickListener(clickListener);
        binding.layout1.setOnClickListener(clickListener);
        binding.layout2.setOnClickListener(clickListener);
        binding.layout3.setOnClickListener(clickListener);
        showWaitingDialog();
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!MultiClickUtil.isFastClick()) {
                return;
            }
            //跳转视频播放
            if (view.getId() == R.id.iv_play) {

                return;
            }
            if (!HintHelper.hasLogin(fragment.getContext())) {
                return;
            }
            if (!HintHelper.hasNetwork()) {
                return;
            }
            final SchoolDetail schoolDetail = binding.getSchool();
            if (schoolDetail == null) {
                return;
            }
            switch (view.getId()) {
                //咨询
                case R.id.layout1:
                    SchoolConsultFragment.create().show(fragment.getChildFragmentManager(), "");
                    break;
                //预报名
                case R.id.layout2:
                    if (schoolDetail.isIs_reg()) {
                        ToastUtils.toast(R.string.hint_repeat_baoming);
                    } else {
                        baoming(schoolDetail);
                    }
                    break;
                //关注
                case R.id.layout3:
                    if (schoolDetail.isIs_att()) {
                        collectionRepository.cancel(String.valueOf(schoolDetail.getId()), "3").observe(fragment, new Observer<Boolean>() {
                            @Override
                            public void onChanged(@Nullable Boolean aBoolean) {
                                if (aBoolean) {
                                    schoolDetail.setIs_att(false);
                                    binding.setSchool(schoolDetail);
                                    ToastUtils.toast(R.string.hint_cancel);
                                }
                            }
                        });
                    } else {
                        collectionRepository.add(String.valueOf(schoolDetail.getId()), "3", schoolDetail.getSchool_name()).observe(fragment, new Observer<Boolean>() {
                            @Override
                            public void onChanged(@Nullable Boolean aBoolean) {
                                if (aBoolean) {
                                    schoolDetail.setIs_att(true);
                                    binding.setSchool(schoolDetail);
                                    ToastUtils.toast(R.string.hint_concern_success);
                                }
                            }
                        });
                    }
                    break;
                default:
                    break;
            }
        }
    };

    private void baoming(final SchoolDetail schoolDetail) {
        DialogInterface.OnClickListener clickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == -1) {
                    schoolRepository.saveStudentRegistration(String.valueOf(schoolDetail.getId()), registrationLiveData);
                    registrationLiveData.observe(fragment, new Observer<Boolean>() {
                        @Override
                        public void onChanged(@Nullable Boolean aBoolean) {
                            if (aBoolean) {
                                ToastUtils.toast(R.string.hint_baoming_success);
                                schoolDetail.setIs_reg(true);
                                binding.setSchool(schoolDetail);
                            }
                        }
                    });
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getContext());
        builder.setMessage(R.string.hint_school_baoming)
                .setPositiveButton(R.string.hint_want_baoming, clickListener)
                .setNegativeButton(R.string.hint_no_thanks, clickListener)
                .create()
                .show();
    }

    private void showWaitingDialog() {
        SimpleDialog.with(fragment.getActivity()).content(DialogContent.loading()).show();
    }

    public void dissWaitingDialog() {
        SimpleDialog.with(fragment.getActivity()).cancle();
    }

    public LiveData<List<SchoolNews>> getSchoolNews(String schoolId, int page) {
        schoolRepository.getSchoolNews(schoolId, "3", "", String.valueOf(page), schoolNewsLiveData);
        return schoolNewsLiveData;
    }

    private RadioGroup.OnCheckedChangeListener checkedChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            int index = binding.radioGroup.indexOfChild(fragment.findViewById(i));
            if (binding.viewPager.getCurrentItem() != index) {
                binding.viewPager.setCurrentItem(index);
            }
        }
    };

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            binding.radioGroup.check(binding.radioGroup.getChildAt(i).getId());
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    public LiveData<SchoolDetail> getSchoolLiveData() {
        return schoolLiveData;
    }

    public LiveData<List<MajorBat>> getSchoolRecruitMajor(String schoolId) {
        schoolRepository.getSchoolRecruitMajor(schoolId, majorLiveData);
        return majorLiveData;
    }

    public LiveData<List<ScoreBat>> getSchoolScore(String schoolId) {
        schoolRepository.getSchoolScore(schoolId, scoreLiveData);
        return scoreLiveData;
    }

    public LiveData<ApiModel<List<RongyUser>>> teacherListBySchoolId() {
        SchoolDetail schoolDetail = schoolLiveData.getValue();
        if (schoolDetail != null) {
            schoolRepository.teacherListBySchoolId(String.valueOf(schoolDetail.getId()), rongUserLiveData);
        }
        return rongUserLiveData;
    }
}
