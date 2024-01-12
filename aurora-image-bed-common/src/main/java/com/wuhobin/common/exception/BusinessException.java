package com.wuhobin.common.exception;


import com.wuhobin.common.api.ResultCode;

/**
 * Created by @authorChenLiBang on 2022/7/18
 */
public class BusinessException extends RuntimeException {

    private Long code;

    public BusinessException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }
}
