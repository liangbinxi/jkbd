package com.example.lbx.myapplication.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lbx.myapplication.ExamApplication;
import com.example.lbx.myapplication.R;
import com.example.lbx.myapplication.bean.Question;
import com.example.lbx.myapplication.bean.item;
import com.example.lbx.myapplication.blz.ExamBiz;
import com.example.lbx.myapplication.blz.IExamBiz;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by lbx on 2017/7/1.
 */

public class ExamActivity extends AppCompatActivity {
    TextView tvExamInfo,tvExamTitle,tv0p1,tv0p2,tv0p3,tv0p4;
    ImageView mImageView;
    IExamBiz biz;
    boolean isloadExamInfo=false;
    boolean isloadQuestions=false;
    loadExamBroadcast mLoadExamBroadcast;
    loadQuestionBroadcast mLoadQuestionBroadcast;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        mLoadExamBroadcast=new loadExamBroadcast();
        mLoadQuestionBroadcast=new loadQuestionBroadcast();
        setListener();
        initView();
        loadData();
    }

    private void setListener() {
        registerReceiver(mLoadExamBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_INFO));
        registerReceiver(mLoadQuestionBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_QUESTION));
    }

    private void loadData() {
        biz=new ExamBiz();
        new Thread(new Runnable() {
            @Override
            public void run() {
                    biz.beginExam();
            }
        }).start();
    }

    private void initView() {
        tvExamInfo=(TextView)findViewById(R.id.tv_examinfo);
        tvExamTitle = (TextView) findViewById(R.id.tv_exam_title);
        tv0p1 = (TextView) findViewById(R.id.tv_op1);
        tv0p2 = (TextView) findViewById(R.id.tv_op2);
        tv0p3 = (TextView) findViewById(R.id.tv_op3);
        tv0p4 = (TextView) findViewById(R.id.tv_op4);
        mImageView =(ImageView) findViewById(R.id.im_exam_image);
    }

    private void initData() {
        if (isloadExamInfo&&isloadQuestions) {
            item examInfo = ExamApplication.getInstance().getMitem();
            if (examInfo != null) {
                showData(examInfo);
            }
            List<Question> questionList = ExamApplication.getInstance().getmExamList();
            if (questionList != null) {
                showExam(questionList);
            }
        }
    }

    private void showExam(List<Question> questionList) {
        Question exam = questionList.get(0);
        if (exam!=null){
            tvExamTitle.setText(exam.getQuestion());
            tv0p1.setText(exam.getItem1());
            tv0p2.setText(exam.getItem2());
            tv0p3.setText(exam.getItem3());
            tv0p4.setText(exam.getItem4());
            Picasso.with(ExamActivity.this)
                    .load(exam.getUrl())
                    .into(mImageView);
        }
    }

    private void showData(item mitem) {
        tvExamInfo.setText(mitem.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLoadExamBroadcast!=null){
            unregisterReceiver(mLoadExamBroadcast);
        }
        if (mLoadQuestionBroadcast!=null){
            unregisterReceiver(mLoadQuestionBroadcast);
        }
    }

    class loadExamBroadcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSuccess = intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS,false);
            Log.e("loadExamBroadcast","loadExamBroadcast,isSuccess="+isSuccess);
            if (isSuccess){
                isloadExamInfo=true;
            }
            initData();
        }
    }
    class loadQuestionBroadcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSuccess = intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS,false);
            Log.e("loadQuestionBroadcast","loadQuestionBroadcast,isSuccess="+isSuccess);
            if (isSuccess){
                isloadQuestions=true;
            }
            initData();
        }
    }
}
