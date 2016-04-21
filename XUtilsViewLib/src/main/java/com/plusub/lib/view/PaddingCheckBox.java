package com.plusub.lib.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.CheckBox;

/**
 * 修复checkbox的padingLeft属性会随着分辨率的不同，空间大小不同
 * http://stackoverflow.com/questions/4037795/android-spacing-between-checkbox-and-text
 * @author blakequ Blakequ@gmail.com
 *
 */
public class PaddingCheckBox extends CheckBox {

	public PaddingCheckBox(Context context) {
        super(context);
    }

    public PaddingCheckBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public PaddingCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getCompoundPaddingLeft() {
    	// Workarround for version codes < Jelly bean 4.2
        // The system does not apply the same padding. Explantion:
        // http://stackoverflow.com/questions/4037795/android-spacing-between-checkbox-and-text/4038195#4038195

        int compoundPaddingLeft = super.getCompoundPaddingLeft();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN + 1) {
//        	Drawable drawable = getResources().getDrawable(R.drawable.home_list_item_flag_checked);
//        	return compoundPaddingLeft + (drawable != null ? drawable.getIntrinsicWidth() : 0);
            final float scale = this.getResources().getDisplayMetrics().density;
            return compoundPaddingLeft + (int)(10.0f * scale + 0.5f);
        } else {
            return compoundPaddingLeft;
        }
    }

}
