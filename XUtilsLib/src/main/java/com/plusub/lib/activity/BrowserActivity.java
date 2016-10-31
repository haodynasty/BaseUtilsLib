package com.plusub.lib.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.plusub.lib.util.NetStateUtils;
import com.plusub.lib.util.StringUtils;
import com.plusub.lib.util.SystemUtils;
import com.plusub.lib.util.logger.Logger;

/**
 * <p>Here is an example of subclassing:</p>
 * must register in manifest.xml
 * <pre class="prettyprint">
 * {@link #launch(Context, String, String)} or{@link #launch(Context, String, boolean,String)}
 *</pre>
 * Created by PLUSUB on 2015/10/23.
 */
public class BrowserActivity extends BaseActivity {

    private WebView mWebView;
    private String url = "";
    private ProgressBar mLoadingView;
    private String newUrl, currentUrl;
    private boolean isChanged = true;
    private boolean isCache = false;

    public static void launch(Context context, String url, String title) {
        launch(context, url, false, title);
    }

    public static void launch(Context context, String url, boolean isCache, String title){
        Intent intent = new Intent(context, BrowserActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("cache", isCache);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initEvent();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)
                && mWebView.canGoBack()) {
            mWebView.stopLoading();
            if (newUrl != null && !newUrl.equals(currentUrl)){
                mWebView.goBack();
            }else{
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @SuppressLint("NewApi")
    public void initView() {
        if (SystemUtils.getSDKVersion() >= 11){
             ActionBar actionBar = getActionBar();
            if(actionBar != null){
                actionBar.show();
                actionBar.setDisplayShowHomeEnabled(true);
                actionBar.setDisplayShowCustomEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setTitle(getIntent().getStringExtra("title"));
            }
        }
        url = getIntent().getStringExtra("url");
        isCache = getIntent().getBooleanExtra("cache", false);
        currentUrl = newUrl = url;
        if (!StringUtils.isEmpty(url)) {
            mWebView.loadUrl(url);
        }
    }

    public void initEvent() {
        // TODO Auto-generated method stub
        //设置WebView离线缓存，若不缓存注释即可
        if (isCache){
            if (NetStateUtils.hasNetWorkConnection(this)){
                mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            }else{
                mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            }
        }else{
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        }
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                currentUrl = url;
                mWebView.loadUrl(url);
                return true;
            }

            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler, android.net.http.SslError error) {
                super.onReceivedSslError(view, handler, error);
                Logger.e("SimpleWebActivity", "SSL Certificate Error " + error.getPrimaryError());
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                currentUrl = url;
                showProgress();
            }

            private void showProgress() {
                mLoadingView.setVisibility(View.VISIBLE);
            }

            private void dismissProgress() {
                if (isChanged){
                    newUrl = currentUrl;
                    isChanged = false;
                }
                mLoadingView.setVisibility(View.GONE);
            }

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                dismissProgress();
            }
        });
    }

    @Override
    public void onTrimMemory() {

    }

    @Override
    public int provideContentViewId() {
        return 0;
    }

    @Override
    public View provideContentViewLayout() {
        // TODO Auto-generated method stub
        RelativeLayout rl = new RelativeLayout(this);
        rl.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        mWebView = new WebView(this);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        RelativeLayout.LayoutParams rlParams = new RelativeLayout.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rlParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        mLoadingView = new ProgressBar(this, null ,android.R.attr.progressBarStyleLarge);//progressBarStyleSmallTitle

        rl.addView(mWebView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rl.addView(mLoadingView, rlParams);
        return rl;
    }

}
