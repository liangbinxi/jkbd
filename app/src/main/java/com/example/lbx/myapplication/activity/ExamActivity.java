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
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
    CheckBox cb01,cb02,cb03,cb04;
    CheckBox[] cbs=new CheckBox[4];
    LinearLayout layoutLoading,layout03,layout04;
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
        layout03=(LinearLayout) findViewById(R.id.layout_03);
        layout04=(LinearLayout) findViewById(R.id.layout_04);
        dialog=(ProgressBar) findViewById(R.id.load_dialog);
        tvExamInfo=(TextView)findViewById(R.id.tv_examinfo);
        tvExamTitle = (TextView) findViewById(R.id.tv_exam_title);
        tvNo=(TextView) findViewById(R.id.tv_exam_no);
        tv0p1 = (TextView) findViewById(R.id.tv_op1);
        tv0p2 = (TextView) findViewById(R.id.tv_op2);
        tv0p3 = (TextView) findViewById(R.id.tv_op3);
        tv0p4 = (TextView) findViewById(R.id.tv_op4);
        cb01= (CheckBox) findViewById(R.id.cb_01);
        cb02= (CheckBox) findViewById(R.id.cb_02);
        cb03= (CheckBox) findViewById(R.id.cb_03);
        cb04= (CheckBox) findViewById(R.id.cb_04);
        cbs[0]=cb01;
        cbs[1]=cb02;
        cbs[2]=cb03;
        cbs[3]=cb04;
        tvLoad = (TextView) findViewById(R.id.tv_load);
        mImageView =(ImageView) findViewById(R.id.im_exam_image);
        layoutLoading.setOnClickListener(new View.OnClickListener() {
               @Override
              public void onClick(View v) {
                    loadData();
              }
        });
        cb01.setOnCheckedChangeListener(listener);
        cb02.setOnCheckedChangeListener(listener);
        cb03.setOnCheckedChangeListener(listener);
        cb04.setOnCheckedChangeListener(listener);
    }
    CompoundButton.OnCheckedChangeListener listener=new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                int userAnswer = 0;
                switch (buttonView.getId()) {
                    case R.id.cb_01:
                        userAnswer = 1;
                        break;
                    case R.id.cb_02:
                        userAnswer = 2;
                        break;
                    case R.id.cb_03:
                        userAnswer = 3;
                        break;
                    case R.id.cb_04:
                        userAnswer = 4;
                        break;
                }
                if (userAnswer > 0) {
                    for (CheckBox cb : cbs) {
                        cb.setChecked(false);
                    }
                    cbs[userAnswer - 1].setChecked(true);
                }
            }
        }
    };

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
            layout03.setVisibility(exam.getItem3().equals("")?View.GONE:View.VISIBLE);
            cb03.setVisibility(exam.getItem3().equals("")?View.GONE:View.VISIBLE);
            layout04.setVisibility(exam.getItem4().equals("")?View.GONE:View.VISIBLE);
            cb04.setVisibility(exam.getItem4().equals("")?View.GONE:View.VISIBLE);
            if (exam.getUrl()!=null&&!exam.getUrl().equals("")) {
                Picasso.with(ExamActivity.this)
                        .load(exam.getUrl())
                        .into(mImageView);
            }else {
                mImageView.setVisibility(View.GONE);
            }
            resetOptions();
        }
    }

    private void resetOptions() {
        for (CheckBox cb : cbs) {
            cb.setChecked(false);
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
