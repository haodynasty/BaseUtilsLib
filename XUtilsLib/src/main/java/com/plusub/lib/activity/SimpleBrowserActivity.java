/*
 * FileName: IActivity.java
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
 * date     : 2014-12-1 上午11:16:49
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 简单浏览器模板<br>
 * <b>说明</b> 开发者必须首先实现initWebView(WebViewmWebView)方法，
 * 将webview返回。若要显示网页，可手动调用mWebView.loadUrl(url);<br>
 * @ClassName: SimpleBrowserActivity
 * @Description: TODO 简单浏览器模板
 * @author qh@plusub.com
 * @date 2014-12-1 下午9:14:30
 * @version v1.0
 */
@SuppressLint("SetJavaScriptEnabled")
public abstract class SimpleBrowserActivity extends BaseActivity {

    /** 浏览器的webview，你可以在子类中使用 */
    protected WebView mWebView;
    protected String mCurrentUrl;
    
	/**
     * 必须实现, 初始化视图，返回WebView视图
     * <p>Title: initWebVie
     * <p>Description: 
     * @param waitInit
     * @return
     */
    protected abstract WebView initWebView(WebView waitInit);

    @Override
    public void initView(){
    	mWebView = initWebView(mWebView);
        initWebView();
    }

    /**
     * 载入链接之前会被调用
     * 
     * @param view
     *            WebView
     * @param url
     *            链接地址
     */
    protected void onUrlLoading(WebView view, String url) {}

    /**
     * 链接载入成功后会被调用
     * 
     * @param view
     *            WebView
     * @param url
     *            链接地址
     */
    protected void onUrlFinished(WebView view, String url) {}

    /**
     * 获取当前WebView显示页面的标题
     * 
     * @param view
     *            WebView
     * @param title
     *            web页面标题
     */
    protected void getWebTitle(WebView view, String title) {}

    /**
     * 获取当前WebView显示页面的图标
     * 
     * @param view
     *            WebView
     * @param icon
     *            web页面图标
     */
    protected void getWebIcon(WebView view, Bitmap icon) {}

    /**
     * 初始化浏览器设置信息
     */
    private void initWebView() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new KJWebViewClient());
        mWebView.setWebChromeClient(new KJWebChromeClient());
    }

    /**
     * 返回事件屏蔽
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)
                && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class KJWebChromeClient extends WebChromeClient {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            getWebTitle(view, title);
        }

        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            super.onReceivedIcon(view, icon);
            getWebIcon(view, icon);
        }
    }

    private class KJWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view,
                String url) {
            onUrlLoading(view, url);
            mCurrentUrl = url;
            return super.shouldOverrideUrlLoading(view, url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            onUrlFinished(view, url);
        }
    }
}
