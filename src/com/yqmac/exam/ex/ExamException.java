package com.yqmac.exam.ex;

/**
 * Created by yqmac on 2016/4/18 0018.
 */
public class ExamException extends  RuntimeException {
    public ExamException() {
    }

    public ExamException(String message) {
        super(message);
    }

    public ExamException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExamException(Throwable cause) {
        super(cause);
    }

    public ExamException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
