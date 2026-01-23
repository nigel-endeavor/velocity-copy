package com.vertek.corporate.qto.common.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** @author dkelly created Nov 17, 2009 */
public class NoSuchViewException extends AdapterException {

    /** Serial version. */
    private static final long serialVersionUID = 3671293411190604717L;

    /** Logging. */
    private static final Logger OUT = LoggerFactory.getLogger(NoSuchColumnException.class);

    /**
     * Constructor takes a message param.
     * @param message the message.
     */
    public NoSuchViewException(final String message) {
        super(message);
        OUT.debug(message);
    }
}
