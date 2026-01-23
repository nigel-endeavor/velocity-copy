package com.vertek.corporate.qto.common;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.Serializable;

/**
 * Very simply models an HTTP Error.
 * @author rconnolly
 * @since 1.0.0
 */
@JacksonXmlRootElement
public class HttpError implements Serializable {

    /** The HTTP Status Code.*/
    private Integer statusCode;

    /** A message describing the Error.*/
    private String message;

    /** The message to show developers which may aid in troubleshooting the error.*/
    private String developerMessage;


    /**
     * Sole Constructor.
     * @param statusCode the HTTP Status code.
     * @param message the message describing the error.
     * @param developerMessage the message describing the error in detail for developer troubleshooting.
     */
    public HttpError(final Integer statusCode, final String message, final String developerMessage) {
        this.statusCode = statusCode;
        this.message = message;
        this.developerMessage = developerMessage;
    }



    public Integer getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }
}
