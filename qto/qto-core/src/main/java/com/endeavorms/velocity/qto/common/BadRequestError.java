package com.endeavorms.velocity.qto.common;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * An HttpError containing validation errors.
 * @author rconnolly
 * @since 1.2.0
 */
public class BadRequestError extends HttpError {

    /** HTTP Status Code for a Bad Request.*/
    private static final int STATUS_CODE = 400;


    /** List of ValidationErrors.*/
    /** The Collection of resources. */
    @JacksonXmlElementWrapper(localName = "errors")
    @JacksonXmlProperty(localName = "error")
    private List<ValidationError> errors = new ArrayList<ValidationError>();


    /**
     * Constructor that takes a list of ValidationErrors.
     * @param errors the list of ValidationErrors.
     */
    public BadRequestError(final List<ValidationError> errors) {
        super(STATUS_CODE, "Bad Request",
                "The request failed validation. Please consult the list of validation errors for details");
        this.errors = errors;
    }


    public List<ValidationError> getErrors() {
        return errors;
    }
}
