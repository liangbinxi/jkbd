package com.example.lbx.myapplication.blz;

import com.example.lbx.myapplication.bean.Question;

/**
 * Created by lbx on 2017/7/3.
 */

public interface IExamBiz {
    void beginExam();
    Question getExam();
    Question nextQuestion();
    Question preQuestion();
    int commitExam();
    String getExamIndex();
}
