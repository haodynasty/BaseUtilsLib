package com.plusub.lib.activity;

/**
 * Copyright (C) BlakeQu All Rights Reserved <blakequ@gmail.com>
 * <p/>
 * Licensed under the blakequ.com License, Version 1.0 (the "License");
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p/>
 * author  : quhao <blakequ@gmail.com>
 * date     : 2016/4/26 16:51
 * last modify author :
 * version : 1.0
 * description:
 */
public interface BaseTask {

    /**
     * Method: onTrimMemory <br>
     * Description: 需要释放内存的操作，当内存不足时会自动调用,或者只有当我们程序中的<b>所有UI组件<b/>全部不可见的时候才会触发<br>
     * Date: 2016/4/26 10:30
     */
    void onTrimMemory();
}
