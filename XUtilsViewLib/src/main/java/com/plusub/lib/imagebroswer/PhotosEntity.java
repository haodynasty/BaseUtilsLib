/*
 * FileName: PhotosEntity.java
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
 * date     : 2014-6-14 下午4:56:49
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.imagebroswer;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 图片实体
 * @author blakequ Blakequ@gmail.com
 *
 */
public class PhotosEntity implements Parcelable{
	private String name;
	private String zipPath;
	private String path;
	
	public PhotosEntity(){
		
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getZipPath() {
		return zipPath;
	}
	public void setZipPath(String zipPath) {
		this.zipPath = zipPath;
	}
	
	@Override
	public String toString() {
		return "PhotosEntity [name=" + name + ", zipPath=" + zipPath
				+ ", path=" + path + "]";
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(name);
		dest.writeString(path);
		dest.writeString(zipPath);
	}
	
	public static final Parcelable.Creator<PhotosEntity> CREATOR = new Creator<PhotosEntity>() {

		@Override
		public PhotosEntity createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			PhotosEntity entity = new PhotosEntity();
			entity.setName(source.readString());
			entity.setPath(source.readString());
			entity.setZipPath(source.readString());
			return entity;
		}

		@Override
		public PhotosEntity[] newArray(int size) {
			// TODO Auto-generated method stub
			return new PhotosEntity[size];
		}
		
	};
	
}
