package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.customfield.value.ServiceCustomFieldValue;
import com.endeavorms.velocity.qto.customfield.value.ServiceCustomFieldValueListDto;
import com.endeavorms.velocity.qto.customfield.value.ServiceCustomFieldValueManager;
import com.endeavorms.velocity.qto.interceptors.ServiceCustomFieldValueInterceptor;
import org.springframework.security.access.prepost.PreAuthorize;

import jakarta.inject.Inject;
import jakarta.interceptor.Interceptors;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/serviceCustomfieldValues")
@Consumes("application/json")
@Produces("application/json")
@PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
public class ServiceCustomFieldResource {

    @Inject
    private ServiceCustomFieldValueManager serviceCustomFieldValueManager;

    @GET
    public Response findByServiceId(@QueryParam("serviceId") final Long serviceId) {
        List<ServiceCustomFieldValue> serviceCustomFieldValues = serviceCustomFieldValueManager.findByRecordId(serviceId);
        return Response.ok(serviceCustomFieldValues).build();
    }

    @POST
    @Path("/saveValues")
    @Interceptors({ServiceCustomFieldValueInterceptor.class})
    @PreAuthorize("hasAnyAuthority('inventory:read','order:read')")
    public Response saveValues(final ServiceCustomFieldValueListDto serviceCustomFieldValues) {
        try {
            List<ServiceCustomFieldValue> created = serviceCustomFieldValueManager.saveValues(serviceCustomFieldValues.getValues());
            return Response.ok(created).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

}
