package com.chailijun.mtime.api;

public class ApiException extends RuntimeException{

    public static final int FAILED = 0;
    public static final int NO_DATA = 1;

    private static String message;

    public ApiException(int resultCode) {
        this(getApiExceptionMessage(resultCode));
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    @Override
    public String getMessage() {
        return message;
    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，再显示给用户
     * @param code
     * @return
     */
    private static String getApiExceptionMessage(int code){
        switch (code) {
            case FAILED:
                message = "数据不存在";
                break;

            default:
                message = "未知错误";
        }
        return message;
    }

}
