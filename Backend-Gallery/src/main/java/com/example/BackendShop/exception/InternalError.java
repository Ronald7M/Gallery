package com.example.BackendShop.exception;

public class InternalError extends Exception {
    private  int code=450;
    public InternalError(int code ,String message ) {super(message); this.code=code;}
    public int getCode() {
        return code;
    }

}
