package com.vertek.corporate.qto.common;

import com.vertek.corporate.qto.attachment.FileAttachment;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import com.vertek.corporate.qto.attachment.FileAttachmentContent;
import org.apache.commons.io.IOUtils;

import javax.inject.Inject;

public abstract class AbstractFileAttachmentManager<T extends FileAttachment> extends AbstractManager<T, Long> {
    @Inject
    private TenantSubjectManager tenantSubjectManager;

    public AbstractFileAttachmentManager() {
    }

    protected abstract AbstractJpaDao<T, Long> getDao();

    public void updateDescription(long fileAttachmentId, String description) {
        T file = this.getDao().retrieve(fileAttachmentId);
        file.setDescription(description);
        this.getDao().edit(file);
    }

    public FileAttachment uploadFile(String fileName, String fileMimeType, long fileSize, String fileDescription, Date fileModifiedDate, String uploadedByUserName, InputStream inputStream, FileAttachmentFactory<T> fileAttachmentFactory) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        IOUtils.copy(inputStream, outputStream);
        byte[] data = outputStream.toByteArray();
        T fileAttachment = fileAttachmentFactory.createInstance();
        fileAttachment.setTenantId(fileAttachment.getTenantId() != null ? fileAttachment.getTenantId() : this.tenantSubjectManager.getCurrentTenant().getId());
        fileAttachment.setName(fileName);
        fileAttachment.setMimeType(fileMimeType);
        fileAttachment.setSize(fileSize);
        fileAttachment.setUploadDate(new Date());
        fileAttachment.setFileModifiedDate(fileModifiedDate);
        fileAttachment.setUploadedByUserName(uploadedByUserName);
        fileAttachment.setDescription(fileDescription);
        FileAttachmentContent content = new FileAttachmentContent(data);
        fileAttachment.setContent(content);
        this.create(fileAttachment);
        return fileAttachment;
    }
}
