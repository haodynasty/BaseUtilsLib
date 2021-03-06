package com.plusub.lib.util;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Parcel;
import android.text.ParcelableSpan;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * 可点击的媒体链接实现，如播放音频
 * @author blakequ Blakequ@gmail.com
 *
 */
public class MediaSpan extends ClickableSpan implements ParcelableSpan {

	private final String mediaUrl;
	private MediaPlayer mMediaPlayer;
	
	public MediaSpan(String mediaUrl) {
		super();
		this.mediaUrl = mediaUrl;
		mMediaPlayer = new MediaPlayer();
	}
	
	public MediaSpan(Parcel src) {
		mediaUrl = src.readString();
    }

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(mediaUrl);
	}
	
	public String getMediaUrl(){
		return mediaUrl;
	}

	@Override
	public int getSpanTypeId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void onClick(View widget) {
		// TODO Auto-generated method stub
		 Context context = widget.getContext();
		 playMedia();
	}
	
	private void playMedia(){
		try {
			mMediaPlayer.reset();
			mMediaPlayer.setDataSource(mediaUrl);
			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mMediaPlayer.prepare();
			mMediaPlayer.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}
}
