package com.vertek.corporate.qto;

import com.vertek.corporate.qto.common.lookup.LookupValue;
import com.vertek.corporate.qto.common.lookup.LookupValueManager;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Stateless
@Path("/lookupValues")
@Consumes("application/json")
@Produces("application/json")
public class LookupValueResource extends AbstractLookupValueResource<LookupValue> {

    /** Business logic for manage lookupvalue. */
    @Inject
    private LookupValueManager manager;

    @Override
    protected LookupValueManager getManager() {
        return manager;
    }

}
