package com.vertek.corporate.qto;


import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.report.ReportMetaData;
import com.vertek.corporate.qto.report.ReportMetaDataManager;
import com.vertek.corporate.qto.report.ReportBuilder;
import com.vertek.corporate.qto.report.ReportHeaders;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import java.util.List;

import static com.vertek.corporate.qto.authentication.Permissions.ORDER_READ;

@Stateless
@Path("/reports")
@RequiresPermissions(ORDER_READ)
public class ReportResource extends AbstractResource<ReportMetaData> {

    @Inject
    ReportMetaDataManager manager;

    @Inject
    private ReportBuilder reportBuilder;


    @GET
    @Path("/{type: .+}")
    @Produces("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public Response downloadReport(@PathParam("type") final String type) {
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
                StreamingOutput streamingOutput = workbook::write;
                return Response.ok(streamingOutput)
                        .header("Content-Disposition", "attachment; filename=" + name)
                        .header("Access-Control-Expose-Headers", "Content-Disposition")
                        .build();
            } else {
                return Response.serverError().entity("Invalid report type").build();
            }
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
