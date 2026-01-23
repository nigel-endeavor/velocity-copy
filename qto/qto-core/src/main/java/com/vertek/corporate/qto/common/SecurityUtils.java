package com.vertek.corporate.qto.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * From platform.
 * Encapsulates some commonly needed Security-related methods.
 * @author <a href="rconnolly@vertek.com">rconnolly@vertek.com</a>.
 * @since 2.4.0 - 2/11/15
 */
public final class SecurityUtils {

    /** Logging Facade. */
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityUtils.class);

    /** The username to return when there is no authenticated Subject.*/
    public static final String ANON = "Anonymous";

    /** A user principal for scheduled tasks running outside of Shiro's web context. */
    public static final String SCHEDULER = "scheduler@vertek.com";


    /** Hidden default Constructor to discourage direct instantiation.*/
    private SecurityUtils() {
    }


    /**
     * Get the current logged in user name.
     * @return the current logged in user name.
     */
    public static String getLoggedInUser() {
        String loggedInUser = null;
        try {
            if (org.apache.shiro.SecurityUtils.getSubject().getPrincipal() instanceof Jws) {
                Claims claims = (Claims) ((Jws) org.apache.shiro.SecurityUtils.getSubject().getPrincipal()).getBody();
                loggedInUser = (String) claims.get("unique_name");
            } else {
                LOGGER.trace("Do not have a principal, casting to String.");
                loggedInUser = (String) org.apache.shiro.SecurityUtils.getSubject().getPrincipal();
            }
        } catch (UnavailableSecurityManagerException ex) {
            LOGGER.warn(ex.getMessage());
        }

        return (loggedInUser == null)
                ? ANON
                : loggedInUser;
    }


    /**
     * Authenticates a Subject.
     * @param username the username.
     * @param credential the credential.
     */
    public static void authenticate(final String username, final String credential) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, credential);
        org.apache.shiro.SecurityUtils.getSecurityManager().authenticate(token);
    }

}
