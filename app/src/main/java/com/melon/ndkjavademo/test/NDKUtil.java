package com.melon.ndkjavademo.test;

public class NDKUtil {


    //数组方法 测试
    public static native void arrayTest(int[] array,String[] str);

    public static native void javaBean(BeanJava bean);

}
