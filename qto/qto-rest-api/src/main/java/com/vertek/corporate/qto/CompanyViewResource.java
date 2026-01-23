package com.vertek.corporate.qto;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.company.CompanyView;
import com.vertek.corporate.qto.company.CompanyViewManager;
import com.vertek.corporate.qto.company.CompanyViewSearchCriteria;
import com.vertek.corporate.qto.company.MasterCustomerWorklistMeta;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jboss.resteasy.annotations.Form;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/companyViews")
@Consumes("application/json")
@Produces({"application/json", "application/vnd.ms-excel"})
@RequiresPermissions(value = {Permissions.INVENTORY_READ, Permissions.ORDER_READ},
                     logical = Logical.OR)
public class CompanyViewResource extends AbstractResource<CompanyView> {

    @Inject
    private CompanyViewManager companyViewManager;

    @GET
    public Response findBySearchCriteria(@Form final CompanyViewSearchCriteria criteria) {
        PaginatedResult<CompanyView> result = companyViewManager.findBySearchCriteria(criteria);
        return getCollectionResource(result, criteria, getLocation(CompanyViewResource.class));
    }

    @GET
    @Path("/meta")
    public Response getMasterCustomerWorklistMeta(@Form final CompanyViewSearchCriteria criteria) {
        MasterCustomerWorklistMeta meta = companyViewManager.getMasterCustomerWorklistMeta(criteria);
        return Response.ok(meta).build();
    }
}
