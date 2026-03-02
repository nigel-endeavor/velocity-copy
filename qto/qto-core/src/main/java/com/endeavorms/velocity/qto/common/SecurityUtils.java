package com.endeavorms.velocity.qto.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Encapsulates Security-related methods. Uses Spring Security.
 * @author <a href="rconnolly@vertek.com">rconnolly@vertek.com</a>.
 * @since 2.4.0 - 2/11/15
 */
public final class SecurityUtils {

    /** Logging Facade. */
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityUtils.class);

    /** The username to return when there is no authenticated Subject.*/
    public static final String ANON = "Anonymous";

    /** A user principal for scheduled tasks running outside of web context. */
    public static final String SCHEDULER = "scheduler@vertek.com";


    /** Hidden default Constructor to discourage direct instantiation.*/
    private SecurityUtils() {
    }


    /**
     * Get the current logged in user name.
     * @return the current logged in user name.
     */
    public static String getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() != null) {
            Object principal = auth.getPrincipal();
            if (principal instanceof Jws) {
                Claims claims = (Claims) ((Jws<?>) principal).getBody();
                String uniqueName = (String) claims.get("unique_name");
                return uniqueName != null ? uniqueName : ANON;
            }
            return principal.toString();
        }
        return ANON;
    }

    /**
     * Check if the current user has the given authority/permission.
     * Checks if the current user has the given permission.
     */
    public static boolean hasAuthority(String permission) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getAuthorities() == null) {
            return false;
        }
        Collection<String> authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        return authorities.contains(permission) || authorities.contains("*");
    }
}
