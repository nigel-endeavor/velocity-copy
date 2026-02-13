package com.endeavorms.velocity.qto.common;

import com.google.common.base.Strings;
import com.endeavorms.velocity.qto.attachment.FileAttachment;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.endeavorms.velocity.qto.config.ConfigPropertyManager;
import com.endeavorms.velocity.qto.config.ConfigurationProperty;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;

import static com.endeavorms.velocity.qto.config.ConfigKey.EXCLUDED_FILE_EXTENSIONS;
import static com.endeavorms.velocity.qto.config.ConfigKey.MAX_FILE_UPLOAD_SIZE_BYTES;

public abstract class AbstractFileAttachmentResource<T extends FileAttachment> extends AbstractResource<FileAttachment> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractFileAttachmentResource.class);

    @Autowired
    protected ConfigPropertyManager configPropertyManager;

    protected Long fileCountMax = 2L;

    public AbstractFileAttachmentResource() {
    }

    protected abstract AbstractFileAttachmentManager<T> getManager();

    @Override
    protected abstract String getResourcePath();

    @GetMapping("/userPermissions")
    public ResponseEntity<?> userPermissions() {
        FileAttachmentPermissions permissions = new FileAttachmentPermissions();
        permissions.setAttach(true);
        permissions.setView(true);
        permissions.setDelete(true);
        return NoCacheResponse.ok(permissions);
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> download(@RequestParam("id") Long id,
                                            @RequestParam(value = "inline", defaultValue = "false") boolean inline) {
        PreconditionsUtil.checkArgument(id, "File Attachment ID is required.");
        FileAttachment fileAttachment = (FileAttachment) this.getManager().retrieve(id);
        final byte[] data = fileAttachment.getContent().getData();
        String contentDisposition = inline ? "inline" : "attachment";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Disposition", contentDisposition + "; filename=\"" + fileAttachment.getName() + "\"");
        headers.setContentType(MediaType.parseMediaType(fileAttachment.getMimeType()));
        return ResponseEntity.ok()
                .headers(headers)
                .body(data);
    }

    @GetMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam("id") Long id) {
        PreconditionsUtil.checkArgument(id, "File Attachment ID is required.");
        this.getManager().remove(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/updateDescription")
    public ResponseEntity<?> updateDescription(@RequestBody FileAttachment fileAttachment) {
        PreconditionsUtil.checkArgument(fileAttachment, "File Attachment is required.");
        PreconditionsUtil.checkArgument(fileAttachment.getId(), "File Attachment ID is required.");
        this.getManager().updateDescription(fileAttachment.getId(), fileAttachment.getDescription());
        return ResponseEntity.noContent().build();
    }

    protected ResponseEntity<?> upload(HttpServletRequest servletRequest, FileAttachmentFactory<T> fileAttachmentFactory) {
        try {
            String userName = SecurityUtils.getLoggedInUser();
            List<FileAttachment> fileAttachments = new ArrayList<>();
            Long maxFileUploadSizeBytes = null;
            ConfigurationProperty maxFileUploadSizeBytesProperty = configPropertyManager.findByKey(MAX_FILE_UPLOAD_SIZE_BYTES.toString());
            if (maxFileUploadSizeBytesProperty != null) {
                maxFileUploadSizeBytes = Long.parseLong(maxFileUploadSizeBytesProperty.getValue());
            }

            List<String> excludedFileExtensions = new ArrayList<>();
            ConfigurationProperty excludedFileExtensionsProperty = configPropertyManager.findByKey(EXCLUDED_FILE_EXTENSIONS.toString());
            if (excludedFileExtensionsProperty != null) {
                excludedFileExtensions = Arrays.asList(excludedFileExtensionsProperty.getValue().split(","));
            }

            ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
            servletFileUpload.setFileCountMax(getFileCountMax());

            List<FileItem> fileItems = servletFileUpload.parseRequest(new JakartaServletRequestContext(servletRequest));
            SimpleDateFormat dateFormat = new SimpleDateFormat("E, d MMM yyyy k:m:s z");
            Dictionary<Integer, String> descriptions = new Hashtable<>();
            Dictionary<Integer, Date> modifiedDates = new Hashtable<>();
            Integer descriptionIndex = 0;
            Integer modifiedDateIndex = 0;
            Iterator<FileItem> var14 = fileItems.iterator();

            while (var14.hasNext()) {
                FileItem fileItem = var14.next();
                if (fileItem.isFormField()) {
                    if (fileItem.getFieldName().equals("description")) {
                        String description = fileItem.getString();
                        if (description == null) {
                            description = "";
                        }
                        descriptions.put(descriptionIndex, description);
                        descriptionIndex = descriptionIndex + 1;
                    } else if (fileItem.getFieldName().equals("modifiedDate")) {
                        Date modifiedDate;
                        try {
                            if (fileItem.getString() != null) {
                                modifiedDate = dateFormat.parse(fileItem.getString());
                            } else {
                                modifiedDate = new Date();
                            }
                        } catch (ParseException var20) {
                            modifiedDate = new Date();
                            LOGGER.error("Invalid modified date format when uploading file(s). Supplied date: " + fileItem.getString());
                        }
                        modifiedDates.put(modifiedDateIndex, modifiedDate);
                        modifiedDateIndex = modifiedDateIndex + 1;
                    }
                }
            }

            Integer i = 0;
            Iterator<FileItem> var25 = fileItems.iterator();

            while (var25.hasNext()) {
                FileItem fileItem = var25.next();
                if (!fileItem.isFormField()) {
                    String filename = (new File(fileItem.getName())).getName();
                    String description = descriptions.get(i);
                    Date modifiedDate = modifiedDates.get(i);
                    if (Strings.isNullOrEmpty(description)) {
                        description = null;
                    }

                    if (maxFileUploadSizeBytes != null && fileItem.getSize() > maxFileUploadSizeBytes) {
                        int maxFileUploadSizeMegs = (int) (maxFileUploadSizeBytes / 1000000L);
                        return ResponseEntity.internalServerError()
                                .body(String.format("File '%s' is too large. Max file size is %,dMB", filename, maxFileUploadSizeMegs));
                    }

                    String fileExtension = filename.substring(filename.lastIndexOf("."));
                    if (excludedFileExtensions.contains(fileExtension)) {
                        return ResponseEntity.internalServerError()
                                .body(String.format("File '%s' has an invalid file extension.", filename));
                    }

                    FileAttachment attachment = this.getManager().uploadFile(filename, fileItem.getContentType(), fileItem.getSize(), description, modifiedDate, userName, fileItem.getInputStream(), fileAttachmentFactory);
                    fileAttachments.add(attachment);
                    i = i + 1;
                }
            }

            return ResponseEntity.ok(fileAttachments.stream().map(FileAttachment::getId).collect(Collectors.toList()));
        } catch (Exception var21) {
            LOGGER.error("Error uploading file(s).", var21);
            return ResponseEntity.internalServerError().build();
        }
    }

    protected Long getFileCountMax() {
        return fileCountMax;
    }
}
