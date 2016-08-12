package com.plusub.lib.example.ui.tab1.loadview;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import com.plusub.lib.example.R;
import com.plusub.lib.example.ui.ToolbarActivity;
import com.plusub.lib.view.loadview.VaryViewHelperX;

public class Example2Activity extends ToolbarActivity {

	private EditText editText;
	private WebView webView;
	private LoadViewHelper helper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		editText = (EditText) findViewById(R.id.editText1);
		webView = (WebView) findViewById(R.id.webView1);
		helper = new LoadViewHelper(new VaryViewHelperX(webView));
		webView.setWebViewClient(webViewClient);
		webView.loadUrl(editText.getText().toString());
	}

	public void clickLoad(View view) {
		webView.loadUrl(editText.getText().toString());
	}

	private WebViewClient webViewClient = new WebViewClient() {
		@Override
		public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
			super.onPageStarted(view, url, favicon);
			helper.showLoading("");
		};

		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			helper.restore();
		};

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}

	};

	@Override
	public void onTrimMemory() {

	}

	@Override
	public int provideContentViewId() {
		return R.layout.activity_example2;
	}
}
