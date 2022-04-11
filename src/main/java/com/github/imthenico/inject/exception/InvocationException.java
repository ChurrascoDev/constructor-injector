package com.github.imthenico.inject.exception;

public class InvocationException extends Exception {

    public InvocationException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvocationException(Throwable cause) {
        super(cause);
    }
}