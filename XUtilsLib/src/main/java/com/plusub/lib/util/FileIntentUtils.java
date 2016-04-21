/*
 * FileName: FileIntentUtils.java
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
 * date     : 2014-9-21 下午8:57:17
 * last modify author :
 * version : 1.0
 */
package com.plusub.lib.util;

import java.io.File;

import android.content.Intent;
import android.net.Uri;

/**
 * 文件打开选择器
 * @author service@plusub.com
 *
 */
public class FileIntentUtils {
	
	/**
	 * 打开类型文件
	 * <p>Title: openFile
	 * <p>Description: 
	 * @param filePath
	 * @return
	 */
	public static Intent openFile(String filePath){
		File file = new File(filePath);
		if(!file.exists()) return null;
		/* 取得扩展名 */
		String end=file.getName().substring(file.getName().lastIndexOf(".") + 1,file.getName().length()).toLowerCase(); 
		/* 依扩展名的类型决定MimeType */
		if(end.equals("m4a")||end.equals("mp3")||end.equals("mid")||
				end.equals("xmf")||end.equals("ogg")||end.equals("wav")){
			return getAudioFileIntent(filePath);
		}else if(end.equals("3gp")||end.equals("mp4")){
			return getAudioFileIntent(filePath);
		}else if(end.equals("jpg")||end.equals("gif")||end.equals("png")||
				end.equals("jpeg")||end.equals("bmp")){
			return getImageFileIntent(filePath);
		}else if(end.equals("apk")){
			return getApkFileIntent(filePath);
		}else if(end.equals("ppt")){
			return getPptFileIntent(filePath);
		}else if(end.equals("xls")){
			return getExcelFileIntent(filePath);
		}else if(end.equals("doc")){
			return getWordFileIntent(filePath);
		}else if(end.equals("pdf")){
			return getPdfFileIntent(filePath);
		}else if(end.equals("chm")){
			return getChmFileIntent(filePath);
		}else if(end.equals("txt")){
			return getTextFileIntent(filePath,false);
		}else{
			return getAllIntent(filePath);
		}
	}
	
	/**
	 * Android获取一个用于打开APK文件的intent
	 **/
	public static Intent getAllIntent( String param ) {

		Intent intent = new Intent();  
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
		intent.setAction(android.content.Intent.ACTION_VIEW);  
		Uri uri = Uri.fromFile(new File(param ));
		intent.setDataAndType(uri,"*/*"); 
		return intent;
	}
	/**
	 * Android获取一个用于打开APK文件的intent
	 * <p>Title: getApkFileIntent
	 * <p>Description: 
	 * @param param
	 * @return
	 */
	public static Intent getApkFileIntent( String param ) {

		Intent intent = new Intent();  
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
		intent.setAction(android.content.Intent.ACTION_VIEW);  
		Uri uri = Uri.fromFile(new File(param ));
		intent.setDataAndType(uri,"application/vnd.android.package-archive"); 
		return intent;
	}

