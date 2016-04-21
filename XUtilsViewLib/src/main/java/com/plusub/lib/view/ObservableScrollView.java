/*
 * FileName: ObservableScrollView.java
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
 * date     : 2015-7-2 下午4:27:42
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * @ClassName: ObservableScrollView
 * @Description: TODO  只是滑动监听的ScrollView
 * @author qh@plusub.com
 * @date： 
 *     <b>文件创建时间：</b>2015-7-2 下午4:27:42<br>
 *     <b>最后修改时间：</b>2015-7-2 下午4:27:42
 * @version v1.0
 */
public class ObservableScrollView extends ScrollView {
	public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObservableScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 滑动监听器
     * @ClassName: OnScrollChangedListener
     * @Description: TODO
     * @author qh@plusub.com
     * @date 2015-7-2 下午4:28:26
     * @version v1.0
     */
    public interface OnScrollChangedListener {
        public void onScrollChanged(int x, int y, int oldX, int oldY);
    }

    private OnScrollChangedListener onScrollChangedListener;

    public void setOnScrollListener(OnScrollChangedListener onScrollChangedListener) {
        this.onScrollChangedListener = onScrollChangedListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldX, int oldY) {
        super.onScrollChanged(x, y, oldX, oldY);
        if (onScrollChangedListener != null) {
            onScrollChangedListener.onScrollChanged(x, y, oldX, oldY);
        }
    }
}
