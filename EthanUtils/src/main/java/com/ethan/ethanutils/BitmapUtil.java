package com.ethan.ethanutils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.ImageView;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;

/**
 * NOTE:
 *
 * @author wxc 2021/11/14
 * @version 1.0.0
 */
public class BitmapUtil {

    /**
     * 通过路径压缩Bitmap
     * @param path Bitmap路径
     * @param imageView 图片显示窗口
     * @return 压缩后的Bitmap
     */
    public static Bitmap getScaledBitmap(String path, ImageView imageView) {
        return getScaledBitmap(path, imageView.getMeasuredWidth(), imageView.getMeasuredHeight());
    }

    /**
     * 通过Uri压缩Bitmap
     * @param uri Bitmap Uri
     * @param imageView 图片显示窗口
     * @return 压缩后的Bitmap
     */
    public static Bitmap getScaledBitmap(Uri uri, Context context, ImageView imageView) {
        return getScaledBitmap(uri, context, imageView.getMeasuredWidth(), imageView.getMeasuredHeight());
    }

    public static Bitmap getScaledBitmap(String path, int destWidth, int destHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//不加载,只想看看Bitmap属性数据
        BitmapFactory.decodeFile(path, options);

        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;

        int inSampleSize = 1;
        if (srcWidth > destWidth || srcHeight > destHeight) {
            //图片宽高除以控件高度
            float widthScale = srcWidth / destWidth;
            float heightScale = srcHeight / destHeight;
            inSampleSize = Math.round(Math.max(heightScale, widthScale));//得到最接近最大缩放率的整数
        }

        //重新设定Options
        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;
        return BitmapFactory.decodeFile(path, options);
    }

    public static Bitmap getScaledBitmap(Uri uri, Context context, int destWidth, int destHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//不加载,只想看看Bitmap属性数据
        FileDescriptor fd = null;
        try {
            fd = context.getContentResolver().openFileDescriptor(uri, "r").getFileDescriptor();
            BitmapFactory.decodeFileDescriptor(fd, null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;

        int inSampleSize = 1;
        if (srcWidth > destWidth || srcHeight > destHeight) {
            //图片宽高除以控件高度
            float widthScale = srcWidth / destWidth;
            float heightScale = srcHeight / destHeight;
            inSampleSize = Math.round(Math.max(heightScale, widthScale));//得到最接近最大缩放率的整数
        }

        //重新设定Options
        options.inJustDecodeBounds = false;
        options.inSampleSize = inSampleSize;
        return BitmapFactory.decodeFileDescriptor(fd, null, options);
    }
}
