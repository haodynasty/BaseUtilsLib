/*
 * FileName: ImageSelectPopupWindow.java
 * Copyright (C) 2014 Plusub Tech. Co. Ltd. All Rights Reserved <admin@plusub.com>
 * 
 * Licensed under the Plusub License, Version 1.0 (the "License");
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * author  : service@plusub.com
 * date     : 2015-4-24 下午5:09:41
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.view.other;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.plusub.lib.BaseApplication;
import com.plusub.lib.util.FileUtils;
import com.plusub.lib.util.ImageUtils;
import com.plusub.lib.util.StringUtils;
import com.plusub.lib.util.logger.Logger;
import com.plusub.lib.view.R;
import com.plusub.lib.view.ViewInjectUtils;

import java.io.File;

/**
 * @ClassName: ImageSelectPopupWindow
 * @Description: TODO 只需要实现onActivityResult方法，从相机的时候请求码requestCode为{@link #FROM_CANMERA}，从相册是{@link #FROM_ALBUM},如果剪切则为{@link #FROM_CROP}
 * <li>如果从相机中获取，照片缓存路径从{@link #getCameraFilePath()}获取</li>
 * <li>如果从相册中获取，图片缓存路径从{@link #getAlbumFilePath(Intent)}获取</li>
 * <li>获取图片后剪切路径，调用{@link #cropImage(String, int, int)},然后从{@link #getCropImgFilePath(Intent)}获取图片路径
 * 
 * <p>注意：</p>
 * 如果需要复写选择button的颜色，只需要复写color.xml中的bg_bt_blue_normal和bg_bt_blue_press
 * <p>如果需要复写整个dialog样式，则覆盖布局文件include_pop_upload_image.xml
 * 选择的图片未曾压缩需要自己压缩
 * @author qh@plusub.com
 * @date： 
 *     <b>文件创建时间：</b>2015-4-24 下午5:09:41<br>
 *     <b>最后修改时间：</b>2015-4-24 下午5:09:41
 * @version v1.0
 */
public class ImageSelectPopupWindow extends PopupWindow implements OnClickListener{
	public static final int FROM_ALBUM = 0;
	public static final int FROM_CANMERA = 1;
	public static final int FROM_CROP = 2;
	private Activity context;
	private String tempPath;

	@SuppressLint("NewApi")
	public ImageSelectPopupWindow(Activity context){
		super(context);
		this.context = context;
		View view = LayoutInflater.from(context).inflate(R.layout.include_pop_upload_image, null);
		view.findViewById(R.id.pop_from_album).setOnClickListener(this);
		view.findViewById(R.id.pop_from_camera).setOnClickListener(this);
		view.findViewById(R.id.pop_cancel).setOnClickListener(this);
		setContentView(view);
		setWidth(LayoutParams.MATCH_PARENT);
		setHeight(LayoutParams.WRAP_CONTENT);
		setOutsideTouchable(true);
		setBackgroundDrawable(new ColorDrawable(0xe0000000));
		setFocusable(true);
		setAnimationStyle(R.style.popwin_anim_style);
		setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		setOnDismissListener(new OnDismissListener(){

			@Override
			public void onDismiss() {
				// TODO Auto-generated method stub
//				mCoverBoard.setVisibility(View.GONE);
				backgroundAlpha(1f);
			}

		});
	}

	@Override
	public void showAtLocation(View parent, int gravity, int x, int y) {
		// TODO Auto-generated method stub
		backgroundAlpha(0.5f);
		super.showAtLocation(parent, gravity, x, y);
	}


	/**
	 * 设置添加屏幕的背景透明度
	 * @param bgAlpha 0为不可见，1为透明
	 */
	private void backgroundAlpha(float bgAlpha)
	{
		WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        context.getWindow().setAttributes(lp);
	}

