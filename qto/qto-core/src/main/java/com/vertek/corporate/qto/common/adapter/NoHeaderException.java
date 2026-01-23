package com.vertek.corporate.qto.common.adapter;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is thrown when someone doesn't specify that there's a header but a header is requested using methods like
 * getHeader() or getColumnName() on the BatchAdapter interface.  Programmer error, so unchecked.
 * @author dkelly
 * @since Nov 11, 2009
 */
public class NoHeaderException extends RuntimeException {

    /** Logging. */
    private static final Logger OUT = LoggerFactory.getLogger(NoHeaderException.class);

    /** Serial version. */
    private static final long serialVersionUID = -2859869137144790515L;

    /**
     * Constructor takes a message param.
     * @param message the message.
     */
    public NoHeaderException(final String message) {
        super(message);
        OUT.debug(message);
    }
}