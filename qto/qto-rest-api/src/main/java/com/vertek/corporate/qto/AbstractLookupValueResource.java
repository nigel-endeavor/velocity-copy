package com.vertek.corporate.qto;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.common.NoCacheResponse;
import com.vertek.corporate.qto.common.lookup.AbstractLookupValueManager;
import com.vertek.corporate.qto.common.lookup.LookupValue;
import com.vertek.corporate.qto.common.lookup.LookupValueSearchCriteria;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jboss.resteasy.annotations.Form;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * Base service class for LookupValues.
 * @author rconnolly
 * @since 1.1.0
 * @param <T> a LookupValue Type.
 */
@Consumes("application/json")
@Produces("application/json")
public abstract class AbstractLookupValueResource<T extends LookupValue> extends AbstractResource<T> {

    /** Logging.*/
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractLookupValueResource.class);

    /** Permission necessary for writing to LookupTypes. */
    protected static final String PERMISSION = Permissions.USER;

    /**
     * Implementations supply a LookupValueManager.
     * @return a LookupValueManager.
     */
    protected abstract AbstractLookupValueManager<T> getManager();


    /**
     * Gets LookupValues.
     * @param criteria search criteria.
     * @return a Response.
     */
    @GET
    public Response findBySearchCriteria(@Form final LookupValueSearchCriteria criteria) {
        try {
            return NoCacheResponse.ok(getManager().findBySearchCriteria(criteria)).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }


    /**
     * Gets a resource.
     * @param id identifier.
     * @return a Response.
     */
    @GET
    @Path("/{id : \\d+}")
    @RequiresPermissions(PERMISSION)
    public Response get(@PathParam("id") final Long id) {
        return Response.ok(getManager().retrieve(id)).build();
    }


    /**
     * Updates a resource.
     * @param id identifier.
     * @param entity resource to update.
     * @return a Response.
     */
    @PUT
    @Path("/{id : \\d+}")
    @RequiresPermissions(PERMISSION)
//    @Interceptors(ValidationInterceptor.class)
    public Response edit(@PathParam("id") final Long id, final T entity) {
        if (!id.equals(entity.getId())) {
            throw new IllegalArgumentException("identifier in path does not match that of passed entity");
        }
        T updated = getManager().edit(entity);
        return Response.ok(updated).build();
    }


    /**
     * Creates a resource.
     * @param entity resource to create.
     * @return a Response.
     */
    @POST
    @RequiresPermissions(PERMISSION)
//    @Interceptors(ValidationInterceptor.class)
    public Response create(final T entity) {
        return Response.ok(getManager().create(entity)).build();
    }


    /**
     * Deletes a resource.
     * @param id resource identifier.
     * @return a Response.
     */
    @DELETE
    @Path("/{id : \\d+}")
    @RequiresPermissions(PERMISSION)
    public Response remove(@PathParam("id") final Long id) {
        getManager().remove(id);
        return Response.noContent().build();
    }

}