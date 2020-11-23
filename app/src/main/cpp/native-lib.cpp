#include <jni.h>
#include <string>
#include "android/bitmap.h"


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
            source[y*width+x]=argb;

        }
    }

    jintArray result=(*env).NewIntArray(size);

    //设置数据
    (*env).SetIntArrayRegion(result,0,size,source);
    //释放资源

    (*env).ReleaseIntArrayElements(buffer,source,0);

    return  result;
}