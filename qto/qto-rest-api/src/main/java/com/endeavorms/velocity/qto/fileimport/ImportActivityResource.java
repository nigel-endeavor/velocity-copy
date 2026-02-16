package com.endeavorms.velocity.qto.fileimport;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.fileimport.importactivity.ImportActivity;
import com.endeavorms.velocity.qto.fileimport.importactivity.ImportActivityManager;
import com.endeavorms.velocity.qto.fileimport.importactivity.ImportActivitySearchCriteria;
import org.jboss.resteasy.annotations.Form;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

/**
 * @author rcasey
 * @since 9/1/2023
 */
@Component
@Consumes("application/json")
@Produces("application/json")
@Path("/importActivities")
//@RequiresPermissions() //todo: file import permissions?
public class ImportActivityResource extends AbstractResource<ImportActivity> {

    @Override
    protected String getResourcePath() {
        return "/importActivities";
    }

    @Autowired
    private ImportActivityManager importActivityManager;

    @GET
    public Response getImportActivities(@Form ImportActivitySearchCriteria criteria) {
        ImportActivitySearchCriteria crit = getExportCriteria(criteria);
        PaginatedResult<ImportActivity> result = importActivityManager.findBySearchCriteria(crit);
        return toResponse(getCollectionResource(result, crit, getLocation(ImportActivityResource.class)));
    }

}
