package com.endeavorms.velocity.qto.common;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;

/**
 * From vertek-commons.
 * This class resolves issues with AJAX caching in IE.  IE will aggressively cache AJAX
 * requests unless caching is no-cache or no-store.  There are a number of approaches to
 * resolving this issue.  This seamed the simplest to get going in a pinch.  Alternatives
 * include updating the server to send the correct response header, using CacheControl on
 * the RequestBuilder, updating the client side to pass a unique time stamp on the query
 * string, etc.
 *
 * @since 1.0
 * @author dkelly
 * @see Response
 */
public final class NoCacheResponse {

    /**
     * Hidden Constructor to prevent instantiation.
     * @throws IllegalAccessException should the Constructor be invoked.
     */
    private NoCacheResponse() throws IllegalAccessException {
        throw new IllegalAccessException(this.getClass().getSimpleName() + " is not meant to be instantiated directly");
    }

    /**
     * Convenience for setting no-cache header on the Response.
     * @param object the Response entity.
     * @return a No Cache Response.
     */
    public static ResponseBuilder ok(final Object object) {
        return Response.ok(object).header("Cache-Control", "no-cache");
    }
}
