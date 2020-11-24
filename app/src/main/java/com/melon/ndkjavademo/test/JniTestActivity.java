package com.melon.ndkjavademo.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.melon.ndkjavademo.NdkImgUtil;
import com.melon.ndkjavademo.R;

import java.util.Arrays;

/**
 * 测试 jni c++ 与 java 方法互掉
 */
public class JniTestActivity extends AppCompatActivity {

    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jni_test);



    }

    /**
     * 数组传递 值
     * @param view
     */
    public void arrayClick(View view) {
        int[] arr={1,3,5};
        String []str={"我","在","学"};
        NDKUtil.arrayTest(arr,str);
        Log.e("han","C++修改值后 java中数组值:" + Arrays.toString(arr));

    }

    /**
     * javaBean 数据交互 方法调用
     * @param view
     */
    public void beanClick(View view) {
        BeanJava beanJava=new BeanJava();
        beanJava.setAge(3);
        beanJava.setName("HanChao");
        NDKUtil.javaBean(beanJava);

        Log.e("han","C++调用setAge()后 age="+beanJava.getAge());
    }
}