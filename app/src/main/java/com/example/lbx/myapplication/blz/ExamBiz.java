package com.example.lbx.myapplication.blz;

import com.example.lbx.myapplication.ExamApplication;
import com.example.lbx.myapplication.bean.Question;
import com.example.lbx.myapplication.dao.ExamDao;
import com.example.lbx.myapplication.dao.IExamDao;

import java.util.List;

/**
 * Created by lbx on 2017/7/3.
 */

public class ExamBiz implements IExamBiz{
    IExamDao dao;
    int examIndex=0;
    List<Question> questionList=null;

    public ExamBiz() {
        this.dao = new ExamDao();
    }

    @Override
    public void beginExam() {
        examIndex=0;
        dao.loadExamInfo();
        dao.loadQuestionLists();

    }

    @Override
    public Question getExam() {
        questionList=ExamApplication.getInstance().getmExamList();
        if (questionList!=null) {
            return questionList.get(examIndex);
        }else {
            return null;
        }
    }

    public Question getExam(int index) {
        questionList=ExamApplication.getInstance().getmExamList();
        examIndex=index;
        if (questionList!=null) {
            return questionList.get(examIndex);
        }else {
            return null;
        }
    }

    @Override
    public Question nextQuestion() {
        if (questionList!=null && examIndex<questionList.size()-1){
            examIndex++;
            return questionList.get(examIndex);
        }else {
            return null;
        }
    }

    @Override
    public Question preQuestion() {
        if (questionList!=null && examIndex>0){
            examIndex--;
            return questionList.get(examIndex);
        }else {
            return null;
        }
    }

    @Override
    public int commitExam() {
        int s=0;
        for (Question question : questionList) {
            String userAnswer=question.getUserAnswer();
            if (userAnswer!=null&&userAnswer.equals("")){
                if (question.getUserAnswer().equals(userAnswer)){
                    s++;
                }
            }
        }
        return s;
    }

    @Override
    public String getExamIndex() {
        return (examIndex+1)+".";
    }
}
