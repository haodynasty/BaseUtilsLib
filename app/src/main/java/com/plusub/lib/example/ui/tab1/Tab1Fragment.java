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
package com.plusub.lib.example.ui.tab1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.plusub.lib.activity.BaseFragment;
import com.plusub.lib.activity.BrowserActivity;
import com.plusub.lib.activity.SimpleWebActivity;
import com.plusub.lib.annotate.BindView;
import com.plusub.lib.example.R;

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

	@BindView(id = R.id.tab1_bt1, click = true)
	private Button mBt1;
	@BindView(id = R.id.tab1_bt2, click = true)
	private Button mBt2;
	@BindView(id = R.id.tab1_bt3, click = true)
	private Button mBt3;
	@BindView(id = R.id.tab1_bt4, click = true)
	private Button mBt4;
	@BindView(id = R.id.tab1_bt5, click = true)
	private Button mBt6;

	@Override
	protected View inflaterView(LayoutInflater inflater, ViewGroup container,
			Bundle bundle) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_tab_1, null);
	}

	@Override
	protected void initView(View parentView) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.tab1_bt1:
				startActivity(BrowserActivity.class);
				BrowserActivity.launch(getActivity(), "http://www.baidu.com", "浏览器");
				break;
			case R.id.tab1_bt2:
				startActivity(RefreshActivity.class);
				break;
			case R.id.tab1_bt3:
				startActivity(ImageSelectActivity.class);
				break;
			case R.id.tab1_bt4:
				 Intent intent = new Intent(getActivity(), SimpleWebActivity.class);
				 intent.putExtra(SimpleWebActivity.URL, "http://www.baidu.com");
				 startActivity(intent);
				break;
			case R.id.tab1_bt5:
				BrowserActivity.launch(getActivity(), "https://github.com/bm-x/PhotoView", "图片浏览");
				break;
			default:
				break;
		}
	}

	@Override
	public void onTrimMemory() {

	}
}
