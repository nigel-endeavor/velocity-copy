package com.vertek.corporate.qto.authentication;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.PassThruAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Demo filter that auto-authenticates every request with a demo user.
 * This ensures @RequiresPermissions annotations work properly (they require
 * an authenticated subject — anonymous subjects fail permission checks).
 * FOR DEVELOPMENT/DEMO USE ONLY.
 */
public class DemoAutoAuthFilter extends PassThruAuthenticationFilter {

    @Override
    protected boolean isAccessAllowed(final ServletRequest request, final ServletResponse response, final Object mappedValue) {
        Subject subject = getSubject(request, response);
        if (!subject.isAuthenticated()) {
            try {
                subject.login(new UsernamePasswordToken("demo-user@demo.com", "demo-password", false));
            } catch (Exception e) {
                // Login failed - should not happen with DemoAllAccessRealm, but log and continue
                System.err.println("DemoAutoAuthFilter: auto-login failed: " + e.getMessage());
            }
        }
        return true;
    }

    @Override
    protected boolean onAccessDenied(final ServletRequest request, final ServletResponse response) throws Exception {
        // Never deny - always allow
        return true;
    }
}
