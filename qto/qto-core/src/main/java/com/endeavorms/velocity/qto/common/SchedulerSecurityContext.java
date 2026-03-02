package com.endeavorms.velocity.qto.common;

import com.endeavorms.velocity.qto.authentication.Permissions;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;

/**
 * Utility to run code with scheduler identity for JMS listeners and background jobs.
 * Sets Spring Security context for scheduler jobs.
 */
public final class SchedulerSecurityContext {

    private SchedulerSecurityContext() {
    }

    private static final UsernamePasswordAuthenticationToken SCHEDULER_AUTH =
            new UsernamePasswordAuthenticationToken(
                    SecurityUtils.SCHEDULER,
                    null,
                    Collections.singletonList(new SimpleGrantedAuthority(Permissions.ADMIN)));

    /**
     * Runs the given runnable with scheduler authentication.
     * Restores the previous security context in a finally block.
     */
    public static void runAsScheduler(Runnable runnable) {
        SecurityContext previous = SecurityContextHolder.getContext();
        try {
            SecurityContextHolder.getContext().setAuthentication(SCHEDULER_AUTH);
            runnable.run();
        } finally {
            SecurityContextHolder.getContext().setAuthentication(previous.getAuthentication());
        }
    }
}
