package com.vertek.corporate.qto.common.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dkelly
 * @since Nov 12, 2009
 */
public class NoSuchColumnException extends AdapterException {

    /** Logging. */
    private static final Logger OUT = LoggerFactory.getLogger(NoSuchColumnException.class);

    /** Serial version. */
    private static final long serialVersionUID = -8922096231667050281L;

    /**
     * Constructor takes a message param.
     * @param message the message.
     */
    public NoSuchColumnException(final String message) {
        super(message);
        OUT.debug(message);
    }
}
