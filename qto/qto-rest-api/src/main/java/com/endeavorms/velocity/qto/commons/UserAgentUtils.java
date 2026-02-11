package com.endeavorms.velocity.qto.commons;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility Class for working with User Agent Strings.
 * @author <a href="mailto:rconnolly@vertek.com">rconnolly</a>
 * @since 1.2.0
 */
public final class UserAgentUtils {


    /** Name of the User-Agent request Header.*/
    public static final String USER_AGENT = "User-Agent";

    /** Logging Facade.*/
    private static final Logger LOGGER = LoggerFactory.getLogger(UserAgentUtils.class);


    /**
     * Hidden to help prevent direct instantiation.
     */
    private UserAgentUtils() {
        throw new UnsupportedOperationException("Utility classes should not be directly instantiated.");
    }
}
