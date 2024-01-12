package com.wuhobin.common.exception;

public class SM4EncryptException extends Exception{

    private static final long serialVersionUID = 8583550718712492947L;

    public SM4EncryptException() {
        super();
    }

    public SM4EncryptException(String message) {
        super(message);
    }

    public SM4EncryptException(String message, Throwable cause) {
        super(message, cause);
    }

    public SM4EncryptException(Throwable cause) {
        super(cause);
    }
}
