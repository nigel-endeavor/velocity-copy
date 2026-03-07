package com.vertek.corporate.qto;

import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.location.Location;
import com.vertek.corporate.qto.multiedit.request.MultiEditRequestDto;
import com.vertek.corporate.qto.service.Service;
import com.vertek.corporate.qto.service.ServiceManager;
import com.vertek.corporate.qto.service.ServiceSearchCriteria;
import com.vertek.corporate.qto.service.macd.MacdInterceptor;
import com.vertek.corporate.qto.service.macd.MultiMacdQueueHandler;
import com.vertek.corporate.qto.service.macd.MultiMacdRequestDto;
import com.vertek.corporate.qto.service.macd.request.MacdRequestDto;
import com.vertek.corporate.qto.service.multiedit.ServiceMultiEditQueueHandler;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jboss.resteasy.annotations.Form;

import jakarta.inject.Inject;
import jakarta.interceptor.Interceptors;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

/**
 * @author rcasey
 * @since 1/6/2023
 */
@Path("/services")
@Consumes("application/json")
@Produces("application/json")
public class ServiceResource extends AbstractResource<Service> {

    /** Business methods associated with Services. */
    @Inject
    private ServiceManager manager;

    /** MultiEdit Message Queue Handler. */
    @Inject
    private ServiceMultiEditQueueHandler multiEditQueueHandler;

    /** Multi MACD Message Queue Handler. */
    @Inject
    private MultiMacdQueueHandler multiMacdQueueHandler;

    /**
     * Retrieves all Services matching the given criteria.
     * @param criteria the criteria to filter by.
     * @return matching services.
     */
    @GET
    @RequiresPermissions(Permissions.ORDER_READ)
    public Response getServices(@Form final ServiceSearchCriteria criteria) {
        PaginatedResult<Service> result = manager.findBySearchCriteria(criteria);
        return getCollectionResource(result, criteria, getLocation(ServiceResource.class));
    }

    /**
     * Given a collection of service IDs and fields to process, loops through the services and makes the appropriate
     * changes/additions.
     * @param multiEditRequest the request.
     * @return a response indicating any failures.
     */
    @POST
    @Path("/multiEdit")
    @RequiresPermissions(Permissions.ORDER_WRITE)
    public Response multiEdit(final MultiEditRequestDto multiEditRequest) {
        try {
            multiEditQueueHandler.sendMessageToQueue(multiEditRequest);
            return Response.accepted().build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    /**
     * Created a macd service based on the given request.
     * @param macdRequestDto the request.
     * @return the created macd service.
     */
    @POST
    @Path("/macd")
    @RequiresPermissions(Permissions.INVENTORY_WRITE)
    @Interceptors({MacdInterceptor.class})
    public Response createMacd(final MacdRequestDto macdRequestDto ) {
        try {
            Service macd = manager.createMacd(macdRequestDto);
            return Response.ok(macd).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    /**
     * Submits a multi macd request based on the given request.
     * @param multiMacdRequestDto the request.
     * @return a response indicating any failures.
     */
    @POST
    @Path("/multiMacd")
    @RequiresPermissions(Permissions.INVENTORY_WRITE)
    @Interceptors({MacdInterceptor.class})
    public Response createMacd(final MultiMacdRequestDto multiMacdRequestDto ) {
        try {
            multiMacdQueueHandler.sendMessageToQueue(multiMacdRequestDto);
            return Response.accepted().build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }

    /**
     * Retrieves the inventory location for the given service.
     * @param serviceId the service ID.
     * @return the inventory location.
     */
    @GET
    @Path("/{id: \\d+}/inventoryLocation")
    @RequiresPermissions(Permissions.INVENTORY_READ)
    public Response getInventoryLocation(@PathParam("id") final Long serviceId) {
        //retrieve the inventory location for the service and return it
        Service service = manager.retrieve(serviceId);
        Location inventoryLocation = manager.getInventoryLocation(service);
        return Response.ok(inventoryLocation).build();
    }

    /**
     * Retrieves the open related MACDs for the given service. Should be no more than 2 services.
     * @param serviceId the service ID.
     * @return the related MACDs.
     */
    @GET
    @Path("/{id: \\d+}/relatedMacds")
    @RequiresPermissions(Permissions.ORDER_READ)
    public Response getOpenRelatedMacds(@PathParam("id") final Long serviceId) {
        return Response.ok(manager.findOpenByInventoryId(serviceId)).build();
    }
}
