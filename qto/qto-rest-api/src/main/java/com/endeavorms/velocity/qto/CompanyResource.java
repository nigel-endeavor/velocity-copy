package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.company.Company;
import com.endeavorms.velocity.qto.company.CompanyManager;
import com.endeavorms.velocity.qto.company.CompanySearchCriteria;
import com.endeavorms.velocity.qto.company.task.CompanyTaskManager;
import com.endeavorms.velocity.qto.interceptors.CompanyInterceptor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.jboss.resteasy.annotations.Form;

import jakarta.inject.Inject;
import jakarta.interceptor.Interceptors;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

/**
 * @author rcasey
 * @since 1/19/2023
 */
@Path("/companies")
@Consumes("application/json")
@Produces("application/json")
public class CompanyResource extends AbstractResource<Company> {

    @Override
    protected String getResourcePath() {
        return "/companies";
    }

    /**
     * Business methods for Companies.
     */
    @Inject
    private CompanyManager manager;

    /**
     * Business methods for CompanyTasks.
     */
    @Inject
    private CompanyTaskManager companyTaskManager;

    /**
     * Retrieves all Companies matching the given criteria.
     *
     * @param criteria the criteria to filter by.
     * @return matching companies.
     */
    @GET
    public Response getCompanies(@Form final CompanySearchCriteria criteria) {
        PaginatedResult<Company> result = manager.findBySearchCriteria(criteria);
        return toResponse(getCollectionResource(result, criteria, getLocation(CompanyResource.class)));
    }

    @POST
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public Response create(final Company company) {
        try {
            Company created = manager.create(company);
            return Response.ok(created).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id: \\d+}")
    @PreAuthorize("hasAuthority('tenant-admin')")
    public Response edit(@PathParam("id") final Long id, final Company company) {
        try {
            if (!id.equals(company.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            Company updated = manager.edit(company);
            return Response.ok(updated).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id: \\d+}")
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public Response retrieve(@PathParam("id") final Long id) {
        Company retrieved = manager.retrieve(id);
        return Response.ok(retrieved).build();
    }

    /**
     * Retrieves all tasks for a given company.
     *
     * @param companyId the company to retrieve tasks for.
     * @return the tasks for the company.
     */
    @GET
    @Path("/{companyId}/tasks")
    public Response getCompanyTasks(@PathParam("companyId") final Long companyId) {
        return Response.ok(companyTaskManager.findByCompanyId(companyId)).build();
    }

    @Interceptors(CompanyInterceptor.class)
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") final Long id) {
        manager.remove(id);
        return Response.ok().build();
    }
}
