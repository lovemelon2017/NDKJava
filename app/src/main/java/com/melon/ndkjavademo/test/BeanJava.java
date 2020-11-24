package com.melon.ndkjavademo.test;

public class BeanJava {

    private int age;
    private String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    //静态方法调用

    public static String beanStaticMethod(){
        return "我是Java静态方法";
    }
}
