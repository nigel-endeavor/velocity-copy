package com.vertek.corporate.qto.task;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.company.task.TaskGroup;
import com.vertek.corporate.qto.company.task.TaskGroupManager;
import com.vertek.corporate.qto.company.task.TaskGroupSearchCriteria;
import com.vertek.corporate.qto.interceptors.TaskGroupInterceptor;
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
 * @author fcurran
 * @since 9/5/2024
 */
@Path("/taskGroups")
@Consumes("application/json")
@Produces("application/json")
public class TaskGroupResource extends AbstractResource<TaskGroup> {

    /** Business methods for TaskGroups. */
    @Inject
    private TaskGroupManager manager;

    /**
     * Retrieves all TaskGroups matching the given criteria.
     * @param criteria the criteria to filter by.
     * @return matching task groups.
     */
    @GET
    @RequiresPermissions(value = {
            Permissions.INVENTORY_READ,
            Permissions.ORDER_READ}, logical = Logical.OR)
    public Response getTaskGroups(@Form final TaskGroupSearchCriteria criteria) {
        PaginatedResult<TaskGroup> templates = manager.findBySearchCriteria(criteria);
        return getCollectionResource(templates, criteria, getLocation(TaskGroupResource.class));
    }

    /**
     * Retrieves a single TaskGroup by its identifier.
     * @param id the identifier of the TaskGroup to retrieve.
     * @return the TaskGroup, if found.
     */
    @GET
    @Path("/{id: \\d+}")
    @RequiresPermissions(value = {
            Permissions.INVENTORY_READ,
            Permissions.ORDER_READ}, logical = Logical.OR)
    public Response retrieve(@PathParam("id") final Long id) {
        TaskGroup retrieved = manager.retrieve(id);
        return Response.ok(retrieved).build();
    }

    /**
     * Creates a new TaskGroup.
     * @param template the TaskGroup to create.
     * @return the created TaskGroup.
     */
    @POST
    @RequiresPermissions(value = {
            Permissions.INVENTORY_WRITE,
            Permissions.ORDER_WRITE}, logical = Logical.OR)
    @Interceptors({ TaskGroupInterceptor.class })
    public Response create(final TaskGroup template) {
        try {
            TaskGroup created = manager.create(template);
            return Response.ok(created).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    /**
     * Updates a TaskGroup.
     * @param id the identifier of the TaskGroup to update.
     * @param template the TaskGroup to update.
     * @return the updated TaskGroup.
     */
    @PUT
    @Path("/{id: \\d+}")
    @RequiresPermissions(value = {
            Permissions.INVENTORY_WRITE,
            Permissions.ORDER_WRITE}, logical = Logical.OR)
    @Interceptors({ TaskGroupInterceptor.class })
    public Response edit(@PathParam("id") final Long id, final TaskGroup template) {
        try {
            if (!id.equals(template.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            TaskGroup updated = manager.edit(template);
            return Response.ok(updated).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    /**
     * Removes a TaskGroup.
     * @param id the identifier of the TaskGroup to remove.
     * @return a 204 response.
     */
    @DELETE
    @Path("/{id: \\d+}")
    @RequiresPermissions(value = {
            Permissions.INVENTORY_WRITE,
            Permissions.ORDER_WRITE}, logical = Logical.OR)
    @Interceptors({ TaskGroupInterceptor.class })
    public Response remove(@PathParam("id") final Long id) {
        try {
            manager.remove(id);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}
