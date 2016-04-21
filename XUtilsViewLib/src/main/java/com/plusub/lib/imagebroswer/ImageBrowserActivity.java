package com.plusub.lib.imagebroswer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.plusub.lib.BaseApplication;
import com.plusub.lib.view.R;
import com.plusub.lib.activity.BaseActivity;
import com.plusub.lib.annotate.BindView;
import com.plusub.lib.util.FileUtils;
import com.plusub.lib.util.TextUtils;
import com.plusub.lib.view.ScrollViewPager;
import com.plusub.lib.view.ViewInjectUtils;

/**
 * 图片浏览
 * <p>使用步骤：</p>
 * <li>1.在AndroidManifest.xml中注册com.plusub.lib.imagebrowser.ImageBrowserActivity</li>
 * <li>2.使用静态方法{@link #launch(Context, String)}启动Activity，只需要传入一张图片的路径即可(网络或本地);
 * 		<p>或使用{@link #launch(Context, ImageRequestType, PhotosEntity)}, 需要传入图片类型和图片实体（包含大图、中图、小图）；
 * 		<p>或使用{@link #launch(Context, ImageRequestType, ArrayList, int)}, 传入图片类型和图片实体列表，以及初始位置，可滑动查看}</li>
 * <li>如果需要显示下载按钮，直接调用{@link #setShowDownload(boolean)}</li>
 * <li>如果不需要循环查看图片，直接调用{@link #setIsCycle(boolean)}</li>
 * <li>如果要修改头部，直接复写布局文件include_image_browser_bar.xml
 * <p>
 * @ClassName: ImageBrowserActivity
 * @Description: TODO
 * @author qh@plusub.com
 * @date 2015-5-16 下午9:22:43
 * @version v1.0
 */
