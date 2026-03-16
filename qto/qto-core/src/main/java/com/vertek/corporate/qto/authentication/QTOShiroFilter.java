package com.vertek.corporate.qto.authentication;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.servlet.ShiroFilter;

public class QTOShiroFilter extends ShiroFilter {
    public QTOShiroFilter() {
    }

    public void init() throws Exception {
        super.init();
        SecurityUtils.setSecurityManager(this.getSecurityManager());
    }

    @Override
    protected void doFilterInternal(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain chain) throws ServletException, IOException {
        //always allow OPTIONS requests (CORS preflight) - respond with CORS headers
        if ("OPTIONS".equalsIgnoreCase(((HttpServletRequest) servletRequest).getMethod())) {
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            httpResponse.setHeader("Access-Control-Allow-Origin", "*");
            httpResponse.setHeader("Access-Control-Allow-Headers",
                    "origin, content-type, accept, authorization, x-requested-with");
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.setHeader("Access-Control-Allow-Methods",
                    "GET, POST, PUT, DELETE, OPTIONS, HEAD");
            httpResponse.setHeader("Access-Control-Max-Age", "1209600");
            httpResponse.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        // Demo mode: auto-authenticate every request so @RequiresPermissions works
        // The DemoAllAccessRealm grants all permissions to any authenticated user
        Subject subject = SecurityUtils.getSubject();
        if (!subject.isAuthenticated()) {
            try {
                subject.login(new UsernamePasswordToken("demo-user@demo.com", "demo-password", false));
            } catch (Exception ignored) {
                // If auth fails (realm not configured for real auth), continue anyway
            }
        }

        super.doFilterInternal(servletRequest, servletResponse, chain);
    }
}
