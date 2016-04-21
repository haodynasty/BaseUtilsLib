/*
 * FileName: RotateLoadingDialog.java
 * Copyright (C) 2014 Plusub Tech. Co. Ltd. All Rights Reserved <admin@plusub.com>
 * 
 * Licensed under the Plusub License, Version 1.0 (the "License");
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * author  : quhao <blakequ@gmail.com>
 * date     : 2014-6-7 下午4:19:20
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.view;

import com.plusub.lib.view.R;

import android.content.Context;
import android.widget.TextView;

/**
 * 带旋转动画的载入dialog
 * @author blakequ Blakequ@gmail.com
 *
 */
public class RotateLoadingDialog extends BaseDialog {

	private RotateImageView mImageView;
	private TextView mTextView;
	private String mText;
	
	public RotateLoadingDialog(Context context, String text){
		super(context);
		mText = text;
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		setContentView(R.layout.common_rotate_loading_dialog);
		mImageView = (RotateImageView) findViewById(R.id.loadingdialog_fiv_icon);
		mTextView = (TextView) findViewById(R.id.loadingdialog_htv_text);
		mImageView.startAnimation();
		mTextView.setText(mText);
	}
	
	public void setText(String mText) {
		this.mText = mText;
		mTextView.setText(mText);
	}

	@Override
	public void dismiss() {
		if (isShowing()) {
			super.dismiss();
		}
	}
}
