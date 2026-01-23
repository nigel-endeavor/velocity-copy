package com.vertek.corporate.qto.invocing.surcharge;

import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.common.PreconditionsUtil;
import com.vertek.corporate.qto.interceptors.SurchargeInterceptor;
import com.vertek.corporate.qto.invoicing.surcharge.service.ServiceSurcharge;
import com.vertek.corporate.qto.invoicing.surcharge.service.ServiceSurchargeManager;
import com.vertek.corporate.qto.invoicing.surcharge.service.ServiceSurchargeSearchCriteria;
import org.jboss.resteasy.annotations.Form;

import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * @author fcurran
 * @since 2023-07-12
 */
@Path("/serviceSurcharges")
@Consumes("application/json")
@Produces("application/json")
public class ServiceSurchargeResource extends AbstractResource<ServiceSurcharge> {

    /** Business layer for Surcharges. */
    @Inject
    private ServiceSurchargeManager manager;

    /**
     * Returns service surcharges that match the provided search criteria. A service ID is required.
     * @param criteria what to match service surcharges on.
     * @return the matching service surcharges, if any.
     */
    @GET
    public Response getServiceSurcharges(@Form final ServiceSurchargeSearchCriteria criteria) {
        PreconditionsUtil.checkArgument(criteria.getServiceId(), "A serviceId is required");
        PaginatedResult<ServiceSurcharge> result = manager.findBySearchCriteria(criteria);
        return getCollectionResource(result, criteria, getLocation(ServiceSurchargeResource.class));
    }

    /**
     * Attempts to persist the provided service surcharge.
     * @param serviceSurcharge the surcharge to persist.
     * @return the persisted surcharge.
     */
    @POST
    public Response create(final ServiceSurcharge serviceSurcharge) {
        try {
            PreconditionsUtil.checkArgument(serviceSurcharge.getServiceId(), "A serviceId is required");
            ServiceSurcharge createServiceSurcharge = manager.create(serviceSurcharge);
            return Response.ok(createServiceSurcharge).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    /**
     * Attempts to update an existing service surcharge.
     * @param id the ID of the surcharge to update.
     * @param serviceSurcharge the surcharge with its updates.
     * @return the updated surcharge.
     */
    @PUT
    @Path("/{id: \\d+}")
    @Interceptors({SurchargeInterceptor.class})
    public Response edit(@PathParam("id") final Long id, final ServiceSurcharge serviceSurcharge) {
        try {
            if (!id.equals(serviceSurcharge.getId())) {
                throw new IllegalArgumentException("identifier in path does not match that of passed entity");
            }
            PreconditionsUtil.checkArgument(serviceSurcharge.getServiceId(), "A serviceId is required");
            ServiceSurcharge updatedServiceSurcharge = manager.edit(serviceSurcharge);
            return Response.ok(updatedServiceSurcharge).build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    /**
     * Deletes a resource.
     * @param id resource identifier.
     * @return a Response.
     */
    @DELETE
    @Path("/{id : \\d+}")
    @Interceptors({SurchargeInterceptor.class})
    public Response remove(@PathParam("id") final Long id) {
        manager.remove(id);
        return Response.noContent().build();
    }
}