	/**
	 * Android获取一个用于打开VIDEO文件的intent
	 * <p>Title: getVideoFileIntent
	 * <p>Description: 
	 * @param param
	 * @return
	 */
	public static Intent getVideoFileIntent( String param ) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("oneshot", 0);
		intent.putExtra("configchange", 0);
		Uri uri = Uri.fromFile(new File(param ));
		intent.setDataAndType(uri, "video/*");
		return intent;
	}

	/**
	 * Android获取一个用于打开AUDIO文件的intent
	 * <p>Title: getAudioFileIntent
	 * <p>Description: 
	 * @param param
	 * @return
	 */
	public static Intent getAudioFileIntent( String param ){

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("oneshot", 0);
		intent.putExtra("configchange", 0);
		Uri uri = Uri.fromFile(new File(param ));
		intent.setDataAndType(uri, "audio/*");
		return intent;
	}

	/**
	 * Android获取一个用于打开Html文件的intent   
	 * <p>Title: getHtmlFileIntent
	 * <p>Description: 
	 * @param param
	 * @return
	 */
	public static Intent getHtmlFileIntent( String param ){

		Uri uri = Uri.parse(param ).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(param ).build();
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.setDataAndType(uri, "text/html");
		return intent;
	}

	/**
	 * Android获取一个用于打开图片文件的intent
	 * <p>Title: getImageFileIntent
	 * <p>Description: 
	 * @param param
	 * @return
	 */
	public static Intent getImageFileIntent( String param ) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri = Uri.fromFile(new File(param ));
		intent.setDataAndType(uri, "image/*");
		return intent;
	}

	/**
	 * Android获取一个用于打开PPT文件的intent   
	 * <p>Title: getPptFileIntent
	 * <p>Description: 
	 * @param param
	 * @return
	 */
	public static Intent getPptFileIntent( String param ){  

		Intent intent = new Intent("android.intent.action.VIEW");   
		intent.addCategory("android.intent.category.DEFAULT");   
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
		Uri uri = Uri.fromFile(new File(param ));   
		intent.setDataAndType(uri, "application/vnd.ms-powerpoint");   
		return intent;   
	}   

	/**
	 * Android获取一个用于打开Excel文件的intent   
	 * <p>Title: getExcelFileIntent
	 * <p>Description: 
	 * @param param
	 * @return
	 */
	public static Intent getExcelFileIntent( String param ){  

		Intent intent = new Intent("android.intent.action.VIEW");   
		intent.addCategory("android.intent.category.DEFAULT");   
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
		Uri uri = Uri.fromFile(new File(param ));   
		intent.setDataAndType(uri, "application/vnd.ms-excel");   
		return intent;   
	}   

	/**
	 * Android获取一个用于打开Word文件的intent   
	 * <p>Title: getWordFileIntent
	 * <p>Description: 
	 * @param param
	 * @return
	 */
	public static Intent getWordFileIntent( String param ){  

		Intent intent = new Intent("android.intent.action.VIEW");   
		intent.addCategory("android.intent.category.DEFAULT");   
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
		Uri uri = Uri.fromFile(new File(param ));   
		intent.setDataAndType(uri, "application/msword");   
		return intent;   
	}   

	/**
	 * Android获取一个用于打开CHM文件的intent   
	 * <p>Title: getChmFileIntent
	 * <p>Description: 
	 * @param param
	 * @return
	 */
	public static Intent getChmFileIntent( String param ){   

		Intent intent = new Intent("android.intent.action.VIEW");   
		intent.addCategory("android.intent.category.DEFAULT");   
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
		Uri uri = Uri.fromFile(new File(param ));   
		intent.setDataAndType(uri, "application/x-chm");   
		return intent;   
	}   

	/**
	 * Android获取一个用于打开文本文件的intent   
	 * <p>Title: getTextFileIntent
	 * <p>Description: 
	 * @param param
	 * @param paramBoolean
	 * @return
	 */
	public static Intent getTextFileIntent( String param, boolean paramBoolean){   

		Intent intent = new Intent("android.intent.action.VIEW");   
		intent.addCategory("android.intent.category.DEFAULT");   
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
		if (paramBoolean){   
			Uri uri1 = Uri.parse(param );   
			intent.setDataAndType(uri1, "text/plain");   
		}else{   
			Uri uri2 = Uri.fromFile(new File(param ));   
			intent.setDataAndType(uri2, "text/plain");   
		}   
		return intent;   
	}  
	
	/**
	 * Android获取一个用于打开PDF文件的intent   
	 * <p>Title: getPdfFileIntent
	 * <p>Description: 
	 * @param param
	 * @return
	 */
	public static Intent getPdfFileIntent( String param ){   

		Intent intent = new Intent("android.intent.action.VIEW");   
		intent.addCategory("android.intent.category.DEFAULT");   
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
		Uri uri = Uri.fromFile(new File(param ));   
		intent.setDataAndType(uri, "application/pdf");   
		return intent;   
	}
}
