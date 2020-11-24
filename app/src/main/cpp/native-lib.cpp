#include <jni.h>
#include <string>
#include "android/bitmap.h"
#include "android/log.h"

#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG , "Tag", __VA_ARGS__)    // DEBUG

extern "C"
JNIEXPORT jintArray JNICALL
Java_com_melon_ndkjavademo_NdkImgUtil_getResultBitmap(JNIEnv *env, jclass clazz, jintArray buffer,
                                                      jint width, jint height) {


    jint *source = (*env).GetIntArrayElements(buffer, 0);

    int size = width * height;

    float bright = 0.2f * 255;

    //处理像素点
    int a, r, g, b;

    for (int x = 0; x < width; x++) {

        for (int y = 0; y < height; y++) {

            /**
             *  1 1 1 1
             *  1 2 1
             *
             *  查看像素点 如上矩阵 2位置 列 * 宽 + x
             *
             */
            int color = source[y * width + x];

            /**
             * 可以查看java Color.blue(color)里面计算
             */

            a = color >> 24;
            r = (color >> 16) & 0xFF;
            g = (color >> 8) & 0xFF;
            b = color & 0xFF;

            //美黑处理
            int ra = a - bright;
            int rr = r - bright;
            int rg = g - bright;
            int rb = b - bright;

            r = rr > 255 ? 255 : (rr < 0 ? 0 : rr);
            g = rg > 255 ? 255 : (rg < 0 ? 0 : rg);
            b = rb > 255 ? 255 : (rb < 0 ? 0 : rb);

            int argb = (a << 24) | (r << 16) | (g << 8) | b;

            //指针赋值
            source[y * width + x] = argb;

        }
    }

    jintArray result = (*env).NewIntArray(size);

    //设置数据
    (*env).SetIntArrayRegion(result, 0, size, source);
    //释放资源

    (*env).ReleaseIntArrayElements(buffer, source, 0);

    return result;
}


extern "C"
JNIEXPORT void JNICALL
Java_com_melon_ndkjavademo_test_NDKUtil_arrayTest(JNIEnv *env, jclass clazz, jintArray array,
                                                  jobjectArray str) {

    // 获取int数组数据
    //第二个参数 true 拷贝一个新的数据(新申请内存)  false 使用java数组 （地址）
    jint *jArr = env->GetIntArrayElements(array, NULL);
    //获取数组长度
    jsize sizeArr = env->GetArrayLength(array);
    //for 取值
    for (int i = 0; i < sizeArr; ++i) {
        LOGD("C++中获取java int 数组数据: %d ", *(jArr + i));
        //改变数组值
        *(jArr + i) = *(jArr + i) + 10;

    }

    jsize strLen = env->GetArrayLength(str);
    for (int i = 0; i < strLen; ++i) {
        jstring strJava = static_cast<jstring>(env->GetObjectArrayElement(str, i));

        const char *s = env->GetStringUTFChars(strJava, 0);
        LOGD("C++中获取java string 数组数据: %s ", s);
        //释放
        env->ReleaseStringUTFChars(strJava, s);
    }

    //释放引用
    //mode 类型 0 刷新java 并释放 1只刷新java 2只释放
    env->ReleaseIntArrayElements(array, jArr, 0);

}

extern "C"
JNIEXPORT void JNICALL
Java_com_melon_ndkjavademo_test_NDKUtil_javaBean(JNIEnv *env, jclass clazz, jobject bean) {

    //1通过反射获取java的类
    jclass beanClass = env->GetObjectClass(bean);

    //获取属性
    jfieldID fieldId1 = env->GetFieldID(beanClass, "age", "I");
    jint ageFiled = env->GetIntField(bean, fieldId1);
    LOGD("C++中获取java 属性age: %d ", ageFiled);



    /**
     * 2 找到相应方法
     * 3 签名 （参数）返回值
     *
     * boolean   Z
     * short     S
     * float     F
     * byte      B
     * int       I
     * double    D
     * char      C
     * long      J
     * void      V
     * 引用类型  L+全限定名
     * 数组      [+类型签名
     *
     *
     */
    //获取java get方法
    jmethodID id1 = env->GetMethodID(beanClass, "getAge", "()I");
    //调用
    jint age = env->CallIntMethod(bean, id1);
    LOGD("C++中获取java getAge()方法: %d ", age);

    //set方法
    jmethodID id2 = env->GetMethodID(beanClass, "setAge", "(I)V");
    env->CallVoidMethod(bean, id2, 10);

    //静态方法调用
    jmethodID id3 = env->GetStaticMethodID(beanClass, "beanStaticMethod", "()Ljava/lang/String;");

    jstring strJava = static_cast<jstring>(env->CallStaticObjectMethod(beanClass, id3));

    const char *s = env->GetStringUTFChars(strJava, 0);
    LOGD("C++中获取java 静态方法: %s ", s);
    //释放
    env->ReleaseStringUTFChars(strJava, s);



}