package com.example.lbx.myapplication.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lbx.myapplication.ExamApplication;
import com.example.lbx.myapplication.R;
import com.example.lbx.myapplication.bean.Question;

import java.util.List;

/**
 * Created by lbx on 2017/7/6.
 */

public class QuestionAdapter extends BaseAdapter{
    Context mContext;
    List<Question> questionList;
    public QuestionAdapter(Context context) {
        mContext = context;
        questionList=ExamApplication.getInstance().getmExamList();
    }

    @Override
    public int getCount() {
        return questionList==null?0:questionList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=View.inflate(mContext, R.layout.item_question,null);
        TextView tvNo= (TextView) view.findViewById(R.id.tv_no);
        ImageView ivQuestion= (ImageView) view.findViewById(R.id.iv_question);
        String ua=questionList.get(position).getUserAnswer();
        if (ua!=null&&!ua.equals("")){
            ivQuestion.setImageResource(R.mipmap.answer24x24);
        }else {
            ivQuestion.setImageResource(R.mipmap.ques24x24);
        }

        tvNo.setText("第"+(position+1)+"题");
        return view;
    }
}
