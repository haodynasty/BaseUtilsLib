package com.plusub.lib.example.ui.tab1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.plusub.lib.annotate.BindView;
import com.plusub.lib.example.R;
import com.plusub.lib.example.ui.ToolbarActivity;
import com.plusub.lib.util.ImageUtil;
import com.plusub.lib.util.StringUtils;
import com.plusub.lib.util.ToastUtils;
import com.plusub.lib.util.logger.Logger;
import com.plusub.lib.view.other.ImageSelectPopupWindow;

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
 * author  : quhao <blakequ@gmail.com> <br>
 * date     : 2016/8/12 20:12 <br>
 * last modify author : <br>
 * version : 1.0 <br>
 * description:
 */
public class ImageSelectActivity extends ToolbarActivity {
    @BindView(id = R.id.imageselect_bt, click = true)
    private Button mBt6;
    @BindView(id = R.id.imageselect_image)
    private ImageView mIvImage;

    private ImageSelectPopupWindow mImagePopupWin;
    private String imgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImagePopupWin = new ImageSelectPopupWindow(this);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.imageselect_bt:
                mImagePopupWin.showAtLocation(findViewById(R.id.root_view), Gravity.BOTTOM, 0, 0);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        mImagePopupWin.dismiss();
        imgPath = null;
        switch (requestCode) {
            case ImageSelectPopupWindow.FROM_ALBUM:
                imgPath = mImagePopupWin.getAlbumFilePath(data);
                mImagePopupWin.cropImage(imgPath, 150, 150);
                break;
            case ImageSelectPopupWindow.FROM_CANMERA:
                imgPath = mImagePopupWin.getCameraFilePath();
                mImagePopupWin.cropImage(imgPath, 150, 150);
                break;
            case ImageSelectPopupWindow.FROM_CROP:
                imgPath = mImagePopupWin.getCropImgFilePath(data);
                if (!StringUtils.isEmpty(imgPath)) {
                    mIvImage.setVisibility(View.VISIBLE);
                    mIvImage.setImageBitmap(ImageUtil.getBitmapFromPath(imgPath));
                }else{
                    mIvImage.setVisibility(View.VISIBLE);
                    ToastUtils.show(this, "获取图片失败");
                }
                break;
            default:
                break;
        }
        Logger.i("imagepath:" + imgPath);
    }

    @Override
    public void onTrimMemory() {

    }

    @Override
    public int provideContentViewId() {
        return R.layout.activity_imageselect;
    }
}
