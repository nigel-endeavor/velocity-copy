package com.vertek.corporate.qto;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.company.Company;
import com.vertek.corporate.qto.company.CompanyManager;
import com.vertek.corporate.qto.company.CompanySearchCriteria;
import com.vertek.corporate.qto.company.task.CompanyTaskManager;
import com.vertek.corporate.qto.interceptors.CompanyInterceptor;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
        return getCollectionResource(result, criteria, getLocation(CompanyResource.class));
    }

    @POST
    @RequiresPermissions(value = {
            Permissions.INVENTORY_READ,
            Permissions.ORDER_READ}, logical = Logical.OR)
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
    @RequiresPermissions(Permissions.TENANT_ADMIN)
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
    @RequiresPermissions(value = {
            Permissions.INVENTORY_READ,
            Permissions.ORDER_READ}, logical = Logical.OR)
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
