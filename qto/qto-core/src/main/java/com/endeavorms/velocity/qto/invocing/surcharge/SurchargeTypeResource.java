package com.endeavorms.velocity.qto.invocing.surcharge;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.invoicing.surchargeType.SurchargeType;
import com.endeavorms.velocity.qto.invoicing.surchargeType.SurchargeTypeManager;
import com.endeavorms.velocity.qto.invoicing.surchargeType.SurchargeTypeSearchCriteria;
import org.jboss.resteasy.annotations.Form;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;

/**
 * Resource for surcharge types.
 */
@Path("/surchargeTypes")
@Consumes("application/json")
@Produces("application/json")
public class SurchargeTypeResource extends AbstractResource<SurchargeType> {

    @Override
    protected String getResourcePath() {
        return "/surchargeTypes";
    }
    /** Business layer for surcharge types. */
    @Inject
    private SurchargeTypeManager manager;

    /**
     * Returns surcharge types that match the provided search criteria. A service ID is required.
     * @param criteria what to match surcharge types on.
     * @return the matching surcharge types, if any.
     */
    @GET
    public Response getSurchargeTypes(@Form final SurchargeTypeSearchCriteria criteria) {
        PaginatedResult<SurchargeType> result = manager.findBySearchCriteria(criteria);
        return toResponse(getCollectionResource(result, criteria, getLocation(SurchargeTypeResource.class)));
    }
}
