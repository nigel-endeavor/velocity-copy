package com.vertek.corporate.qto.common;

import com.google.common.base.Strings;
import com.vertek.corporate.qto.attachment.FileAttachment;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
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
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.StreamingOutput;

import com.vertek.corporate.qto.config.ConfigPropertyManager;
import com.vertek.corporate.qto.config.ConfigurationProperty;
import org.apache.commons.fileupload2.core.DiskFileItem;
import org.apache.commons.fileupload2.core.DiskFileItemFactory;
import org.apache.commons.fileupload2.core.FileItem;
import org.apache.commons.fileupload2.jakarta.servlet6.JakartaServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.vertek.corporate.qto.config.ConfigKey.EXCLUDED_FILE_EXTENSIONS;
import static com.vertek.corporate.qto.config.ConfigKey.MAX_FILE_UPLOAD_SIZE_BYTES;

@Produces({"application/json", "application/gwt", "application/xml"})
public abstract class AbstractFileAttachmentResource<T extends FileAttachment> extends AbstractResource<FileAttachment> {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractFileAttachmentResource.class);

    @Inject
    protected ConfigPropertyManager configPropertyManager;

    //the default is 2, as a normal file attachment comes in as two "fileitems", the attachment and the description
    protected Long fileCountMax = 2L;

    public AbstractFileAttachmentResource() {
    }

    protected abstract AbstractFileAttachmentManager<T> getManager();

    @GET
    @Path("/userPermissions")
    @Produces({"application/json", "application/gwt", "application/xml"})
    public Response userPermissions() {
        FileAttachmentPermissions permissions = new FileAttachmentPermissions();
        permissions.setAttach(true);
        permissions.setView(true);
        permissions.setDelete(true);
        return NoCacheResponse.ok(permissions).build();
    }

    @GET
    @Path("/download")
    @Produces({"application/json", "application/gwt", "application/xml"})
    public Response download(@QueryParam("id") Long id, @QueryParam("inline") @DefaultValue("false") boolean inline) {
        PreconditionsUtil.checkArgument(id, "File Attachment ID is required.");
        FileAttachment fileAttachment = (FileAttachment) this.getManager().retrieve(id);
        final byte[] data = fileAttachment.getContent().getData();
        StreamingOutput streamingOutput = new StreamingOutput() {
            public void write(OutputStream outputStream) throws IOException {
                ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
                IOUtils.copy(inputStream, outputStream);
                inputStream.close();
            }
        };
        String contentDisposition = inline ? "inline" : "attachment";
        return NoCacheResponse.ok(streamingOutput).header("Content-Disposition", contentDisposition + "; filename=\"" + fileAttachment.getName() + "\"").type(fileAttachment.getMimeType()).build();
    }

    @GET
    @Path("/delete")
    public Response delete(@QueryParam("id") Long id) {
        PreconditionsUtil.checkArgument(id, "File Attachment ID is required.");
        this.getManager().remove(id);
        return Response.noContent().build();
    }

    @POST
    @Path("/updateDescription")
    public Response updateDescription(FileAttachment fileAttachment) {
        PreconditionsUtil.checkArgument(fileAttachment, "File Attachment is required.");
        PreconditionsUtil.checkArgument(fileAttachment.getId(), "File Attachment ID is required.");
        this.getManager().updateDescription(fileAttachment.getId(), fileAttachment.getDescription());
        return Response.noContent().build();
    }

    protected Response upload(HttpServletRequest servletRequest, FileAttachmentFactory<T> fileAttachmentFactory) {
        try {
            String userName = SecurityUtils.getLoggedInUser();
            List<FileAttachment> fileAttachments = new ArrayList();
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

            JakartaServletFileUpload<DiskFileItem, DiskFileItemFactory> servletFileUpload = new JakartaServletFileUpload<>(DiskFileItemFactory.builder().get());
            servletFileUpload.setFileCountMax(getFileCountMax());

            List<DiskFileItem> fileItems = servletFileUpload.parseRequest(servletRequest);
            SimpleDateFormat dateFormat = new SimpleDateFormat("E, d MMM yyyy k:m:s z");
            Dictionary<Integer, String> descriptions = new Hashtable();
            Dictionary<Integer, Date> modifiedDates = new Hashtable();
            Integer descriptionIndex = 0;
            Integer modifiedDateIndex = 0;
            Iterator var14 = fileItems.iterator();

            while(var14.hasNext()) {
                DiskFileItem fileItem = (DiskFileItem)var14.next();
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
            Iterator var25 = fileItems.iterator();

            while(var25.hasNext()) {
                DiskFileItem fileItem = (DiskFileItem)var25.next();
                if (!fileItem.isFormField()) {
                    String filename = (new File(fileItem.getName())).getName();
                    String description = (String)descriptions.get(i);
                    Date modifiedDate = (Date)modifiedDates.get(i);
                    if (Strings.isNullOrEmpty(description)) {
                        description = null;
                    }

                    if (maxFileUploadSizeBytes != null && fileItem.getSize() > maxFileUploadSizeBytes) {
                        int maxFileUploadSizeMegs = (int)(maxFileUploadSizeBytes / 1000000L);
                        return Response.serverError().entity(String.format("File '%s' is too large. Max file size is %,dMB", filename, maxFileUploadSizeMegs)).build();
                    }

                    String fileExtension = filename.substring(filename.lastIndexOf("."));
                    if (excludedFileExtensions.contains(fileExtension)) {
                        return Response.serverError().entity(String.format("File '%s' has an invalid file extension.", filename)).build();
                    }

                    FileAttachment attachment = this.getManager().uploadFile(filename, fileItem.getContentType(), fileItem.getSize(), description, modifiedDate, userName, fileItem.getInputStream(), fileAttachmentFactory);
                    fileAttachments.add(attachment);
                    i = i + 1;
                }
            }

            return Response.ok("200 OK").entity(fileAttachments.stream().map(FileAttachment::getId).collect(Collectors.toList()).toString()).build();
        } catch (Exception var21) {
            LOGGER.error("Error uploading file(s).", var21);
            return Response.serverError().build();
        }
    }

    protected Long getFileCountMax() {
        return fileCountMax;
    }
}
