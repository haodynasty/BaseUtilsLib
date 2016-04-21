/*
 * FileName: CommonConfig.java
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
 * date     : 2014-6-8 上午11:18:32
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.example.common;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.plusub.lib.example.R;

/**
 * 公共配置
 * @author blakequ Blakequ@gmail.com
 *
 */
public class AppConfig {

	public static int PAGE_SIZE = 20;
	
	/**
	 * 载入图片默认配置
	 * <p>Title: getCommonImageViewOptions
	 * <p>Description: 
	 * @param width 定义载入时图片宽度
	 * @param height 定义载入图片高度
	 * @return
	 */
	public static DisplayImageOptions getCommonImageViewOptions(){
		DisplayImageOptions options = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.ic_img_load_default)
			.showImageForEmptyUri(R.drawable.ic_img_load_fail)
			.showImageOnFail(R.drawable.ic_img_load_fail)
			.cacheInMemory(true)
			.cacheOnDisk(true)
			.considerExifParams(true)
			.displayer(new RoundedBitmapDisplayer(4))
			.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
			.bitmapConfig(Bitmap.Config.RGB_565)
			.build();
		return options;
	}
	
	/**
	 * 在CircleImageView使用
	 * <p>Title: getCommonImageViewOptionsNotScale
	 * <p>Description: 
	 * @return
	 */
	public static DisplayImageOptions getCommonImageViewOptionsNotScale(){
		DisplayImageOptions options = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.ic_person_default_headview_bg)
			.showImageForEmptyUri(R.drawable.ic_person_default_headview_bg)
			.showImageOnFail(R.drawable.ic_person_default_headview_bg)
			.cacheInMemory(true)
			.cacheOnDisk(true)
			.considerExifParams(true)
			.bitmapConfig(Bitmap.Config.RGB_565)
			.build();
		return options;
	}
	
}
