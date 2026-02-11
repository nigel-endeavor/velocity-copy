package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.attachment.FileAttachment;
import com.endeavorms.velocity.qto.attachment.FileAttachmentManager;
import com.endeavorms.velocity.qto.common.AbstractFileAttachmentManager;
import com.endeavorms.velocity.qto.common.AbstractFileAttachmentResource;
import org.springframework.security.access.prepost.PreAuthorize;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * For downloading file attachments without a parent.
 * @author rcasey
 * @since 9/14/2023
 */
@Path("/fileAttachments")
public class FileAttachmentResource extends AbstractFileAttachmentResource {

    @Inject
    private FileAttachmentManager manager;

    @Override
    protected AbstractFileAttachmentManager getManager() {
        return manager;
    }

    @Override
    protected String getResourcePath() {
        return "/fileAttachments";
    }

    @POST
    @Path("/upload")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public Response upload(@Context final HttpServletRequest servletRequest) {
        return upload(servletRequest, () -> {
            FileAttachment attachment = new FileAttachment();
            return attachment;
        });
    }

}