public class ImageBrowserActivity extends BaseActivity implements
		OnPageChangeListener {

//	@BindView(id = R.id.imagebrowser_ptv_page)
//	private PhotoTextView mPtvPage;
//	@BindView(id = R.id.imagebrowser_vp_pager)
	private ScrollViewPager mSvpPager;
//	@BindView(id = R.id.imagebrowser_iv_download, click=true)
	private ImageView mIvDownload;
	
	private ImageBrowserAdapter mAdapter;
	private int mPosition = 0;
	private int mTotal;
	private ImageRequestType mType;
	private ArrayList<PhotosEntity> photosList;
	private View rootView;
	
	private View mBackView;
	private TextView mTvTitle;
	private ImageView mIvRight;
	private static boolean isShowDownload = false;
	private static boolean isCycleView = true; //是否可循环滑动

	/**
	 * 图片列表
	 * <p>Title: launch
	 * <p>Description: 
	 * @param context
	 * @param type 图片类型
	 * @param photosList 图片数组实体
	 * @param position 初始显示的位置
	 */
	public static void launch(Context context, ImageRequestType type, ArrayList<PhotosEntity> photosList, int position){
		Intent intent = new Intent(context, ImageBrowserActivity.class);
		intent.putExtra("type", type);
		intent.putExtra("position", position);
		intent.putParcelableArrayListExtra("list", photosList);
		context.startActivity(intent);
	}
	
	/**
	 * 一张图，如果是本地图片，传入格式为：mnt/sdcard/image.png
	 *<p> 如果是网络图片则必须以http开头的地址
	 * <p>Title: launch
	 * <p>Description: 
	 * @param context
	 * @param path 图片的本地或网络路径
	 */
	public static void launch(Context context, String path){
		Intent intent = new Intent(context, ImageBrowserActivity.class);
		intent.putExtra("path", path);
		context.startActivity(intent);
	}
	
	/**
	 * 单张图片
	 * <p>Title: launch
	 * <p>Description: 
	 * @param context
	 * @param type 图片类型
	 * @param photos 图片实体
	 */
	public static void launch(Context context, ImageRequestType type, PhotosEntity photos){
		ArrayList<PhotosEntity> photosList = new ArrayList<PhotosEntity>();
		photosList.add(photos);
		Intent intent = new Intent(context, ImageBrowserActivity.class);
		intent.putExtra("type", type);
		intent.putExtra("position", 0);
		intent.putParcelableArrayListExtra("list", photosList);
		context.startActivity(intent);
	}
	
	/**
	 * 是否显示下载按钮
	 * <p>Title: setShowDownload
	 * <p>Description: 
	 * @param isShowDownload
	 */
	public static void setShowDownload(boolean isDown){
		isShowDownload = isDown;
	}

	/**
	 * 是否循环滑动，默认为true
	 * <p>Title: setIsCycle
	 * <p>Description:
	 * @param isCycle 默认为true
	 */
	public static void setIsCycle(boolean isCycle){
		isCycleView = isCycle;
	}
	
	@SuppressLint("NewApi")
	@Override
	public void initData() {
		// TODO Auto-generated method stub
		mSvpPager = (ScrollViewPager) findViewById(R.id.imagebrowser_vp_pager);
		mIvDownload = (ImageView) findViewById(R.id.imagebrowser_iv_download);
		
		ActionBar ab = getActionBar();
		if (ab != null) {
			ab.setDisplayHomeAsUpEnabled(false);
			ab.setDisplayShowCustomEnabled(true);//显示自定义视图
			ab.setHomeButtonEnabled(false);
			ab.setDisplayShowHomeEnabled(false);
			ab.setDisplayShowTitleEnabled(false);
			
			View titleBar = LayoutInflater.from(this).inflate(R.layout.include_image_browser_bar, null);
			mIvRight = (ImageView) titleBar.findViewById(R.id.image_browser_iv_download);
			mIvRight.setOnClickListener(this);
			mIvRight.setVisibility(View.GONE);
			mTvTitle = (TextView) titleBar.findViewById(R.id.image_browser_tv_title);
			mBackView = titleBar.findViewById(R.id.image_browser_back);
			mBackView.setOnClickListener(this);
			LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, Gravity.CENTER);
			ab.setCustomView(titleBar, lp);
		}else{
			mIvRight = (ImageView) findViewById(R.id.image_browser_iv_download);
			mIvRight.setOnClickListener(this);
			mIvRight.setVisibility(View.GONE);
			mTvTitle = (TextView) findViewById(R.id.image_browser_tv_title);
			mBackView = findViewById(R.id.image_browser_back);
			mBackView.setOnClickListener(this);
		}
	    
	    init();
	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub
		mIvDownload.setOnClickListener(this);
		mSvpPager.setOnPageChangeListener(this);
	}

	@Override
	public void setRootView() {
		// TODO Auto-generated method stub
		rootView = LayoutInflater.from(this).inflate(R.layout.activity_imagebrowser, null);
		setContentView(rootView);
	}

	
	private void init() {
		String path = getIntent().getStringExtra("path");
		if (path == null) {
			mType = (ImageRequestType) getIntent().getSerializableExtra("type");
			mPosition = getIntent().getIntExtra("position", 0);
			photosList = getIntent().getParcelableArrayListExtra("list");
			switch (mType) {
				case TYPE_ALBUM:
					mIvDownload.setVisibility(View.GONE);
					mIvRight.setVisibility(View.GONE);
					break;
				case TYPE_ALBUM_URL:
					if (isShowDownload) {
						mIvDownload.setVisibility(View.VISIBLE);
						mIvRight.setVisibility(View.VISIBLE);
					}
					break;
				default:
					break;
			}
			mTotal = photosList.size();
			if (mPosition > mTotal) {
				mPosition = mTotal - 1;
			}
			if (mTotal > 0) {
				if (isCycleView) {
					mPosition += 1000 * mTotal;
				}
//				mPtvPage.setText((mPosition % mTotal) + 1 + "/" + mTotal);
				mTvTitle.setText((mPosition % mTotal) + 1 + "/" + mTotal);
				mAdapter = new ImageBrowserAdapter(this, photosList, mType);
				mAdapter.setParentView(mSvpPager);
				mAdapter.setCycle(isCycleView);
				mSvpPager.setAdapter(mAdapter);
				mSvpPager.setCurrentItem(mPosition, false);
			}
		}else{
			mSvpPager.setLocked(true);
			mType = ImageRequestType.TYPE_PHOTO;
			mIvDownload.setVisibility(View.GONE);
			mIvRight.setVisibility(View.GONE);
			photosList = new ArrayList<PhotosEntity>();
			PhotosEntity entity = new PhotosEntity();
			entity.setPath(path);
			entity.setZipPath(path);
			photosList.add(entity);
			mTotal = photosList.size();
//			mPtvPage.setText("1/1");
			mTvTitle.setText("1/1");
			mAdapter = new ImageBrowserAdapter(this, photosList, mType);
			mAdapter.setCycle(isCycleView);
			mAdapter.setParentView(rootView);
			mSvpPager.setAdapter(mAdapter);
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		mPosition = arg0;
//		mPtvPage.setText((mPosition % mTotal) + 1 + "/" + mTotal);
		mTvTitle.setText((mPosition % mTotal) + 1 + "/" + mTotal);
		if (mType == ImageRequestType.TYPE_LOCAL_AND_URL) {
			if (photosList != null) {
				PhotosEntity photos = photosList.get(mPosition % mTotal);
				if (isLocalImage(photos.getPath())) {
					mIvDownload.setVisibility(View.GONE);
					mIvRight.setVisibility(View.GONE);
				}else{
					if (isShowDownload) {
						mIvDownload.setVisibility(View.VISIBLE);
						mIvRight.setVisibility(View.VISIBLE);
					}
				}
			}
		}
	}

	@Override
	public void onClick(View arg0) {
		int id = arg0.getId();
		if (id == R.id.imagebrowser_iv_download
				|| id == R.id.image_browser_iv_download) {
			download();
		} else if (id == R.id.image_browser_back) {
			finish();
		} else {
		}
	}
	
	/**
	 * 下载图片
	 * <p>Title: download
	 * <p>Description:
	 */
	private void download(){
		if (photosList != null && photosList.size() > 0) {
			PhotosEntity pe = photosList.get(mPosition % mTotal);
			String url = pe.getPath();
			if (!TextUtils.isEmpty(url)) {
				String path = BaseApplication.getInstance().getImageCache().getDiskCache().get(url).toString();
				if (!TextUtils.isEmpty(path)) {
					saveFile(path, url);
				}else{
					ViewInjectUtils.showCustomToast(this, "图片正在下载，请稍等...");
				}
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
			ViewInjectUtils.showCustomToast(this, "图片已保存到本地："+newPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ViewInjectUtils.showCustomToast(this, "图片保存失败");
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
	

	@SuppressLint("NewApi")
	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(0, R.anim.zoom_exit);
	}

	
	/**
	 * 图片请求类型
	 * @author blakequ Blakequ@gmail.com
	 *
	 */
	public enum ImageRequestType{
		/**
		 * 本地图片列表
		 */
		TYPE_ALBUM(0),
		/**
		 * 单个图片
		 */
		TYPE_PHOTO(1),
		/**
		 * 网络URL列表
		 */
		TYPE_ALBUM_URL(2),
		/**
		 * 网络与本地图片混合
		 */
		TYPE_LOCAL_AND_URL(3);
		
		int type;
		ImageRequestType(int type){
			this.type = type;
		}
		
		public int getType(){
			return this.type;
		}
	}
	
	public boolean isLocalImage(String path){
		if (path.startsWith("http")) {
			return false;
		}
		return true;
	}

}
