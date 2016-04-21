package com.plusub.lib.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 圆角图片ImageView
 * @author blakequ Blakequ@gmail.com
 *
 */
public class RoundImageView extends ImageView {

	public RoundImageView(Context context) {
		super(context);
	}

	public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public RoundImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void setImageBitmap(Bitmap bm) {
		bm = toRoundCorner(bm, 3);
		super.setImageBitmap(bm);
	}

	public void setImageBitmap(Bitmap bm, int pixels) {
		bm = toRoundCorner(bm, pixels);
		super.setImageBitmap(bm);
	}
	
	 /**  
     * 把图片变成圆角
     * @param bitmap 待修改的图片
     * @param pixels 圆角的弧度
     * @return 圆角图片  
     */   
    private Bitmap toRoundCorner(Bitmap bitmap, int pixels) {   
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
}
