package com.plusub.lib.task;

import android.os.Bundle;
import android.os.Handler;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * the message object using for task 
 * @author blakequ Blakequ@gmail.com
 *
 */
public class TaskMessage  implements Parcelable{

	 /**
     * User-defined message code so that the recipient can identify 
     * what this message is about. Each {@link Handler} has its own name-space
     * for message codes, so you do not need to worry about yours conflicting
     * with other handlers.
     */
    public int what;
    
    /**
     * User-defined the error code
     */
    public int errorCode;
    
    /**
     * arg1 and arg2 are lower-cost alternatives to using
     * {@link #setData(Bundle) setData()} if you only need to store a
     * few integer values.
     */
    public int arg1;
    
    public int arg2;
    
    /**
     * the object should send
     */
    public Object obj;
    
    public Bundle data;
    
    /**
     * task
     */
    public DataRefreshTask refreshTask;
    
    /**
     * server return status (if status not right, you can get error cause from {@link #message}})
     */
    public int status;
    
    /**
     * server return message
     */
    public String message;
	
	public Bundle getData() {
		return data;
	}

	public void setData(Bundle data) {
		this.data = data;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeInt(what);
        dest.writeInt(arg1);
        dest.writeInt(arg2);
        if (obj != null) {
            try {
                Parcelable p = (Parcelable)obj;
                dest.writeInt(1);
                dest.writeParcelable(p, flags);
            } catch (ClassCastException e) {
                throw new RuntimeException(
                    "Can't marshal non-Parcelable objects across processes.");
            }
        } else {
            dest.writeInt(0);
        }
        dest.writeBundle(data);
        dest.writeInt(status);
        dest.writeString(message);
	}
	
	private final void readFromParcel(Parcel source) {
        what = source.readInt();
        errorCode = source.readInt();
        arg1 = source.readInt();
        arg2 = source.readInt();
        if (source.readInt() != 0) {
            obj = source.readParcelable(getClass().getClassLoader());
        }
        data = source.readBundle();
        status = source.readInt();
        message = source.readString();
    }

}
