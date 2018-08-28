package com.qxb.student.type;

import android.support.annotation.StringDef;

import static com.qxb.student.type.FunctionType.*;


/**
 * @author winky
 * @date 2018/8/28
 */
@StringDef({
        INTELLIGENT_SELECTION_SCHOOL,
        CONSULT_EXPERT,
        LISTEN_EXPLAIN,
        ASSESS,
        UNIVERSITY_LIB,
        MAJOR_LIB,
        ENROLLMENT_ANSWER,
        INDEPENDENT_RECRUIT,
        ART_RECRUIT,
        ABROAD_STUDY,
        UNIVERSITY_RANKING,
        DISCIPLINE_RANKING,
        FRANCE_STUDY,
        SKILL_CULTIVATE,
        FRACTION_PARAGRAPH,
        SELECT_MAJOR
})
public @interface FunctionType {
    /**
     * 智能选校
     */
    String INTELLIGENT_SELECTION_SCHOOL = "1";
    /**
     * 问专家
     */
    String CONSULT_EXPERT = "2";
    /**
     * 听讲座
     */
    String LISTEN_EXPLAIN = "3";
    /**
     * 测评
     */
    String ASSESS = "4";
    /**
     * 院校库
     */
    String UNIVERSITY_LIB = "5";
    /**
     * 专业库
     */
    String MAJOR_LIB = "6";
    /**
     * 招办答疑
     */
    String ENROLLMENT_ANSWER = "7";
    /**
     * 自主招生
     */
    String INDEPENDENT_RECRUIT = "8";
    /**
     * 艺考招生
     */
    String ART_RECRUIT = "9";
    /**
     * 出国留学
     */
    String ABROAD_STUDY = "10";
    /**
     * 大学排名
     */
    String UNIVERSITY_RANKING = "11";
    /**
     * 学科排名
     */
    String DISCIPLINE_RANKING = "12";
    /**
     * 法国直通车
     */
    String FRANCE_STUDY = "13";
    /**
     * 技能培训
     */
    String SKILL_CULTIVATE = "14";
    /**
     * 一分一段
     */
    String FRACTION_PARAGRAPH = "15";
    /**
     * 选专业
     */
    String SELECT_MAJOR = "16";

}
