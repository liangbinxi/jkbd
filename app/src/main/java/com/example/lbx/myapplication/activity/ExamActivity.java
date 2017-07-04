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
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
    TextView tvExamInfo,tvExamTitle,tv0p1,tv0p2,tv0p3,tv0p4,tvLoad,tvNo;
    LinearLayout layoutLoading;
    ImageView mImageView;
    ProgressBar dialog;
    IExamBiz biz;
    boolean isloadExamInfo=false;
    boolean isloadQuestions=false;

    boolean isLoadExamInfoReceiver=false;
    boolean isLoadQuestionsReceiver=false;

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
        biz=new ExamBiz();
        loadData();
    }

    private void setListener() {
        registerReceiver(mLoadExamBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_INFO));
        registerReceiver(mLoadQuestionBroadcast,new IntentFilter(ExamApplication.LOAD_EXAM_QUESTION));
    }

    private void loadData() {
        layoutLoading.setEnabled(false);
        dialog.setVisibility(View.VISIBLE);
        tvLoad.setText("下载数据...");
        new Thread(new Runnable() {
            @Override
            public void run() {
                    biz.beginExam();
            }
        }).start();
    }

    private void initView() {
        layoutLoading=(LinearLayout) findViewById(R.id.layout_loading);
        dialog=(ProgressBar) findViewById(R.id.load_dialog);
        tvExamInfo=(TextView)findViewById(R.id.tv_examinfo);
        tvExamTitle = (TextView) findViewById(R.id.tv_exam_title);
        tvNo=(TextView) findViewById(R.id.tv_exam_no);
        tv0p1 = (TextView) findViewById(R.id.tv_op1);
        tv0p2 = (TextView) findViewById(R.id.tv_op2);
        tv0p3 = (TextView) findViewById(R.id.tv_op3);
        tv0p4 = (TextView) findViewById(R.id.tv_op4);
        tvLoad = (TextView) findViewById(R.id.tv_load);
        mImageView =(ImageView) findViewById(R.id.im_exam_image);
        layoutLoading.setOnClickListener(new View.OnClickListener() {
               @Override
              public void onClick(View v) {
                    loadData();
              }
        });
    }

    private void initData() {
        if (isLoadExamInfoReceiver&&isLoadQuestionsReceiver){
            if (isloadExamInfo&&isloadQuestions) {
                layoutLoading.setVisibility(View.GONE);
                item examInfo = ExamApplication.getInstance().getMitem();
                if (examInfo != null) {
                    showData(examInfo);
                }

                    showExam(biz.getExam());

            }else {
                layoutLoading.setEnabled(true);
                dialog.setVisibility(View.GONE);
                tvLoad.setText("下载失败，点击重新下载");
            }
        }
    }

    private void showExam(Question exam) {
        Log.e("showExam","showExam,exam="+exam);
        if (exam!=null){
            tvNo.setText(biz.getExamIndex());
            tvExamTitle.setText(exam.getQuestion());
            tv0p1.setText(exam.getItem1());
            tv0p2.setText(exam.getItem2());
            tv0p3.setText(exam.getItem3());
            tv0p4.setText(exam.getItem4());
            if (exam.getUrl()!=null&&!exam.getUrl().equals("")) {
                Picasso.with(ExamActivity.this)
                        .load(exam.getUrl())
                        .into(mImageView);
            }else {
                mImageView.setVisibility(View.GONE);
            }
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

    public void preExam(View view) {
        showExam(biz.preQuestion());
    }

    public void nextExam(View view) {
        showExam(biz.nextQuestion());
    }

    class loadExamBroadcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isSuccess = intent.getBooleanExtra(ExamApplication.LOAD_DATA_SUCCESS,false);
            Log.e("loadExamBroadcast","loadExamBroadcast,isSuccess="+isSuccess);
            if (isSuccess){
                isloadExamInfo=true;
            }
            isLoadExamInfoReceiver=true;
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
            isLoadQuestionsReceiver=true;
            initData();
        }
    }
}
