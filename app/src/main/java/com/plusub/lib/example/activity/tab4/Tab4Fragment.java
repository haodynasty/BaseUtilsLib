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
package com.plusub.lib.example.activity.tab4;

import java.io.InputStream;
import java.util.List;

import org.apache.http.util.EncodingUtils;
import org.json.JSONException;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.plusub.lib.activity.BaseFragment;
import com.plusub.lib.annotate.BindView;
import com.plusub.lib.annotate.JsonParserUtils;
import com.plusub.lib.bean.StatusEntity;
import com.plusub.lib.example.R;
import com.plusub.lib.example.entity.DoctorEntity;
import com.plusub.lib.example.entity.UserEntity;
import com.plusub.lib.example.view.HeaderLayout;
import com.plusub.lib.example.view.HeaderLayout.HeaderStyle;
import com.plusub.lib.task.TaskMessage;
import com.plusub.lib.util.CommException;

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
	
	@BindView(id = R.id.common_head_layout)
	private HeaderLayout mHeader;
	@BindView(id = R.id.frament4_tv)
	private TextView mTvContent;

	@Override
	public void refresh(TaskMessage msg, Object... param) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		mHeader.init(HeaderStyle.TITLE_MIDDLE_TEXT);
		mHeader.setMiddleTitle("测试解析");
		
		long time = System.currentTimeMillis();
		long currentTime = 0;
		StringBuilder sb = new StringBuilder();
		
		try {
			//测试json解析
//			String str1 = getFromAssets("json_error.txt");
//			StatusEntity status = (StatusEntity) JsonParserUtils.initEntityParser(StatusEntity.class, str1);
//			System.out.println("status:"+status);
//			
//			currentTime = System.currentTimeMillis();
//			System.out.println("------------------"+(currentTime-time));
//			time = currentTime;
			
			String str2 = getFromAssets("json_list.txt");
			List<UserEntity> user = (List<UserEntity>) JsonParserUtils.initEntityParser(UserEntity.class, str2, true);
			if (user != null) {
				for (int  i= 0;  i< user.size(); i++) {
					System.out.println("user: "+user.get(i));
					sb.append(user.get(i));
				}
			}
			mTvContent.setText(sb.toString());
			
			currentTime = System.currentTimeMillis();
			System.out.println("------------------"+(currentTime-time));
			time = currentTime;
			
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
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CommException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub

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

	}

	private void jsonParser(String jsonStr){
		//error
		
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
                result = EncodingUtils.getString(buffer, "UTF-8");  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
            return result;  
    }  
}
