package com.melon.ndkjavademo;

import android.graphics.Bitmap;
import android.graphics.Color;

public class JavaImgUtil {

    //调节亮度
    public static float bright = 0.2f;

    public static Bitmap getBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        //创建空白图片
        Bitmap emptyBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

        //处理像素点
        int a, r, g, b;

        //亮度处理
        int bri = (int) (bright * 255);

        for (int x = 0; x < width; x++) {

            for (int y = 0; y < height; y++) {

                int color = bitmap.getPixel(x, y);

                a = Color.alpha(color);
                r = Color.red(color);
                g = Color.green(color);
                b = Color.blue(color);

                //美黑处理
                int ra = a - bri;
                int rr = r - bri;
                int rg = g - bri;
                int rb = b - bri;

                r = rr > 255 ? 255 : (rr < 0 ? 0 : rr);
                g = rg > 255 ? 255 : (rg < 0 ? 0 : rg);
                b = rb > 255 ? 255 : (rb < 0 ? 0 : rb);

                int argb = Color.argb(a, r, g, b);

                emptyBitmap.setPixel(x,y,argb);


            }


        }

        return emptyBitmap;

    }

}
