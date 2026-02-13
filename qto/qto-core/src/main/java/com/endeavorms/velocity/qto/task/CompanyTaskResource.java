package com.endeavorms.velocity.qto.task;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PreconditionsUtil;
import com.endeavorms.velocity.qto.company.task.CompanyTask;
import com.endeavorms.velocity.qto.company.task.CompanyTaskManager;
import org.springframework.security.access.prepost.PreAuthorize;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

/**
 * REST API for CompanyTask entities.
 */
@Path("/companyTasks")
@Consumes("application/json")
@Produces("application/json")
public class CompanyTaskResource extends AbstractResource<CompanyTask> {

    @Override
    protected String getResourcePath() {
        return "/companyTasks";
    }
    @Inject
    private CompanyTaskManager manager;

    @PUT
    @Path("/{id}")
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
    public Response updateCompanyTask(@PathParam("id") final Long companyTaskId, final CompanyTask companyTask) {
        PreconditionsUtil.checkArgument(companyTaskId, "Customer Task ID is required");
        CompanyTask updatedCompanyTask = manager.edit(companyTask);
        return Response.ok(updatedCompanyTask).build();
    }
}
