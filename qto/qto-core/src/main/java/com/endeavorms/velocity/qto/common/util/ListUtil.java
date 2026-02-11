package com.endeavorms.velocity.qto.common.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class ListUtil {
    private ListUtil() {
    }

    public static <T> Map<Integer, T> toMap(List<T> var0) {
        HashMap var1 = new HashMap();
        int var2 = 0;

        for(Iterator var3 = var0.iterator(); var3.hasNext(); ++var2) {
            Object var4 = var3.next();
            var1.put(var2, var4);
        }

        return var1;
    }

    public static <T> Map<T, Integer> toReverseMap(List<T> var0) {
        HashMap var1 = new HashMap();
        int var2 = 0;

        for(Iterator var3 = var0.iterator(); var3.hasNext(); ++var2) {
            Object var4 = var3.next();
            var1.put(var4, var2);
        }

        return var1;
    }

    public static <T> Map<T, T> toMap(T[] var0) {
        HashMap var1 = new HashMap();

        for(int var2 = 0; var2 < var0.length; var2 += 2) {
            var1.put(var0[var2], var0[var2 + 1]);
        }

        return var1;
    }
}
