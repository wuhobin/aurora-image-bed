package com.wuhobin.common.api;

/**
 * 封装API的错误码
 * @author 刘博
 */
public interface IErrorCode {

    /**
     * 获取返回码
     */
    long getCode();

    /**
     * 获取返回信息
     * @return
     */
    String getMessage();
}
