package com.huanhong.mashineshop.net;

public class ServerException extends RuntimeException {
    public String message;

    public int code;

    public ServerException(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
