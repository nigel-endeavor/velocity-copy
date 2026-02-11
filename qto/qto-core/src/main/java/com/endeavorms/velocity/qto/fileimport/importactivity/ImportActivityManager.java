package com.endeavorms.velocity.qto.fileimport.importactivity;

import com.endeavorms.velocity.qto.attachment.FileAttachment;
import com.endeavorms.velocity.qto.attachment.FileAttachmentContent;
import com.endeavorms.velocity.qto.attachment.FileAttachmentManager;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.common.SecurityUtils;
import com.endeavorms.velocity.qto.common.StandardManager;
import com.endeavorms.velocity.qto.common.TenantSubjectManager;
import com.endeavorms.velocity.qto.fileimport.FileImportQueueHandler;
import com.endeavorms.velocity.qto.subject.Subject;
import com.endeavorms.velocity.qto.subject.SubjectManager;
import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import java.util.Date;

/**
 * @author rcasey
 * @since 9/1/2023
 */
@Component
public class ImportActivityManager extends StandardManager<ImportActivity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImportActivityManager.class);

    @Inject
    private ImportActivityJpaDao dao;

    @Inject
    private FileAttachmentManager fileAttachmentManager;

    @Inject
    private TenantSubjectManager tenantSubjectManager;

    @Inject
    private SubjectManager subjectManager;

    @Override
    protected ImportActivityJpaDao getDao() {
        return dao;
    }

    public ImportActivity create(final String type, final FileItem fileItem) {
        try {
            String username = SecurityUtils.getLoggedInUser();
            Subject subject = subjectManager.findByUsername(username);
            Long tenantId = tenantSubjectManager.getCurrentTenant().getId();
            LOGGER.debug("Creating ImportActivity of type {} for user {}", type, username);

            FileAttachment attachment = new FileAttachment();
            attachment.setName(fileItem.getName());
            attachment.setDescription("Import file - " + type);
            attachment.setMimeType(fileItem.getContentType());
            attachment.setSize(fileItem.getSize());
            attachment.setTenantId(tenantId);
            attachment.setContent(new FileAttachmentContent(fileItem.getInputStream().readAllBytes()));
            attachment.setUploadedByUserName(subject.getDisplayName());
            attachment.setUploadDate(new Date());
            FileAttachment createdFileAttachment = fileAttachmentManager.create(attachment);

            ImportActivity importActivity = new ImportActivity();
            importActivity.setFileAttachment(createdFileAttachment);
            importActivity.setTenantId(tenantId);
            importActivity.setSubjectId(subject.getId());
            importActivity.setImportType(type);
            return create(importActivity);
        } catch (Exception e) {
            LOGGER.error("Error creating ImportActivity", e);
            throw new RuntimeException(e);
        }
    }

    public PaginatedResult<ImportActivity> findBySearchCriteria(final ImportActivitySearchCriteria criteria) {
        return dao.findBySearchCriteria(criteria);
    }
}
