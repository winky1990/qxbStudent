package com.qxb.student.common.module.bean;

import java.util.List;

/**
 * @author winky
 * @date 2018/8/27
 */
public class AssessQuestion {

    private List<AssessAnswer> answers;
    private String question;
    private boolean isAnswer;
    private int id;

    public List<AssessAnswer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AssessAnswer> answers) {
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public boolean isAnswer() {
        return isAnswer;
    }

    public void setAnswer(boolean answer) {
        isAnswer = answer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
