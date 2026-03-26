package com.endeavorms.velocity.qto.config;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Development-only filter that injects a fully-authorized user into the
 * SecurityContext so all @PreAuthorize checks pass and SecurityUtils.getLoggedInUser()
 * returns a valid username instead of "Anonymous".
 */
@Component
public class DevAuthFilter extends OncePerRequestFilter {

    private static final String DEV_USER = "devuser@endeavorms.com";

    private static final List<SimpleGrantedAuthority> ALL_AUTHORITIES = List.of(
            new SimpleGrantedAuthority("*"),
            new SimpleGrantedAuthority("admin"),
            new SimpleGrantedAuthority("order:read"),
            new SimpleGrantedAuthority("order:write"),
            new SimpleGrantedAuthority("order:create"),
            new SimpleGrantedAuthority("order:write-terminal"),
            new SimpleGrantedAuthority("inventory:read"),
            new SimpleGrantedAuthority("inventory:write"),
            new SimpleGrantedAuthority("invoice:read"),
            new SimpleGrantedAuthority("invoice:write"),
            new SimpleGrantedAuthority("dispute:read"),
            new SimpleGrantedAuthority("dispute:write"),
            new SimpleGrantedAuthority("file-import"),
            new SimpleGrantedAuthority("tenant-admin"),
            new SimpleGrantedAuthority("change-tenant")
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(DEV_USER, null, ALL_AUTHORITIES);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }
}
