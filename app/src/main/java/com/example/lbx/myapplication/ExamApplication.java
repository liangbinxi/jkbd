package com.example.lbx.myapplication;

import android.app.Application;
import android.util.Log;

import com.example.lbx.myapplication.activity.ExamActivity;
import com.example.lbx.myapplication.bean.Question;
import com.example.lbx.myapplication.bean.item;
import com.example.lbx.myapplication.bean.result;
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
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        initData();
    }

    public static ExamApplication getInstance(){
        return instance;
    }

    private void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpUtils<item> utils=new OkHttpUtils<>(instance);
                String uri="http://101.251.196.90:8080/JztkServer/examInfo";
                utils.url(uri)
                        .targetClass(item.class)
                        .execute(new OkHttpUtils.OnCompleteListener<item>() {
                            @Override
                            public void onSuccess(item result) {
                                Log.e("main","result="+result);
                                mitem=result;
                            }

                            @Override
                            public void onError(String error) {
                                Log.e("main","error="+error);
                            }
                        });
                OkHttpUtils<String> utils1=new OkHttpUtils<String>(instance);
                String url2="http://101.251.196.90:8080/JztkServer/getQuestions?testType=rand";
                utils1.url(url2)
                        .targetClass(String.class)
                        .execute(new OkHttpUtils.OnCompleteListener<String>() {
                            @Override
                            public void onSuccess(String jsonStr) {
                                result result = ResultUtils.getListResultFromJson(jsonStr);
                                if(result!=null && result.getError_code()==0){
                                    List<Question> list=result.getResult();
                                    if (list!=null && list.size()>0){
                                        mExamList=list;
                                    }
                                }
                            }

                            @Override
                            public void onError(String error) {
                                Log.e("main","error="+error);
                            }
                        });
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
