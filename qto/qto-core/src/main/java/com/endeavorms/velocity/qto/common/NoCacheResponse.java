package com.endeavorms.velocity.qto.common;

import org.springframework.http.ResponseEntity;

/**
 * Resolves issues with AJAX caching in IE. IE will aggressively cache AJAX
 * requests unless caching is no-cache or no-store.
 *
 * @since 1.0
 * @author dkelly
 */
public final class NoCacheResponse {

    private NoCacheResponse() {
    }

    /**
     * Returns ResponseEntity with no-cache header.
     */
    public static <T> ResponseEntity<T> ok(final T body) {
        return ResponseEntity.ok()
                .header("Cache-Control", "no-cache")
                .body(body);
    }
}
