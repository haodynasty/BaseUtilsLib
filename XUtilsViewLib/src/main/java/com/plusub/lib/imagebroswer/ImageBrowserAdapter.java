package com.plusub.lib.imagebroswer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.plusub.lib.BaseApplication;
import com.plusub.lib.view.R;
import com.plusub.lib.imagebroswer.ImageBrowserActivity.ImageRequestType;
import com.plusub.lib.util.DensityUtils;
import com.plusub.lib.util.FileUtils;
import com.plusub.lib.util.TextUtils;
import com.plusub.lib.view.ViewInjectUtils;
import com.plusub.lib.view.photoview.PhotoView;

/**
 * 图片适配器
 * @author blakequ Blakequ@gmail.com
 *
 */
public class ImageBrowserAdapter extends PagerAdapter {

	private List<PhotosEntity> mPhotos;
	private ImageRequestType mType;
	private DownloadProgressDialog progressDialog;
	private View parentView;
	private Activity mContext;
	/**是否循环*/
	private boolean isCycle = true;


	/**屏幕的宽度*/
	protected int mScreenWidth;
	/**屏幕高度*/
	protected int mScreenHeight;
	
	public ImageBrowserAdapter(Activity context, List<PhotosEntity> photos, ImageRequestType type) {
		this.mContext = context;
		DisplayMetrics dm = DensityUtils.getScreenD(context);
		mScreenWidth = dm.widthPixels;
		mScreenHeight = dm.heightPixels;
		if (photos != null) {
			mPhotos = photos;
		}else{
			mPhotos = new ArrayList<PhotosEntity>();
		}
		mType = type;
		progressDialog = new DownloadProgressDialog(context);
	}
	
	public void setParentView(View parentView){
		this.parentView = parentView;
	}

	/**
	 * 是否循环滑动
	 * @param isCycle
	 */
	public void setCycle(boolean isCycle){
		this.isCycle = isCycle;
	}

