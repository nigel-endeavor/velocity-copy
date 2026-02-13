package com.endeavorms.velocity.qto.fileimport;

import com.endeavorms.velocity.qto.common.JakartaServletRequestContext;
import com.endeavorms.velocity.qto.common.TenantSubjectManager;
import com.endeavorms.velocity.qto.fileimport.importactivity.ImportActivity;
import com.endeavorms.velocity.qto.fileimport.importactivity.ImportActivityManager;
import com.endeavorms.velocity.qto.fileimport.order.OrderImporter;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author rcasey
 * @since 9/1/2023
 */
@Component
@RestController
@RequestMapping("/api/import")
@PreAuthorize("hasAuthority('file-import')")
public class FileImportResource {

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    private ImportActivityManager manager;

    @Autowired
    private FileImportQueueHandler fileImportQueueHandler;

    @Autowired
    private OrderImporter orderImporter;

    @Autowired
    private ImportTemplateBuilder importTemplateBuilder;

    @Autowired
    private TenantSubjectManager tenantSubjectManager;

    private static final List<String> SPREADSHEET_MIME_TYPES;

    static {
        SPREADSHEET_MIME_TYPES = new ArrayList<>();
        SPREADSHEET_MIME_TYPES.add("application/vnd.ms-excel");
        SPREADSHEET_MIME_TYPES.add("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    @PostMapping("/{type:.+}")
    public ResponseEntity<?> uploadFile(@PathVariable("type") final String type,
                                        HttpServletRequest request) {
        try {
            ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
            servletFileUpload.setFileCountMax(1);
            List<FileItem> fileItems = servletFileUpload.parseRequest(new JakartaServletRequestContext(request));

            if (fileItems == null || fileItems.isEmpty()) {
                return ResponseEntity.badRequest().body("No file uploaded");
            }

            FileItem fileItem = fileItems.get(0);
            if (!SPREADSHEET_MIME_TYPES.contains(fileItem.getContentType())) {
                return ResponseEntity.badRequest().body("Invalid file type. The file must be in xlsx or xls format.");
            }

            ImportActivity importActivity = transactionTemplate.execute(status -> manager.create(type, fileItem));
            if (!"Order".equals(type)) {
                fileImportQueueHandler.sendImportToQueue(importActivity.getId(), type);
            } else {
                importActivity = orderImporter.importFile(importActivity.getId());
            }
            return ResponseEntity.ok(importActivity);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @GetMapping("/template/{type:.+}")
    public ResponseEntity<?> downloadTemplate(@PathVariable("type") final String type) {
        try {
            Long tenantId = tenantSubjectManager.getCurrentTenant().getId();
            Workbook workbook = importTemplateBuilder.getTemplate(type, tenantId);
            String templateName = importTemplateBuilder.getTemplateName(type);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            workbook.write(baos);
            byte[] bytes = baos.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Disposition", "attachment; filename=" + templateName);
            headers.set("Access-Control-Expose-Headers", "Content-Disposition");
            headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(bytes);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
