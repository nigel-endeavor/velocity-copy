package com.vertek.corporate.qto.common;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.Serializable;

/**
 * A Simple Validation Error Object.
 * @author rconnolly
 * @since 1.1
 */
@JacksonXmlRootElement
public class ValidationError implements Serializable {

    /** The property path. */
    private String propertyPath;

    /** The error message. */
    private String message;


    /** Default Constructor. */
    public ValidationError() {
    }


    /**
     * Constructor.
     * @param propertyPath property path.
     * @param message error message.
     */
    public ValidationError(final String propertyPath, final String message) {
        this.propertyPath = propertyPath;
        this.message = message;
    }


    public String getPropertyPath() {
        return propertyPath;
    }
    public void setPropertyPath(final String propertyPath) {
        this.propertyPath = propertyPath;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(final String message) {
        this.message = message;
    }



}

