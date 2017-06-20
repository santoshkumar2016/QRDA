
package com.nalashaa.qrdamu2.exception;

public class QrdaException extends RuntimeException {

    private static final long serialVersionUID = -7511844622376773821L;
    String errorMsg;
    String errorCode;

    public QrdaException() {
        super();

    }

    public QrdaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);

    }

    public QrdaException(String message, Throwable cause) {
        super(message, cause);

    }

    public QrdaException(String message) {
        super(message);

    }

    public QrdaException(Throwable cause) {
        super(cause);

    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public QrdaException(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

}
