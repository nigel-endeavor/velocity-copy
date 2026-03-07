package com.vertek.corporate.qto;

import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.ftdi.FtdiOrderType;
import com.vertek.corporate.qto.ftdi.FtdiOrderTypeManager;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * @author rcasey
 * @since 4/12/2023
 */
@Path("/ftdiOrderTypes")
@Consumes("application/json")
@Produces("application/json")
public class FtdiOrderTypeResource extends AbstractResource<FtdiOrderType> {

    /** Business methods for FtdiOrderTypes. */
    @Inject
    private FtdiOrderTypeManager manager;

    @GET
    @Path("/{id: \\d+}")
    public Response retrieve(@PathParam("id") final Long id) {
        FtdiOrderType ftdiOrderType = manager.retrieve(id);
        return Response.ok(ftdiOrderType).build();
    }

    @GET
    public Response findByVendor(@QueryParam("vendor") final String vendor) {
        List<FtdiOrderType> ftdiOrderTypes = manager.findByVendor(vendor);
        return Response.ok(ftdiOrderTypes).build();
    }

    @GET
    @Path("/sort1")
    public Response findSort1ByVendor(@QueryParam("vendor") final String vendor) {
        List<String> ftdiOrderTypes = manager.findSort1ByVendor(vendor);
        return Response.ok(ftdiOrderTypes).build();
    }

    @GET
    @Path("/sort2")
    public Response findSort2(@QueryParam("sort1") final String sort1) {
        List<String> ftdiOrderTypes = manager.findSort2(sort1);
        return Response.ok(ftdiOrderTypes).build();
    }

    @GET
    @Path("/orderType")
    public Response findOrderType(@QueryParam("sort1") final String sort1, @QueryParam("sort2") final String sort2) {
        List<FtdiOrderType> ftdiOrderTypes = manager.findOrderType(sort1, sort2);
        return Response.ok(ftdiOrderTypes).build();
    }
}
