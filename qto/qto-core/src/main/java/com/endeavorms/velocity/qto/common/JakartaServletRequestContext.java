package com.endeavorms.velocity.qto.common;

import org.apache.commons.fileupload.RequestContext;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

/**
 * RequestContext adapter for commons-fileupload when using jakarta.servlet.
 */
public class JakartaServletRequestContext implements RequestContext {

    private final HttpServletRequest request;

    public JakartaServletRequestContext(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public String getCharacterEncoding() {
        return request.getCharacterEncoding();
    }

    @Override
    public String getContentType() {
        return request.getContentType();
    }

    @Override
    public int getContentLength() {
        return request.getContentLength();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return request.getInputStream();
    }
}
