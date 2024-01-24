package com.wuhobin.domain.constant;

/**
 * @author wuhongbin
 * @version 1.0.0
 * @date 2024/1/24 12:08
 * @description
 */
public enum UserActiveEnums {

    NOT_ACTIVE(0, "未激活"),

    ACTIVE(1, "已激活"),

    ;


    private int type;

    private String message;

    UserActiveEnums(int type, String message) {
        this.type = type;
        this.message = message;
    }

    public int getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
