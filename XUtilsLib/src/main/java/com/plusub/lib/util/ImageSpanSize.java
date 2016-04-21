/*
 * FileName: ImageSpanSize.java
 * Copyright (C) 2014 Plusub Tech. Co. Ltd. All Rights Reserved <admin@plusub.com>
 * 
 * Licensed under the Plusub License, Version 1.0 (the "License");
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * author  : quhao <blakequ@gmail.com>
 * date     : 2014-6-25 下午9:04:16
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.util;

import java.io.InputStream;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.style.DynamicDrawableSpan;
import android.util.Log;

/**
 * 可以设定图片大小的ImageSpan
 * @author blakequ Blakequ@gmail.com
 *
 */
@SuppressLint("NewApi")
public class ImageSpanSize extends DynamicDrawableSpan {

	private Drawable mDrawable;
    private Uri mContentUri;
    private int mResourceId;
    private Context mContext;
    private String mSource;
    
    public ImageSpanSize(Context context, Bitmap b) {
        this(context, b, ALIGN_BOTTOM);
    }
    
    /**
     * @param verticalAlignment one of {@link DynamicDrawableSpan#ALIGN_BOTTOM} or
     * {@link DynamicDrawableSpan#ALIGN_BASELINE}.
     */
	public ImageSpanSize(Context context, Bitmap b, int verticalAlignment) {
        super(verticalAlignment);
        mContext = context;
        mDrawable = context != null
                ? new BitmapDrawable(context.getResources(), b)
                : new BitmapDrawable(b);
        int width = mDrawable.getIntrinsicWidth();
        int height = mDrawable.getIntrinsicHeight();
        mDrawable.setBounds(0, 0, width > 0 ? width : 0, height > 0 ? height : 0); 
    }
    
    
    
    public ImageSpanSize(Context context, Bitmap b, int width, int height) {
    	this(context, b, ALIGN_BOTTOM, width, height);
    }
    
    /**
     * @param verticalAlignment one of {@link DynamicDrawableSpan#ALIGN_BOTTOM} or
     * {@link DynamicDrawableSpan#ALIGN_BASELINE}.
     */
	public ImageSpanSize(Context context, Bitmap b, int verticalAlignment, int width, int height) {
        super(verticalAlignment);
        mContext = context;
        mDrawable = context != null
                ? new BitmapDrawable(context.getResources(), b)
                : new BitmapDrawable(b);
        if (width == 0 || height == 0) {
        	width = mDrawable.getIntrinsicWidth();
        	height = mDrawable.getIntrinsicHeight();
		}
        mDrawable.setBounds(0, 0, width > 0 ? width : 0, height > 0 ? height : 0); 
    }
    
    
    public ImageSpanSize(Drawable d) {
        this(d, ALIGN_BOTTOM);
    }

    /**
     * @param verticalAlignment one of {@link DynamicDrawableSpan#ALIGN_BOTTOM} or
     * {@link DynamicDrawableSpan#ALIGN_BASELINE}.
     */
	public ImageSpanSize(Drawable d, int verticalAlignment) {
        super(verticalAlignment);
        mDrawable = d;
    }

    public ImageSpanSize(Drawable d, String source) {
        this(d, source, ALIGN_BOTTOM);
    }

    /**
     * @param verticalAlignment one of {@link DynamicDrawableSpan#ALIGN_BOTTOM} or
     * {@link DynamicDrawableSpan#ALIGN_BASELINE}.
     */
    public ImageSpanSize(Drawable d, String source, int verticalAlignment) {
        super(verticalAlignment);
        mDrawable = d;
        mSource = source;
    }

    public ImageSpanSize(Context context, Uri uri) {
        this(context, uri, ALIGN_BOTTOM);
    }

    /**
     * @param verticalAlignment one of {@link DynamicDrawableSpan#ALIGN_BOTTOM} or
     * {@link DynamicDrawableSpan#ALIGN_BASELINE}.
     */
	public ImageSpanSize(Context context, Uri uri, int verticalAlignment) {
        super(verticalAlignment);
        mContext = context;
        mContentUri = uri;
        mSource = uri.toString();
    }

    public ImageSpanSize(Context context, int resourceId) {
        this(context, resourceId, ALIGN_BOTTOM);
    }

    /**
     * @param verticalAlignment one of {@link DynamicDrawableSpan#ALIGN_BOTTOM} or
     * {@link DynamicDrawableSpan#ALIGN_BASELINE}.
     */
    public ImageSpanSize(Context context, int resourceId, int verticalAlignment) {
        super(verticalAlignment);
        mContext = context;
        mResourceId = resourceId;
    }
	
	@Override
	public Drawable getDrawable() {
		// TODO Auto-generated method stub
		Drawable drawable = null;
        
        if (mDrawable != null) {
            drawable = mDrawable;
        } else  if (mContentUri != null) {
            Bitmap bitmap = null;
            try {
                InputStream is = mContext.getContentResolver().openInputStream(
                        mContentUri);
                bitmap = BitmapFactory.decodeStream(is);
                drawable = new BitmapDrawable(mContext.getResources(), bitmap);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight());
                is.close();
            } catch (Exception e) {
                Log.e("sms", "Failed to loaded content " + mContentUri, e);
            }
        } else {
            try {
                drawable = mContext.getResources().getDrawable(mResourceId);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight());
            } catch (Exception e) {
                Log.e("sms", "Unable to find resource: " + mResourceId);
            }                
        }

        return drawable;
	}

	/**
     * Returns the source string that was saved during construction.
     */
    public String getSource() {
        return mSource;
    }

}
