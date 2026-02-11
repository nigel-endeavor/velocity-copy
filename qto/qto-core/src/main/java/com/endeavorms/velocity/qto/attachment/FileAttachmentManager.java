package com.endeavorms.velocity.qto.attachment;

import com.endeavorms.velocity.qto.common.AbstractFileAttachmentManager;

import jakarta.inject.Inject;

/**
 * @author rcasey
 * @since 9/1/2023
 */
public class FileAttachmentManager extends AbstractFileAttachmentManager<FileAttachment> {

    @Inject
    private FileAttachmentJpaDao dao;

    @Override
    protected FileAttachmentJpaDao getDao() {
        return dao;
    }
}
