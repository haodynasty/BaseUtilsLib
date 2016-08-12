package com.plusub.lib.example.api;

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
 * date     : 2016/4/28 10:14
 * last modify author :
 * version : 1.0
 * description:服务器统一返回结果实体类
 */
public class HttpResult<T> {
    //结果返回的结果码
    private int status;
    //结果返回的错误信息
    private String msg;
    //返回结果的实体对象（可能是单个实体，数组等）
    private T entity;

    public boolean isResultOk() {
        return status-200 == 0?true:false;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "isResultOk=" + isResultOk() +
                ", status=" + status +
                ", msg='" + msg + '\'' +
                ", entity=" + entity +
                '}';
    }
}
