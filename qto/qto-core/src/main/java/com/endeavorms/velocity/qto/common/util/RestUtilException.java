package com.endeavorms.velocity.qto.common.util;

/**
 * @author fcurran
 * @since 1.0.0
 */
public class RestUtilException extends Exception {

    /** An HTTP response code if this exception is based on an HTTP operation. */
    private int httpResponseCode;

    /**
     * Creates an exception with the specified message.
     * @param message the message.
     */
    public RestUtilException(final String message) {
        super(message);
    }

    /**
     * Creates an exception with the specified message.
     * @param message the message.
     * @param httpResponseCode An HTTP response code if this exception is based on an HTTP operation.
     */
    public RestUtilException(final String message, final int httpResponseCode) {
        super(message);
        this.httpResponseCode = httpResponseCode;
    }

    /**
     * Creates an exception with the specified Throwable.
     * @param throwable the Throwable.
     */
    public RestUtilException(final Throwable throwable) {
        super(throwable);
    }

    /**
     * Creates an exception with the specified message and Throwable.
     * @param message the message.
     * @param throwable the Throwable.
     */
    public RestUtilException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

    public int getHttpResponseCode() {
        return httpResponseCode;
    }

    public void setHttpResponseCode(final int httpResponseCode) {
        this.httpResponseCode = httpResponseCode;
    }
}