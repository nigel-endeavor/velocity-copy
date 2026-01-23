package com.vertek.corporate.qto.task;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.common.PreconditionsUtil;
import com.vertek.corporate.qto.company.task.CompanyTask;
import com.vertek.corporate.qto.company.task.CompanyTaskManager;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * REST API for CompanyTask entities.
 */
@Path("/companyTasks")
@Consumes("application/json")
@Produces("application/json")
public class CompanyTaskResource extends AbstractResource<CompanyTask> {
    @Inject
    private CompanyTaskManager manager;

    @PUT
    @Path("/{id}")
    @RequiresPermissions(value = {
            Permissions.INVENTORY_WRITE,
            Permissions.ORDER_WRITE}, logical = Logical.OR)
    public Response updateCompanyTask(@PathParam("id") final Long companyTaskId, final CompanyTask companyTask) {
        PreconditionsUtil.checkArgument(companyTaskId, "Customer Task ID is required");
        CompanyTask updatedCompanyTask = manager.edit(companyTask);
        return Response.ok(updatedCompanyTask).build();
    }
}
