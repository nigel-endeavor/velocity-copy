package com.endeavorms.velocity.qto.commons;

/**
 * Provides different marker classes to act as views for JSON Serialization.
 *
 * @author rconnolly
 * @since 1.0
 */
public class JsonViews {

    /** For public viewing.*/
    public static class Public { }

    /** For protected viewing.*/
    public static class Internal extends Public { }
}
