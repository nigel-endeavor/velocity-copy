package com.endeavorms.velocity.qto.common;

/**
 * From vertek-commons.
 * Custom Media Type constants.
 * @author rconnolly
 * @since 1.1
 */
public final class MediaTypes {

    private MediaTypes() {
    }

    /** Media Type for Excel format. */
    public static final String MS_EXCEL = "application/vnd.ms-excel";

    /** Media type for Excel xlsx format. */
    public static final String MS_EXCEL_2007 = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    /** Media Type for Excel format. */
    public static final String APPLICATION_PDF = "application/pdf";

    /** Media Type for JSON format required by 2.4 AutoBean API. */
    public static final String APPLICATION_GWT = "application/gwt";

    /** Media Type for JSON format required by 2.4 AutoBean API that serializes using JsonViews.Internal. */
    public static final String APPLICATION_GWT_EXTENDED = "application/vnd.com.vertek.extended+gwt";
}
