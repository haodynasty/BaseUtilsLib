package com.plusub.lib.example.api;

import com.plusub.lib.util.StringUtils;

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
 * date     : 2016/4/27 11:32
 * last modify author :
 * version : 1.0
 * description:处理异常
 */
public class ApiException extends RuntimeException{
    public static final int USER_NOT_EXIST = 100;
    public static final int WRONG_PASSWORD = 101;
    public static final int SESSION_OUT_TIME = 500;
    private int errorCode;

    public ApiException(int resultCode, String message) {
        this(getApiExceptionMessage(resultCode, message));
        this.errorCode = resultCode;
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    public int getErrorCode(){
        return errorCode;
    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户,直接调用e.getMessage()即可
     * @param code
     * @return
     */
    private static String getApiExceptionMessage(int code, String msg){
        String message = "";
        switch (code) {
            case USER_NOT_EXIST:
                message = "该用户不存在";
                break;
            case WRONG_PASSWORD:
                message = "密码错误";
                break;
            case SESSION_OUT_TIME:
                message = "Session超时，请重新登录";
                break;
            default:
                if (!StringUtils.isEmpty(msg)){
                    message = msg;
                }else{
                    message = "未知错误";
                }

        }
        return message;
    }
}
