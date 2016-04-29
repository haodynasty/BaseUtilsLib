package com.plusub.lib.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.plusub.lib.util.StringUtils;

/**
 * <p>Here is an example of subclassing:</p>
 * must register in manifest.xml
 * <pre class="prettyprint">
 * Intent intent = new Intent(this, SimpleWebActivity.class)
 * intent.putExtra(SimpleWebActivity.URL, "www.baidu.com");
 * startActivity(intent);
 *</pre>
 * @author blakequ Blakequ@gmail.com
 *
 */
public class SimpleWebActivity extends BaseActivity {

	public static final String URL = "url";
	private ProgressBar mLoadingView;
	protected WebView mWebView;
	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();
		initEvent();
	}

	@Override
	public int provideContentViewId() {
		return 0;
	}

	@Override
	public View provideContentViewLayout() {
		// TODO Auto-generated method stub
		RelativeLayout rl = new RelativeLayout(this);
		rl.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		mWebView = new WebView(this);
		mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		mWebView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		RelativeLayout.LayoutParams rlParams = new RelativeLayout.LayoutParams  
	            (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT); 
		rlParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		
		mLoadingView = new ProgressBar(this, null ,android.R.attr.progressBarStyleLarge);//progressBarStyleSmallTitle
		
		rl.addView(mWebView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		rl.addView(mLoadingView, rlParams);
		return rl;
	}
	
	public void initView() {
		url = getIntent().getStringExtra(URL);
		if (!StringUtils.isEmpty(url)) {
			mWebView.loadUrl(url);
		}
	}


	public void initEvent() {
		// TODO Auto-generated method stub
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
			}

		});
		mWebView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				mWebView.loadUrl(url);
				return true;
			}

			public void onReceivedSslError(WebView view,
					SslErrorHandler handler, android.net.http.SslError error) {
				handler.proceed();
			}

			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				showProgress();
			}

			private void showProgress() {
				mLoadingView.setVisibility(View.VISIBLE);
			}

			private void dismissProgress() {
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
}
