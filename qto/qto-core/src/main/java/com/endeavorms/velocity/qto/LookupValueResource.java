package com.endeavorms.velocity.qto;

import com.endeavorms.velocity.qto.common.lookup.LookupValue;
import com.endeavorms.velocity.qto.common.lookup.LookupValueManager;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

@Component
@RestController
@RequestMapping("/api/lookupValues")
public class LookupValueResource extends AbstractLookupValueResource<LookupValue> {

    @Autowired
    private LookupValueManager manager;

    @Override
    protected LookupValueManager getManager() {
        return manager;
    }

    @Override
    protected String getResourcePath() {
        return "/lookupValues";
    }
}