	/**
	 * 从相机拍照后，获取未压缩照片路径
	 * <p>Title: getCameraFilePath
	 * <p>Description:
	 * @return 如果为获取到，则返回空字符串
	 */
	public String getCameraFilePath(){
		if (StringUtils.isEmpty(tempPath) || !new File(tempPath).exists()) {
            Logger.e("ImageSelectPopupWindow", "getCameraFilePath tempPath="+tempPath+" or not exist");
			return "";
		}

		int degree = ImageUtils.readPictureDegree(tempPath);
		if (degree > 0){//旋转的图片要纠正
			Bitmap bmp = ImageUtils.getBitmapFromPath(tempPath);
			bmp = ImageUtils.toRotate(bmp, degree);
			ImageUtils.saveBitmapToFile(bmp, new File(tempPath));
			if (bmp == null){
                Logger.e("ImageSelectPopupWindow", "getCameraFilePath bmp is null");
                return "";
            }
		}
		return tempPath;
	}

	/**
	 * 从相册获取未压缩图片
	 * <p>Title: getAlbumFilePath
	 * <p>Description:
	 * @param data 回调onActivityResult回传的intent数据
	 * @return 如果data为空，则返回空字符串
	 */
	public String getAlbumFilePath(Intent data){
		if (data == null) {
            Logger.e("ImageSelectPopupWindow", "getAlbumFilePath data is null");
			return "";
		}
		String filepath = "";
		Cursor cursor = null;
		try {
			Uri originalUri=data.getData();
			String[] proj = {MediaStore.Images.Media.DATA};
			cursor = context.getContentResolver().query(originalUri, proj, null, null, null);
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			filepath = cursor.getString(column_index);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(cursor != null) {
				cursor.close();
			}
		}
		return filepath;
	}

	/**
	 * 获取剪裁后的未压缩图片
	 * @param data 回调onActivityResult回传的intent数据
	 * @return 如果data为空，则返回空字符串
	 */
	public String getCropImgFilePath(Intent data){
		if (data == null) {
            Logger.e("ImageSelectPopupWindow", "getCropImgFilePath data is null");
			return "";
		}
		try {
			Bitmap photo = null;
			Uri photoUri = data.getData();
			if (photoUri != null) {
				photo = BitmapFactory.decodeFile(photoUri.getPath());
			}
			if (photo == null) {
				Bundle extra = data.getExtras();
				if (extra != null) {
					photo = (Bitmap) extra.get("data");
				}
			}
			File path = new File(BaseApplication.mCachePath);
			if (!path.exists()) {
				path.mkdirs();
			}

			// 图片目录
			String fileName = "picture_crop_"+System.currentTimeMillis()+".png";
			String filePath = BaseApplication.mCachePath + "/"+fileName;
			boolean result = ImageUtils.saveBitmapToFolder(photo, BaseApplication.mCachePath, fileName);
			if (result) return filePath;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * 启动图片裁剪Intent（这里设置的剪裁默认比例1:1）
	 * @param imagePath 图片路径
	 * @param outputX 输出后x尺寸，建议150
	 * @param outputY 输出后y尺寸，建议150
	 */
	public void cropImage(String imagePath, int outputX, int outputY) {
		File file = new File(imagePath);
		if (!file.exists()){
			Logger.e("ImageSelectPopupWindow", "crop image is not exist");
			return;
		}
		Uri uri = Uri.fromFile(file);
		// 裁剪图片意图
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// 裁剪框的比例，1：1
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// 裁剪后输出图片的尺寸大小
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		// 图片格式
		intent.putExtra("outputFormat", "PNG");
		intent.putExtra("noFaceDetection", true);
		intent.putExtra("return-data", true);
		context.startActivityForResult(intent, FROM_CROP);
	}


	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.pop_from_album) {
			Intent intentfile = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			context.startActivityForResult(intentfile, FROM_ALBUM);
		} else if (id == R.id.pop_cancel) {
			dismiss();
		} else if (id == R.id.pop_from_camera) {
			if (FileUtils.isSDCardAvailable()) {
				Intent getImageByCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				tempPath = BaseApplication.mCachePath
						+ "/picture_temp_"+System.currentTimeMillis()+".png";
				File output_dir = new File(BaseApplication.mCachePath);
				if (!output_dir.exists()) {
					output_dir.mkdirs();
				}
				getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(new File(tempPath)));
				getImageByCamera.putExtra(
						MediaStore.EXTRA_VIDEO_QUALITY, 0.5);
				context.startActivityForResult(getImageByCamera, FROM_CANMERA);
			} else {
				ViewInjectUtils.showCustomToast(context, "请确认已经插入SD卡");
			}
		}
	}
	
}
