package com.plusub.lib.example.ui;

import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.animation.DecelerateInterpolator;

import com.plusub.lib.activity.BaseActivity;
import com.plusub.lib.example.R;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

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
 * date     : 2016/4/27 15:00
 * last modify author :
 * version : 1.0
 * description:
 */
public abstract class ToolbarActivity extends BaseActivity {
    protected AppBarLayout mAppBar;
    protected Toolbar mToolbar;
    protected boolean mIsHidden = false;
    private CompositeSubscription mCompositeSubscription;


    /**
     * 获取RxJava订阅者组（管理多个订阅者）
     * @return
     */
    public CompositeSubscription getCompositeSubscription() {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        return this.mCompositeSubscription;
    }


    /**
     * 添加订阅者
     * @param s
     */
    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        this.mCompositeSubscription.add(s);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAppBar = (AppBarLayout) findViewById(R.id.toolbar_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar == null || mAppBar == null) {
            throw new IllegalStateException("No toolbar");
        }

        setSupportActionBar(mToolbar);
        if (isCanBack()) {
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //设置ToolBar下面的阴影
        if (Build.VERSION.SDK_INT >= 21) {
            mAppBar.setElevation(0);//10.6f
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
    }

    /**
     * @return 是否显示返回按钮
     */
    public boolean isCanBack() {
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }else {
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * 设置AppBar透明度:0全透明，1不透明
     * @param alpha
     */
    protected void setAppBarAlpha(float alpha) {
        mAppBar.setAlpha(alpha);
    }


    /**
     *显示或隐藏toolbar
     */
    protected void hideOrShowToolbar() {
        mAppBar.animate()
                .translationY(mIsHidden ? 0 : -mAppBar.getHeight())
                .setInterpolator(new DecelerateInterpolator(2))
                .start();

        mIsHidden = !mIsHidden;
    }

    public void setToolbarBackgroundColor(int colorRes) {
        if (mToolbar != null) {
            ColorDrawable background = (ColorDrawable) mToolbar.getBackground();
            background.setColor(getResources().getColor(colorRes));
        }
    }
}
