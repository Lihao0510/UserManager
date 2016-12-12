package com.lihao.usermanager.rxjava;

/**
 * Created by lihao on 2016/12/12.
 */

public class HttpResult<T> {

    public int resultCode;
    public String resultMessage;
    public T t;
}
