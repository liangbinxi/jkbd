package com.example.lbx.myapplication;

import android.app.Application;
import android.util.Log;

import com.example.lbx.myapplication.activity.ExamActivity;
import com.example.lbx.myapplication.bean.Question;
import com.example.lbx.myapplication.bean.item;
import com.example.lbx.myapplication.bean.result;
import com.example.lbx.myapplication.blz.ExamBiz;
import com.example.lbx.myapplication.blz.IExamBiz;
import com.example.lbx.myapplication.utils.OkHttpUtils;
import com.example.lbx.myapplication.utils.ResultUtils;

import java.util.List;

/**
 * Created by lbx on 2017/7/2.
 */

public class ExamApplication extends Application{
    item mitem;
    List<Question> mExamList;
    private static ExamApplication instance;
    IExamBiz biz;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        biz=new ExamBiz();
        initData();
    }

    public static ExamApplication getInstance(){
        return instance;
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                biz.beginExam();
            }
        }).start();

    }

    public item getMitem() {
        return mitem;
    }

    public void setMitem(item mitem) {
        this.mitem = mitem;
    }

    public List<Question> getmExamList() {
        return mExamList;
    }

    public void setmExamList(List<Question> mExamList) {
        this.mExamList = mExamList;
    }


}
