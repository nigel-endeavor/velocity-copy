package com.endeavorms.velocity.qto.fileimport;

import com.endeavorms.velocity.qto.common.TenantSubjectManager;
import com.endeavorms.velocity.qto.fileimport.importactivity.ImportActivity;
import com.endeavorms.velocity.qto.fileimport.importactivity.ImportActivityManager;
import com.endeavorms.velocity.qto.fileimport.order.OrderImporter;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.StreamingOutput;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author rcasey
 * @since 9/1/2023
 */
@Component
@Path("/import")
@PreAuthorize("hasAuthority('file-import')")
public class FileImportResource {

    @Inject
    private TransactionTemplate transactionTemplate;

    @Inject
    private ImportActivityManager manager;

    @Inject
    private FileImportQueueHandler fileImportQueueHandler;

    @Inject
    private OrderImporter orderImporter;

    @Inject
    private ImportTemplateBuilder importTemplateBuilder;

    @Inject
    private TenantSubjectManager tenantSubjectManager;

    /** Accepted MIME types. */
    private static final List<String> SPREADSHEET_MIME_TYPES;

    static {
        SPREADSHEET_MIME_TYPES = new ArrayList<>();
        SPREADSHEET_MIME_TYPES.add("application/vnd.ms-excel"); //xls
        SPREADSHEET_MIME_TYPES.add("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); //xlsx
    }

    @POST
    @Consumes("multipart/form-data")
    @Produces("application/json")
    @Path("/{type: .+}")
    public Response uploadFile(@PathParam("type") final String type,
                               @Context final HttpServletRequest servletRequest) throws Exception {

        ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
        servletFileUpload.setFileCountMax(1);
        List<FileItem> fileItems = servletFileUpload.parseRequest(new com.endeavorms.velocity.qto.common.JakartaServletRequestContext(servletRequest));

        //verify that we have a file
        if (fileItems == null || fileItems.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("No file uploaded").build();
        }

        //verify that the file is a valid format
        FileItem fileItem = fileItems.get(0);
        if (!SPREADSHEET_MIME_TYPES.contains(fileItem.getContentType())) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Invalid file type. The file must be in xlsx or xls format.").build();
        }

        try {
            ImportActivity importActivity = transactionTemplate.execute(status -> manager.create(type, fileItem));
            if (!"Order".equals(type)) {
                fileImportQueueHandler.sendImportToQueue(importActivity.getId(), type);
            } else {
                importActivity = orderImporter.importFile(importActivity.getId());
            }
            return Response.ok().entity(importActivity).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/template/{type: .+}")
    @Produces("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public Response downloadTemplate(@PathParam("type") final String type) {

        try {
            Long tenantId = tenantSubjectManager.getCurrentTenant().getId();
            Workbook workbook = importTemplateBuilder.getTemplate(type, tenantId);
            String templateName = importTemplateBuilder.getTemplateName(type);
            StreamingOutput streamingOutput = workbook::write;
            return Response.ok(streamingOutput)
                    .header("Content-Disposition", "attachment; filename=" + templateName)
                    .header("Access-Control-Expose-Headers", "Content-Disposition")
                    .build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
