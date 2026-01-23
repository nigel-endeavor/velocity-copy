package com.vertek.corporate.qto;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.common.lookup.LookupType;
import com.vertek.corporate.qto.common.lookup.LookupTypeManager;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

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
