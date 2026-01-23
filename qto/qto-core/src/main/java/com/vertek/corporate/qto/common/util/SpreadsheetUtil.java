package com.vertek.corporate.qto.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public final class SpreadsheetUtil {
    private static final Logger OUT = LoggerFactory.getLogger(SpreadsheetUtil.class);

    private SpreadsheetUtil() {
    }

    public static short convertLetterToIndex(String var0) {
        short var2 = 0;
        byte[] var1 = var0.trim().toLowerCase().getBytes();
        if (var1.length > 2) {
            var2 = -1;
        }

        if (var1.length == 2) {
            var2 = (short)((var1[0] - 96) * 26);
            var2 = (short)(var2 + (var1[1] - 97));
        } else {
            var2 = (short)(var2 + (var1[0] - 97));
        }

        return var2;
    }

    public static String convertIndexToLetters(int var0) {
        int var1 = var0 + 1;
        if (var1 >= 1 && var1 <= 17575) {
            HashMap var2 = new HashMap();
            var2.put(0, 1);
            var2.put(1, 26);
            var2.put(2, 676);
            int[] var3 = new int[var2.size()];
            int var4 = 0;

            for(int var5 = var2.size() - 1; var5 >= 0; --var5) {
                Integer var6 = new Integer((var1 - var4) / (Integer)var2.get(var5));
                if (var6 != 0) {
                    var4 += var6 * (Integer)var2.get(var5);
                } else {
                    try {
                        if (var3[var5 + 1] > 0) {
                            --var3[var5 + 1];
                            var6 = 26;
                        }
                    } catch (IndexOutOfBoundsException var8) {
                        OUT.error("Error converting index to letters: " + var8.toString());
                    }
                }

                var3[var5] = var6;
            }

            StringBuilder var9 = new StringBuilder();
            String var10 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

            for(int var7 = var2.size() - 1; var7 >= 0; --var7) {
                if (var3[var7] != 0) {
                    var9.append(var10.charAt(var3[var7] - 1));
                }
            }

            return var9.toString();
        } else {
            throw new NumberFormatException("invalid column index " + var1);
        }
    }
}
