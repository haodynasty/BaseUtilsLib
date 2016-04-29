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

import android.view.View;

/**
 * @ClassName: IActivity
 * @Description: TODO
 * @author blakequ@gmail.com
 * @date： 
 *     <b>文件创建时间：</b>2014-12-1 上午11:16:49<br>
 *     <b>最后修改时间：</b>2014-12-1 上午11:16:49
 * @version v1.0
 */
public interface IActivity {

    /** 必须在这里面返回主界面布局id */
    int provideContentViewId();

    /**
     * 可以返回主界面布局视图
     * @return
     */
    View provideContentViewLayout();
}
