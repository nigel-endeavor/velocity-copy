package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.report.ReportMetaData;
import com.endeavorms.velocity.qto.report.ReportMetaDataManager;
import com.endeavorms.velocity.qto.report.ReportBuilder;
import com.endeavorms.velocity.qto.report.ReportHeaders;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.inject.Inject;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Component
@RestController
@RequestMapping("/api/reports")
@PreAuthorize("hasAuthority('order:read')")
public class ReportResource extends AbstractResource<ReportMetaData> {

    @Override
    protected String getResourcePath() {
        return "/reports";
    }

    @Inject
    ReportMetaDataManager manager;

    @Inject
    private ReportBuilder reportBuilder;

    @GetMapping(value = "/{type}", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public ResponseEntity<?> downloadReport(@PathVariable("type") final String type) {
        try {
            Workbook workbook = null;
            String name = null;
            if ("inventory".equalsIgnoreCase(type)) {
                List<Object[]> data = manager.getInventoryReportView();
                name = "InventoryReport";
                workbook = reportBuilder.createNativeReport(name, ReportHeaders.INVENTORY_HEADERS, data);
            } else if ("wip".equalsIgnoreCase(type)) {
                List<Object[]> data = manager.getWipReportView();
                name = "WIPReport";
                workbook = reportBuilder.createNativeReport(name, ReportHeaders.WIP_HEADERS, data);
            }
            if (workbook != null) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                workbook.write(baos);
                workbook.close();
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
                headers.set("Content-Disposition", "attachment; filename=" + name);
                headers.set("Access-Control-Expose-Headers", "Content-Disposition");
                return ResponseEntity.ok().headers(headers).body(baos.toByteArray());
            } else {
                return ResponseEntity.internalServerError().body("Invalid report type");
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