	@Override
	public int getCount() {
		int count = mPhotos.size();
		if (mPhotos.size() > 0 && isCycle) {
			count = Integer.MAX_VALUE;
		}
		return count;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public View instantiateItem(ViewGroup container, final int position) {
		final PhotoView photoView = new PhotoView(container.getContext());
//		photoView.setOnLongClickListener(new OnLongClickListener() {
//			
//			@Override
//			public boolean onLongClick(View v) {
//				// TODO Auto-generated method stub
//				if (mType == ImageRequestType.TYPE_ALBUM_URL) {
//					String url = mPhotos.get(position % mPhotos.size()).getPath();
//					showSaveDialog(url);
//				}else if(mType == ImageRequestType.TYPE_LOCAL_AND_URL){
//					if (mPhotos != null && mPhotos.size() > 0) {
//						String url = mPhotos.get(position % mPhotos.size()).getPath();
//						if (!isLocalImage(url)) {
//							showSaveDialog(url);
//						}
//					}
//				}
//				return true;
//			}
//		});
		String filePath = null;
		Bitmap bitmap;
		switch (mType) {
			case TYPE_ALBUM:
				filePath = mPhotos.get(position % mPhotos.size()).getPath();
				showImage(photoView, filePath, 0);
				break;
			case TYPE_ALBUM_URL:
				String zipPath = mPhotos.get(position % mPhotos.size()).getZipPath();
				String cachePath = BaseApplication.getInstance().getImageCache().getDiskCache().get(zipPath).toString();
				bitmap = BitmapFactory.decodeFile(cachePath);
				if (bitmap != null) {
					photoView.setImageBitmap(bitmap);
				}else{
//					photoView.setImageResource(R.drawable.ic_img_load_default);
					photoView.setBackgroundColor(Color.parseColor("#000000"));
				}
				filePath = mPhotos.get(position % mPhotos.size()).getPath();
				showImage(photoView, filePath, 1);
				break;
			case TYPE_PHOTO:
				//file:///mnt/sdcard/image.png
				if (mPhotos != null && mPhotos.size() > 0) {
					filePath = mPhotos.get(0).getPath();
					if(isLocalImage(filePath)){
						showImage(photoView, filePath, 0);
					}else{
						showImage(photoView, filePath, 1);
					}
				}
				break;
			case TYPE_LOCAL_AND_URL:
				if (mPhotos != null && mPhotos.size() > 0) {
					filePath = mPhotos.get(position % mPhotos.size()).getPath();
					if (isLocalImage(filePath)) {
						showImage(photoView, filePath, 0);
					}else{
						String zp = mPhotos.get(position % mPhotos.size()).getZipPath();
						String cp = BaseApplication.getInstance().getImageCache().getDiskCache().get(zp).toString();
						bitmap = BitmapFactory.decodeFile(cp);
						if (bitmap != null) {
							photoView.setImageBitmap(bitmap);
						}else{
//							photoView.setImageResource(R.drawable.ic_img_load_default);
							photoView.setBackgroundColor(Color.parseColor("#000000"));
						}
						filePath = mPhotos.get(position % mPhotos.size()).getPath();
						showImage(photoView, filePath, 1);
					}
				}
				break;
			default:
				break;
		}
		container.addView(photoView, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
		return photoView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}
	
	/**
	 * 
	 * <p>Title: showImage
	 * <p>Description: 
	 * @param photoView
	 * @param path
	 * @param type 0:本地图片， 1：网络图片
	 */
	private void showImage(final PhotoView photoView, String path, int type){
		String filePath;
		if (type == 0) {
			filePath = "file:///"+path;
		}else{
			filePath = path;
		}
		BaseApplication.getInstance().getImageCache().displayImage(filePath, 
				photoView, getCommonImageViewOptions(), 
				new SimpleImageLoadingListener() {
					
					@Override
					public void onLoadingStarted(String imageUri, View view) { 
						// TODO Auto-generated method stub
						if (parentView != null) {
							progressDialog.setDownloadStatus(false);
//							progressDialog.showAtLocation(parentView, Gravity.NO_GRAVITY, mScreenWidth/2-100, mScreenHeight/2);
						}
					}
					
					@Override
					public void onLoadingFailed(String imageUri, View view,
							FailReason failReason) {
						// TODO Auto-generated method stub
						if (parentView != null && progressDialog.isShowing()) {
							progressDialog.setDownloadStatus(true);
							progressDialog.dismiss();
						}
					}
					
					@Override
					public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
						// TODO Auto-generated method stub
						if (parentView != null && progressDialog.isShowing()) {
							progressDialog.setDownloadStatus(false);
							progressDialog.dismiss();
						}
						photoView.setImageBitmap(loadedImage);
					}
					
					@Override
					public void onLoadingCancelled(String imageUri, View view) {
						// TODO Auto-generated method stub
						if (parentView != null && progressDialog.isShowing()) {
							progressDialog.setDownloadStatus(true);
							progressDialog.dismiss();
						}
					}
				}, new ImageLoadingProgressListener() {
					 @Override
					 public void onProgressUpdate(String imageUri, View view, int current,
							 int total) {
						 if (parentView != null) {
							 if (progressDialog.isShowing()) {
								 progressDialog.setProgressValue(total, current);
							}else{
								progressDialog.showAtLocation(parentView, Gravity.NO_GRAVITY, mScreenWidth/2-100, mScreenHeight/2);
							}
						}
					 }
				 });
	}
	
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
			.showImageOnFail(R.drawable.ic_img_load_fail)
			.cacheOnDisk(true)
			.considerExifParams(true)
			.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
			.bitmapConfig(Bitmap.Config.RGB_565)
			.build();
		return options;
	}
	
	public boolean isLocalImage(String path){
		if (path.startsWith("http")) {
			return false;
		}
		return true;
	}
	
	/**
	 * 下载图片
	 * <p>Title: downloadFile
	 * <p>Description: 
	 * @param url
	 */
	private void downloadFile(String url){
		if (!TextUtils.isEmpty(url)) {
			String path = BaseApplication.getInstance().getImageCache().getDiskCache().get(url).toString();
			if (!TextUtils.isEmpty(path)) {
				saveFile(path, url);
			}else{
				ViewInjectUtils.showCustomToast(mContext, "图片正在下载，请稍等...");
			}
		}
	}
	
	@SuppressLint("NewApi")
	private void saveFile(String path, String url){
		File picPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		if (!FileUtils.isDirectoryAvailable(picPath)) {
			picPath.mkdirs();
		}
		//获取后缀
		String newPath = null;
		String newFileName = getFileName(url);
		if (newFileName != null) {
			newPath = picPath+"/"+newFileName;
		}else{
			newPath = picPath+"/"+System.currentTimeMillis()+".jpg";
		}
		if (FileUtils.isFileExist(newPath)) {
			FileUtils.deleteFile(newPath);
		}
		try {
			FileUtils.copyFile(new File(path), newPath);
			ViewInjectUtils.showCustomToast(mContext, "图片已保存到本地："+newPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ViewInjectUtils.showCustomToast(mContext, "图片保存失败");
		}
	}
	
	public String getFileName(String path){
		int start=path.lastIndexOf("/");
	    if (start!=-1) {
	        return path.substring(start+1, path.length());	
	    }
	    else {
	        return null;
	    }
	}
}
