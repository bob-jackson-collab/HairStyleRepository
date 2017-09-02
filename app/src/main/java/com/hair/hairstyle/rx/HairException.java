package com.hair.hairstyle.rx;

/**
 * Created by yunshan on 17/7/28.
 */

public class HairException extends Throwable{

    private int code;

    private String message;

    public HairException(String message) {
        super(message);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
