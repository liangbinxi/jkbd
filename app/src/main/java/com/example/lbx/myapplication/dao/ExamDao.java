package com.example.lbx.myapplication.dao;

import android.util.Log;

import com.example.lbx.myapplication.ExamApplication;
import com.example.lbx.myapplication.bean.Question;
import com.example.lbx.myapplication.bean.item;
import com.example.lbx.myapplication.bean.result;
import com.example.lbx.myapplication.utils.OkHttpUtils;
import com.example.lbx.myapplication.utils.ResultUtils;

import java.util.List;

/**
 * Created by lbx on 2017/7/3.
 */

public class ExamDao implements IExamDao{
    @Override
    public void loadExamInfo() {
        OkHttpUtils<item> utils=new OkHttpUtils<>(ExamApplication.getInstance());
        String uri="http://101.251.196.90:8080/JztkServer/examInfo";
        utils.url(uri)
                .targetClass(item.class)
                .execute(new OkHttpUtils.OnCompleteListener<item>() {
                    @Override
                    public void onSuccess(item result) {
                        Log.e("main","result="+result);
                        ExamApplication.getInstance().setMitem(result);

                    }

                    @Override
                    public void onError(String error) {
                        Log.e("main","error="+error);
                    }
                });
    }

    @Override
    public void loadQuestionLists() {
        OkHttpUtils<String> utils1=new OkHttpUtils<String>(ExamApplication.getInstance());
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
                                ExamApplication.getInstance().setmExamList(list);
                            }
                        }
                    }
                    @Override
                    public void onError(String error) {
                        Log.e("main","error="+error);
                    }
                });
    }
}
