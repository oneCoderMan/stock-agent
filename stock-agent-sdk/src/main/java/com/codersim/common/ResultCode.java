package com.codersim.common;

/**
 * @Author： yijun
 * @DATE: 2025/8/23 15:32
 * @Description
 *
 */
public enum ResultCode {
    SUCCESS(0, "success"),
    FAILED(-1, "failed");

    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
