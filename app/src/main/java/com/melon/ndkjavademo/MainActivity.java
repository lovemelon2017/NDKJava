package com.melon.ndkjavademo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    ImageView ivImage;
    Bitmap bitmap;

    boolean isShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivImage=findViewById(R.id.image_iv);
        bitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.img);


    }

    /**
     * java处理图片
     * @param view
     */
    public void javaImage(View view) {

        if (!isShow){
            Toast.makeText(this,"先显示原图",Toast.LENGTH_SHORT).show();
            return;
        }
        long millis1 = System.currentTimeMillis();

        if (bitmap!=null){
            Bitmap imageBt = JavaImgUtil.getBitmap(this.bitmap);
            if (imageBt!=null){
                ivImage.setImageBitmap(imageBt);
            }
        }
        long millis2 = System.currentTimeMillis();
        long time=millis2-millis1;
        Toast.makeText(this,"耗时 毫秒: "+time,Toast.LENGTH_SHORT).show();

    }

    /**
     * c++处理
     * @param view
     */

    public void ndkImage(View view) {

        if (!isShow){
            Toast.makeText(this,"先显示原图",Toast.LENGTH_SHORT).show();
            return;
        }
        long millis1 = System.currentTimeMillis();

        if (bitmap!=null){
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int buffer[]=new int[width*height];
            bitmap.getPixels(buffer,0,width,0,0,width,height);
            int[] resultBitmap = NdkImgUtil.getResultBitmap(buffer, width, height);
            Bitmap result=Bitmap.createBitmap(resultBitmap,width,height, Bitmap.Config.RGB_565);
            ivImage.setImageBitmap(result);
        }

        long millis2 = System.currentTimeMillis();
        long time=millis2-millis1;
        Toast.makeText(this,"耗时 毫秒: "+time,Toast.LENGTH_SHORT).show();


    }



    public void setImage(View view) {
        if (bitmap!=null){
            isShow=true;
            ivImage.setImageBitmap(bitmap);
        }
    }
}