package com.vertek.corporate.qto.common.adapter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dkelly
 * @since Feb 9, 2011
 */
public class ColumnCountException extends RuntimeException {

    /** Serial version. */
    private static final long serialVersionUID = 1L;

    /** Logger. */
    private static final Logger OUT = LoggerFactory.getLogger(ColumnCountException.class);

    /**
     * Constructor takes a message parameter.
     * @param message a message.
     */
    public ColumnCountException(final String message) {
        super(message);
        OUT.debug(message);
    }

    /** Default Constructor. */
    public ColumnCountException() {
        super("column count doesn't match for this row");
        OUT.debug(super.getMessage());
    }
}
