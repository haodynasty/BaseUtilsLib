/*
 * FileName: Tab1Fragment.java
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
 * date     : 2014-12-12 下午4:56:53
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.example.ui.tab4;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.plusub.lib.activity.BaseFragment;
import com.plusub.lib.annotate.BindView;
import com.plusub.lib.example.R;

import java.io.InputStream;

/**
 * @ClassName: Tab1Fragment
 * @Description: TODO
 * @author blakequ@gmail.com
 * @date： 
 *     <b>文件创建时间：</b>2014-12-12 下午4:56:53<br>
 *     <b>最后修改时间：</b>2014-12-12 下午4:56:53
 * @version v1.0
 */
public class Tab4Fragment extends BaseFragment {

	@BindView(id = R.id.tab4_iv_img1)
	private SimpleDraweeView mImgView1;
	@BindView(id = R.id.tab4_iv_img2)
	private SimpleDraweeView mImgView2;
	@BindView(id = R.id.tab4_iv_gif1)
	private SimpleDraweeView mGifView1;
	@BindView(id = R.id.tab4_iv_gif2)
	private SimpleDraweeView mGifView2;


	public void initData() {
		// TODO Auto-generated method stub
//		long time = System.currentTimeMillis();
//		long currentTime = 0;
//		StringBuilder sb = new StringBuilder();
		
//		try {
			//测试json解析
//			String str1 = getFromAssets("json_error.txt");
//			StatusEntity status = (StatusEntity) JsonParserUtils.initEntityParser(StatusEntity.class, str1);
//			System.out.println("status:"+status);
//			
//			currentTime = System.currentTimeMillis();
//			System.out.println("------------------"+(currentTime-time));
//			time = currentTime;
			
//			String str2 = getFromAssets("json_list.txt");
//			List<UserEntity> user = (List<UserEntity>) JsonParserUtils.initEntityParser(UserEntity.class, str2, true);
//			if (user != null) {
//				for (int  i= 0;  i< user.size(); i++) {
//					System.out.println("user: "+user.get(i));
//					sb.append(user.get(i));
//				}
//			}
//			mTvContent.setText(sb.toString());
//
//			currentTime = System.currentTimeMillis();
//			System.out.println("------------------"+(currentTime-time));
//			time = currentTime;
			
//			String str3 = getFromAssets("json_normal.txt");
//			UserEntity user2 = (UserEntity) JsonParserUtils.initEntityParser(UserEntity.class, str3);
//			System.out.println("user2: "+user2);
//			
//			currentTime = System.currentTimeMillis();
//			System.out.println("------------------"+(currentTime-time));
//			time = currentTime;
			
//			String str4 = getFromAssets("json_object_normal.txt");
//			List<DoctorEntity> doctor = (List<DoctorEntity>) JsonParserUtils.initEntityParser(DoctorEntity.class, str4);
//			if (doctor != null) {
//				for (int  i= 0;  i< doctor.size(); i++) {
//					System.out.println("doctor: "+doctor.get(i));
//				}
//			}
//			
//			currentTime = System.currentTimeMillis();
//			System.out.println("------------------"+(currentTime-time));
//			time = currentTime;
			
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (CommException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}

	@Override
	protected View inflaterView(LayoutInflater inflater, ViewGroup container,
			Bundle bundle) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment_tab_4, null);
	}

	@Override
	protected void initView(View parentView) {
		// TODO Auto-generated method stub
		Uri uri = Uri.parse("res://" + getActivity().getPackageName() + "/" + R.drawable.ic_person_head_bg);
		mImgView1.setImageURI(uri);
		Uri uri1 = Uri.parse("res://" + getActivity().getPackageName() + "/" + R.drawable.ic_person_head_bg_webp);
		mImgView2.setImageURI(uri1);

		Uri uri2 = Uri.parse("res://" + getActivity().getPackageName() + "/" + R.drawable.ic_gif);
		DraweeController controller = Fresco.newDraweeControllerBuilder()
				.setUri(uri2)
				.setAutoPlayAnimations(true)//自动播放动画
				.build();
		mGifView1.setController(controller);

		Uri uri3 = Uri.parse("res://" + getActivity().getPackageName() + "/" + R.drawable.ic_gif_webp);
		DraweeController controller2 = Fresco.newDraweeControllerBuilder()
				.setUri(uri3)
				.setAutoPlayAnimations(true)//自动播放动画
				.build();
		mGifView2.setController(controller2);
	}



	//从assets 文件夹中获取文件并读取数据  
    public String getFromAssets(String fileName){  
        String result = "";  
            try {  
                InputStream in = getActivity().getResources().getAssets().open(fileName);  
                //获取文件的字节数  
                int lenght = in.available();  
                //创建byte数组  
                byte[]  buffer = new byte[lenght];  
                //将文件中的数据读到byte数组中  
                in.read(buffer);  
//                result = EncodingUtils.getString(buffer, "UTF-8");
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
            return result;  
    }

	@Override
	public void onTrimMemory() {

	}
}
