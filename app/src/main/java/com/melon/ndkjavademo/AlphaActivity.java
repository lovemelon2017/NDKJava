package com.melon.ndkjavademo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.melon.ndkjavademo.test.JniTestActivity;

public class AlphaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alpha);
    }

    /**
     * jni 基本学习
     * @param view
     */
    public void mainJniClick(View view) {
        startActivity(new Intent(this,JniTestActivity.class));
    }

    /**
     * 图片处理速度
     * @param view
     */
    public void mainCAndJavaClick(View view) {
        startActivity(new Intent(this,MainActivity.class));
    }
}