package com.vertek.corporate.qto;


import com.vertek.corporate.qto.authentication.Permissions;
import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.multiedit.request.MultiEditRequestDto;
import com.vertek.corporate.qto.service.ServiceManager;
import com.vertek.corporate.qto.service.cyberView.ServiceCyberView;
import com.vertek.corporate.qto.service.cyberView.ServiceCyberViewManager;
import com.vertek.corporate.qto.service.cyberView.ServiceCyberViewSearchCriteria;
import com.vertek.corporate.qto.service.multiedit.ServiceMultiEditQueueHandler;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jboss.resteasy.annotations.Form;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/serviceCyberViews")
@Consumes("application/json")
@Produces({"application/json", "application/vnd.ms-excel"})

public class ServiceCyberViewResource  extends AbstractResource<ServiceCyberView> {
/** Logging Facade.*/
    protected static final Logger LOGGER = LoggerFactory.getLogger(ServiceCyberViewResource.class);

    @Inject
    private ServiceCyberViewManager manager;

    @Inject
    private ServiceManager serviceManager;

    /** MultiEdit Message Queue Handler. */
    @Inject
    private ServiceMultiEditQueueHandler multiEditQueueHandler;

    @GET
    @RequiresPermissions(Permissions.ORDER_READ)
    public Response getServiceCyberViews(@Form final ServiceCyberViewSearchCriteria criteria) {
        ServiceCyberViewSearchCriteria crit = getExportCriteria(criteria);
        if (crit.getFields() != null && crit.getFields().contains("address")) {
            crit.setFields(crit.getFields().replace("address", "address1,address2,city,stateProvince,postalCode"));
            crit.setHeaders(crit.getHeaders().replace("Address", "Address 1,Address 2,City,State/Province,Postal Code"));
        }
        PaginatedResult<ServiceCyberView> result = manager.findBySearchCriteria(crit);
        return getCollectionResource(result, crit, getLocation(ServiceViewResource.class));
    }

    @PUT
    @Path("/{id: \\d+}")
    @RequiresPermissions(Permissions.ORDER_WRITE)
    public Response edit(@PathParam("id") final Long id, final ServiceCyberView serviceView) {
        try {
            if (!id.equals(serviceView.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            ServiceCyberView updated = manager.edit(serviceView);
            return Response.ok(updated).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @GET
    @RequiresPermissions(Permissions.ORDER_WRITE)
    @Path("/link")
    public Response getServiceViewsForLink(@Form final ServiceCyberViewSearchCriteria criteria) {
        PaginatedResult<ServiceCyberView> result = manager.getServiceViewsForLink(criteria);
        return getCollectionResource(result, criteria, getLocation(ServiceViewResource.class));
    }

    @GET
    @RequiresPermissions(Permissions.ORDER_WRITE)
    @Path("/bundle")
    public Response getServiceViewsForBundle(@Form final ServiceCyberViewSearchCriteria criteria) {
        PaginatedResult<ServiceCyberView> result = manager.getServiceViewsForBundle(criteria);
        return getCollectionResource(result, criteria, getLocation(ServiceViewResource.class));
    }

    @PUT
    @RequiresPermissions(Permissions.ORDER_WRITE)
    @Path("/link")
    public Response link(@QueryParam("incomingServiceId") final Long incomingServiceId,
                         @QueryParam("selectedItems") final String selectedItems,
                         @QueryParam("linkType") final String linkType) {
        serviceManager.link(incomingServiceId, selectedItems, linkType);
        return Response.ok().build();
    }

    /**
     * Given a collection of Cyber IDs and fields to process, loops through the servics and makes the appropriate
     * changes/additions.
     * @param multiEditRequest the request.
     * @return a response indicating any failures.
     */
    @POST
    @Path("/multiEdit")
    @RequiresPermissions(value = {
            Permissions.ORDER_WRITE})
    public Response multiEdit(final MultiEditRequestDto multiEditRequest) {
        try {
            multiEditQueueHandler.sendMessageToQueue(multiEditRequest);
            return Response.accepted().build();
        } catch (Exception e) {
            return Response.serverError().build();
        }
    }
}
