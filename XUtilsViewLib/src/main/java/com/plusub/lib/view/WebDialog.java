package com.plusub.lib.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.plusub.lib.util.NetStateUtils;

/**
 * 从web获取内容的dialog
 * <p>可以设置监听器,如果载入失败{@link #setOnWebDialogErrorListener(OnWebDialogErrorListener)}</>
 * @author blakequ Blakequ@gmail.com
 *
 */
public class WebDialog extends BaseDialog {

	private WebView mWebView;
	private View mLoadingView;
	private Context mContext;
	private NetStateUtils mNetWorkUtils;
	private boolean isCache = false;
	private OnWebDialogErrorListener mOnWebDialogErrorListener;

	/**
	 * 是否设置缓存
	 * @param context
	 * @param isCache
	 */
	public WebDialog(Context context, boolean isCache) {
		super(context);
		this.isCache = isCache;
		mContext = context;
		setDialogContentView(R.layout.include_dialog_webview);
		mLoadingView = findViewById(R.id.dialog_web_loading_indicator);
		mWebView = (WebView) findViewById(R.id.dialog_web_webview);
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

	public void init(CharSequence title, CharSequence button1,
			OnClickListener listener) {
		super.setTitle(title);
		super.setButton1(button1, listener);
	}
	

	public void loadUrl(String url) {
		if (url == null) {
			if (mOnWebDialogErrorListener != null) {
				mOnWebDialogErrorListener.urlError();
			}
			return;
		}
		if (mNetWorkUtils.getNetWorkConnectionType(mContext) == NetStateUtils.NetWorkState.NONE) {
			if (mOnWebDialogErrorListener != null) {
				mOnWebDialogErrorListener.networkError();
			}
//			return;
		}
		mWebView.getSettings().setJavaScriptEnabled(true);
		if (isCache){
			if (NetStateUtils.hasNetWorkConnection(this.mContext)){
				mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
			}else{
				mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
			}
		}else{
			mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		}
		mWebView.getSettings().setLayoutAlgorithm(
				WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
		mWebView.loadUrl(url);
	}
	

	public void setOnWebDialogErrorListener(OnWebDialogErrorListener listener) {
		mOnWebDialogErrorListener = listener;
	}

	/**
	 * 载入错误异常家庭器
	 */
	public interface OnWebDialogErrorListener {

		void urlError();

		void networkError();
	}
}
