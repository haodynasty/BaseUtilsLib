/*
 * FileName: DownloadProgressDialog.java
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
 * date     : 2014-6-15 下午8:59:02
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.imagebroswer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.plusub.lib.view.R;
import com.plusub.lib.view.BasePopupWindow;
import com.plusub.lib.view.RoundProgressBar;

public class DownloadProgressDialog extends BasePopupWindow {

	private Context mContext;
	private TextView mTvCurrentProgress;
	private TextView mTvSizeProgress;
	private RoundProgressBar mRoundBar;
	private TextView mTvError;
	private TextView mTvWait;
	
	public DownloadProgressDialog(Context context){
		super(LayoutInflater.from(context).inflate(
				R.layout.dialog_photo_download_progress, null),
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		mContext = context;
	}
	
	@Override
	public void initViews() {
		// TODO Auto-generated method stub
		mTvCurrentProgress = (TextView) findViewById(R.id.photo_tv_download_progress);
		mTvSizeProgress = (TextView) findViewById(R.id.photo_tv_download_size);
		mRoundBar = (RoundProgressBar) findViewById(R.id.photo_download_progressbar);
		mTvError = (TextView) findViewById(R.id.photo_tv_error);
		mTvWait = (TextView) findViewById(R.id.photo_tv_wait);
	}

	@Override
	public void initEvents() {
		// TODO Auto-generated method stub
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
	}

	public void setProgressValue(int total, int current){
		mRoundBar.setMax(total);
		mRoundBar.setProgress(current);
		mTvCurrentProgress.setText(getPercentString(current, total));
	}
	
	/**
	 * 下载失败为true
	 * <p>Title: setDownloadStatus
	 * <p>Description: 
	 * @param status
	 */
	public void setDownloadStatus(boolean isFail){
		if (isFail) {
			mTvError.setVisibility(View.VISIBLE);
		}else{
			mTvError.setVisibility(View.INVISIBLE);
		}
	}
	
	public static String getPercentString(long progress, long max) {
        int rate = 0;
        if (progress <= 0 || max <= 0) {
            rate = 0;
        } else if (progress > max) {
            rate = 100;
        } else {
            rate = (int)((double)progress / max * 100);
        }
        return new StringBuilder(16).append(rate).append("%").toString();
    }
}
