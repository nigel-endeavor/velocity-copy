package com.vertek.corporate.qto.authentication;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * QTO Shiro Filter - passthrough mode for local development.
 * Authentication is disabled; all requests are passed through.
 * TODO: Restore Shiro authentication for production by upgrading Shiro to 2.x.
 */
public class QTOShiroFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // no-op for dev
    }

    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain chain) throws ServletException, IOException {
        // Always allow OPTIONS (CORS preflight)
        if ("OPTIONS".equalsIgnoreCase(((HttpServletRequest) servletRequest).getMethod())) {
            servletResponse.getWriter().flush();
            return;
        }
        chain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        // no-op
    }
}
