package com.vertek.corporate.qto;

import com.google.common.base.Strings;
import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.interceptors.ServiceDeleteInterceptor;
import com.vertek.corporate.qto.interceptors.ServiceInterceptor;
import com.vertek.corporate.qto.service.AbstractServiceManager;
import com.vertek.corporate.qto.service.Service;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import javax.interceptor.Interceptors;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 * The core operations that a Resource for a Service should perform.
 * @param <T> the type of Service.
 */
public abstract class AbstractServiceResource<T extends Service> extends AbstractResource<T> {
    /**
     * The manager for the concrete Resource.
     * @return an injected business logic tier for the given Service of type <T>.
     * @param <X> the type of Manager.
     */
    protected abstract <X extends AbstractServiceManager<T>> X getManager();

    /**
     * Retrieves an object of type <T>.
     * @param id the id of the desired entity.
     * @return the matching entity if it exists.
     */
    @GET
    @Path("/{id: \\d+}")
    @RequiresPermissions(value = {
            Permissions.INVENTORY_READ,
            Permissions.ORDER_READ}, logical = Logical.OR)
    public Response retrieve(@PathParam("id") final Long id) {
        T retrieved = getManager().retrieve(id);
        return Response.ok(retrieved).build();
    }

    /**
     * Persists the incoming entity.
     * @param entity the entity to persist.
     * @return the created entity, with an ID.
     */
    @Interceptors({ServiceInterceptor.class})
    @POST
    @RequiresPermissions(value = {
            Permissions.INVENTORY_WRITE,
            Permissions.ORDER_WRITE}, logical = Logical.OR)
    public Response create(final T entity) {
        try {
            if (Strings.isNullOrEmpty(entity.getRecordSource())) {
                entity.setRecordSource(RecordSource.MANUAL_ENTRY.getName());
            }
            T created = getManager().create(entity);
            return Response.ok(created).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    /**
     * Updates the incoming entity.
     * @param id the ID of the entity to update
     * @param entity the entity to update.
     * @return the updated entity.
     */
    @Interceptors({ServiceInterceptor.class})
    @PUT
    @Path("/{id: \\d+}")
    @RequiresPermissions(value = {
            Permissions.INVENTORY_WRITE,
            Permissions.ORDER_WRITE}, logical = Logical.OR)
    public Response edit(@PathParam("id") final Long id, final T entity) {
        try {
            if (!id.equals(entity.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            T updated = getManager().edit(entity);
            return Response.ok(updated).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    /**
     * Clones key fields from the incoming entity and returns a new entity of type <T> with those fields set.
     * @param entityToClone the entity to clone.
     * @return the new entity.
     */
    @POST
    @Path("/cloneAndCancel")
    @RequiresPermissions(value = {
            Permissions.INVENTORY_WRITE,
            Permissions.ORDER_WRITE}, logical = Logical.OR)
    public <X extends Service> Response cloneAndCancel(final X entityToClone) {
        try {
            T created = getManager().cloneAndCancel(entityToClone);
            return Response.ok(created).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }


    @PUT
    @Path("/pullFromCrm")
    @RequiresPermissions(value = {
            Permissions.INVENTORY_WRITE,
            Permissions.ORDER_WRITE}, logical = Logical.OR)
    public Response pullFromCrm(final T entity) {
        try {
//            T edited = getManager().edit(entity);
            T edited = getManager().pullFromCrm(entity);
            return Response.ok(edited).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @Interceptors({ServiceDeleteInterceptor.class})
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") final Long id) {
        getManager().markForDeletion(id);
        return Response.ok().build();
    }
}
