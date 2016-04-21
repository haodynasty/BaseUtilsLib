package com.plusub.lib.util;

import java.util.Map;
import java.util.Set;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spanned;
import android.text.Html.ImageGetter;

/**
 * 显示资源文件
 * 图片资源映射，key:[哭]，value：smail(R.drawable.smail的名字)
 * @author blakequ Blakequ@gmail.com
 *
 */
public class ImageSpan {
	
	private Map<String, String> faceMap; //图片资源映射，key:[哭]，value：smail(R.drawable.smail的名字)
	private Context context;
	
	public ImageSpan(Context context, Map<String, String> faceMap){
		this.context = context;
		this.faceMap = faceMap;
	} 

	private ImageGetter imageGetter = new Html.ImageGetter() {
		@Override
		public Drawable getDrawable(String source) {
			Drawable drawable = null;
			String sourceName = context.getPackageName() + ":drawable/"
					+ source;
			int id = context.getResources().getIdentifier(sourceName, null, null);
			if (id != 0) {
				drawable = context.getResources().getDrawable(id);
				if (drawable != null) {
					drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
							drawable.getIntrinsicHeight());
				}
			}
			return drawable;
		}
	};
	
	public Spanned getImageSpan(CharSequence text){
		String cs = text.toString();
		if (faceMap != null) {
			Set<String> keys = faceMap.keySet();
			for (String key : keys) {
				if (cs.contains(key)) {
					cs = cs.replace(key, "<img src='" + faceMap.get(key) + "'>");
				}
			}
		}
		return Html.fromHtml(cs, imageGetter, null);
	}

}
