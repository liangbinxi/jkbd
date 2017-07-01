package com.example.lbx.myapplication.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import com.example.lbx.myapplication.R;
import com.example.lbx.myapplication.utils.OkHttpUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void test(View view){
        OkHttpUtils<EditorInfo> utils=new OkHttpUtils<>(getApplicationContext());
        String uri="http://101.251.196.90:8080/JztkServer/examInfo";
        utils.url(uri)
                .targetClass(EditorInfo.class)
                .execute(new OkHttpUtils.OnCompleteListener<EditorInfo>() {
                    @Override
                    public void onSuccess(EditorInfo result) {
                        Log.e("main","result="+result);
                    }

                    @Override
                    public void onError(String error) {
                        Log.e("main","error="+error);
                    }
                });
        startActivity(new Intent(MainActivity.this,ExamActivity.class));
    }

    public void exit(View view) {
        finish();
    }
}
