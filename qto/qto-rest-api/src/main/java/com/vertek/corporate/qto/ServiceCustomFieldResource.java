package com.vertek.corporate.qto;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.customfield.value.ServiceCustomFieldValue;
import com.vertek.corporate.qto.customfield.value.ServiceCustomFieldValueListDto;
import com.vertek.corporate.qto.customfield.value.ServiceCustomFieldValueManager;
import com.vertek.corporate.qto.interceptors.ServiceCustomFieldValueInterceptor;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;

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
@RequiresPermissions(value = {Permissions.INVENTORY_READ, Permissions.ORDER_READ},
        logical = Logical.OR)
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
    @RequiresPermissions(value = {Permissions.INVENTORY_READ, Permissions.ORDER_READ},
            logical = Logical.OR)
    public Response saveValues(final ServiceCustomFieldValueListDto serviceCustomFieldValues) {
        try {
            List<ServiceCustomFieldValue> created = serviceCustomFieldValueManager.saveValues(serviceCustomFieldValues.getValues());
            return Response.ok(created).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

}
