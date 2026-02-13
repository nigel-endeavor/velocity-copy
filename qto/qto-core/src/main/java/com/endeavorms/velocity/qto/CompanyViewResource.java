package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.company.CompanyView;
import com.endeavorms.velocity.qto.company.CompanyViewManager;
import com.endeavorms.velocity.qto.company.CompanyViewSearchCriteria;
import com.endeavorms.velocity.qto.company.MasterCustomerWorklistMeta;
import org.springframework.security.access.prepost.PreAuthorize;
import org.jboss.resteasy.annotations.Form;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

@Path("/companyViews")
@Consumes("application/json")
@Produces({"application/json", "application/vnd.ms-excel"})
@PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
public class CompanyViewResource extends AbstractResource<CompanyView> {

    @Override
    protected String getResourcePath() {
        return "/companyViews";
    }

    @Inject
    private CompanyViewManager companyViewManager;

    @GET
    public Response findBySearchCriteria(@Form final CompanyViewSearchCriteria criteria) {
        PaginatedResult<CompanyView> result = companyViewManager.findBySearchCriteria(criteria);
        return toResponse(getCollectionResource(result, criteria, getLocation(CompanyViewResource.class)));
    }

    @GET
    @Path("/meta")
    public Response getMasterCustomerWorklistMeta(@Form final CompanyViewSearchCriteria criteria) {
        MasterCustomerWorklistMeta meta = companyViewManager.getMasterCustomerWorklistMeta(criteria);
        return Response.ok(meta).build();
    }
}
