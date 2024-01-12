package com.wuhobin.common.exception;

public class SMSignException extends Exception {

    private static final long serialVersionUID = -6256587486451163601L;

    public SMSignException() {
        super();
    }

    public SMSignException(String message) {
        super(message);
    }

    public SMSignException(String message, Throwable cause) {
        super(message, cause);
    }

    public SMSignException(Throwable cause) {
        super(cause);
    }
}
