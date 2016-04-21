/*
 * FileName: BrowserActivity.java
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
 * date     : 2014-12-12 下午5:51:01
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.example.activity.tab1;

import android.content.Context;
import android.content.Intent;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.plusub.lib.activity.SimpleBrowserActivity;
import com.plusub.lib.annotate.BindView;
import com.plusub.lib.example.R;
import com.plusub.lib.example.view.HeaderLayout;
import com.plusub.lib.example.view.HeaderLayout.HeaderStyle;
import com.plusub.lib.example.view.HeaderLayout.onLeftImageButtonClickListener;
import com.plusub.lib.util.NetStateUtils;
import com.plusub.lib.util.StringUtils;

/**
 * @ClassName: BrowserActivity
 * @Description: TODO
 * @author blakequ@gmail.com
 * @date： 
 *     <b>文件创建时间：</b>2014-12-12 下午5:51:01<br>
 *     <b>最后修改时间：</b>2014-12-12 下午5:51:01
 * @version v1.0
 */
public class BrowserActivity extends SimpleBrowserActivity {

	@BindView(id = R.id.common_head_layout)
	private HeaderLayout mHeader;
	@BindView(id = R.id.activity_browser)
	private WebView mWebView;
	private String url = "";
	
	public static void launch(Context context, String url){
		Intent intent = new Intent(context, BrowserActivity.class);
		intent.putExtra("url", url);
		context.startActivity(intent);
	}
	
	@Override
	public void initData() {
		// TODO Auto-generated method stub
		//设置WebView离线缓存，若不缓存注释即可
		if (NetStateUtils.hasNetWorkConnection(this)){
			mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		}else{
			mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		}
		url = getIntent().getStringExtra("url");
		if (StringUtils.isEmpty(url)) {
			url = "http://www.baidu.com";
		}
		
		mWebView.loadUrl(url);
		mHeader.init(HeaderStyle.TITLE_LEFT_IMAGEBUTTON);
		mHeader.setTitleLeftImageButton("嵌入式浏览器", R.drawable.ic_actionbar_back_selector, 
				new onLeftImageButtonClickListener() {
			
			@Override
			public void onClick() {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub
	}

	@Override
	protected WebView initWebView(WebView waitInit) {
		// TODO Auto-generated method stub
		return mWebView;
	}

	@Override
	public void setRootView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_browser);
	}


}
