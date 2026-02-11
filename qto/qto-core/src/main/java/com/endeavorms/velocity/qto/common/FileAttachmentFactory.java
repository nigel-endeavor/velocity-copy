package com.endeavorms.velocity.qto.common;

import com.endeavorms.velocity.qto.attachment.FileAttachment;

/**
 * Used to create instance of TenantOwnedFileAttachment.
 * @param <T> TenantOwnedFileAttachment or sub-class of TenantOwnedFileAttachment.
 */
public interface FileAttachmentFactory<T extends FileAttachment> {
    /**
     * Creates a TenantOwnedFileAttachment instance and performs any implementation specific initialization.
     * @return An instance of a class that extends FileAttachment.
     */
    T createInstance();
}
