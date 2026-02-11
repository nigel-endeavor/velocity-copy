package com.endeavorms.velocity.qto.multiedit;

import com.endeavorms.velocity.qto.multiedit.request.MultiEditRequestDto;

import java.math.BigDecimal;
import java.util.Date;

public final class MultiEditUtility {
    private MultiEditUtility() {
    }

    /**
     * Utility method to check if the multiEditDto contains a key.
     * @param multiEditRequest multiEditDto instance.
     * @param key key to check for.
     * @return true if the key exists, false otherwise.
     */
    public static boolean containsKey(final MultiEditRequestDto multiEditRequest, final String key) {
        return multiEditRequest.getFieldValues().containsKey(key);
    }

    /**
     * Utility method to get a key from the multiEditDto as a String.
     * @param multiEditRequest multiEditDto instance.
     * @param key key to get.
     * @return the key as a String.
     */
    public static String getKeyString(final MultiEditRequestDto multiEditRequest, final String key) {
        return getKey(multiEditRequest, key).toString();
    }

    /**
     * Utility method to get a key from the multiEditDto as a Date.
     * @param multiEditRequest multiEditDto instance.
     * @param key key to get.
     * @return the key as a Date.
     */
    public static Date getKeyDate(final MultiEditRequestDto multiEditRequest, final String key) {
        Object value = getKey(multiEditRequest, key);
        if (value != null) {
            return new Date(Long.parseLong(getKey(multiEditRequest, key).toString()));
        } else {
            return null;
        }
    }

    /**
     * Utility method to get a key from the multiEditDto as a Long.
     * @param multiEditRequest multiEditDto instance.
     * @param key key to get.
     * @return the key as a Long.
     */
    public static Long getKeyLong(final MultiEditRequestDto multiEditRequest, final String key) {
        Object keyValue = getKey(multiEditRequest, key);
        if (keyValue != null) {
            try {
                return Long.parseLong(keyValue.toString());
            } catch (NumberFormatException e) {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * Utility method to get a key from the multiEditDto as a Long.
     * @param multiEditRequest multiEditDto instance.
     * @param key key to get.
     * @return the key as a Long.
     */
    public static BigDecimal getKeyBigDecimal(final MultiEditRequestDto multiEditRequest, final String key) {
        try {
            return new BigDecimal(getKeyString(multiEditRequest, key));
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }

    /**
     * Utility method to get a key from the multiEditDto.
     * @param multiEditRequest multiEditDto instance.
     * @param key key to get.
     * @return the key.
     */
    public static Object getKey(final MultiEditRequestDto multiEditRequest, final String key) {
        return multiEditRequest.getFieldValues().get(key);
    }
}
