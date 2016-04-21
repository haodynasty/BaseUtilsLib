/*
 * FileName: Tab1Fragment.java
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
 * date     : 2014-12-12 下午4:56:53
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.example.activity.tab1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.plusub.lib.activity.BaseFragment;
import com.plusub.lib.activity.SimpleWebActivity;
import com.plusub.lib.annotate.BindView;
import com.plusub.lib.example.R;
import com.plusub.lib.example.view.HeaderLayout;
import com.plusub.lib.example.view.HeaderLayout.HeaderStyle;
import com.plusub.lib.imagebroswer.ImageBrowserActivity;
import com.plusub.lib.imagebroswer.ImageBrowserActivity.ImageRequestType;
import com.plusub.lib.imagebroswer.PhotosEntity;
import com.plusub.lib.task.TaskMessage;

import java.util.ArrayList;

/**
 * @ClassName: Tab1Fragment
 * @Description: TODO
 * @author blakequ@gmail.com
 * @date： 
 *     <b>文件创建时间：</b>2014-12-12 下午4:56:53<br>
 *     <b>最后修改时间：</b>2014-12-12 下午4:56:53
 * @version v1.0
 */
public class Tab1Fragment extends BaseFragment {

	@BindView(id = R.id.common_head_layout)
	private HeaderLayout mHeader;
	@BindView(id = R.id.tab1_bt1, click = true)
	private Button mBt1;
	@BindView(id = R.id.tab1_bt2, click = true)
	private Button mBt2;
	@BindView(id = R.id.tab1_bt3, click = true)
	private Button mBt3;
	@BindView(id = R.id.tab1_bt4, click = true)
	private Button mBt4;
	@BindView(id = R.id.tab1_bt5, click = true)
	private Button mBt5;
	@BindView(id = R.id.tab1_bt6, click = true)
	private Button mBt6;
	
	@Override
	public void refresh(TaskMessage msg, Object... param) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub

	}

	@Override
	protected View inflaterView(LayoutInflater inflater, ViewGroup container,
			Bundle bundle) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_tab_1, null);
	}

	@Override
	protected void initView(View parentView) {
		// TODO Auto-generated method stub
		mHeader.init(HeaderStyle.DEFAULT_TITLE);
		mHeader.setMiddleTitle("Blakequ.com");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.tab1_bt1:
//				startActivity(BrowserActivity.class);
				BrowserActivity.launch(getActivity(), "http://www.baidu.com");
//				com.plusub.lib.activity.BrowserActivity.launch(getActivity(), "http://www.baidu.com", true);
				break;
			case R.id.tab1_bt2:
				startActivity(RefreshActivity.class);
				break;
			case R.id.tab1_bt3:
				startActivity(LDrawerActivity.class);
				break;
			case R.id.tab1_bt4:
				 Intent intent = new Intent(getActivity(), SimpleWebActivity.class);
				 intent.putExtra(SimpleWebActivity.URL, "http://www.baidu.com");
				 startActivity(intent);
				break;
			case R.id.tab1_bt5:
//				startActivity(TabActivity.class);
				break;
			case R.id.tab1_bt6:
				ImageBrowserActivity.launch(getActivity(), ImageRequestType.TYPE_LOCAL_AND_URL, getPhotos(), 1);
				ImageBrowserActivity.setShowDownload(true);
				ImageBrowserActivity.setIsCycle(false);
				break;
			default:
				break;
		}
	}

	private ArrayList<PhotosEntity> getPhotos(){
		ArrayList<PhotosEntity> photos = new ArrayList<PhotosEntity>();
		PhotosEntity entity = new PhotosEntity();
		entity.setName("图片1");
		entity.setZipPath("http://ww1.sinaimg.cn/bmiddle/4b807446gw1epxbpllhctj20go0nm0vl.jpg");
		entity.setPath("http://ww1.sinaimg.cn/bmiddle/4b807446gw1epxbpllhctj20go0nm0vl.jpg");
		photos.add(entity);
		
		PhotosEntity entity1 = new PhotosEntity();
		entity1.setName("图片2");
		entity1.setZipPath("http://ww2.sinaimg.cn/bmiddle/4b807446gw1epxbpnjazwj20h80b4gmj.jpg");
		entity1.setPath("http://ww2.sinaimg.cn/bmiddle/4b807446gw1epxbpnjazwj20h80b4gmj.jpg");
		photos.add(entity1);
		
		PhotosEntity entity2 = new PhotosEntity();
		entity2.setName("图片3");
		entity2.setZipPath("http://ww1.sinaimg.cn/bmiddle/4b807446gw1epxbpr4gvij20go0af0ww.jpg");
		entity2.setPath("http://ww1.sinaimg.cn/bmiddle/4b807446gw1epxbpr4gvij20go0af0ww.jpg");
		photos.add(entity2);
		
		return photos;
	}
}
