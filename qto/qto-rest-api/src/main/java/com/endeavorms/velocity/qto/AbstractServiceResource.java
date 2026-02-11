package com.endeavorms.velocity.qto;

import com.google.common.base.Strings;
import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.interceptors.ServiceDeleteInterceptor;
import com.endeavorms.velocity.qto.interceptors.ServiceInterceptor;
import com.endeavorms.velocity.qto.service.AbstractServiceManager;
import com.endeavorms.velocity.qto.service.Service;
import org.springframework.security.access.prepost.PreAuthorize;

import jakarta.interceptor.Interceptors;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

/**
 * The core operations that a Resource for a Service should perform.
 * @param <T> the type of Service.
 */
public abstract class AbstractServiceResource<T extends Service> extends AbstractResource<T> {
    /** Subclasses must return their path, e.g. "/broadbandService". */
    @Override
    protected abstract String getResourcePath();
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
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
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
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
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
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
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
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
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
    @PreAuthorize("hasAnyAuthority('inventory:write','order:write')")
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
