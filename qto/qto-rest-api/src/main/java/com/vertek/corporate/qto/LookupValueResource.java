package com.vertek.corporate.qto;

import com.vertek.corporate.qto.common.lookup.LookupValue;
import com.vertek.corporate.qto.common.lookup.LookupValueManager;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

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
