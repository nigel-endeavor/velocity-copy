package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.attachment.FileAttachment;
import com.endeavorms.velocity.qto.attachment.FileAttachmentManager;
import com.endeavorms.velocity.qto.common.AbstractFileAttachmentManager;
import com.endeavorms.velocity.qto.common.AbstractFileAttachmentResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * For downloading file attachments without a parent.
 * @author rcasey
 * @since 9/14/2023
 */
@RestController
@RequestMapping("/api/fileAttachments")
public class FileAttachmentResource extends AbstractFileAttachmentResource<FileAttachment> {

    @Autowired
    private FileAttachmentManager manager;

    @Override
    protected AbstractFileAttachmentManager<FileAttachment> getManager() {
        return manager;
    }

    @Override
    protected String getResourcePath() {
        return "/fileAttachments";
    }

    @PostMapping("/upload")
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public ResponseEntity<?> upload(HttpServletRequest servletRequest) {
        return upload(servletRequest, () -> {
            FileAttachment attachment = new FileAttachment();
            return attachment;
        });
    }
}
