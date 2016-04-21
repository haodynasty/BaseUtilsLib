package com.plusub.lib.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.os.Build;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *in common use to handle the image method
 * @author qh
 *
 */
public class ImageUtils {

    /**
     * 放大缩小图片
     * @param bitmap the image
     * @param w width
     * @param h height
     * @return  the image after handle
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
        if (bitmap == null || w <= 0 || h <= 0) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newBmp = Bitmap.createBitmap(bitmap, 0, 0, width, height,
                matrix, true);
        return newBmp;
    }

    public static Bitmap zoomBitmapByScale(Bitmap bitmap, int w, int h){
        if (bitmap == null || w <= 0 || h <= 0) {
            return null;
        }
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scaleWidth = ((float) w) / width;
        float scaleHeight = ((float) h) / height;
        float minScale = Math.min(scaleWidth, scaleHeight);
        Matrix matrix = new Matrix();
        matrix.postScale(minScale, minScale);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height,
                matrix, true);
        return bitmap;
    }

    /**
     * 将Drawable转化为Bitmap
     * @param drawable resource
     * @return the drawable after reverse
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
                .getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * convert bitmap to Drawable
     * @param bitmap
     * @return
     */
    public static Drawable BitmapToDrawble(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        return new BitmapDrawable(bitmap);
    }



