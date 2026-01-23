package com.vertek.corporate.qto.fileimport;

/**
 * @author rcasey
 * @since 9/1/2023
 */
public enum ImportActivityStatus {

    /** The import is pending processing. */
    PENDING_PROCESSING("Pending processing"),

    /** The import is processing. */
    PROCESSING("Processing"),

    /** The import processed with no exceptions. */
    PROCESSED_SUCCESSFULLY("Processed successfully"),

    /** The import processed with exceptions. */
    PROCESSED_WITH_ERRORS("Processed with errors"),

    /** There was an upload error of some sort. */
    UPLOAD_ERROR("Upload error"),

    /** There was a system error of some sort. */
    SYSTEM_ERROR("System error");

    /** String representation, for humans, of the enum value. */
    private String displayName;

    /**
     * Constructor.
     * @param displayName the display, or human readable, representation.
     */
    ImportActivityStatus(final String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
