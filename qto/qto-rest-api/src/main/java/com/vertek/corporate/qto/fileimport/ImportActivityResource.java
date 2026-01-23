package com.vertek.corporate.qto.fileimport;

import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.fileimport.importactivity.ImportActivity;
import com.vertek.corporate.qto.fileimport.importactivity.ImportActivityManager;
import com.vertek.corporate.qto.fileimport.importactivity.ImportActivitySearchCriteria;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jboss.resteasy.annotations.Form;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

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
