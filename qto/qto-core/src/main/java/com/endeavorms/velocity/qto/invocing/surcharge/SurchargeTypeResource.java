package com.endeavorms.velocity.qto.invocing.surcharge;

import com.endeavorms.velocity.qto.common.AbstractResource;
import com.endeavorms.velocity.qto.common.PaginatedResult;
import com.endeavorms.velocity.qto.invoicing.surchargeType.SurchargeType;
import com.endeavorms.velocity.qto.invoicing.surchargeType.SurchargeTypeManager;
import com.endeavorms.velocity.qto.invoicing.surchargeType.SurchargeTypeSearchCriteria;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Resource for surcharge types.
 */
@RestController
@RequestMapping("/api/surchargeTypes")
public class SurchargeTypeResource extends AbstractResource<SurchargeType> {

    @Override
    protected String getResourcePath() {
        return "/surchargeTypes";
    }

    @Autowired
    private SurchargeTypeManager manager;

    @GetMapping
    public ResponseEntity<?> getSurchargeTypes(@ModelAttribute final SurchargeTypeSearchCriteria criteria) {
        PaginatedResult<SurchargeType> result = manager.findBySearchCriteria(criteria);
        return getCollectionResource(result, criteria, getLocation(SurchargeTypeResource.class));
    }
}
