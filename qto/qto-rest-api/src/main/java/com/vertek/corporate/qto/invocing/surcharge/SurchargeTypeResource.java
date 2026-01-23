package com.vertek.corporate.qto.invocing.surcharge;

import com.vertek.corporate.qto.common.AbstractResource;
import com.vertek.corporate.qto.common.PaginatedResult;
import com.vertek.corporate.qto.invoicing.surchargeType.SurchargeType;
import com.vertek.corporate.qto.invoicing.surchargeType.SurchargeTypeManager;
import com.vertek.corporate.qto.invoicing.surchargeType.SurchargeTypeSearchCriteria;
import org.jboss.resteasy.annotations.Form;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

/**
 * Resource for surcharge types.
 */
@Path("/surchargeTypes")
@Consumes("application/json")
@Produces("application/json")
public class SurchargeTypeResource extends AbstractResource<SurchargeType> {
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
        return getCollectionResource(result, criteria, getLocation(SurchargeTypeResource.class));
    }
}
