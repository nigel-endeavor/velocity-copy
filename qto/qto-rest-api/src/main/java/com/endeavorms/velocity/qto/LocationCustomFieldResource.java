package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.customfield.value.LocationCustomFieldValue;
import com.endeavorms.velocity.qto.customfield.value.LocationCustomFieldValueListDto;
import com.endeavorms.velocity.qto.customfield.value.LocationCustomFieldValueManager;
import com.endeavorms.velocity.qto.customfield.value.ServiceCustomFieldValue;
import org.springframework.security.access.prepost.PreAuthorize;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/locationCustomfieldValues")
@Consumes("application/json")
@Produces("application/json")
@PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
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
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public Response saveValues(final LocationCustomFieldValueListDto locationCustomFieldValues) {
        try {
            List<LocationCustomFieldValue> created = locationCustomFieldValueManager.saveValues(locationCustomFieldValues.getValues());
            return Response.ok(created).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

}
