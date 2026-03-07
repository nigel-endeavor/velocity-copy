package com.vertek.corporate.qto;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.common.lookup.LookupType;
import com.vertek.corporate.qto.common.lookup.LookupTypeManager;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;

/**
 * @author rcasey
 * @since 1.5.0
 */
@Stateless
@Path("/lookupTypes")
@Consumes("application/json")
@Produces("application/json")
@RequiresPermissions(Permissions.LOOKUP_ADMIN)
public class LookupTypeResource extends AbstractResource<LookupType> {

    /** Business logic for LookupTypes. */
    @Inject
    private LookupTypeManager manager;

    @GET
    public Response listModifiableAlphabetically(@QueryParam("offset") final int offset, @QueryParam("limit") final int limit) {
        try {
            return Response.ok(manager.listModifiableAlphabetically(offset, limit)).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id: \\d+}")
    public Response setValues(@PathParam("id") final Long id, final LookupType type) {
        try {
            manager.setValues(type);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

}
