/*
 * FileName: TabFragment1.java
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
 * date     : 2015-6-2 上午11:05:18
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.example.activity.tab1.tab;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.plusub.lib.activity.BaseFragment;
import com.plusub.lib.example.R;
import com.plusub.lib.task.TaskMessage;

/**
 * @ClassName: TabFragment1
 * @Description: TODO
 * @author qh@plusub.com
 * @date： 
 *     <b>文件创建时间：</b>2015-6-2 上午11:05:18<br>
 *     <b>最后修改时间：</b>2015-6-2 上午11:05:18
 * @version v1.0
 */
public class TabFragment1 extends BaseFragment {

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
		return inflater.inflate(R.layout.fragment_tab_tab1, null);
	}

	@Override
	protected void initView(View parentView) {
		// TODO Auto-generated method stub

	}

}
