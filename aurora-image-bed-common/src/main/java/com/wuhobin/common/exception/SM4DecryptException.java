package com.wuhobin.common.exception;

public class SM4DecryptException extends Exception{

    private static final long serialVersionUID = -436461200204606388L;

    public SM4DecryptException() {
        super();
    }

    public SM4DecryptException(String message) {
        super(message);
    }

    public SM4DecryptException(String message, Throwable cause) {
        super(message, cause);
    }

    public SM4DecryptException(Throwable cause) {
        super(cause);
    }
}
