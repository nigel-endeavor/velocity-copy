package com.vertek.corporate.qto.fileimport;

import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.fileimport.importactivity.ImportActivity;
import com.vertek.corporate.qto.fileimport.importactivity.ImportActivityManager;
import com.vertek.corporate.qto.fileimport.importactivity.ImportActivitySearchCriteria;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jboss.resteasy.annotations.Form;

import jakarta.ejb.Stateless;
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
@Stateless
@Consumes("application/json")
@Produces("application/json")
@Path("/importActivities")
//@RequiresPermissions() //todo: file import permissions?
public class ImportActivityResource extends AbstractResource<ImportActivity> {

    @Inject
    private ImportActivityManager importActivityManager;

    @GET
    public Response getImportActivities(@Form ImportActivitySearchCriteria criteria) {
        ImportActivitySearchCriteria crit = getExportCriteria(criteria);
        PaginatedResult<ImportActivity> result = importActivityManager.findBySearchCriteria(crit);
        return getCollectionResource(result, crit, getLocation(ImportActivityResource.class));
    }

}
