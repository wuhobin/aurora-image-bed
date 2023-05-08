package com.wuhobin.common.exception;

public class SM2Exception extends Exception {
    private static final long serialVersionUID = 8150152925131907037L;

    public SM2Exception() {
        super();
    }

    public SM2Exception(String message) {
        super(message);
    }

    public SM2Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public SM2Exception(Throwable cause) {
        super(cause);
    }
}
