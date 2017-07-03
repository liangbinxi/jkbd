package com.example.lbx.myapplication.blz;

import com.example.lbx.myapplication.dao.ExamDao;
import com.example.lbx.myapplication.dao.IExamDao;

/**
 * Created by lbx on 2017/7/3.
 */

public class ExamBiz implements IExamBiz{
    IExamDao dao;

    public ExamBiz() {
        this.dao = new ExamDao();
    }

    @Override
    public void beginExam() {
        dao.loadExamInfo();
        dao.loadQuestionLists();
    }

    @Override
    public void nextQuestion() {

    }

    @Override
    public void preQuestion() {

    }

    @Override
    public void commitExam() {

    }
}
