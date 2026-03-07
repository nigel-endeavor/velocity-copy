package com.vertek.corporate.qto;

import com.vertek.corporate.qto.attachment.FileAttachment;
import com.vertek.corporate.qto.attachment.FileAttachmentManager;
import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractFileAttachmentManager;
import com.vertek.corporate.qto.common.AbstractFileAttachmentResource;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;

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

    @POST
    @Path("/upload")
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @RequiresPermissions(value = {
            Permissions.INVENTORY_WRITE,
            Permissions.ORDER_WRITE}, logical = Logical.OR)
    public Response upload(@Context final HttpServletRequest servletRequest) {
        return upload(servletRequest, () -> {
            FileAttachment attachment = new FileAttachment();
            return attachment;
        });
    }

}
