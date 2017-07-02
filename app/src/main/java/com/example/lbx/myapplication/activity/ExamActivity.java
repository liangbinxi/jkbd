package com.example.lbx.myapplication.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lbx.myapplication.ExamApplication;
import com.example.lbx.myapplication.R;
import com.example.lbx.myapplication.bean.item;

/**
 * Created by lbx on 2017/7/1.
 */

public class ExamActivity extends AppCompatActivity {
    TextView tvExamInfo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        initView();
        initData();
    }

    private void initView() {
        tvExamInfo=(TextView)findViewById(R.id.tv_examinfo);
    }

    private void initData() {
        item examInfo = ExamApplication.getInstance().getMitem();
        if (examInfo!=null){
            showData(examInfo);
        }
    }

    private void showData(item mitem) {
        tvExamInfo.setText(mitem.toString());
    }
}
