package com.wuhobin.common.api;

/**
 * 枚举了一些常用API操作码
 *
 * @author 刘博
 */
public enum ResultCode implements IErrorCode {

    /**
     * 返回信息
     */
    SUCCESS(200, "操作成功"),
    FAILED(-500, "系统异常"),
    VALIDATE_FAILED(400, "参数检验失败"),
    UNAUTHORIZED(401, "未登录"),

    FORBIDDEN(403, "无权限"),


    EMAIL_EXIST(-1001, "该邮件地址已注册"),

    USER_NAME_EXIST(-1002, "该用户名已被使用"),

    ;
    private long code;
    private String message;

    ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
