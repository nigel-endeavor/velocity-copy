package com.endeavorms.velocity.qto.common;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

public final class PreconditionsUtil {
    static final String NULL_MESSAGE = "Argument must not be null.";
    static final String NOT_EMPTY_MESSAGE = "A non-empty argument is required.";

    private PreconditionsUtil() {
    }

    public static <T> T checkNotNull(T var0) {
        try {
            Preconditions.checkNotNull(var0);
            return var0;
        } catch (NullPointerException var2) {
            throw new NullPointerException("Argument must not be null.");
        }
    }

    public static <T> void checkArgument(T var0) {
        try {
            Preconditions.checkNotNull(var0);
        } catch (NullPointerException var2) {
            throw new IllegalArgumentException("Argument must not be null.");
        }
    }

    public static <T> void checkArgument(T var0, String var1) {
        try {
            Preconditions.checkNotNull(var0);
        } catch (NullPointerException var3) {
            throw new IllegalArgumentException(var1);
        }
    }

    public static void checkArgument(String var0) {
        try {
            Preconditions.checkArgument(var0 != null && !Strings.isNullOrEmpty(var0.trim()));
        } catch (IllegalArgumentException var2) {
            throw new IllegalArgumentException("A non-empty argument is required.");
        }
    }

    public static void checkArgument(String var0, String var1) {
        Preconditions.checkArgument(var0 != null && !Strings.isNullOrEmpty(var0.trim()), var1);
    }

    public static void checkArguments(String... var0) {
        boolean var1 = true;
        String[] var2 = var0;
        int var3 = var0.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String var5 = var2[var4];
            if (var5 != null && !Strings.isNullOrEmpty(var5.trim())) {
                var1 = false;
            }
        }

        if (var1) {
            throw new IllegalArgumentException("A non-empty argument is required.");
        }
    }
}