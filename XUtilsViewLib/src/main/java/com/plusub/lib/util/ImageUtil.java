package com.plusub.lib.util;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.os.Build;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *in common use to handle the image method
 * @author qh
 *
 */
public class ImageUtil {

    /**
     * get inputStream of image
     * @param path the url of image
     * @return
     * @throws Exception
     */
    public static InputStream getRequest(String path) throws Exception {
        if (path == null) {
            return null;
        }
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        if (conn.getResponseCode() == 200){
            return conn.getInputStream();
        }
        return null;
    }

    /**
     * reverse the inputStram to byte array
     * @param inStream the inputStream of image
     * @return byte array of image
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        if (inStream == null) {
            return null;
        }
        ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int len = 0;
        try{
            while ((len = inStream.read(buffer)) != -1) {
                outSteam.write(buffer, 0, len);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                inStream.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return outSteam.toByteArray();
    }

    /**
     * 图片去色,返回灰度图片
     * @param bmpOriginal 传入的图片
     * @return 去色后的图片
     */
    public static Bitmap toGrayscale(Bitmap bmpOriginal) {
        if (bmpOriginal == null) {
            return null;
        }
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Config.RGB_565);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }


    /**
     * 把图片变成圆角
     * @param bitmap 待修改的图片
     * @param pixels 圆角的弧度
     * @return 圆角图片
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
        if (bitmap == null || pixels < 0) {
            return null;
        }
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;
        //设置抗锯齿
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    public static boolean saveBitmapToFile(Bitmap bitmap, File file){
        BufferedOutputStream bof = null;
        if (bitmap == null || file == null) {
            return false;
        }
        File newFile = file;
        if (file.exists()){
            newFile = new File(file.getAbsolutePath());
            file.delete();
        }
        try {
            bof = new BufferedOutputStream(new FileOutputStream(newFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bof);
            bof.flush();
            return true;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally{
            if (bof != null) {
                try {
                    bof.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * save image to application folder
     * for example: <p>mnt/sdcard/pic/name.jpg</p>
     * you should input the para saveBitmapToFolder(bitmap, "mnt/sdcard/pic", "name.jpg")
     * @param bitmap
     * @param path
     * @param name
     * @return
     */
    public static boolean saveBitmapToFolder(Bitmap bitmap, String path, String name){
        BufferedOutputStream bof = null;
        if (bitmap == null || path == null || name == null) {
            return false;
        }
        if(isDirectoryAvailable(path)){
            File dirFile = new File(new File(path), name);
            if(!dirFile.exists()){
                File file = new File(path, name);
                try {
                    bof = new BufferedOutputStream(new FileOutputStream(file));
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bof);
                    bof.flush();
                    return true;
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally{
                    if (bof != null) {
                        try {
                            bof.close();
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return false;
    }

    public static Bitmap getBitmapFromPath(String filePath){
        if (filePath == null) {
            return null;
        }
        File file = new File(filePath);
        Bitmap bitmap = null;
        if(!file.exists()){
            return null;
        }
//        	bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        bitmap = getSmallBitmap(file.getAbsolutePath());
        return bitmap;
    }

    /**
     * 图片压缩
     * <p>Title: getSmallBitmap
     * <p>Description:
     * @param filePath
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath)
    {
        BitmapFactory.Options localOptions = new BitmapFactory.Options();
        localOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, localOptions);
        localOptions.inSampleSize = calculateInSampleSize(localOptions, 480, 800);
        localOptions.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, localOptions);
    }

    public static int calculateInSampleSize(BitmapFactory.Options paramOptions, int paramInt1, int paramInt2)
    {
        int i = paramOptions.outHeight;
        int j = paramOptions.outWidth;
        int k = 1;
        if ((i > paramInt2) || (j > paramInt1))
        {
            int m = Math.round(i / paramInt2);
            int n = Math.round(j / paramInt1);
            k = m < n ? m : n;
        }
        return k;
    }


    /**
     * 旋转图片
     * @param bitmap
     * @param degree 旋转图片角度(为正顺时针旋转，否则逆时针旋转)
     * @return
     */
    public static Bitmap toRotate(Bitmap bitmap, float degree){
        if (bitmap == null){
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(degree); //翻转角度
        int width = bitmap.getWidth();
        int height =bitmap.getHeight();
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return bitmap;
    }


    /**
     * 读取照片exif信息中的旋转角度
     * @param path 照片路径
     * @return角度
     */
    @TargetApi(Build.VERSION_CODES.ECLAIR)
    public static int readPictureDegree(String path) {
        if (!new File(path).exists()){
            return 0;
        }
        int degree  = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    private static boolean isDirectoryAvailable(String path){
        File file = new File(path);
        if(file.exists()){
            return true;
        }
        return file.mkdirs();
    }
}
