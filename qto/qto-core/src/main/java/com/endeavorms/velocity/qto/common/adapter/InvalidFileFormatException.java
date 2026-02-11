package com.endeavorms.velocity.qto.common.adapter;

/**
 * @author dkelly
 * @since Mar 21, 2011
 */
public class InvalidFileFormatException extends AdapterException {

    /** Serial version. */
    private static final long serialVersionUID = 7654339305309737491L;

    /**
     * Constructor takes a Throwable param.
     * @param t the cause.
     */
    public InvalidFileFormatException(final Throwable t) {
        super(t);
    }

    /**
     * Constructor takes message param.
     * @param message the message.
     */
    public InvalidFileFormatException(final String message) {
        super(message);
    }
}