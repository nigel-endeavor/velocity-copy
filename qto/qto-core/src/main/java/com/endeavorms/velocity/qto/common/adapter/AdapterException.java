package com.endeavorms.velocity.qto.common.adapter;

/**
 * @author dkelly
 * @since Nov 30, 2009
 */
public class AdapterException extends Exception {

    /** Serial version UID. */
    private static final long serialVersionUID = -8070077640215669688L;

    /**
     * Constructor taking a cause as a parameter.
     * @param t the cause.
     */
    public AdapterException(final Throwable t) {
        super(t);
    }

    /**
     * Constructor taking a message parameter.
     * @param message the message.
     */
    public AdapterException(final String message) {

        super(message);
    }
}