    /**
     * 获得带阴影的图片方法
     * @param bitmap resource
     * @return the image after handle
     */
    public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        final int reflectionGap = 4;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2,
                width, height / 2, matrix, false);

        Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
                (height + height / 2), Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(bitmap, 0, 0, null);
        Paint deafalutPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);

        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
                bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
                0x00ffffff, TileMode.CLAMP);
        paint.setShader(shader);
        // Set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
                + reflectionGap, paint);

        return bitmapWithReflection;
    }

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
     * load drawable from url
     * @param url
     * @return
     */
    public static Drawable loadImageFromUrl(String url){
        if (url == null) {
            return null;
        }
        URL m;
        InputStream i = null;
        Drawable drawable = null;
        try {
            m = new URL(url);
            i = (InputStream) m.getContent();
            drawable = Drawable.createFromStream(i, "src");
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if (i != null) {
                try {
                    i.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return drawable;
    }

    /**
     * get drawable from url
     * @param url
     * @return
     * @throws Exception
     */
    public static Drawable getDrawableFromUrl(String url) throws Exception{
        if (url == null) {
            return null;
        }
        return Drawable.createFromStream(getRequest(url),null);
    }

    /**
     * get bitmap from url
     * @param url
     * @return
     * @throws Exception
     */
    public static Bitmap getBitmapFromUrl(String url) throws Exception{
        if (url == null) {
            return null;
        }
        byte[] bytes = getBytesFromUrl(url);
        return byteToBitmap(bytes);
    }

    /**
     * get round angle bitmap from url
     * @param url the url of image
     * @param pixels the angle of image
     * @return
     * @throws Exception
     */
    public static Bitmap getRoundBitmapFromUrl(String url,int pixels) throws Exception{
        if (url == null || pixels < 0) {
            return null;
        }
        byte[] bytes = getBytesFromUrl(url);
        Bitmap bitmap = byteToBitmap(bytes);
        return toRoundCorner(bitmap, pixels);
    }

    /**
     * get round angle drawable from url
     * @param url the url of image
     * @param pixels the angle of reverse
     * @return
     * @throws Exception
     */
    public static Drawable geRoundDrawableFromUrl(String url,int pixels) throws Exception{
        if (url == null || pixels < 0) {
            return null;
        }
        byte[] bytes = getBytesFromUrl(url);
        BitmapDrawable bitmapDrawable = (BitmapDrawable)byteToDrawable(bytes);
        return toRoundCorner(bitmapDrawable, pixels);
    }

    /**
     * get byte array from url 
     * @param url
     * @return
     * @throws Exception
     */
    public static byte[] getBytesFromUrl(String url) throws Exception{
        if (url == null) {
            return null;
        }
        return readInputStream(getRequest(url));
    }

    /**
     * reverse byte array to bitmap
     * @param byteArray
     * @return
     */
    public static Bitmap byteToBitmap(byte[] byteArray){
        if (byteArray.length == 0) {
            return null;
        }
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
    }

    /**
     * reverse byte array to drawable
     * @param byteArray
     * @return
     */
    public static Drawable byteToDrawable(byte[] byteArray){
        if (byteArray.length == 0) {
            return null;
        }
        ByteArrayInputStream ins = new ByteArrayInputStream(byteArray);
        return Drawable.createFromStream(ins, null);
    }

    /**
     * reverse bitmap to byte array
     * @param bm
     * @return
     */
    public static byte[] Bitmap2Bytes(Bitmap bm){
        if (bm == null) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
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

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
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
     * 去色同时加圆角
     * @param bmpOriginal 原图
     * @param pixels 圆角弧度
     * @return 修改后的图片
     */
    public static Bitmap toGrayscale(Bitmap bmpOriginal, int pixels) {
        return toRoundCorner(toGrayscale(bmpOriginal), pixels);
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


    /**
     * 使圆角功能支持BitampDrawable
     * @param bitmapDrawable
     * @param pixels
     * @return
     */
    public static BitmapDrawable toRoundCorner(BitmapDrawable bitmapDrawable, int pixels) {
        if (bitmapDrawable == null || pixels < 0) {
            return null;
        }
        Bitmap bitmap = bitmapDrawable.getBitmap();
        bitmapDrawable = new BitmapDrawable(toRoundCorner(bitmap, pixels));
        return bitmapDrawable;
    }

    /**
     * save inputStream to folder
     * @param inputStream
     * @param path the path of file will store
     * @param name
     * @return
     */
    public static boolean saveInputStreamToFolder(InputStream inputStream, String path, String name){
        FileOutputStream fos = null;
        if (inputStream == null || path == null || name == null) {
            return false;
        }
        try {
            fos = new FileOutputStream(new File(path, name));
            byte[] buffer = new byte[1024];
            int length = 0;
            while((length = inputStream.read(buffer)) != -1){
                fos.write(buffer, 0, length);
            }
            fos.flush();
            return true;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e ){
            e.printStackTrace();
        }finally{
            if (fos != null) {
                try {
                    fos.close();
                    inputStream.close();
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
     * for example: <p>mnt\sdcard\pic\name.jpg</p>
     * you should input the para saveBitmapToFolder(bitmap, "mnt\sdcard\pic", "name.jpg")
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
        if(FileUtils.isDirectoryAvailable(path)){
            if(!FileUtils.isFileExists(new File(path), name)){
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

    /**
     * for example: <p>mnt\sdcard\pic\name.jpg</p>
     * you should input the para replaceBitmapToFloder(bitmap, "mnt\sdcard\pic", "name.jpg")
     * <p>Title: replaceBitmapToFloder
     * <p>Description: 如果存在,则覆盖写入
     * @param bitmap
     * @param path
     * @param name
     * @return
     */
    public static boolean replaceBitmapToFloder(Bitmap bitmap, String path, String name){
        if (bitmap == null || path == null || name==null) {
            return false;
        }
        if(FileUtils.isDirectoryAvailable(path)){
            File file = null;
            if(FileUtils.isFileExists(new File(path), name)){
                file = new File(path, name);
                file.delete();
            }
            file = new File(path, name);
            saveBitmapToFolder(bitmap, path, name);
            return true;
        }
        return false;
    }


    /**
     * get bitmap from Sdcard
     * for example: <p>mnt\sdcard\pic\name.jpg</p>
     * you should input the para getBitmapFromPath("mnt\sdcard\pic", "name.jpg")
     * @param path
     * @param name
     * @return
     */
    public static Bitmap getBitmapFromPath(String path, String name){
        if (path == null || name==null) {
            return null;
        }
        File file = new File(path, name);
        Bitmap bitmap = null;
        if(!file.exists()){
            return null;
        }
//        	bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        bitmap = getSmallBitmap(file.getAbsolutePath());
        return bitmap;
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
     * 获取bitmap大小，单位为字节byte，可以使用{@link com.plusub.lib.util.SizeUtils#byteToString(long)}进行转换
     * @param bitmap
     * @return
     */
    public int getBitmapSize(Bitmap bitmap){
        if (bitmap == null){
            return 0;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){//API 19
            return bitmap.getAllocationByteCount();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1){//API 12
            return bitmap.getByteCount();
        }
        return bitmap.getRowBytes() * bitmap.getHeight();//earlier version
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
     * 围绕点旋转图片
     * @param bitmap
     * @param degree 角度
     * @param px 围绕点x坐标（相对图片）
     * @param py 围绕点y坐标（相对图片）
     * @return
     */
    public static Bitmap toRotate(Bitmap bitmap, float degree, float px, float py){
        if (bitmap == null){
            return null;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(degree, px, py); //翻转角度
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

    /**
     * dp转换为像素px
     * <p>Title: Dp2Px
     * <p>Description:
     * @param context
     * @param dp
     * @return
     */
    public static int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * 像素单位px转换为dp
     * <p>Title: Px2Dp
     * <p>Description:
     * @param context
     * @param px
     * @return
     */
    public static int Px2Dp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
}
