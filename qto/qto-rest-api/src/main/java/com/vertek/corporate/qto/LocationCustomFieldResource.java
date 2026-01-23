package com.vertek.corporate.qto;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.customfield.value.LocationCustomFieldValue;
import com.vertek.corporate.qto.customfield.value.LocationCustomFieldValueListDto;
import com.vertek.corporate.qto.customfield.value.LocationCustomFieldValueManager;
import com.vertek.corporate.qto.customfield.value.ServiceCustomFieldValue;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/locationCustomfieldValues")
@Consumes("application/json")
@Produces("application/json")
@RequiresPermissions(value = {Permissions.INVENTORY_READ, Permissions.ORDER_READ},
        logical = Logical.OR)
public class LocationCustomFieldResource {

    @Inject
    private LocationCustomFieldValueManager locationCustomFieldValueManager;

    @GET
    public Response findByLocationId(@QueryParam("locationId") final Long locationId) {
        List<LocationCustomFieldValue> locationCustomFieldValues = locationCustomFieldValueManager.findByRecordId(locationId);
        return Response.ok(locationCustomFieldValues).build();
    }

    @POST
    @Path("/saveValues")
    @RequiresPermissions(value = {Permissions.INVENTORY_READ, Permissions.ORDER_READ},
            logical = Logical.OR)
    public Response saveValues(final LocationCustomFieldValueListDto locationCustomFieldValues) {
        try {
            List<LocationCustomFieldValue> created = locationCustomFieldValueManager.saveValues(locationCustomFieldValues.getValues());
            return Response.ok(created).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

}
