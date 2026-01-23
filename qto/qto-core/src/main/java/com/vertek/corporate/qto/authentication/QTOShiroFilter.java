package com.vertek.corporate.qto.authentication;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.servlet.ShiroFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class QTOShiroFilter extends ShiroFilter {
    public QTOShiroFilter() {
    }

    public void init() throws Exception {
        super.init();
        SecurityUtils.setSecurityManager(this.getSecurityManager());
    }

    @Override
    protected void doFilterInternal(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain chain) throws ServletException, IOException {
        //always allow OPTIONS requests
        if("OPTIONS".equalsIgnoreCase(((HttpServletRequest) servletRequest).getMethod())){
            return;
        }
        super.doFilterInternal(servletRequest, servletResponse, chain);
    }
}
